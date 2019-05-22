package search;

import java.util.*;

public class Searcher {
    private final String originalText;
    private final String cleanedText;
    private final Set<Integer[]> indexesPositions;

    public Searcher(String originalText, String cleanedText) {
        this.originalText = originalText;
        this.cleanedText = cleanedText;
        indexesPositions = new HashSet<>();
    }


    public Set<Integer[]> execute() {
        execute(0, 0, originalText.toCharArray(), cleanedText.toCharArray(), 0, new Integer[originalText.length()], 0, indexesPositions);
        return indexesPositions;
    }

    /**
     * Returns an array of indexes with occurrences of the cleaned text in the original text
     * @param originalTextIndex
     * @param cleanedTextIndex
     * @return
     */
    private static boolean execute(final int originalTextIndex, final int cleanedTextIndex,
                          final char[] originalChars, final char[] cleanedChars,
                          final int resultIndex, final Integer[] occurredIndexes,
                          final int foundCharacters, final Set<Integer[]> indexesPositions) {

        for (int clIdx = cleanedTextIndex; clIdx < cleanedChars.length; clIdx++){
            for (int orIdx = originalTextIndex; orIdx < originalChars.length; orIdx++){
                if (cleanedChars[clIdx] == originalChars[orIdx]) {
                    occurredIndexes[resultIndex] = orIdx;

                    boolean result = execute(orIdx +1, clIdx + 1,
                            originalChars, cleanedChars,
                            resultIndex + 1, occurredIndexes,
                            foundCharacters +1,
                            indexesPositions);

                    if (!result) {
                        occurredIndexes[resultIndex+1] = null;
                        continue;
                    } else {
                        indexesPositions.add(Arrays.copyOf(occurredIndexes, occurredIndexes.length));
                    }
                }
            }

            //output if reachedEnd and found all chars
            if (foundCharacters == cleanedChars.length-1){
                return true;
            } else {
                if (resultIndex > 0) {
                    occurredIndexes[resultIndex - 1] = null;
                }
                return false;
            }
        }
        return false;
    }
}
