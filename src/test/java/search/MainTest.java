package search;

import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void mainTestSampleText(){
        Main.main(new String[]{
                "This is the original text, before cleaning the text",
                "This is the clean text"}
        );
    }

    @Test
    public void mainTestShortTextForDebug(){
        Main.main(new String[]{"Thi", "Ti"});
    }
}

