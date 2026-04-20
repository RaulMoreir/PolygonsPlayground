package main.FIPE.services;

import main.FIPE.models.GenericItem;

import java.util.List;

public interface IModelFilter {
    List<GenericItem> filterModelsProgressivelyByName(List<GenericItem> models, String modelName);
}
