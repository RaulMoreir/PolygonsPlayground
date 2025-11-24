package api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

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
    }

    @Test
    private void a(){
        String url = "https://www.claudiokussleiloes.com.br";
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebElement closeAnnoyingTab = driver.findElement(By.xpath("//button[text()='Fechar']"));
        closeAnnoyingTab.click();

        WebElement botao = driver.findElement(By.xpath("//*[@id=\"slider_home\"]/div/div/div/div/div[1]/div/a"));
        botao.click();


    };


}

