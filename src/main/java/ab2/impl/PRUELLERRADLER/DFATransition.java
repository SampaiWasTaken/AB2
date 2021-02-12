package ab2.impl.PRUELLERRADLER;

import java.util.Objects;

public class DFATransition implements ab2.DFATransition
{
    private int from;
    private int to;
    private char symbol;

    public DFATransition(int from, int to, char symbol)
    {
        this.from = from;
        this.to = to;
        this.symbol = symbol;
    }


    public char symbol()
    {
        return symbol;
    }


    public int from()
    {
        return from;
    }


    public int to()
    {
        return to;
    }

    @Override
    public String toString() {
        return "DFATransition{" +
                "from=" + from +
                ", to=" + to +
                ", symbol=" + symbol +
                '}'+"\n";
    }

    public String symbols()
    { //jo gibts halt bei DFA nicht mehr...
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DFATransition that = (DFATransition) o;
        return from == that.from &&
                to == that.to &&
                symbol == that.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, symbol);
    }
}

