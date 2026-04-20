package main.FIPE.services;

import java.io.IOException;

public interface IModelYearCache {
     String cacheSearchYearsByBrandAndModel(int brandCode, int modelCode, String ano)
             throws IOException, InterruptedException ;
}
