package main.FIPE.services.interfaces;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.ModelData;

import java.io.IOException;

public interface IModelFetcher {
    ModelData getModelsFromBrand(BrandsEnum brand) throws IOException, InterruptedException;
}
