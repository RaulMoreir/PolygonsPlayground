package testFipeProgram;

import main.FIPE.services.JsonServices.FullCarInformationFromAPII;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class FullCarInformationFromAPITest {

    @Test
    public void testDateMethod(){
        FullCarInformationFromAPII api = new FullCarInformationFromAPII();

        assertEquals(api.checkWhichMonth(), "05");

    }

    @Test
    public void testGetFullCarInformationC3() throws IOException, InterruptedException {
        int BrandCode = 13;
        String modelCode = "6776";
        String carYear = "2015-5";

        String cars = new FullCarInformationFromAPII().getFullCarInformation(BrandCode, modelCode,carYear);

        System.out.println(cars);
        assertNotNull(cars);
    }

    @Test
    public void testGetFullCarInformation() throws IOException, InterruptedException {
        //"Uno Mille WAY ECONOMY 1.0 F.Flex 4p";
        int BrandCode = 21;
        String modelCode = "4766";
        String carYear = "2010-5";

        String cars = new FullCarInformationFromAPII().getFullCarInformation(BrandCode, modelCode,carYear);

        assertNotNull(cars);
    }

    @Test
    public void testGetFullCarInformationReturnsNull() throws IOException, InterruptedException {
        //"Uno Mille WAY ECONOMY 1.0 F.Flex 4p";
        FullCarInformationFromAPII serv = new FullCarInformationFromAPII();

        int BrandCode = 21;
        String modelCode = "4766";
        String carYear = null;

        String cars = serv.getFullCarInformation(BrandCode, modelCode,carYear);

        assertNull(cars);
    }

    @Test
    public void testGetFullCarInformationWithNoParameters() throws IOException, InterruptedException {
        //"Uno Mille WAY ECONOMY 1.0 F.Flex 4p";
        FullCarInformationFromAPII serv = new FullCarInformationFromAPII();

        int BrandCode = 21;
        String modelCode = "4766";
        String carYear = "";

        String cars = serv.getFullCarInformation(BrandCode, modelCode,carYear);

        assertNull(cars);
    }

}