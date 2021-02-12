package ab2.impl.PRUELLERRADLER;

import java.util.Objects;
import java.util.Set;

public class TransitionRename {
    private int from;
    private int to;

    public TransitionRename(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitionRename that = (TransitionRename) o;
        return from == that.from &&
                to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "\nTransitionRename{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
