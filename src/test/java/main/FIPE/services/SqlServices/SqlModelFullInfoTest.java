package main.FIPE.services.SqlServices;

import main.FIPE.models.FipeResponse;
import main.FIPE.services.ExternalFipeApiConsumer;
import main.FIPE.services.database.ConnectionFactory;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SqlModelFullInfoTest {
    //"code": "1848",
    //"name": "206 CC 1.6 16V 2p"
    //"code": "2008-1",
    //"name": "2008 Gasolina"
    @Mock
    private final SqlModelFullInfo sqlModelFullInfo = new SqlModelFullInfo();

    @Test
    private void InvalidYearReturningEmptyList(){
        List<FipeResponse> fipeResponseList = sqlModelFullInfo.searchInDatabase("1848","2008-1");

        Assert.assertEquals(fipeResponseList.size(),0,"should be 0");
    }
    @Test
    private void ReciveCarList() throws IOException, InterruptedException {
        SqlModelFullInfo sqlModelFullInfo1 = Mockito.spy(new SqlModelFullInfo());

        List<FipeResponse> fipeResponseList = new ArrayList<>();
        fipeResponseList.add(createCar());
        fipeResponseList.add(createCar());

        doReturn(fipeResponseList).when(sqlModelFullInfo1).searchInDatabase("123","2020-1");
        FipeResponse result =
                sqlModelFullInfo1.getFullCarInformation(10, "123", "2020-1");

        Assert.assertNotNull(result);

        Assert.assertEquals(result.getBrand(), "Honda");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenSearchFails() throws Exception {

        Connection connection = mock(Connection.class);

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        try (MockedStatic<ConnectionFactory> connectionMock = Mockito.mockStatic(ConnectionFactory.class)) {

            connectionMock.when(ConnectionFactory::getConnection).thenReturn(connection);

            SqlModelFullInfo service = new SqlModelFullInfo();

            service.searchInDatabase("123", "2020-1");
        }
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenInsertFails() throws Exception {

        Connection connection = mock(Connection.class);

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Expected Exeption"));

        try (MockedStatic<ConnectionFactory> connectionMock = Mockito.mockStatic(ConnectionFactory.class))
        {
            connectionMock.when( ConnectionFactory::getConnection).thenReturn(connection);

            SqlModelFullInfo.insertCarDetails(createCar(), 10, "123", "2020-1");
        }
    }

    private FipeResponse createCar() {

        FipeResponse car = new FipeResponse();

        car.setVehicleType("1");

        car.setPrice("R$ 50.000,00");

        car.setBrand("Honda");

        car.setModel("Civic");

        car.setModelYear("2020");

        car.setFuel("Gasolina");

        car.setCodeFipe("001234-5");

        car.setReferenceMonth("Agosto de 2026");

        car.setFuelAcronym("G");

        return car;
    }

    @Test
    public void shouldSearchAndCreateFipeResponse()
            throws Exception {

        Connection connection = mock(Connection.class);

        PreparedStatement statement = mock(PreparedStatement.class);

        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getString("vehicle_type")).thenReturn("1");

        when(resultSet.getString("price")).thenReturn("R$ 50.000,00");

        when(resultSet.getString("brand")).thenReturn("Honda");

        when(resultSet.getString("model")).thenReturn("Civic");

        when(resultSet.getString("model_year")).thenReturn("2020");

        when(resultSet.getString("fuel")).thenReturn("Gasolina");

        when(resultSet.getString("code_fipe")).thenReturn("001234-5");

        when(resultSet.getString("reference_month")).thenReturn("Agosto de 2026");

        when(resultSet.getString("fuel_acronym")).thenReturn("G");

        try (MockedStatic<ConnectionFactory> connectionMock = Mockito.mockStatic(ConnectionFactory.class))
        {

            connectionMock.when(ConnectionFactory::getConnection).thenReturn(connection);

            SqlModelFullInfo service = new SqlModelFullInfo();

            List<FipeResponse> result = service.searchInDatabase("123", "2020-1");

            Assert.assertEquals(result.size(), 1);

            FipeResponse car = result.get(0);

            Assert.assertEquals(car.getBrand(), "Honda");

            Assert.assertEquals(car.getModel(), "Civic");

            Assert.assertEquals(car.getPrice(), "R$ 50.000,00");

            verify(statement).setInt(1, 123);
        }
    }

    @Test
    public void shouldReturnNullWhenApiStatusIsNot200() throws Exception {
        SqlModelFullInfo service = Mockito.spy(new SqlModelFullInfo());
        doReturn(new ArrayList<FipeResponse>()).when(service).searchInDatabase("123", "2020-1");

        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(500);

        try (MockedStatic<ExternalFipeApiConsumer> apiMock = Mockito.mockStatic(ExternalFipeApiConsumer.class))
        {
            apiMock.when(() -> ExternalFipeApiConsumer.ApiCallToGetFullInfo(10, "123", "2020-1")).thenReturn(response);

            FipeResponse result = service.getFullCarInformation(10, "123", "2020-1");

            Assert.assertNull(result);
        }
    }

}