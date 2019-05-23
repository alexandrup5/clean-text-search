package search.service;

import org.springframework.stereotype.Service;
import search.Searcher;
import search.dto.SearchRequest;
import search.dto.SearchResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {

    /**'
     * Returns text occurrences bounds of one text into another filtered by bounds
     * @param request
     * @return
     */
    public Set<SearchResponse> getTextOccurrences(SearchRequest request){
        if (request.getSelectStartIndex()== request.getSelectEndIndex())
            return null;

        Searcher searcher = new Searcher(request.getOriginalText(), request.getCleanText());
        Set<Integer[]> allCombinations = searcher.execute();

        List<int[]> truncatedCombinations = getTruncatedIndexes(request.getSelectStartIndex(), request.getSelectEndIndex(), allCombinations);

        if (truncatedCombinations.isEmpty()){
            return null;
        } else {
            Set<SearchResponse> occurrences = new HashSet<>();

            for (int[] truncatedIndexes: truncatedCombinations) {
                String reconstructedPartialCleanText = reconstructFromOriginalText(truncatedIndexes, request.getOriginalText());
                if (reconstructedPartialCleanText.isEmpty()) {
                    continue;
                } else {
                    Set<SearchResponse> subOccurences = getBoundsByOccurrences(request.getCleanText(), reconstructedPartialCleanText);
                    occurrences.addAll(subOccurences);
                }
            }

            return occurrences;
        }
    }

    /**
     * Fetches all bounds of the subtext in
     * @param sourceText text to find in
     * @param subText subtext to search in sourceText
     * @return set of bounds where this text can be found
     */
    private Set<SearchResponse> getBoundsByOccurrences(String sourceText, String subText) {
        Set<SearchResponse> bounds = new HashSet<>();

        int startIndex = 0;
        while (sourceText.indexOf(subText, startIndex) != -1 && startIndex < sourceText.length()) {
            int beginIndex = sourceText.indexOf(subText, startIndex);
            int endIndex = beginIndex + subText.length();
            startIndex = endIndex;

            if (endIndex - beginIndex == 0)
                break;

            bounds.add(new SearchResponse(beginIndex +1, endIndex));
        }

        return bounds;
    }

    /**
     * Selects text by indexes from originalText
     * @param indexes indexes which to select from originalText
     * @param originalText text from which selection must be done
     * @return a substring of the original by input indexes
     */
    private String reconstructFromOriginalText(int[] indexes, String originalText) {
        StringBuilder reconstructedString = new StringBuilder();

        for (int i :indexes){
            reconstructedString.append(originalText.charAt(i));
        }

        return reconstructedString.toString();
    }

    /**
     * Returns truncated indexes from the original text by bounds
     * @param startIndex start bound of truncation
     * @param endIndex end bound of truncation
     * @param allCombinations indexes of the original text that contains all combination of clean text in original text
     * @return
     */
    private List<int[]> getTruncatedIndexes(int startIndex, int endIndex, Set<Integer[]> allCombinations) {
        return allCombinations.stream()
                .map(array -> truncate(startIndex, endIndex, array))
                //.filter(array -> array.length <= 1)
                .collect(Collectors.toList());
    }

    /**
     * Removes from array that are outside of the bounds
     * @param startIndex low bound
     * @param endIndex high bound
     * @param array source array for filtering
     * @return
     */
    private int[] truncate(int startIndex, int endIndex, Integer[] array){
        int[] inBounds = new int[endIndex - startIndex + 1];
        for (int i = 0; i < inBounds.length; i++)
            inBounds[i] = -1;

        int resultCounter = 0;
        for (int i = 0; i < array.length; i++){
            if (array[i] != null && startIndex <= array[i] && array[i] < endIndex){
                inBounds[resultCounter++] = array[i];
            }
        }

        int[] filteredArray = new int[resultCounter];
        for (int i = 0; i < resultCounter; i++)
            filteredArray[i] = inBounds[i];

        return filteredArray;
    }
}
