package ab2.impl.PRUELLERRADLER;

import ab2.*;

import java.util.Set;

public class FactoryImpl implements Factory {

	@Override
	public FATransition createTransition(int from, int to, String symbols) {
		return null;
	}

	@Override
	public PDATransition createTransition(int from, int to, Character read, Character readStack, Character writeStack) {
		return null;
	}

	@Override
	public FA createFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<FATransition> transitions) {
		return null;
	}

	@Override
	public DFA createDFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions) {
		return null;
	}

	@Override
	public RSA createRSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions) {
		return null;
	}

	@Override
	public RSA createPatternMatcher(String pattern) {
		return null;
	}

	@Override
	public PDA createPDA(int numStates, Set<Character> inputSymbols, Set<Character> stackSymbols, Set<Integer> acceptingStates, Set<PDATransition> transitions) {
		return null;
	}

	@Override
	public PDA getPDAFromCFG(char startSymbol, Set<String> rules) {
		return null;
	}
}
