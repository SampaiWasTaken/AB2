package ab2.impl.PRUELLERRADLER;

public class Transition implements ab2.Transition
{
    private final int from;
    private final int to;

    public Transition(int from, int to)
    {
        this.from = from;
        this.to = to;
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
