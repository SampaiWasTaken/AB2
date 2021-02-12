package ab2.impl.PRUELLERRADLER;

public class PDATransition implements ab2.PDATransition
{
    private int from;
    private int to;
    private Character read;
    private Character readStack;
    private Character writeStack;

    public PDATransition(int from, int to, Character read, Character readStack, Character writeStack)
    {
        this.from = from;
        this.to = to;
        this.read = read;
        this.readStack = readStack;
        this.writeStack = writeStack;
    }

    @Override
    public Character symbolRead()
    {
        return read;
    }

    @Override
    public Character symbolStackRead()
    {
        return readStack;
    }

    @Override
    public Character symbolStackWrite()
    {
        return writeStack;
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
    public String toString()
    {
        return "[from: " + from + " | to: " + to + " | with: " + symbolRead() + " | read from Stack: " + readStack + " | write on Stack: " + writeStack + "]";
    }
}
