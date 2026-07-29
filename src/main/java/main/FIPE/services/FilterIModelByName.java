package main.FIPE.services;

import main.FIPE.models.GenericItem;
import main.FIPE.services.interfaces.IModelFilter;

import java.util.ArrayList;
import java.util.List;

public class FilterIModelByName implements IModelFilter {


    public List<GenericItem> filterModelsProgressivelyByName(List<GenericItem> modelsFromAPI, String textFromAuctionSite) {
        if (modelsFromAPI.isEmpty() || modelsFromAPI.size() == 1) return modelsFromAPI;

        if (textFromAuctionSite == null || textFromAuctionSite.trim().isEmpty()) {
            return modelsFromAPI;  // devolve lista sem alterações
        }
        int originalSize = modelsFromAPI.size();
        String[] splitedAuctionString = textFromAuctionSite.split("\\s+");
        for (String words : splitedAuctionString) {
            String searchCriteria = normalize(words);
            List<GenericItem> filtered =  modelsFromAPI.stream()
                    .filter(apiModel -> normalize(apiModel.getName()).contains(searchCriteria))
                    .toList();
            if (!filtered.isEmpty()) {
                modelsFromAPI = filtered;
            }

        }
        if (originalSize == modelsFromAPI.size()) {
            return new ArrayList<>();
        }
        return modelsFromAPI;
    }

    private String normalize(String s) {
        return s.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // remover pontuacao
                .replaceAll("\\s+", " ")
                .trim();
    }


}
