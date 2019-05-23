package search.service;

import org.springframework.stereotype.Service;
import search.Searcher;
import search.dto.SearchRequest;
import search.dto.SearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {

    public List<SearchResponse> getTextOccurences(SearchRequest request){
        Searcher searcher = new Searcher(request.getOriginalText(), request.getCleanText());
        Set<Integer[]> allCombinations = searcher.execute();

        List<int[]> allTruncatedCombinations = getTruncatedIndexes(request.getOriginalText(), request.getCleanText(),
                request.getSelectStartIndex(), request.getSelectEndIndex(),
                allCombinations);

        if (allCombinations.isEmpty()){
            return null;
        } else {
            List<SearchResponse> occurrences = new ArrayList<>();
            String reconstructedCleanText = reconstructFromOriginalText(allTruncatedCombinations.get(0), request.getOriginalText());

            for (int[] indexes : allTruncatedCombinations) {
                int startIndex = 0;
                while (request.getCleanText().indexOf(reconstructedCleanText, startIndex) != -1) {
                    int beginIndex = request.getCleanText().indexOf(reconstructedCleanText, startIndex);
                    int endIndex = beginIndex + reconstructedCleanText.length();
                    startIndex = endIndex - 1;

                    occurrences.add(new SearchResponse(beginIndex, endIndex));
                }
            }

            return occurrences;
        }
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

    private List<int[]> getTruncatedIndexes(String originalText, String cleanText, int startIndex, int endIndex, Set<Integer[]> allCombinations) {
        return allCombinations.stream()
                .map(array -> convertToIntArray(array))
                .filter(positions -> isBetween(positions, startIndex, endIndex))
                .collect(Collectors.toList());
    }

    private int[] convertToIntArray(Integer[] sourceArray) {
        int lastNullIndex = -1;

        for (int i = 0; i < sourceArray.length; i++){
            if (sourceArray[i] == null) {
                lastNullIndex = i;
                break;
            }
        }

        int resultLength = lastNullIndex == -1 ? sourceArray.length : lastNullIndex;
        int[] cleanConvertedArray = new int[resultLength];

        for (int i = 0; i < resultLength; i++){
            cleanConvertedArray[i] = sourceArray[i];
        }

        return cleanConvertedArray;
    }

    private boolean isBetween(int[] positions, int startIndex, int endIndex) {
        return startIndex <= positions[0] && positions[positions.length-1] <= endIndex;
    }
}
