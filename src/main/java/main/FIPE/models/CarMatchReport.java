package main.FIPE.models;

import java.util.List;

// representa o relátorio completo de uma unidade de carro do leilão
public class CarMatchReport {
    private final CarData carData;
    private final List<FipeResponse> matches;

    public CarMatchReport(CarData carData, List<FipeResponse> response) {
        this.carData = carData;
        this.matches = response;
    }

    public CarData getCarData() {
        return carData;
    }

    public List<FipeResponse> getMatches() {
        return matches;
    }


}
