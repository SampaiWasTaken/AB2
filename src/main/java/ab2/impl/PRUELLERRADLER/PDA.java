package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.Factory;
import ab2.PDATransition;

import java.util.*;

public class PDA implements ab2.PDA
{
    private Stack<Character> stack = new Stack<>();
    private int numStates;
    private final Set<Character> inputSymbols;
    private final java.util.Set<Character> stackSymbols;
    private final Set<Integer> acceptingStates;
    private final Set<ab2.PDATransition> transitions;

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
        if (!input.matches("[\\w]*"))
            throw new IllegalArgumentException();
        if (numStates == 0 || stackSymbols == null || input == null)
            throw new IllegalStateException();
        List<ab2.PDATransition> newTrans = new ArrayList<>();
        newTrans.addAll(transitions);

        return accepts(input, 0, input.length(), newTrans, false, new Stack<>());
    }

    /**
     * Erzeugt einen neuen PDA, indem an den PDA (this) der überegebene PDA
     * angehängt wird, sodass die akzeptierte Sprache des zurückgegebenen PDAs der
     * Konkatenation der Sprachen der beiden PDAs entspricht. Keiner der beiden PDAs
     * darf verändert werden. es muss ein neuer PDA erzeugt werden.
     */
    @Override
    public ab2.PDA append(ab2.PDA pda) throws IllegalArgumentException, IllegalStateException
    {
        PDA parsePDA = (PDA) pda;

        if (parsePDA.getNumStates() == 0 || parsePDA.getInputSymbols() == null || parsePDA.getStackSymbols() == null)
            throw new IllegalArgumentException();
        if (this.numStates == 0 || this.inputSymbols == null || this.stackSymbols == null)
            throw new IllegalStateException();
        Set<Integer> newAcc = new HashSet<>();
        Set<PDATransition> newTrans = new HashSet<>();
        int pda2start = this.numStates;

        int newNumstates = this.numStates + parsePDA.getNumStates();

        for (int i : parsePDA.getAcceptingStates())
        {
            newAcc.add(i + pda2start);
        }
        newTrans.addAll(this.transitions);
        newTrans.add(new ab2.impl.PRUELLERRADLER.PDATransition(this.numStates - 1, pda2start, null, null, null));

        for (ab2.PDATransition tr : parsePDA.getTransitions())
        {
            newTrans.add(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from() + pda2start, tr.to() + pda2start, tr.symbolRead(), tr.symbolStackRead(), tr.symbolStackWrite()));
        }

        return new PDA(newNumstates, inputSymbols, stackSymbols, newAcc, newTrans);
    }

    /**
     * Erzeugt einen neuen PDA, indem der PDA (this) und der überegebene PDA
     * vereinigt werden. Die Sprache des zurückgegebenen PDAs entspricht der
     * Vereinigung der Sprachen der beiden PDAs. Keiner der beiden PDAs darf
     * verändert werden. es muss ein neuer PDA erzeugt werden.
     *
     * @throws IllegalArgumentException falls numStates, inputChars oder stackChars des übergebenen PDA nicht gesetzt wurden
     * @throws IllegalStateException    falls numStates, inputChars oder stackChars nicht gesetzt wurden
     */
    @Override
    public ab2.PDA union(ab2.PDA pda) throws IllegalArgumentException, IllegalStateException
    {
        PDA parsePDA = (PDA) pda;

        if (parsePDA.getNumStates() == 0 || parsePDA.getInputSymbols() == null || parsePDA.getStackSymbols() == null)
            throw new IllegalArgumentException();
        if (this.numStates == 0 || this.inputSymbols == null || this.stackSymbols == null)
            throw new IllegalStateException();

        Set<Integer> newAcc = new HashSet<>();
        Set<PDATransition> newTrans = new HashSet<>();
        int pda2start = this.numStates + 1;
        int pdastart = 1;
        int newNumstates = this.numStates + parsePDA.getNumStates() + 1;

        for (int i : acceptingStates)
            newAcc.add(i + pdastart);

        for (int i : parsePDA.getAcceptingStates())
            newAcc.add(i + pda2start);

        newTrans.add(new ab2.impl.PRUELLERRADLER.PDATransition(0, pda2start, null, null, null));
        newTrans.add(new ab2.impl.PRUELLERRADLER.PDATransition(0, pdastart, null, null, null));

        for (ab2.PDATransition tr : transitions)
            newTrans.add(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from() + pdastart, tr.to() + pdastart, tr.symbolRead(), tr.symbolStackRead(), tr.symbolStackWrite()));

        for (ab2.PDATransition tr : parsePDA.getTransitions())
            newTrans.add(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from() + pda2start, tr.to() + pda2start, tr.symbolRead(), tr.symbolStackRead(), tr.symbolStackWrite()));

        return new PDA(newNumstates, inputSymbols, stackSymbols, newAcc, newTrans);
    }

    @Override
    public boolean isDPDA() throws IllegalStateException
    {
        if (this.numStates == 0 || this.inputSymbols == null || this.stackSymbols == null)
            throw new IllegalStateException();

        boolean morethanone = false;
        Stack<Character> stack = new Stack<>();
        Set<Character> chars = new HashSet<>();
        Set<PDATransition> possTrans = new HashSet<>();
        chars.add('a');
        chars.add('b');
        chars.add('c');

        for (char c : chars)
        {
            for (PDATransition tr : transitions)
            {
                if (tr.symbolRead() == c)
                {
                    for (char s : chars)
                    {
                        for (int i = 0; i < numStates; i++)
                        {
                            if (tr.symbolStackRead() == null)
                            {
                                if (transitions.contains(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from(), i, c, s, c)) ||
                                        transitions.contains(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from(), i, c, s, null)) ||
                                        transitions.contains(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from(), i, c, null, null)))
                                {
                                    morethanone = true;
                                }
                            }

                            else if (tr.symbolStackRead() == c)
                            {
                                if (transitions.contains(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from(), i, null, s, c)) ||
                                        transitions.contains(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from(), i, null, s, null)) ||
                                        transitions.contains(new ab2.impl.PRUELLERRADLER.PDATransition(tr.from(), i, c, null, null)))
                                {
                                    morethanone = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return !morethanone;
    }

    public PDA simplify()
    {
        int acceptingState = 0;
        Set<Integer> newAccept = new HashSet<>();

        for (int i : acceptingStates)
        {
            transitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(i, numStates, null, null, null));
        }
        acceptingStates.clear();
        acceptingStates.add(numStates);
        acceptingState = acceptingStates.iterator().next();
        numStates++;

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
        acceptingStates.add(numStates - 1);
        acceptingState = acceptingStates.iterator().next();

        System.out.println("# States: " + numStates + " | Accepting: " + acceptingState + " | " + transitions.size() + " | " + Arrays.deepToString(transitions.toArray()));
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

    //counter set to word.length at init to avoid complications as input gets changed within method and called with differen input ever time
    public boolean accepts(String input, int currentState, int counter, List<PDATransition> newTransitions,
                           boolean accepted, Stack<Character> stack1)
    {

        Stack<Character> newStack = new Stack<>();
        newStack.clear();
        newStack.addAll(stack1);
        int newCounter = counter;
        if (newStack.isEmpty() && input.isBlank())
        {
            for (int i : acceptingStates)
                if (currentState == i)
                {
                    accepted = true;
                    return true;
                }
                else
                {
                    for (PDATransition tr : newTransitions)
                    {
                        if (tr.equals(new ab2.impl.PRUELLERRADLER.PDATransition(currentState, i, null, null, null)))
                        {
                            accepted = true;
                            return true;
                        }
                    }
                }
        }
        else if (counter <= 0)
            return false;

        for (PDATransition tr : newTransitions)
        {
            newStack.clear();
            newStack.addAll(stack1);
            if (accepted || counter == 0)
                break;
            if ((tr.symbolRead() == null || input.charAt(0) == tr.symbolRead()) && tr.from() == currentState)
            {
                if (newStack.isEmpty())
                {
                    if (tr.symbolStackRead() == null)
                    {
                        if (tr.symbolRead() == null)
                            accepted = accepts(input, tr.to(), counter, newTransitions, accepted, newStack);
                        else
                        {
                            if (tr.symbolStackWrite() != null)
                                newStack.push(tr.symbolStackWrite());
                            accepted = accepts(input.substring(1), tr.to(), counter - 1, newTransitions, accepted, newStack);
                        }
                    }
                }
                else if ((tr.symbolStackRead() == null || newStack.peek() == tr.symbolStackRead()) && (tr.symbolRead() == null || tr.symbolRead() == input.charAt(0)))
                {
                    if (tr.symbolRead() == null)
                        accepted = accepts(input, tr.to(), counter, newTransitions, accepted, newStack);
                    else
                    {
                        if (tr.symbolStackRead() != null)
                            newStack.pop();
                        if (tr.symbolStackWrite() != null)
                            newStack.push(tr.symbolStackWrite());
                        accepted = accepts(input.substring(1), tr.to(), counter - 1, newTransitions, accepted, newStack);
                    }
                }
            }
            if (newStack.isEmpty() && input.isBlank())
            {
                for (int i : acceptingStates)
                    if (currentState == i)
                    {
                        accepted = true;
                        return true;
                    }
            }
        }
        return accepted;
    }

//    public static Set<String> createTree(Set<String> rules, Set<String> possibleWords, int wordLength, int count)
//    {
//        Set<String> newPossWords = new HashSet<>();
//        // newPossWords.addAll(possibleWords);
//        String[] tokens = new String[2];
//
//        System.out.println(count);
//        if (count == 7) return possibleWords;
//
//        for (String s : rules)
//        {
//            tokens = s.split("→");
//            tokens = s.split("→");
//            if (s.equals("S")) { }
//            else
//            {
//                for (String words : possibleWords)
//                {
//                    if (words.contains(tokens[0]))
//                    {
//                        if (words.equals("S")) newPossWords.add(tokens[1]);
//                        else
//                        {
//                            String nicerString = (words.substring(0, words.indexOf(tokens[0])) + tokens[1] + words.substring(words.indexOf(tokens[0]) + 3, words.length()));
//                            newPossWords.add(nicerString);
//                        }
//                    }
//                }
//            }
//        }
//        return createTree(rules, newPossWords, wordLength, ++count);
//    }
}

