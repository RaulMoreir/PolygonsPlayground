package testFipeProgram;

import main.FIPE.cache.APICache;
import main.FIPE.models.BrandsEnum;
import main.FIPE.models.CarData;
import main.FIPE.models.GenericItem;
import main.FIPE.models.ModelData;
import main.FIPE.services.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class FipeModelMatcherServiceTest {

    @Mock
    private IModelFetcher fetcher;

    @Mock
    private IModelFilter filter;

    @Mock
    private IModelGetFullInfo info;

    @Mock
    private IModelYearCache cache;

    @InjectMocks
    private FipeModelMatcherService service;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveCarregarPossiveisModelos() throws Exception {
        //arrange
        CarData carData = mock(CarData.class);
        BrandsEnum brand = mock(BrandsEnum.class);

        when(carData.getBrandEnum()).thenReturn(brand);
        when(carData.getModel()).thenReturn("civic");
        when(carData.getModelYear()).thenReturn("2020");
        when(brand.getId()).thenReturn(1);

        ModelData modelData = mock(ModelData.class);

        GenericItem item = mock(GenericItem.class);
        when(item.getCode()).thenReturn("123");

        var modelList = java.util.List.of(item);

        when(fetcher.getModelsFromBrand(brand)).thenReturn(modelData);
        when(modelData.getModels()).thenReturn(modelList);

        when(filter.filterModelsProgressivelyByName(modelList, "civic"))
                .thenReturn(modelList);

        when(cache.cacheSearchYearsByBrandAndModel(1, 123, "2020"))
                .thenReturn("2020-1");

        when(info.getFullCarInformation(1, "123", "2020-1"))
                .thenReturn("dados");

        //act
        service.carregarPossiveisModelos(carData);

        //assert
        verify(fetcher).getModelsFromBrand(brand);
        verify(filter).filterModelsProgressivelyByName(modelList, "civic");
        verify(cache).cacheSearchYearsByBrandAndModel(1, 123, "2020");
        verify(info).getFullCarInformation(1, "123", "2020-1");
    }


    @Test
    public void testBmwX1() throws IOException, InterruptedException {
        FilterIModelNamesProgressivelyByName filter = new FilterIModelNamesProgressivelyByName();
        GetIModelFromBrandID jsonReader = new GetIModelFromBrandID();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.BMW);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(), "X1 S20I ACTIVE FLEX");
        Assert.assertEquals(actual.size(), 2);

        GetIModelFromBrandID fetcher = new GetIModelFromBrandID();
        FullCarInformationFromAPII info = new FullCarInformationFromAPII();
        APICache cache = new APICache();


        FipeModelMatcherService matcherService = new FipeModelMatcherService(fetcher, filter, info, cache);

        CarData cardata = new CarData();
        cardata.setModel("");

        matcherService.carregarPossiveisModelos(cardata);
    }

    @Test
    public void testOnix() throws IOException {
        FilterIModelNamesProgressivelyByName filter = new FilterIModelNamesProgressivelyByName();
        GetIModelFromBrandID jsonReader = new GetIModelFromBrandID();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.CHEVROLET);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(), "ONIX PLUS 10 MT LT1");
        Assert.assertEquals(actual.size(), 3);
    }

    @Test
    public void testOnix2() throws IOException {
        FilterIModelNamesProgressivelyByName filter = new FilterIModelNamesProgressivelyByName();
        GetIModelFromBrandID jsonReader = new GetIModelFromBrandID();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.CHEVROLET);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(), "ONIX 10 MT LT2");
        Assert.assertEquals(actual.size(), 26);
    }

}