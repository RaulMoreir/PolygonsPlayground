package main.FIPE.services.SqlServices;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.ModelData;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SqlModelFetcherTest {
    @Test
    private void UndefinedBrandEnum(){
        SqlModelFetcher sqlModelFetcher = new SqlModelFetcher();
        ModelData data = sqlModelFetcher.getModelsFromBrand(BrandsEnum.UNDEFINED);

        Assert.assertNull(data);
    }

    @Test
    private void NullParameter(){
        SqlModelFetcher sqlModelFetcher = new SqlModelFetcher();
        BrandsEnum brandsEnum = null;
        ModelData data = sqlModelFetcher.getModelsFromBrand(brandsEnum);

        Assert.assertNull(data);

    }


}