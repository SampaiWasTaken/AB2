package ab2.impl.PRUELLERRADLER;

import java.util.Objects;
import java.util.Set;

public class EpsilonQuantity
{
    private int from;
    private Set<Integer> to;

    public EpsilonQuantity(int from, Set<Integer> to)
    {
        this.from = from;
        this.to = to;
    }

    public EpsilonQuantity(int from)
    {
        this.from = from;
    }

    public int getFrom()
    {
        return from;
    }

    public void setFrom(int from)
    {
        this.from = from;
    }

    public Set<Integer> getTo()
    {
        return to;
    }

    public void setTo(Set<Integer> to)
    {
        this.to = to;
    }

    @Override
    public String toString()
    {
        return "EpsilonQuantity{" +
                from +
                "-->" + to +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EpsilonQuantity that = (EpsilonQuantity) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(from, to);
    }
}
