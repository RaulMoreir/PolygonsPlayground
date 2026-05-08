package main.FIPE.services.interfaces;

import main.FIPE.models.GenericItem;

import java.util.List;

public interface IModelFilter {
    List<GenericItem> filterModelsProgressivelyByName(List<GenericItem> models, String modelName);
}
