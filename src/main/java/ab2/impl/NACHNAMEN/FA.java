package ab2.impl.NACHNAMEN;

import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.RSA;

import java.util.Set;

public class FA implements ab2.FA
{
    private int numStates;
    private Set<Character> characters;
    private Set<Integer> acceptingStates;
    private Set<FATransition> transitions;

    //kek

    @Override
    public Set<Character> getSymbols()
    {
        return characters;
    }

    @Override
    public Set<Integer> getAcceptingStates()
    {
        return null;
    }

    @Override
    public boolean isAcceptingState(int s) throws IllegalStateException
    {
        return false;
    }

    @Override
    public Set<? extends FATransition> getTransitions()
    {
        return null;
    }

    @Override
    public int getNumStates()
    {
        return 0;
    }

    @Override
    public ab2.FA union(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA intersection(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA minus(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA concat(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA complement()
    {
        return null;
    }

    @Override
    public ab2.FA kleeneStar()
    {
        return null;
    }

    @Override
    public ab2.FA plus()
    {
        return null;
    }

    @Override
    public RSA toRSA()
    {
        return null;
    }

    @Override
    public boolean accepts(String w) throws IllegalCharacterException
    {
        return false;
    }

    @Override
    public boolean acceptsNothing()
    {
        return false;
    }

    @Override
    public boolean acceptsEpsilonOnly()
    {
        return false;
    }

    @Override
    public boolean acceptsEpsilon()
    {
        return false;
    }

    @Override
    public boolean isInfinite()
    {
        return false;
    }

    @Override
    public boolean isFinite()
    {
        return false;
    }

    @Override
    public boolean subSetOf(ab2.FA a)
    {
        return false;
    }

    @Override
    public boolean equalTo(ab2.FA b)
    {
        return false;
    }

    @Override
    public Boolean equalsPlusAndStar()
    {
        return null;
    }
}
