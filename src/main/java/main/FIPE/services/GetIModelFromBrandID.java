package main.FIPE.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.BrandsEnum;
import main.FIPE.models.ModelData;
import main.FIPE.services.interfaces.IModelFetcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetIModelFromBrandID implements IModelFetcher {
    //coleta os modelos dos "{codigodomodelo}.json e retorna-os

    private final ObjectMapper mapper = new ObjectMapper();

    public ModelData getModelsFromBrand(BrandsEnum brandsEnum) throws IOException {
        if (brandsEnum == null || brandsEnum == BrandsEnum.UNDEFINED) {
            return null;
        }
        Path packageModel = Paths.get("src/main/resources/JsonModels/");
        String fileName = brandsEnum.getId() + ".json";
        Path jsonBrandFilePath = packageModel.resolve(fileName);
        if (!Files.exists(jsonBrandFilePath)) {
            return null;
        }
        return mapper.readValue(jsonBrandFilePath.toFile(), ModelData.class);
    }
}
