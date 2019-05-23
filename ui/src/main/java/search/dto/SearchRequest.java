package search.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Setter
public class SearchRequest {
    String originalText;
    String cleanText;
    String selectedText;
}
