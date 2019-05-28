package search.service;

import org.springframework.stereotype.Service;
import search.Searcher;
import search.dto.SearchRequest;
import search.dto.SearchResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    /**
     * '
     * Returns text occurrences bounds of one text into another filtered by bounds
     *
     * @param request
     * @return
     */
    public Set<SearchResponse> getTextOccurrences(SearchRequest request) {
        if (request.getSelectStartIndex() == request.getSelectEndIndex())
            return null;

        Searcher searcher = new Searcher(request.getOriginalText(), request.getCleanText());
        Set<Integer[]> allCombinations = searcher.execute();

        //List of mappings of original-text-character-index [to] clean-text-character-index
        List<Integer[]> truncatedCombinations = getTruncatedIndexes(allCombinations, request.getSelectStartIndex(), request.getSelectEndIndex(), request.getCleanText().length());

        Set<SearchResponse> occurrences = truncatedCombinations.stream().map(arrayIn -> {
            int[] arrayOut = new int[arrayIn.length];

            for (int i = 0; i < arrayIn.length; i++){
                arrayOut[i] = arrayIn[i];
            }

            return new SearchResponse(arrayOut);
        }).collect(Collectors.toSet());

        return occurrences;
    }

    private List<Integer[]> getTruncatedIndexes(Set<Integer[]> allCombinations, int selectStartIndex, int selectEndIndex, int cleanTextLength) {
        List<Integer[]> truncatedIndexes = new ArrayList<>();

        for (Integer[] positions: allCombinations){
            List<Integer> indexesAsList = new ArrayList<>();

            for (int i = 0; i < cleanTextLength; i++){
                if (positions[i] >= selectStartIndex && positions[i] < selectEndIndex) {
                    indexesAsList.add(i);
                }
            }
            truncatedIndexes.add(indexesAsList.toArray(new Integer[0]));
        }

        return truncatedIndexes;
    }

}
