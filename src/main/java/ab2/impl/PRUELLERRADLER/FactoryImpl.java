package ab2.impl.PRUELLERRADLER;

import ab2.*;
import ab2.DFA;
import ab2.DFATransition;
import ab2.FA;
import ab2.FATransition;
import ab2.RSA;
import ab2.Transition;

import java.util.Set;

public class FactoryImpl implements Factory
{


    @Override
    public FATransition createTransition(int from, int to, String symbols)
    {
        return new ab2.impl.PRUELLERRADLER.FATransition(from, to, symbols);
    }

    @Override
    public PDATransition createTransition(int from, int to, Character read, Character readStack, Character writeStack)
    {
        return null;
    }

    @Override
    public FA createFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<FATransition> transitions)
    {
        return new ab2.impl.PRUELLERRADLER.FA(numStates, characters, acceptingStates, transitions);
    }

    @Override
    public DFA createDFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions)
    {
        return new ab2.impl.PRUELLERRADLER.DFA(numStates, characters, acceptingStates, transitions);
    }

    @Override
    public RSA createRSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions)
    {
        return new ab2.impl.PRUELLERRADLER.RSA(numStates, characters, acceptingStates, transitions);
    }

    @Override
    public RSA createPatternMatcher(String pattern)
    {
        return null;
    }

    @Override
    public PDA createPDA(int numStates, Set<Character> inputSymbols, Set<Character> stackSymbols, Set<Integer> acceptingStates, Set<PDATransition> transitions)
    {
        return null;
    }

    @Override
    public PDA getPDAFromCFG(char startSymbol, Set<String> rules)
    {
        return null;
    }
}
