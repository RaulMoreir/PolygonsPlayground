package main.FIPE.services.interfaces;

import main.FIPE.models.FipeResponse;

import java.io.IOException;

public interface IModelGetFullInfo {
    FipeResponse getFullCarInformation(int BrandCode, String ModelCode, String carYear) throws IOException, InterruptedException;
}
