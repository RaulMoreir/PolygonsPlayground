package main.FIPE;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.pojo.BrandsEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OldFIPEProgram {

    private static final String URL ="https://www.claudiokussleiloes.com.br";

    private WebDriver driver;

    public void run() {
        //setup();
        //collectDataAndGenerateReport();
        getModelID();
        //driver.quit();
    }

    private void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        WebElement closeAnnoyingTab = driver.findElement(By.xpath("//button[text()='Fechar']"));
        closeAnnoyingTab.click();
    }

    private void getModelID(){
        ObjectMapper Mapper = new ObjectMapper();
        Path packageModel = Paths.get("src/main/java/main/FIPE/JsonModels");

        try (BufferedReader br = Files.newBufferedReader(Paths.get("src/test/resources/Report.csv"))){
        String line;
        while ((line = br.readLine()) !=null){
            String[] columns = line.split(",");
                String brandCsv = columns[0].trim();

                //Brand pelo enum
                BrandsEnum brand = BrandsEnum.fromString(brandCsv);
                if (brand == null || brand == BrandsEnum.UNDEFINED) {
                    continue;}


                String fileName = brand.name().toLowerCase() + ".json";

                Path jsonBrand = packageModel.resolve(fileName);

                if (!Files.exists(jsonBrand)) {
                    continue;
                }

                JsonNode root = Mapper.readTree(Files.readString(jsonBrand));
                JsonNode modelsNode = root.get("Models");

                String modelCsv = columns[1].trim();
                    String[] modelsCsvs = modelCsv.split(" ");

                List<String> potentialModels = new ArrayList<>();
                for(JsonNode node : modelsNode){
                    String modelName = node.get("name").asText();

                    boolean hasAll = Arrays.stream(modelsCsvs)
                            .filter(s -> !s.isEmpty())
                            .allMatch(modelName::contains);

                    if(hasAll){
                        String code = node.get("code").asText();
                        String FullName = node.get("name").asText();

                        String possibleModelsLine = code + " , " + FullName;
                        potentialModels.add(possibleModelsLine);
                        System.out.println(possibleModelsLine);
                    }
                }
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void collectDataAndGenerateReport() {
        final String Path = "C:\\Users\\raula\\IdeaProjects\\untitled\\src\\test\\resources\\Report.csv";

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

        File csvFile = new File(Path);

        try (PrintWriter writer = new PrintWriter(csvFile)) {
            // Escrevendo cabeçalho
            writer.println("Marca,Modelo,Ano,Combustível,Lance,Taxa Adm,Valor_FIPE,BrandID");

            for(int i  = 1;  i < quantidadeVeiculos ; i++){
                String runningCar = "https://www.claudiokussleiloes.com.br/lance/"+auctionNumber[4]+"/"+i;
                driver.get(runningCar);

                //valor do carro
                WebElement GetCarName = driver.findElement(By.tagName("h1"));
                String car = GetCarName.getText();


                if(car.contains(" R E T I R A D O") || !car.contains("/")) {
                    continue;
                } else if(car.contains("LOTE")) {
                    String[] dividedCarName = car.split(" - ");
                    car = dividedCarName[1];
                }

                String[] brandAndModel = car.split("/");
                String brand = brandAndModel[0];
                String model = brandAndModel[1];

                //ano do carro
                WebElement GetCarYear = driver.findElement(By.xpath("//span[@name='tblAno']"));
                String year = GetCarYear.getText();

                //Combustível do carro
                WebElement GetCarFuel = driver.findElement(By.xpath("//span[@name='tblComb']"));
                String Fuel = GetCarFuel.getText();

                //valor do lance
                WebElement GetCarLance = driver.findElement(By.xpath("//div[@class='col-md-7 dados_veic']//strong[@name='topLance']"));
                String Lance = GetCarLance.getText();
                String[] dividedCarLance = Lance.split(",");

                //taxa administrativa
                WebElement GetCarTaxa = driver.findElement(By.xpath("//span[@name='tblDespAdm']"));
                String Taxa = GetCarTaxa.getText();
                String[] dividedCarTaxa = Taxa.split(",");

                //for now, FIPE value will be zero. needs APi implementation
                String FIPE = "null";

                BrandsEnum brandsEnum = BrandsEnum.fromString(brand);

                writer.println(brand + "," + model + "," + year + "," + dividedCarLance[0] + "," + dividedCarTaxa[0] + "," + FIPE + "," + brandsEnum.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

