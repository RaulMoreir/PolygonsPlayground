package main.FIPE.models;

public class FipeResponse {

    /* {"vehicleType":1,
    "price":"R$ 76.611,00",
    "brand":"GM - Chevrolet",
    "model":"TRACKER LT 1.4 Turbo 16V Flex 4x2 Aut.",
    "modelYear":2018,
    "fuel":"Flex",
    "codeFipe":"004476-8",
    "referenceMonth":"abril de 2026",
    "fuelAcronym":"F"}
    */

    private String vehicleType;
    private String price;
    private String brand;
    private String model;
    private String modelYear;
    private String fuel;
    private String codeFipe;
    private String referenceMonth;
    private String fuelAcronym;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getCodeFipe() {
        return codeFipe;
    }

    public void setCodeFipe(String codeFipe) {
        this.codeFipe = codeFipe;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
    }

    public String getFuelAcronym() {
        return fuelAcronym;
    }

    public void setFuelAcronym(String fuelAcronym) {
        this.fuelAcronym = fuelAcronym;
    }



}
