package search;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class Displayer {
    private final Set<Integer[]> allCombinations;
    private final String originalText;
    private final String cleanText;

    public void displayToConsole() {
        for (Integer[] positions: allCombinations){
            String[][] displayMatrix = generateDisplayMatrix(positions);
            outputToConsole(displayMatrix);
        }
    }

    private void outputToConsole(String[][] displayMatrix) {
        System.out.println("********************************************************");

        for (int y = 0; y < displayMatrix.length; y++){
            for (int x = 0; x < displayMatrix[y].length; x++){
                if (x < displayMatrix[y].length-1) {
                    System.out.print(displayMatrix[y][x] + ", ");
                } else {
                    System.out.print(displayMatrix[y][x]);
                }
            }
            System.out.println();
        }

        System.out.println("********************************************************");
    }

    /**
     * first line is the original text
     * second line is the clean text insert
     * third line is the order number to show if the cell is whether empty or white space
     * @return
     * @param positions
     */
    private String[][] generateDisplayMatrix(Integer[] positions) {
        String[][] displayMatrix = new String[3][originalText.length()];

        for (int x = 0; x < originalText.length(); x++){
            displayMatrix[0][x] = originalText.charAt(x) + "";
            displayMatrix[1][x] = " ";
            displayMatrix[2][x] = " ";
        }

        for (int x = 0; x < cleanText.length(); x++){
            try {
                displayMatrix[1][positions[x]] = cleanText.charAt(x) + "";
                displayMatrix[2][positions[x]] = Integer.toString(x);
            } catch (NullPointerException ex){
                ex.printStackTrace();
            }
        }

        return displayMatrix;
    }
}
