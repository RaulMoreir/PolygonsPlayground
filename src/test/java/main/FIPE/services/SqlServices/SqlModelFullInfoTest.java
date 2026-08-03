package main.FIPE.services.SqlServices;

import main.FIPE.models.FipeResponse;
import main.FIPE.services.ExternalFipeApiConsumer;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

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