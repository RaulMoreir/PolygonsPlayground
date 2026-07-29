package main.FIPE.Apps;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.SearchModeEnum;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchServiceTest {

    public void testSearchCar(String marca, String modelo, String ano) throws IOException {
        boolean verify = true;
        SearchModeEnum mode = SearchModeEnum.SQLITE_DATABASE;

        SearchService searchService = new SearchService(mode);
        searchService.SearchCar(marca,modelo,ano);
        Path path = Paths.get("Pesquisa_"+ marca + "_" + modelo + "_" + ano +"_Report.csv");

        if(Files.exists(path)){
            Assert.assertTrue(verify);
        }else {
            Assert.assertFalse(verify);
        }
    }

    public void testSearchCarDB(String marca, String modelo, String ano) throws IOException {
        SearchModeEnum mode = SearchModeEnum.JSON;
        boolean verify = true;

        SearchService searchService = new SearchService(mode);
        searchService.SearchCar(marca,modelo,ano);
        Path path = Paths.get("Pesquisa_"+ marca + "_" + modelo + "_" + ano +"_Report.csv");

        if(Files.exists(path)){
            Assert.assertTrue(verify);
        }else {
            Assert.assertFalse(verify);
        }
    }

    @Test
    public void testNormalParametersDB() throws IOException {
        testSearchCarDB("chev","sonic","2014");
    }

    @Test
    public void testNormalParameters() throws IOException {
        testSearchCar("chev","sonic","2014");
    }

    @Test
    public void testNormalParametersAgain() throws IOException {
        testSearchCar("caoa","qq","2020");
    }

//    @Test
//    public void testBlankAno() throws IOException {
//        cenário impssível poois é obrigatório possuir um ano
//        testSearchCar("chev","sonic","");
//    }

    /*
    @Test
    public void testBlankMarca() throws IOException {
    //Esse teste retrata um cenário impossível, pois há uma obrigatoriedade de selecionar uma marca.
        testSearchCar("","sonic","2014");

    }*/
    /*
    Funciona, mas consome a api.
    Retorna todos os modelos de carros do ano de 2014
    @Test
    public void testBlankModelo() throws IOException {
        testSearchCar("chev","","2014");
    }*/
}