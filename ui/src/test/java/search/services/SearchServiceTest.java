package search.services;

import org.junit.jupiter.api.Test;
import search.dto.SearchRequest;
import search.dto.SearchResponse;
import search.service.SearchService;

import java.util.Set;

public class SearchServiceTest {


    private final SearchService searchService = new SearchService();

    @Test
    public void getTruncatedIndexesTest(){
        //String originalText = "abc kekbke";
        String originalText = "abc keebke";
        String cleanedText = "bke";
        int[] expectedResult = new int[]{1, 4, 5};

        Set<SearchResponse> actualResult = searchService.getTextOccurrences(new SearchRequest(originalText, cleanedText, 1, 6));

        System.out.println(actualResult);
    }

    @Test
    public void getTruncatedIndexesTest2(){
        String originalText = "sabc keebkez";
        String cleanedText = "sbkekz";
        int[] expectedResult = new int[]{1, 4, 5};

        Set<SearchResponse> actualResult = searchService.getTextOccurrences(new SearchRequest(originalText, cleanedText, 1, 6));

        System.out.println(actualResult);
    }
}
