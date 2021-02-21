package ab2.impl.PRUELLERRADLER;

import java.util.Objects;

public class PDATransition implements ab2.PDATransition
{
    private final int from;
    private final int to;
    private final Character read;
    private final Character readStack;
    private final Character writeStack;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDATransition that = (PDATransition) o;
        return from == that.from &&
                to == that.to &&
                Objects.equals(read, that.read) &&
                Objects.equals(readStack, that.readStack) &&
                Objects.equals(writeStack, that.writeStack);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(from, to, read, readStack, writeStack);
    }
}
