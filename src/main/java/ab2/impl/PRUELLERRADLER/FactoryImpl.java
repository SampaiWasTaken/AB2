package ab2.impl.PRUELLERRADLER;

import ab2.*;
import ab2.DFA;
import ab2.DFATransition;
import ab2.FA;

import java.util.Set;

public class FactoryImpl{


	public FATransition createTransition(int from, int to, String symbols)
	{
		return new FATransition(from, to, symbols);
	}


	public PDATransition createTransition(int from, int to, Character read, Character readStack, Character writeStack) {
		return null;
	}


	public FA createFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<FATransition> transitions) {
		return new ab2.impl.PRUELLERRADLER.FA(numStates, characters, acceptingStates, transitions);
	}



	public DFA createDFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions) {
		return null;
	}


	public RSA createRSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<ab2.impl.PRUELLERRADLER.DFATransition> transitions) {
		return new RSA(numStates, characters, acceptingStates, transitions, 0);
	}



	public RSA createPatternMatcher(String pattern) {
		return null;
	}


	public PDA createPDA(int numStates, Set<Character> inputSymbols, Set<Character> stackSymbols, Set<Integer> acceptingStates, Set<PDATransition> transitions) {
		return null;
	}


	public PDA getPDAFromCFG(char startSymbol, Set<String> rules) {
		return null;
	}
}
