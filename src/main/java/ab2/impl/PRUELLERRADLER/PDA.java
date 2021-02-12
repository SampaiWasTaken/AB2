package ab2.impl.PRUELLERRADLER;

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
        stack = new Stack<>();
        char[] characters = input.toCharArray();
        int currentState = 0;
        boolean wordRead = false;

        for (int i = 0; i < characters.length; i++)
        {
            for (ab2.PDATransition tr : transitions)
            {
                if(tr.symbolRead() == characters[i] && !stack.empty() && tr.symbolStackRead() == stack.peek())
                {
                    stack.pop();
                    stack.push(tr.symbolStackWrite());
                    currentState = tr.to();
                    break;
                }
                else if (tr.symbolRead() == characters[i] && tr.symbolStackRead() == null)
                {
                    stack.push(tr.symbolStackWrite());
                    currentState = tr.to();
                    break;
                }
            }
        }

        if (acceptingStates.contains(currentState) && stack.isEmpty() && wordRead)
            return true;
        else
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
}
