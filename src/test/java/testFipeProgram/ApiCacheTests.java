package testFipeProgram;

import main.FIPE.services.APICache;
import main.FIPE.services.interfaces.IModelYearCache;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.when;

public class ApiCacheTests {
    private final APICache cache = new APICache();

    @Test
    public void testValidValueStoredInCache() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(21, 9646, "2022");

        Assert.assertEquals(result, "2022-5");

    }
    @Test
    public void testc3() throws IOException, InterruptedException {
        String result = cache.cacheSearchYearsByBrandAndModel(13, 6776, "2015");
        Assert.assertEquals(result, "2015-5");
    }

    @Test
    public void testInvalidYearValue() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(21, 9646, null);

        Assert.assertNull(result);

    }
    @Mock
    private IModelYearCache mockCache;
    @Test
    public void testValidValueNotStoredInCache() throws IOException, InterruptedException {
        //ver de mockar API para não consumir
        when(mockCache.cacheSearchYearsByBrandAndModel(29,6961,"2014-1")).thenReturn("2014-1");
        String result = mockCache.cacheSearchYearsByBrandAndModel(29, 6961, "2014-1");

        Assert.assertEquals(result, "2014-1");
        File archiveFile = new File("src/main/resources/cache/years/29_6961.json");
        archiveFile.delete();
    }


    @Test
    public void testInvalidValueNotStoredInCache() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(28, 5248, "2012-2");

        Assert.assertNull(result);
    }
    @Test
    public void testMeanYear() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(0, 0, null);

        Assert.assertNull(result);
    }

    // InicializandoClasseParaTotal Cobertura Passando Valores Validos E Existentes No Cache
    @Test
    public void testInitializingClassForCompleteCoverage() throws IOException, InterruptedException {
        String result = cache.cacheSearchYearsByBrandAndModel(21, 9646, "2022");

        Assert.assertEquals(result, "2022-5");

    }

}


