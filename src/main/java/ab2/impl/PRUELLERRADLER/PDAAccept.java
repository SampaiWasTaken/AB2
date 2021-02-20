package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
import ab2.PDATransition;

import java.util.*;

public class PDAAccept
{
    private final int numStates = 1;
    private final Set<Stack<Character>> possibleStacks = new HashSet<>();
    private final Set<Integer> possibleStates = new HashSet<>();
    private final Set<Set<PDATransition>> yikes = new HashSet<>();

    //give list of possible stacks. give list of transitions?????????????????????????????
    public void calcSteps(Set<ab2.PDATransition> transitions, Stack<Character> stack, String word, int charCounter)
    {
        char[] tokens = word.toCharArray();
        for (char c : tokens)
        {
            yikes.add(nextPossibleSteps(0, transitions, c, stack));
        }
        System.out.println(yikes);
        possibleStacks.add(stack);
        for (Set<ab2.PDATransition> a : yikes)
        {
            for (ab2.PDATransition b : a)
            {
                    possibleStacks.add(modStack(b, stack));
            }
        }
        System.out.println(possibleStacks);
    }

    public Set<ab2.PDATransition> nextPossibleSteps(int currentState, Set<ab2.PDATransition> transitions, char c, Stack<Character> stack)
    {
        Set<ab2.PDATransition> possibleTransitions = new HashSet<>();
        for (ab2.PDATransition tr : transitions)
        {
            if (tr.from() == currentState && tr.symbolRead() == c && (tr.symbolStackRead() == null || (!stack.isEmpty() && stack.peek() == tr.symbolStackRead())))
                possibleTransitions.add(tr);
        }
        return possibleTransitions;
    }

    public Stack<Character> modStack(ab2.PDATransition transition, Stack<Character> stack)
    {
        Stack<Character> newStack = new Stack<>();
        newStack = (Stack<Character>) stack.clone();
        if (transition.symbolStackRead() == null)
            newStack.push(transition.symbolStackWrite());
        else if (!newStack.isEmpty() && transition.symbolStackRead() == newStack.peek())
        {
            newStack.pop();
            newStack.push(transition.symbolStackWrite());
        }
        return newStack;
    }

    public int getCurrentState()
    {
        int currentState = 0;
        return currentState;
    }

    public Set<Stack<Character>> getStack()
    {
        return possibleStacks;
    }

    public Set<Integer> getPossibleStates()
    {
        return possibleStates;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Can reach: ");
        for (int i : possibleStates)
            sb.append(i + ", ");
        sb.append("with Stacks: ");
        for (Stack<Character> s : possibleStacks)
            sb.append("[" + Arrays.deepToString(s.toArray()) + "], ");
        return sb.toString();
    }
}
