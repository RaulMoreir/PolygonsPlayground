package main.FIPE.services.JsonServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.GenericItem;
import main.FIPE.services.interfaces.IModelYearCache;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static main.FIPE.services.ExternalFipeApiConsumer.apiCallGetYearsByBrandAndModel;

public class YearSearch implements IModelYearCache {

    private static final ObjectMapper mapper = new ObjectMapper();

    public String cacheSearchYearsByBrandAndModel(int brandCode, int modelCode, String ano)
            throws IOException, InterruptedException {
        String cacheFileName = "src/main/resources/cache/years/"+ brandCode + "_" + modelCode + ".json";
        Path path = Paths.get(cacheFileName);
        List<GenericItem> items;

        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toString())) {
                items = Arrays.asList(mapper.readValue(reader, GenericItem[].class));
            }
        } else {
            HttpResponse<String> response = apiCallGetYearsByBrandAndModel(brandCode,modelCode);
            if (response.statusCode() != 200) {
                return null;
            }

            try (FileWriter newFile = new FileWriter(cacheFileName)) {
                newFile.write(response.body());
            }
            items = Arrays.asList(mapper.readValue(response.body(), GenericItem[].class));
        }

        items.forEach(it -> it.setName(String.valueOf(modelCode)));

        return items.stream()
                .map(GenericItem::getCode)
                .filter(code -> ano != null && code.contains(ano))
                .findFirst()
                .orElse(null);

    }


}
