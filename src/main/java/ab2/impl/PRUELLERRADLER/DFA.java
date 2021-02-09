package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
import ab2.FA;
import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.RSA;

import java.util.Set;

public class DFA implements ab2.DFA {

    private int numStates;
    private Set<Character> characters;
    private Set<Integer> acceptingStates;
    private Set<ab2.DFATransition> transitions;
    private int currentState;

    public DFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions) {
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
    public int getActState() {
        return currentState;
    }

    @Override
    public int doStep(char c) throws IllegalArgumentException, IllegalStateException {
        return 0;
    }

    @Override
    public Integer getNextState(int s, char c) throws IllegalCharacterException, IllegalStateException {
        return null;
    }

    @Override
    public boolean isAcceptingState() {
        if (acceptingStates.contains(currentState))
            return true;
        return false;
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
        if (s > acceptingStates.size() - 1)
            throw new IllegalStateException("State does not exist.");

        if (acceptingStates.contains(s))
            return true;
        return false;
    }

    @Override
    public Set<ab2.DFATransition> getTransitions() {
        return transitions;
    }

    @Override
    public int getNumStates() {
        return numStates;
    }

    @Override
    public FA union(FA a) {
        return null;
    }

    @Override
    public FA intersection(FA a) {
        return null;
    }

    @Override
    public FA minus(FA a) {
        return null;
    }

    @Override
    public FA concat(FA a) {
        return null;
    }

    @Override
    public FA complement() {
        return null;
    }

    @Override
    public FA kleeneStar() {
        return null;
    }

    @Override
    public FA plus() {
        return null;
    }

    @Override
    public RSA toRSA() {
        return null;
    }

    @Override
    public boolean accepts(String w) throws IllegalCharacterException {
        if (w.contains("\\.[]{}()<>*+-=!?^$|"))
            throw new IllegalCharacterException();

        if (w == "")
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
        if (acceptingStates.contains(currentState))
            return true;
        return false;
    }

    @Override
    public boolean acceptsNothing() {
        if (acceptingStates.isEmpty())
        return true;
        for (int i : acceptingStates)
        {
            if (reaches(0, i))
                return false;
        }
        return true;
    }

    @Override
    public boolean acceptsEpsilonOnly() {
        if (numStates == 1 && acceptingStates.contains(0))
            return true;
        else return false;
    }

    @Override
    public boolean acceptsEpsilon() {
        if (acceptingStates.contains(0))
            return true;
        else return false;
    }

    @Override
    public boolean isInfinite() {
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
    public boolean isFinite() {
        return !isInfinite();
    }

    @Override
    public boolean subSetOf(FA a) {
        return false;
    }

    @Override
    public boolean equalTo(FA b) {
        return false;
    }

    @Override
    public Boolean equalsPlusAndStar() {
        return null;
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
