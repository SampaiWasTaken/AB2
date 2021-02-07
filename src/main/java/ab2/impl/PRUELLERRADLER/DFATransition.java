package ab2.impl.PRUELLERRADLER;

public class DFATransition implements ab2.DFATransition {
    private int from;
    private int to;
    private char symbol;

    public DFATransition(int from, int to, char symbol) {
        this.from = from;
        this.to = to;
        this.symbol = symbol;
    }


    public char symbol() {
        return symbol;
    }



    public int from() {
        return from;
    }


    public int to() {
        return to;
    }


    public String symbols() { //jo gibts halt bei DFA nicht mehr...
        return null;
    }
}
