package search.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import search.dto.SearchRequest;
import search.dto.SearchResponse;
import search.service.SearchService;

import java.util.Set;

@RequestMapping("/api/text/search/")
@RestController
@AllArgsConstructor
public class SearchRestController {

    private final SearchService searchService;

    @PostMapping("/by-original")
    public Set<SearchResponse> getIndexes(@RequestBody SearchRequest searchRequest){
        return searchService.getTextOccurrences(searchRequest);
    }
}
