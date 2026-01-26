package main.FIPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import main.FIPE.pojo.BrandsEnum;
import main.FIPE.pojo.CarData;
import main.FIPE.pojo.GenericItem;
import main.FIPE.pojo.ModelData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static main.FIPE.cache.APICache.cacheSearchYearsByBrandAndModel;

public class NewFIPE {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String URL = "https://www.claudiokussleiloes.com.br";
    private WebDriver driver;

    public void run() {
        setup();
        List<CarData> carsExtracted = collectData();
        driver.quit();
        carsExtracted.forEach(carData -> {
            System.out.println("Verificando dados da FIPE...");
            try {
                carregarPossiveisModelos(carData);
            } catch (IOException | InterruptedException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        WebElement closeAnnoyingTab = driver.findElement(By.xpath("//button[text()='Fechar']"));
        closeAnnoyingTab.click();
    }

    private List<CarData> collectData() {
        List<WebElement> openAuctions = driver.findElements(By.xpath("//*[@class='desc']"));

        WebElement enterAuctionButton = null;

        for (int i = 0; i < openAuctions.size(); i++) {
            WebElement auction = openAuctions.get(i);
            WebElement auctionType = auction.findElement(By.className("local"));
            if (auctionType.getText().equals("Veículos - Financeiras")) {
                enterAuctionButton = auction.findElement(By.tagName("a"));
                break;
            }
        }

        if (Objects.nonNull(enterAuctionButton)) {
            enterAuctionButton.click();
        } else {
            throw new AssertionError("There is no open auctions to validate.");
        }

        //ver em lista
        driver.findElement(By.xpath("//*[contains(text(), 'em lista')]")).click();

        String[] auctionNumber = Objects.requireNonNull(driver.getCurrentUrl()).split("/");

        // Escrevendo cabeçalho
        //("Marca,Modelo,Ano,Combustível,Lance,Taxa Adm");
        List<CarData> carsFromAuctionSite = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            CarData car = new CarData();
            String runningCar = "https://www.claudiokussleiloes.com.br/lance/" + auctionNumber[4] + "/" + i;
            driver.get(runningCar);

            //valor do carro
            WebElement GetCarName = driver.findElement(By.tagName("h1"));
            car.setModel(GetCarName.getText());


            if (car.getModel().contains(" R E T I R A D O") || !car.getModel().contains("/")) {
                continue;
            } else if (car.getModel().contains("LOTE")) {
                String[] dividedCarName = car.getModel().split(" - ");
                car.setModel(dividedCarName[1]);
            }

            String[] brandAndModel = car.getModel().split("/");
            car.setBrand(brandAndModel[0]);
            car.setModel(brandAndModel[1]);

            car.setBrandEnum(BrandsEnum.fromString(brandAndModel[0]));

            //ano do carro
            WebElement GetCarYear = driver.findElement(By.xpath("//span[@name='tblAno']"));

            String[] splitedGetCarYear = GetCarYear.getText().split("/");
            String year = splitedGetCarYear[0];

            if(year.startsWith("9")){
            car.setModelYear(String.valueOf(Integer.parseInt(year) + 1900));
            }else car.setModelYear(String.valueOf(Integer.parseInt(year) + 2000));


            //Combustível do carro
            WebElement GetCarFuel = driver.findElement(By.xpath("//span[@name='tblComb']"));
            car.setFuel(GetCarFuel.getText());

            //valor do lance
            WebElement GetCarLance = driver.findElement(By.xpath("//div[@class='col-md-7 dados_veic']//strong[@name='topLance']"));
            if (GetCarLance.getText().contains(",") || GetCarLance.getText().contains(" ")) {

                String[] formatedGetCarLance = GetCarLance.getText().split(" ");
                String[] dividedCarLance = formatedGetCarLance[1].split(",");

                car.setLance(dividedCarLance[0]);
            } else {
                car.setLance(GetCarLance.getText());
            }

            //taxa administrativa
            WebElement GetCarTaxa = driver.findElement(By.xpath("//span[@name='tblDespAdm']"));
            if (GetCarTaxa.getText().contains(",")) {
                String[] dividedCarTaxa = GetCarTaxa.getText().split(",");
                car.setTaxaAdm(dividedCarTaxa[0]);
            } else{
                car.setTaxaAdm(GetCarTaxa.getText());
            }

            carsFromAuctionSite.add(car);
        }

        return carsFromAuctionSite;
    }

//    private void writingCSVFileWithInformation(String carInformation) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            if (carInformation != null && !carInformation.trim().isEmpty()) {
//                CarData data = mapper.readValue(carInformation, CarData.class);
//
//                try(Writer writer= new FileWriter("Report.csv",true)) {
//                    StatefulBeanToCsv<CarData> toCsv =
//                            new StatefulBeanToCsvBuilder<CarData>(writer).build();
//                    toCsv.write(data);
//
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Erro no JSON: " + carInformation);
//            e.printStackTrace();
//        } /
//    }

    private void carregarPossiveisModelos(CarData carData) throws IOException, InterruptedException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        ModelData modelData = getModelsFromBrand(carData.getBrandEnum());

        List<GenericItem> filteredOptionsByModelName =
                filterModelsProgressivelyByName(
                        modelData.getModels(),
                        carData.getModel());

        String modelYearsFromCache = null;

        for (GenericItem modelo : filteredOptionsByModelName) {
            String modelCode = modelo.getCode();
            modelYearsFromCache = cacheSearchYearsByBrandAndModel(carData.getBrandEnum().getId(), Integer.parseInt(modelCode),carData.getModelYear());

            String possibleCarsFromApi = getCarInformation(carData.getBrandEnum().getId(),modelCode,modelYearsFromCache);

            //writingCSVFileWithInformation(possibleCars);
        }
        //System.out.println(savedcarsTobigString);
    }

    private ModelData getModelsFromBrand(BrandsEnum brandsEnum) throws IOException {
        if (brandsEnum == null || brandsEnum == BrandsEnum.UNDEFINED) {
            return null;
        }
        Path packageModel = Paths.get("src/main/resources/JsonModels/");
        String fileName = brandsEnum.getId() + ".json";
        Path jsonBrandFilePath = packageModel.resolve(fileName);
        if (!Files.exists(jsonBrandFilePath)) {
            return null;
        }
        return mapper.readValue(jsonBrandFilePath.toFile(), ModelData.class);
    }

    private String textFormater(String textFromAuctionSites){
        //Formata o nome do onix
        if (textFromAuctionSites.contains("ONIX")){
            textFromAuctionSites = textFromAuctionSites
                    .replace("10", "1.0")
                    .replace("LT1", "LT")
                    .replace("MT","Mec.");
        }
        //Formata nome do Jac
        if (textFromAuctionSites.contains("JS1")){
            textFromAuctionSites = textFromAuctionSites
                    .replaceAll("\\s+","-");
        }
        return textFromAuctionSites;
    }

    private List<GenericItem> filterModelsProgressivelyByName(List<GenericItem> modelsFromAPI, String textFromAuctionSite){
        //devolve as possibilidades de modelos "Fox Trendline 1.6 Flex 8V 5p" codigo "6940"

        textFromAuctionSite = textFormater(textFromAuctionSite);

        String[] splitedAuctionString = textFromAuctionSite.split("\\s+");

        List<GenericItem> actual = modelsFromAPI;

        for (String words : splitedAuctionString){

            if(actual.size() == 1){
                return actual;
            }

            String w = words.toLowerCase();

            List<GenericItem> filteredCarPossibilities = actual.stream()
                    .filter(m -> {
                        String[] tokens = m.getName().toLowerCase().split("\\s+");
                        return Arrays.asList(tokens).contains(w);
                    }).toList();


            if (filteredCarPossibilities.isEmpty()) {
                continue;
            }
            actual = filteredCarPossibilities;
        }
        return actual;
    }

    private static String getCarInformation(int brandCode, String modelCode, String carYear) throws IOException, InterruptedException {
        if (carYear == null){
            return null;
        }

        Thread.sleep(1000);
        String url ="https://fipe.parallelum.com.br/api/v2/cars/brands/" + brandCode
                + "/models/" + modelCode + "/years/" + carYear.trim();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

