package main.FIPE.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import main.FIPE.models.FipeResponse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    private final BufferedWriter writer;

    public CsvWriter(String path) throws IOException {
        writer = new BufferedWriter(new FileWriter(path));

        // cabeçalho
        writer.write("vehicleType,price,brand,model,modelYear,fuel,codeFipe,referenceMonth,fuelAcronym");
        writer.newLine();
    }
    public void writeAll(List<FipeResponse> cars) throws IOException {
        for (FipeResponse car : cars) {
            write(car);
        }
    }

    public void blankLine() throws IOException {
        writer.newLine();
    }

    public void write(FipeResponse car) throws IOException {
        if (car == null) return;
       String linha =
                car.getVehicleType() + "," +
                        format(car.getPrice()) + "," +
                        format(car.getBrand()) + "," +
                        format(car.getModel()) + "," +
                        car.getModelYear() + "," +
                        format(car.getFuel()) + "," +
                        format(car.getCodeFipe()) + "," +
                        format(car.getReferenceMonth()) + "," +
                        format(car.getFuelAcronym());

        writer.write(linha);
        writer.write("\n");
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }

    private String format(String value) {
        if (value == null) return "";

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}

