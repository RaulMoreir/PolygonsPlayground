package main.FIPE.testFipeProgram;

import main.FIPE.models.FipeResponse;
import main.FIPE.services.JsonServices.FullCarInformationFromAPI;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class FullCarInformationFromAPITest {

    @Test
    public void testDateMethod(){
        FullCarInformationFromAPI api = new FullCarInformationFromAPI();


        assertEquals(api.checkWhichMonth(), "08");

    }

    @Test
    public void testGetFullCarInformationC3() throws IOException, InterruptedException {
        int BrandCode = 13;
        String modelCode = "6776";
        String carYear = "2015-5";

        FipeResponse cars = new FullCarInformationFromAPI().getFullCarInformation(BrandCode, modelCode,carYear);

        System.out.println(cars);
        assertNotNull(cars);
    }

    @Test
    public void testGetFullCarInformation() throws IOException, InterruptedException {
        //"Uno Mille WAY ECONOMY 1.0 F.Flex 4p";
        int BrandCode = 21;
        String modelCode = "4766";
        String carYear = "2010-5";

        FipeResponse cars = new FullCarInformationFromAPI().getFullCarInformation(BrandCode, modelCode,carYear);

        assertNotNull(cars);
    }

    @Test
    public void testGetFullCarInformationReturnsNull() throws IOException, InterruptedException {
        //"Uno Mille WAY ECONOMY 1.0 F.Flex 4p";
        FullCarInformationFromAPI serv = new FullCarInformationFromAPI();

        int BrandCode = 21;
        String modelCode = "4766";
        String carYear = null;

        FipeResponse cars = serv.getFullCarInformation(BrandCode, modelCode,carYear);

        assertNull(cars);
    }

    @Test
    public void testGetFullCarInformationWithNoParameters() throws IOException, InterruptedException {
        //"Uno Mille WAY ECONOMY 1.0 F.Flex 4p";
        FullCarInformationFromAPI serv = new FullCarInformationFromAPI();

        int BrandCode = 21;
        String modelCode = "4766";
        String carYear = "";

        FipeResponse cars = serv.getFullCarInformation(BrandCode, modelCode,carYear);

        assertNull(cars);
    }

}