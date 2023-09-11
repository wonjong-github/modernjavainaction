package chap9;

public class HeaderTextProcessing extends ProcessingObject<String> {
    @Override
    protected String handleWork(String input) {
        return "From Roul, Mario and Alan" + input;
    }
}