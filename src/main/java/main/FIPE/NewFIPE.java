package main.FIPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.pojo.BrandsEnum;
import main.FIPE.pojo.CarData;
import main.FIPE.pojo.GenericItem;
import main.FIPE.pojo.ModelData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
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
            } catch (IOException | InterruptedException e) {
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

        List<WebElement> linhas = driver.findElements(By.cssSelector("table tr"));
        int quantidadeVeiculos = linhas.size();


        // Escrevendo cabeçalho
        //("Marca,Modelo,Ano,Combustível,Lance,Taxa Adm");
        List<CarData> carsFromAuctionSite = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            CarData car = new CarData();
            String runningCar = "https://www.claudiokussleiloes.com.br/lance/" + auctionNumber[4] + "/" + i;
            driver.get(runningCar);

            //valor do carro
            WebElement GetCarName = driver.findElement(By.tagName("h1"));
            car.setModelo(GetCarName.getText());


            if (car.getModelo().contains(" R E T I R A D O") || !car.getModelo().contains("/")) {
                continue;
            } else if (car.getModelo().contains("LOTE")) {
                String[] dividedCarName = car.getModelo().split(" - ");
                car.setModelo(dividedCarName[1]);
            }

            String[] brandAndModel = car.getModelo().split("/");
            car.setMarca(brandAndModel[0]);
            car.setModelo(brandAndModel[1]);

            car.setBrandEnum(BrandsEnum.fromString(brandAndModel[0]));

            //ano do carro
            WebElement GetCarYear = driver.findElement(By.xpath("//span[@name='tblAno']"));

            String[] splitedGetCarYear = GetCarYear.getText().split("/");
            String year = splitedGetCarYear[0];

            if(year.startsWith("9")){
            car.setAno(String.valueOf(Integer.parseInt(year) + 1900));
            }else car.setAno(String.valueOf(Integer.parseInt(year) + 2000));


            //Combustível do carro
            WebElement GetCarFuel = driver.findElement(By.xpath("//span[@name='tblComb']"));
            car.setCombustivel(GetCarFuel.getText());

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

    private void carregarPossiveisModelos(CarData carData) throws IOException, InterruptedException {
        ModelData modelData = getModelsFromBrand(carData.getBrandEnum());

        List<GenericItem> filteredOptionsByModelName =
                filterModelsProgressivelyByName(
                        modelData.getModels(),
                        carData.getModelo());

        String modelYearsFromCache = null;
        for (GenericItem modelo : filteredOptionsByModelName) {
            String modelCode = modelo.getCode();
            modelYearsFromCache = cacheSearchYearsByBrandAndModel(carData.getBrandEnum().getId(), Integer.parseInt(modelCode),carData.getAno());

            String a = getCarInformation(carData.getBrandEnum().getId(),modelCode,modelYearsFromCache);
            if (a == null){
                System.out.print("");
            }else {System.out.println(a);}

        }
    }

    private ModelData getModelsFromBrand(BrandsEnum brandsEnum) throws IOException {
        if (brandsEnum == null || brandsEnum == BrandsEnum.UNDEFINED) {
            return null;
        }
        Path packageModel = Paths.get("src/main/resources/JsonModels/");
        String fileName = brandsEnum.getId() + ".json";
        Path jsonBrandFilePath = packageModel.resolve(fileName);
        if (!Files.exists(jsonBrandFilePath)) {
            downloadJsonModelsFromAPI(brandsEnum);
            return null;
        }
        return mapper.readValue(jsonBrandFilePath.toFile(), ModelData.class);
    }

    private List<GenericItem> filterModelsProgressivelyByName(List<GenericItem> modelsFromAPI, String textFromAuctionSite){
        //devolve as possibilidades de modelos "Fox Trendline 1.6 Flex 8V 5p" codigo "6940"
        String[] splitedAuctionString = textFromAuctionSite.trim().split("\\s+");

        List<GenericItem> actual = modelsFromAPI;
        for (String words : splitedAuctionString){

            if(actual.size() == 1){
                return actual;
            }
            String w = words.toLowerCase();
            List<GenericItem> filteredCarPossibilities = actual.stream()
                    .filter(m -> m.getName().toLowerCase().contains(w))
                    .toList();
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

    private void checkFipePrice(CarData carData) {
        checkBrandId(carData);
        checkFipeModels(carData);
    }

    private void checkBrandId(CarData carData) {
        carData.setBrandEnum(BrandsEnum.valueOf(carData.getMarca()));
    }

    private void checkFipeModels(CarData carData) {
        BrandsEnum currentBrand = carData.getBrandEnum();
        //baseado no cardataenum. descubra qual eh o arquivo jsonmodels que tem q baixar.....


    }

    private void downloadJsonModelsFromAPI(BrandsEnum brandsEnum) {
        // executar uma chamada REST na API pra pegar a lista de modelos

        // escrever no arquivo em resources o conteudo baixado

    }



    }

