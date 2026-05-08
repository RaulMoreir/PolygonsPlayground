package main.FIPE.services;

import main.FIPE.models.CarData;
import main.FIPE.models.CarMatchReport;
import main.FIPE.models.FipeResponse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private final BufferedWriter writer;


    public CsvWriter(String path) throws IOException {
        // Recebe a String path (src/report.csv) e chama o header
        writer = new BufferedWriter(new FileWriter(path));
//        writeHeader();
    }
/*
    private void writeHeader() throws IOException{
        // Faz o cabeçalho
        writer.write("vehicleType,price,brand,model,modelYear,fuel,codeFipe,referenceMonth,fuelAcronym");
        writer.newLine();
    }
*/
    public void writeCarReport(CarMatchReport report) throws IOException {
        // recebe POJO CarMatchReport que contém Cardata e FipeResponse
        // 1 linha de carro leiloado , N linhas da Fipe, uma linha em branco entre

        writeAuctionCarOnCsv(report.getCarData());

        for (FipeResponse match : report.getMatches()) {
            writeFipeResponseCarOnCsv(match);
        }

        writer.newLine();
        writer.flush();
    }


    private void writeAuctionCarOnCsv(CarData car) throws IOException {
        //Processa UMA UNIDADE de carro vinda do leilão para csv
        if (car == null){
            return;
        };
        writer.write(
                format(car.getModel()) + "," +
                        "R$ " + format(car.getLance()) + "," +
                        "R$ "+ car.getTaxaAdm() + "," + //ok
                        format(car.getBrandEnum().name()) + "," + //ok
                        car.getModelYear() + "," + //ok
                        format(car.getFuel())); //ok

        writer.newLine();
    }

        private void writeFipeResponseCarOnCsv(FipeResponse car) throws IOException {
        //Processa UMA UNIDADE de carro vinda da API
        if (car == null) return;
        writer.write(
                car.getVehicleType() + "," +
                        format(car.getPrice()) + "," +
                        format(car.getBrand()) + "," +
                        format(car.getModel()) + "," +
                        car.getModelYear() + "," +
                        format(car.getFuel()) + "," +
                        format(car.getCodeFipe()) + "," +
                        format(car.getReferenceMonth()) + "," +
                        format(car.getFuelAcronym())
        );
        writer.newLine();
    }

    private String format(String value) {
        /*
        format("abc") → "abc"
        format("a\"b") → "a""b"
        format(null) → ""
        */
        if (value == null) return "";

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    public void close() throws IOException {
        writer.flush();
        writer.close();
    }
}
