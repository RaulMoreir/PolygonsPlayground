package testFipeProgram;

import main.FIPE.services.APICache;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ApiCacheTests {
    private final APICache cache = new APICache();

    @Test
    public void testPassandoValoresVálidosEExistentesNoCache() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(21, 9646, "2022");

        Assert.assertEquals(result, "2022-5");

    }
    @Test
    public void testc3() throws IOException, InterruptedException {
        String result = cache.cacheSearchYearsByBrandAndModel(13, 6776, "2015");
        Assert.assertEquals(result, "2015-5");
    }

    @Test
    public void testPassandoValorInválidoNoAno() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(21, 9646, null);

        Assert.assertNull(result);

    }

    @Test
    public void testPassandoValorVálidoAindaNaoArmazenadoEmCache() throws IOException, InterruptedException {
        //ver de mockar API para não consumir

        String result = cache.cacheSearchYearsByBrandAndModel(29, 6961, "2014-1");

        Assert.assertEquals(result, "2014-1");
        File archiveFile = new File("src/main/resources/cache/years/29_6961.json");
        archiveFile.delete();
    }


    @Test
    public void testPassandoValorInValidoAindaNaoArmazenadoEmCache() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(28, 5248, "2012-2");

        Assert.assertNull(result);
    }
    @Test
    public void testAnoZoado() throws IOException, InterruptedException {

        String result = cache.cacheSearchYearsByBrandAndModel(0, 0, null);

        Assert.assertNull(result);
    }

    @Test
    public void testInicializandoClasseParaTotalCoberturaPassandoValoresVálidosEExistentesNoCache() throws IOException, InterruptedException {
        String result = cache.cacheSearchYearsByBrandAndModel(21, 9646, "2022");

        Assert.assertEquals(result, "2022-5");

    }

}


