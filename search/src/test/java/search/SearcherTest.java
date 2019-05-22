package search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SearcherTest {

    @Test
    public void executeTestLength(){
        String originalText = "This is the original text, before cleaning the text";
        String cleanedText = "This is the clean text";

        Searcher searcher = new Searcher(originalText, cleanedText);
        Set<Integer[]> allCombinations = searcher.execute();

        for (Integer[] indexes: allCombinations){
            long nonNullElements = Arrays.stream(indexes).filter(Objects::nonNull).count();
            assertEquals(nonNullElements, cleanedText.length());
        }
    }

    @Test
    public void executeTestUniquePositions(){
        String originalText = "This is the original text, before cleaning the text";
        String cleanedText = "This is the clean text";

        Searcher searcher = new Searcher(originalText, cleanedText);
        Integer[][] allCombinations = searcher.execute().toArray(new Integer[0][0]);

        for (int i = 0; i < allCombinations.length; i++){
            for (int j = 0; j < allCombinations.length; j++){
                if (i != j){
                    assertFalse(Arrays.equals(allCombinations[i], allCombinations[j]));
                }
            }
        }
    }

}
