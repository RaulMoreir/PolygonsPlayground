package main.FIPE.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarData {

    private String brand;
    private String model;
    private String modelYear;
    private String fuel;
    private String codeFipe;

    private String price;

    private String lance;
    private String taxaAdm;
    private BrandsEnum brandEnum;
    private List<Object> possibleModels = new ArrayList<>();
    public CarData() {
    }

    @Override
    public String toString() {
        return brand + ", " + model + ", " + modelYear + ", " + fuel + ", " + lance + ", " + taxaAdm + "\n";
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

    public String getLance() {
        return lance;
    }

    public void setLance(String lance) {
        this.lance = lance;
    }

    public String getTaxaAdm() {
        return taxaAdm;
    }

    public void setTaxaAdm(String taxaAdm) {
        this.taxaAdm = taxaAdm;
    }


    public BrandsEnum getBrandEnum() {
        return brandEnum;
    }

    public void setBrandEnum(BrandsEnum brandEnum) {
        this.brandEnum = brandEnum;
    }

    public List<Object> getPossibleModels() {
        return possibleModels;
    }

    public void setPossibleModels(List<Object> possibleModels) {
        this.possibleModels = possibleModels;
    }

    public void addPossibleModel(Object possibleModel) {
        this.possibleModels.add(possibleModel);
    }

    public String getCodeFipe() {
        return codeFipe;
    }

    public void setCodeFipe(String codeFipe) {
        this.codeFipe = codeFipe;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}

