package ab2.impl.PRUELLERRADLER;

import ab2.PDATransition;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class PDA implements ab2.PDA
{
    private Stack<Character> stack;
    private int numStates;
    private Set<Character> inputSymbols;
    private java.util.Set<Character> stackSymbols;
    private Set<Integer> acceptingStates;
    private Set<ab2.PDATransition> transitions;

    public PDA(int numStates, Set<Character> inputSymbols, Set<Character> stackSymbols, Set<Integer> acceptingStates, Set<ab2.PDATransition> transitions)
    {
        this.numStates = numStates;
        this.inputSymbols = inputSymbols;
        this.stackSymbols = stackSymbols;
        this.acceptingStates = acceptingStates;
        this.transitions = transitions;
    }

    @Override
    public boolean accepts(String input) throws IllegalArgumentException, IllegalStateException
    {
        return false;
    }

    @Override
    public ab2.PDA append(ab2.PDA pda) throws IllegalArgumentException, IllegalStateException
    {
        return null;
    }

    @Override
    public ab2.PDA union(ab2.PDA pda) throws IllegalArgumentException, IllegalStateException
    {
        return null;
    }

    @Override
    public boolean isDPDA() throws IllegalStateException
    {
        return false;
    }

    public PDA simplify()
    {
        int acceptingState = 0;
        Set<Integer> newAccept = new HashSet<>();


        if (acceptingStates.size() == 1) { }
        else
        {
            for (int i : acceptingStates)
            {
                transitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(i, numStates, null, null, null));
            }
            acceptingStates.clear();
            acceptingStates.add(numStates);
            acceptingState = acceptingStates.iterator().next();
            numStates++;
        }

        for (char c : stackSymbols)
        {
            transitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(acceptingState, acceptingState, null, c, null));
        }

        for (PDATransition tr : transitions)
        {
            if (tr.symbolStackRead() != null && tr.symbolStackWrite() != null)
            {
                transitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from(), ++numStates, tr.symbolRead(), null, tr.symbolStackWrite()));
                transitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(numStates, tr.to(), tr.symbolRead(), tr.symbolStackRead(), null));
                transitions.remove(tr);
            }
        }

        transitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(acceptingState, numStates++, null, null, null));
        acceptingStates.clear();
        acceptingStates.add(numStates-1);
        acceptingState = acceptingStates.iterator().next();

        System.out.println("# States: " + numStates + " | Accepting: " + acceptingState + " | " +  transitions.size() + " | " + Arrays.deepToString(transitions.toArray()));
        return new PDA(numStates, inputSymbols, stackSymbols, acceptingStates, transitions);
    }

    public Stack<Character> getStack()
    {
        return stack;
    }

    public int getNumStates()
    {
        return numStates;
    }

    public Set<Character> getInputSymbols()
    {
        return inputSymbols;
    }

    public Set<Character> getStackSymbols()
    {
        return stackSymbols;
    }

    public Set<Integer> getAcceptingStates()
    {
        return acceptingStates;
    }

    public Set<PDATransition> getTransitions()
    {
        return transitions;
    }
}
