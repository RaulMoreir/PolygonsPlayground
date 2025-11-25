package api;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Selleniums {
    @Test

    private void initialize () {
        String url = "https://www.claudiokussleiloes.com.br";
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebElement closeAnnoyingTab = driver.findElement(By.xpath("//button[text()='Fechar']"));
        closeAnnoyingTab.click();

        WebElement botao = driver.findElement(By.xpath("//*[@id=\"slider_home\"]/div/div/div/div/div[1]/div/a"));
        botao.click();
        String newURL = driver.getCurrentUrl();
        Assert.assertEquals(newURL, "https://www.claudiokussleiloes.com.br/relacao-foto/845");
        driver.quit();
    }

    @Test
    private void a() {
        String url = "https://www.claudiokussleiloes.com.br";
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebElement closeAnnoyingTab = driver.findElement(By.xpath("//button[text()='Fechar']"));
        closeAnnoyingTab.click();

        WebElement botao = driver.findElement(By.xpath("//*[@id=\"slider_home\"]/div/div/div/div/div[1]/div/a"));
        botao.click();

        WebElement getUrlList = driver.findElement(By.xpath("//a[contains(text(),'Ver em formato em lista')]"));
        String listUrl = getUrlList.getAttribute("href");
        driver.get(listUrl);

        List<WebElement> linhas = driver.findElements(By.cssSelector("table tr"));
        int quantidadeVeiculos = linhas.size();

        for(int i  = 1;  i < quantidadeVeiculos; i++){
            String runningCar = "https://www.claudiokussleiloes.com.br/lance/845/"+i;
            driver.get(runningCar);

            WebElement GetCarName = driver.findElement(By.tagName("h1"));
            String car = GetCarName.getText();
            System.out.println(car);
        }

        driver.quit();


    }
}

