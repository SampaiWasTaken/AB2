package ab2.impl.PRUELLERRADLER;

public class Transition implements ab2.Transition
{
    private int from;
    private int to;

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
