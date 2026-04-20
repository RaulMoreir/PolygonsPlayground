package main.FIPE.services;

import main.FIPE.models.GenericItem;

import java.util.Arrays;
import java.util.List;

public class FilterIModelNamesProgressivelyByName implements IModelFilter {


    public List<GenericItem> filterModelsProgressivelyByName(List<GenericItem> modelsFromAPI, String textFromAuctionSite) {
        if (modelsFromAPI.isEmpty() || modelsFromAPI.size() == 1) return modelsFromAPI;

        if (textFromAuctionSite == null || textFromAuctionSite.trim().isEmpty()) {
            return modelsFromAPI;  // devolve lista sem alterações
        }

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
        return modelsFromAPI;
    }

    private String normalize(String s) {
        return s.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // remover pontuacao
                .replaceAll("\\s+", " ")
                .trim();
    }


}
