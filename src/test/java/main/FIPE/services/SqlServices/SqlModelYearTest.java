package main.FIPE.services.SqlServices;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class SqlModelYearTest {
    @Test(expectedExceptions = NullPointerException.class)
    private void NullYearParameter() throws IOException, InterruptedException {
        SqlModelYear sqlModelYear = new SqlModelYear();
        String andModel = sqlModelYear.cacheSearchYearsByBrandAndModel( 23, 2332, null );

        Assert.assertNull(andModel);
    }
    @Test
    private void NullYearParametdasder(){

    }

}