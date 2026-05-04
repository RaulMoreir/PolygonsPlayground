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
        when(carData.getModel()).thenReturn("Civic Coupe EX/ EXS 1.6 16V 2p");
        when(carData.getModelYear()).thenReturn("1998");
        when(brand.getId()).thenReturn(25);

        ModelData modelData = mock(ModelData.class);

        GenericItem item = mock(GenericItem.class);
        when(item.getCode()).thenReturn("1248");

        var modelList = java.util.List.of(item);

        when(fetcher.getModelsFromBrand(brand)).thenReturn(modelData);
        when(modelData.getModels()).thenReturn(modelList);

        when(filter.filterModelsProgressivelyByName(modelList, "Civic Coupe EX/ EXS 1.6 16V 2p"))
                .thenReturn(modelList);

        when(cache.cacheSearchYearsByBrandAndModel(25, 1248, "1998"))
                .thenReturn("1998-1");

        when(info.getFullCarInformation(25, "1248", "1998-1"))
                .thenReturn("{\n" +
                        "    \"vehicleType\": 1,\n" +
                        "    \"price\": \"R$ 35.950,00\",\n" +
                        "    \"brand\": \"Honda\",\n" +
                        "    \"model\": \"Civic Coupe EX/ EXS 1.6 16V 2p\",\n" +
                        "    \"modelYear\": 1998,\n" +
                        "    \"fuel\": \"Gasolina\",\n" +
                        "    \"codeFipe\": \"014019-8\",\n" +
                        "    \"referenceMonth\": \"maio de 2026\",\n" +
                        "    \"fuelAcronym\": \"G\"\n" +
                        "}");

        //act
        service.carregarPossiveisModelos(carData);

        //assert
        verify(fetcher).getModelsFromBrand(brand);
        verify(filter).filterModelsProgressivelyByName(modelList, "Civic Coupe EX/ EXS 1.6 16V 2p");
        verify(cache).cacheSearchYearsByBrandAndModel(25, 1248, "1998");
        verify(info).getFullCarInformation(25, "1248", "1998-1");
    }

    @Test
    public void testOnix() throws IOException {
        FilterIModelNamesProgressivelyByName filter = new FilterIModelNamesProgressivelyByName();
        GetIModelFromBrandID jsonReader = new GetIModelFromBrandID();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.CHEVROLET);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(), "ONIX PLUS 10 MT LT1");
        Assert.assertEquals(actual.size(), 9);
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