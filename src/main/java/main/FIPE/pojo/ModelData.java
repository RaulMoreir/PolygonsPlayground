package main.FIPE.pojo;

import java.util.List;

public class ModelData {

    private Integer fipeModelCode;
    private List<GenericItem> models;

    public Integer getFipeModelCode() {
        return fipeModelCode;
    }

    public void setFipeModelCode(Integer fipeModelCode) {
        this.fipeModelCode = fipeModelCode;
    }

    public List<GenericItem> getModels() {
        return models;
    }

    public void setModels(List<GenericItem> models) {
        this.models = models;
    }
}
