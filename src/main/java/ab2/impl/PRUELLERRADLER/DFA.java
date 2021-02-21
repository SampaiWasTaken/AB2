package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
import ab2.FA;
import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.RSA;

import java.util.HashSet;
import java.util.Set;

public class DFA implements ab2.DFA
{

    private int numStates;
    private final Set<Character> characters;
    private final Set<Integer> acceptingStates;
    private final Set<ab2.DFATransition> transitions;
    private int currentState;

    public DFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions)
    {
        this.numStates = numStates;
        this.characters = characters;
        this.acceptingStates = acceptingStates;
        this.transitions = transitions;
    }

    @Override
    public void reset()
    {
        this.currentState = 0;
    }

    @Override
    public int getActState()
    {
        return currentState;
    }

    @Override
    public int doStep(char c) throws IllegalArgumentException, IllegalStateException
    {
        return 0;
    }

    @Override
    public Integer getNextState(int s, char c) throws IllegalCharacterException, IllegalStateException
    {
        return null;
    }

    @Override
    public boolean isAcceptingState()
    {
        return acceptingStates.contains(currentState);
    }

    @Override
    public Set<Character> getSymbols()
    {
        return characters;
    }

    @Override
    public Set<Integer> getAcceptingStates()
    {
        return acceptingStates;
    }

    @Override
    public boolean isAcceptingState(int s) throws IllegalStateException
    {
        if (s > acceptingStates.size() - 1)
            throw new IllegalStateException("State does not exist.");

        return acceptingStates.contains(s);
    }

    @Override
    public Set<ab2.DFATransition> getTransitions()
    {
        return transitions;
    }

    @Override
    public int getNumStates()
    {
        return numStates;
    }

    @Override
    public FA union(FA a)
    {
        return null;
    }

    @Override
    public FA intersection(FA a)
    {
        return null;
    }

    @Override
    public FA minus(FA a)
    {
        return null;
    }

    @Override
    public FA concat(FA a)
    {
        return null;
    }

    @Override
    public FA complement()
    {
        return null;
    }

    @Override
    public ab2.FA kleeneStar()
    {
        Set<ab2.FATransition> _transitions = new HashSet<>();
        for (int i : acceptingStates)
        {
            _transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
        }
        this.acceptingStates.add(0);
        return new ab2.impl.PRUELLERRADLER.FA(this.numStates, this.characters, this.acceptingStates, _transitions);
    }

    @Override
    public ab2.FA plus()
    {
        Set<ab2.FATransition> _transitions = new HashSet<>();
        for (int i : acceptingStates)
        {
            _transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
            numStates++;
        }

        return new ab2.impl.PRUELLERRADLER.FA(this.numStates, this.characters, this.acceptingStates, _transitions);
    }

    @Override
    public RSA toRSA()
    {
        return null;
    }

    @Override
    public boolean accepts(String w) throws IllegalCharacterException
    {
        if (w.contains("\\.[]{}()<>*+-=!?^$|"))
            throw new IllegalCharacterException();

        if (w.equals(""))
            return acceptsEpsilon();

        char[] word = w.toCharArray();
        int currentState = 0;
        int charCounter = 0;

        while (charCounter < word.length - 1)
        {
            for (FATransition tr : transitions)
            {

                if (tr.from() == currentState && (tr.symbols().equals("") || tr.symbols().equals("" + word[charCounter])))
                {
                    currentState = tr.to();
                    charCounter++;
                }
            }
            if (charCounter == 0) return false;
        }
        return acceptingStates.contains(currentState);
    }

    @Override
    public boolean acceptsNothing()
    {
        if (acceptingStates.isEmpty())
            return true;
        for (int i : acceptingStates)
        {
            if (reaches(0, i))
                return true;
        }
        return false;
    }

    @Override
    public boolean acceptsEpsilonOnly()
    {
        return numStates == 1 && acceptingStates.contains(0);
    }

    @Override
    public boolean acceptsEpsilon()
    {
        return acceptingStates.contains(0);
    }

    @Override
    public boolean isInfinite()
    {
        boolean infinite = false;
        boolean loop = false;
        for (FATransition tr : transitions)
        {
            for (FATransition _tr : transitions)
            {
                if (tr.to() == _tr.from() && tr.from() == _tr.to())
                    loop = true;
            }
        }
        if (loop)
        {
            for (int i : acceptingStates)
                if (reaches(0, i))
                    infinite = true;
        }
        return infinite;
    }

    @Override
    public boolean isFinite()
    {
        return !isInfinite();
    }

    @Override
    public boolean subSetOf(FA a)
    {
        return this.toRSA().subSetOf(a);
    }

    @Override
    public boolean equalTo(FA b)
    {
        return this.toRSA().equalTo(b);
    }

    @Override
    public Boolean equalsPlusAndStar()
    {
        return this.toRSA().equalsPlusAndStar();
    }

    public boolean reaches(int from, int to)
    {
        int currentState = from;
        boolean found = false;
        FATransition prevState;
        for (FATransition tr : transitions)
        {
            if (tr.from() == currentState)
            {
                prevState = tr;
                for (FATransition _tr : transitions)
                {
                    if (currentState == _tr.from() && to == _tr.to())
                        found = true;
                }
                currentState = tr.to();
            }
        }
        return found;
    }
}
