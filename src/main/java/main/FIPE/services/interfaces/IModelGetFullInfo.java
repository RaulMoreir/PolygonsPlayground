package main.FIPE.services.interfaces;

import java.io.IOException;

public interface IModelGetFullInfo {
    String getFullCarInformation(int BrandCode, String ModelCode, String carYear) throws IOException, InterruptedException;
}
