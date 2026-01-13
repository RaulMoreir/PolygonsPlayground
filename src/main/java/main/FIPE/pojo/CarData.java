package main.FIPE.pojo;

import java.util.ArrayList;
import java.util.List;

public class CarData {

    private String marca;
    private String modelo;
    private String ano;
    private String combustivel;
    private String lance;
    private String taxaAdm;
    private BrandsEnum brandEnum;
    private List<Object> possibleModels = new ArrayList<>();


    public CarData() {
    }

    @Override
    public String toString() {
        return marca + ", " + modelo + ", " + ano + ", " + combustivel + ", " + lance + ", " + taxaAdm + "\n";
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
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

}

