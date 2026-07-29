package testFipeProgram;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.CarData;
import main.FIPE.models.GenericItem;
import main.FIPE.models.ModelData;

import main.FIPE.services.FilterIModelByName;
import main.FIPE.services.FipeModelMatcherService;
import main.FIPE.services.JsonServices.JsonModelFetcher;
import main.FIPE.services.interfaces.IModelFetcher;
import main.FIPE.services.interfaces.IModelFilter;
import main.FIPE.services.interfaces.IModelGetFullInfo;
import main.FIPE.services.interfaces.IModelYearCache;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
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

    //@Test
    public void testAnoImcompatívelComModelo() throws Exception {
        //arrange
        CarData carData = mock(CarData.class);
        //BrandsEnum brand = mock(BrandsEnum.class);

        when(carData.getBrandEnum()).thenReturn(BrandsEnum.FIAT);
        when(carData.getModel()).thenReturn("ARGO DRIVE 1.0 6V Flex");
        when(carData.getModelYear()).thenReturn("2020");
        //when(brand.getId()).thenReturn(21);

        ModelData modelData = mock(ModelData.class);
        GenericItem item = mock(GenericItem.class);
        when(item.getCode()).thenReturn("7965");
        when(item.getName()).thenReturn("Argo Drive");

        List<GenericItem> genericItemList = new ArrayList<>();
        genericItemList.add(item);

        var modelList = java.util.List.of(item);

        when(fetcher.getModelsFromBrand(BrandsEnum.FIAT)).thenReturn(modelData);
        when(modelData.getModels()).thenReturn(modelList);

        when(filter.filterModelsProgressivelyByName(modelList, "ARGO DRIVE 1.0 6V Flex"))
                .thenReturn(modelList);

        cache.cacheSearchYearsByBrandAndModel(21, 7965, "2020");

        when(info.getFullCarInformation(21, "7965", "2020-1"))
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
        verify(fetcher).getModelsFromBrand(BrandsEnum.FIAT);
        verify(filter).filterModelsProgressivelyByName(modelList, "ARGO DRIVE 1.0 6V Flex");

    }

    @Test
    public void testOnix() throws IOException {
        FilterIModelByName filter = new FilterIModelByName();
        JsonModelFetcher jsonReader = new JsonModelFetcher();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.CHEVROLET);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(), "ONIX PLUS 10 MT LT1");
        Assert.assertEquals(actual.size(), 9);
    }

    @Test
    public void testOnix2() throws IOException {
        FilterIModelByName filter = new FilterIModelByName();
        JsonModelFetcher jsonReader = new JsonModelFetcher();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.CHEVROLET);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(), "ONIX 10 MT LT2");
        Assert.assertEquals(actual.size(), 26);
    }

    @Test
    public void testCC3() throws IOException {
        FilterIModelByName filter = new FilterIModelByName();
        JsonModelFetcher jsonReader = new JsonModelFetcher();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.CITROEN);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(), "C3 1.6 Aut.");
        Assert.assertEquals(actual.size(), 15);
    }

    @Test
    public void testbruh() throws IOException {
        FilterIModelByName filter = new FilterIModelByName();
        JsonModelFetcher jsonReader = new JsonModelFetcher();
        main.FIPE.models.ModelData test = jsonReader.getModelsFromBrand(BrandsEnum.FIAT);
        List<GenericItem> actual = filter.filterModelsProgressivelyByName(test.getModels(),"ARGO DRIVE 1.0 6V Flex");

        Assert.assertEquals(actual.size(), 2);
    }
}