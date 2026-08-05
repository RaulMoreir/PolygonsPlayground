package main.FIPE.services.SqlServices;

import main.FIPE.services.ExternalFipeApiConsumer;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class SqlModelYearTest {
    @Test(expectedExceptions = NullPointerException.class)
    private void testNullYearParameter() throws IOException, InterruptedException {
        SqlModelYear sqlModelYear = new SqlModelYear();
        String andModel = sqlModelYear.cacheSearchYearsByBrandAndModel( 23, 2332, null );

        Assert.assertNull(andModel);
    }
    @Test
    private void httpResponse500() throws IOException, InterruptedException {
        SqlModelYear sqlModelYear = Mockito.spy(new SqlModelYear());
        doReturn(null).when(sqlModelYear).searchYearsInDatabase(123, "2014-1");

        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(500);

        try (MockedStatic<ExternalFipeApiConsumer> apiMock = Mockito.mockStatic(ExternalFipeApiConsumer.class)) {

            apiMock.when(() -> ExternalFipeApiConsumer.apiCallGetYearsByBrandAndModel(10, 123)).thenReturn(response);

            String result = sqlModelYear.cacheSearchYearsByBrandAndModel(10, 123, "2014-x");

        // confirma que caiu no return null
        Assert.assertNull(result);

        // confirma que a apiConsumer foi chamado
            apiMock.verify(() -> ExternalFipeApiConsumer.apiCallGetYearsByBrandAndModel(10, 123));

    }

}}