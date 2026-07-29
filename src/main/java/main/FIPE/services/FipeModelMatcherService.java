package main.FIPE.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.CarData;
import main.FIPE.models.FipeResponse;
import main.FIPE.models.GenericItem;
import main.FIPE.models.ModelData;
import main.FIPE.services.interfaces.IModelFetcher;
import main.FIPE.services.interfaces.IModelFilter;
import main.FIPE.services.interfaces.IModelGetFullInfo;
import main.FIPE.services.interfaces.IModelYearCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FipeModelMatcherService {
    private final IModelFetcher iModelFetcher;
    private final IModelFilter iModelFilter;
    private final IModelGetFullInfo modelInfo;
    private final IModelYearCache iModelYearCache;

    public FipeModelMatcherService(IModelFetcher iModelFetcher,
                                   IModelFilter iModelFilter,
                                   IModelGetFullInfo modelInfo,
                                   IModelYearCache iModelYearCache) {
        this.iModelFetcher = iModelFetcher;
        this.iModelFilter = iModelFilter;
        this.modelInfo = modelInfo;
        this.iModelYearCache = iModelYearCache;
    }


    public List<FipeResponse>  carregarPossiveisModelos(CarData carData) throws IOException, InterruptedException {
        // A string vem dos jsons 10.json etc etc
        ModelData modelData = iModelFetcher.getModelsFromBrand(carData.getBrandEnum());

        List<GenericItem> filteredOptionsByModelName =
                this.iModelFilter.filterModelsProgressivelyByName(
                        modelData.getModels(),
                        carData.getModel());

        String modelYearsFromCache;
        List<FipeResponse> carros = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for (GenericItem modelo : filteredOptionsByModelName) {
            String modelCode = modelo.getCode();

            modelYearsFromCache = iModelYearCache.cacheSearchYearsByBrandAndModel(
                    carData.getBrandEnum().getId(),
                    Integer.parseInt(modelCode),
                    carData.getModelYear()
            );

            if (modelYearsFromCache == null) {
                System.out.println("Ano incompativel com modelo. descartando registro.");
                continue;
            }

            FipeResponse possibleCarsFromApi = modelInfo.getFullCarInformation(
                    carData.getBrandEnum().getId(),
                    modelCode,
                    modelYearsFromCache
            );
            //String possibleCarsFromApiString = possibleCarsFromApi.;
            System.out.println(possibleCarsFromApi);

            if (possibleCarsFromApi == null) continue;

            //FipeResponse car = mapper.readValue(possibleCarsFromApi, FipeResponse.class);
            carros.add(possibleCarsFromApi);
        }
        return carros;
    }


}

