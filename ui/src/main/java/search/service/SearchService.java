package search.service;

import org.springframework.stereotype.Service;
import search.Searcher;

@Service
public class SearchService {
    private Searcher searcher;

    public int[] getIndexes(String originalText, String cleanText, String selectedText){
        return new int[]{1, 2, 3};
    }
}
