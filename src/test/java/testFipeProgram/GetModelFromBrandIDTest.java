package testFipeProgram;

import main.FIPE.models.BrandsEnum;

import main.FIPE.models.ModelData;
import main.FIPE.services.GetIModelFromBrandID;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class GetModelFromBrandIDTest {


    @Test
    public void testPassandoValoresInválidos() throws IOException {
        GetIModelFromBrandID service = new GetIModelFromBrandID();
        assertNull(service.getModelsFromBrand(null));
        assertNull(service.getModelsFromBrand(BrandsEnum.UNDEFINED));
    }

    @Test
    public void testRetornaModelDataQuandoArquivoExiste() throws IOException {
        GetIModelFromBrandID service = new GetIModelFromBrandID();
        ModelData result = service.getModelsFromBrand(BrandsEnum.FORD);
        assertNotNull(result);
    }

    @Test
    public void testRetornaNullQuandoArquivoNaoExiste() throws IOException {
        GetIModelFromBrandID service = new GetIModelFromBrandID();
        ModelData result = service.getModelsFromBrand(BrandsEnum.NON_EXISTENT);
        assertNull(result);
    }

}