package api;

import org.testng.annotations.DataProvider;

public class VehicleTypeDataProvider {

    @DataProvider(name = "validVehicleTypes" )
    public static Object[][] getValidVehicleTypes(){
        return new Object[][] {{"carros", 103},{"motos", 98},{"caminhoes", 29}};
    }

}
