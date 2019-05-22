package search;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String args[]){
        if (args == null || args.length != 2) {
            System.out.println("usage: {original text} {cleaned text}");
        } else if (args[0].equals("help")){
            System.out.println("usage: {original text} {cleaned text}");
        } else {
            Searcher searcher = new Searcher(args[0], args[1]);
            Displayer displayer = new Displayer(searcher.execute(), args[0], args[1]);
            displayer.displayToConsole();
        }
    }

}
