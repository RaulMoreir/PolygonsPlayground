package main.FIPE.services;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.CarData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuctionScraperService {
    private WebDriver driver;
    private static final String URL = "https://www.claudiokussleiloes.com.br";

    public List<CarData> scrapeAuctionCars() {
        setup();
        try {
            return collectData();
        } finally {
            driver.quit();
        }
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

        //Nao apague tableRows mostra quantos carros tem pra percorrer ate o fim
        // List<WebElement> tableRows = driver.findElements(By.xpath("//tr[@id='tabela']"));

        String[] auctionNumber = Objects.requireNonNull(driver.getCurrentUrl()).split("/");

        // Escrevendo cabeçalho
        //("Marca,Modelo,Ano,Combustível,Lance,Taxa Adm");
        List<CarData> carsFromAuctionSite = new ArrayList<>();

        // quantidade de carros para pegar de amostra = 4
        for (int i = 1; i <= 10; i++) {
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

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        /* SetTaxa
        SetLance
        SetFuel
        SetModelYear
        SetModel
        SetBrandEnum
        Esses são setados no auction scrapper */
        return carsFromAuctionSite;
    }

}
