package main.FIPE.services;

import java.io.IOException;

public interface IModelGetFullInfo {
    String getFullCarInformation(int BrandCode, String ModelCode, String carYear) throws IOException, InterruptedException;
}
