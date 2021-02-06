package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.RSA;

import java.util.Iterator;
import java.util.Set;

public class FA implements ab2.FA
{
    private int numStates;
    private Set<Character> characters;
    private Set<Integer> acceptingStates;
    private Set<ab2.impl.PRUELLERRADLER.FATransition> transitions;

    public FA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<ab2.impl.PRUELLERRADLER.FATransition> transitions) {
        this.numStates = numStates;
        this.characters = characters;
        this.acceptingStates = acceptingStates;
        this.transitions = transitions;
    }

    @Override
    public Set<Character> getSymbols() {
        return characters;
    }

    @Override
    public Set<Integer> getAcceptingStates() {
        return acceptingStates;
    }

    @Override
    public boolean isAcceptingState(int s) throws IllegalStateException {
        if (acceptingStates.contains(s))
            return true;
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
        return numStates;
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
        //checks for epsilon transitions
        boolean hasEpsisonTransitions = false;
        for(ab2.impl.PRUELLERRADLER.FATransition trans : this.transitions){
            if(trans.symbols().equals("")) hasEpsisonTransitions = true;
        }

        if(hasEpsisonTransitions){
            //doing RSA convertion with Epsilon quantity


        }else {
            //doing simple RSA convertion without epsilon quantity


        }


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
        if (acceptingStates.isEmpty())
            return true;
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
        if (numStates == 1 && acceptingStates.contains(1))
            return true;
        else return false;
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
