package ab2.impl.PRUELLERRADLER;

import java.util.Objects;

public class FATransition implements ab2.FATransition
{
    private final int from;
    private final int to;
    private final String symbols;

    public FATransition(int from, int to, String symbols)
    {
        this.from = from;
        this.to = to;
        this.symbols = symbols;
    }

    @Override
    public String symbols()
    {
        return symbols;
    }

    @Override
    public int from()
    {
        return from;
    }

    @Override
    public int to()
    {
        return to;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FATransition that = (FATransition) o;
        return from == that.from &&
                to == that.to &&
                Objects.equals(symbols, that.symbols);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(from, to, symbols);
    }

    @Override
    public String toString()
    {
        return "[from: " + from + " | to: " + to + " | with: " + symbols +  "]";
    }
}
