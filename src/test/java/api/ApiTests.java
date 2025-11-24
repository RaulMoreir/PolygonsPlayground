package api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;

public class ApiTests {

    static {
        baseURI = "https://brasilapi.com.br";
    }

    @Test(dataProviderClass = VehicleTypeDataProvider.class, dataProvider = "validVehicleTypes")
    public void parametrizedTests(String type, Integer expectedCount) {
        //bate na API
        Response response = get("/api/fipe/marcas/v1/"+type);

        //Valida o status code
        response.then().statusCode(200);

        //extrai o JSON como jsonPath
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map<String, ?>> listOfObjects = jsonPathEvaluator.getList("$");

        //Realiza a validacao do dado
        Assert.assertEquals(listOfObjects.size(), expectedCount);
    }

    @Test
    public void fourthTest(){
        //bate na API
        Response response = get("/api/fipe/marcas/v1/loremipsum");

        //Valida o status code
        response.then().statusCode(400);

        //extrai o JSON como jsonPath
        JsonPath jsonPathEvaluator = response.jsonPath();
        Map<String, Object> object = jsonPathEvaluator.get();

        //Realiza a validacao do dado
        Assert.assertTrue(Objects.nonNull(object));

    }
    @Test
    public void chevetteTest(){
        String FIPENumber = "004030-4";

        //bate na API
        Response response = get("/api/fipe/preco/v1/"+FIPENumber);

        //Valida o status code
        response.then().statusCode(200);

        //extrai o JSON como jsonPath
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map<String, ?>> listOfObjects = jsonPathEvaluator.getList("$");

        //Realiza a validacao do dado
//        System.out.println(listOfObjects);

        List<String> expectedPrices = List.of("R$ 20.560,00", "R$ 16.240,00", "R$ 15.843,00", "R$ 15.456,00", "R$ 15.079,00", "R$ 11.149,00", "R$ 8.698,00", "R$ 6.230,00", "R$ 5.560,00");
        List<String> actualPrices = new ArrayList<>();


        listOfObjects.forEach(item -> {
            System.out.println(item.get("valor"));
            actualPrices.add((String) item.get("valor"));
        });

        Assert.assertEquals(actualPrices, expectedPrices);

    }
    @Test
    public void asxTest(){
        String FIPENumber = "022115-5";
        Response response = get("/api/fipe/preco/v1/"+FIPENumber);

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map<String, ?>> listOfObjects = jsonPathEvaluator.getList("$");

        List<String> expectedFuel = List.of("Gasolina","Gasolina","Gasolina","Gasolina","Gasolina","Gasolina","Gasolina");
        List<String> actualFuel = new ArrayList<>();

        listOfObjects.forEach(item -> {
            //System.out.println(item.get("combustivel"));
            actualFuel.add((String) item.get("combustivel"));
        });
        Assert.assertEquals(actualFuel, expectedFuel);

    }


}

