package ab2.impl.PRUELLERRADLER;

public class FATransition implements ab2.FATransition
{
    private int from;
    private int to;
    private String symbols;

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
}
