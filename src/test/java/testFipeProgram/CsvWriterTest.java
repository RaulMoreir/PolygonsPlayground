package testFipeProgram;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.CarData;
import main.FIPE.models.CarMatchReport;
import main.FIPE.models.FipeResponse;
import main.FIPE.services.CsvWriter;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class CsvWriterTest {

    /*
    private void setCarData(CarData carData) {
        this.carData = carData;
    }

    private void setFipeResponse(FipeResponse response) {
        this.response = response;
    }*/

    //o que preciso mockar?
    /*
    * Response e cardata
    * getVehicleType
    * getPrice
    * getBrand
    * getModel
    * getModelYear
    * getFuel
    * getCodeFipe
    * getReferenceMonth
    * getFuelAcronym
    *
    *
    * */


    @Test
    public void testWriteAuctionCarOnCsv() {
    }

    @Test
    public void testWriteCarReport() throws IOException {
        //arrange
        CarData cardata = mock(CarData.class);
        FipeResponse response = mock(FipeResponse.class);

        when(cardata.getModel()).thenReturn("Sonic Ltz");
        when(cardata.getLance()).thenReturn("1200");
        when(cardata.getBrandEnum()).thenReturn(BrandsEnum.CHEVROLET);
        when(cardata.getModelYear()).thenReturn("2015");
        when(cardata.getFuel()).thenReturn("G");

        when(response.getVehicleType()).thenReturn("1");
        when(response.getPrice()).thenReturn("R$ 30.000");
        when(response.getBrand()).thenReturn("Chevrolet");
        when(response.getModel()).thenReturn("Sonic Ltz");
        when(response.getModelYear()).thenReturn("2015");
        when(response.getFuel()).thenReturn("G");
        when(response.getCodeFipe()).thenReturn("12332-0");
        when(response.getReferenceMonth()).thenReturn("abril");
        when(response.getFuelAcronym()).thenReturn("G");
        List<FipeResponse> fipeResponseList = new ArrayList<>();
        fipeResponseList.add(response);
        //act
        CsvWriter writer = new CsvWriter(System.currentTimeMillis()*1000 +"Report.csv");
        CarMatchReport carMatchReport = new CarMatchReport(cardata,fipeResponseList);
        writer.writeCarReport(carMatchReport);

        //assert

    }

    @Test
    public void testWriteFipeResponseCarOnCsv() {
    }

    @Test
    public void testClose() {
    }
}