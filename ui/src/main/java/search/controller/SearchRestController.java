package search.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import search.dto.SearchRequest;
import search.service.SearchService;

@RequestMapping("/api/text/search/")
@RestController
@AllArgsConstructor
public class SearchRestController {

    private final SearchService searchService;

    @PostMapping("/by-original")
    public int[] getIndexes(@RequestBody SearchRequest request){
        return searchService.getIndexes(request.getOriginalText(), request.getCleanText(), request.getSelectedText());
    }
}
