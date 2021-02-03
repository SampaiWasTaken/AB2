package ab2;

import java.util.Set;

public interface Factory {
	/**
	 * Erzeugt eine Transition eines endlichen Automaten
	 */
	FATransition createTransition(int from, int to, String symbols);

	/**
	 * Erzeugt eine Transition für einen PDA
	 */
	PDATransition createTransition(int from, int to, Character read, Character readStack, Character writeStack);

	/**
	 * Erzeugt einen FA. Die Zustände des Automaten sind 0-indiziert, dh der erste
	 * Zustand trägt den Wert 0. Der Startzustand ist der Zustannd mit dem Index 0
	 *
	 * @param numStates       Anzahl Zustände des Automaten
	 * @param characters      Mögliche Zeichenmenge
	 * @param acceptingStates Akzeptierender Zustand (0-indiziert)
	 * @param transitions     Transitionen des Automaten
	 * @return einen FA
	 */
	FA createFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<FATransition> transitions);

	/**
	 * Erzeugt einen DFA. Die Zustände des Automaten sind 0-indiziert, dh der erste
	 * Zustand trägt den Wert 0. Der Startzustand ist der Zustannd mit dem Index 0.
	 *
	 * @param numStates       Anzahl Zustände des Automaten
	 * @param characters      Mögliche Zeichenmenge
	 * @param acceptingStates Akzeptierender Zustand (0-indiziert)
	 * @param transitions     Transitionen des Automaten
	 * @return einen DFA
	 */
	DFA createDFA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions);

	/**
	 * Erzeugt einen RSA. Die Zustände des Automaten sind 0-indiziert, dh der erste
	 * Zustand trägt den Wert 0. Der Startzustand ist der Zustannd mit dem Index 0.
	 *
	 * @param numStates       Anzahl Zustände des Automaten
	 * @param characters      Mögliche Zeichenmenge
	 * @param acceptingStates Akzeptierender Zustand (0-indiziert)
	 * @param transitions     Transitionen des Automaten
	 * @return einen RSA
	 */
	RSA createRSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<DFATransition> transitions);

	/**
	 * Erzeugt einen RSA, der zur Suche des übergebenen Patterns in Texten verwendet
	 * werden kann. Das Muster darf nur aus Zeichen und den Symbolen * und .
	 * bestehen. Zudem dürfen runde Klammern verwenndet werden. Geschachtelte Klammern sind erlaubt.
	 * Das Symbol * bedeutet, dass das Zeichen bzw. der geklammerte Block davor beliebig oft
	 * vorkommen kann. Das Symbol . bedeutet, dass ein beliebiges Zeichen gelesen
	 * werden kann. Beispiele für Muster wären "abcd." oder "ab*cd". (ab)* bedeutet,
	 * dass ab beliebig oft wiederholt werden kann. Der Automat
	 * muss sich immer dann in einem akzeptierenden Zustand befinden, wenn der Text dem Muster entspricht.
	 *
	 * @param pattern Das Muster, nach welchem gesucht werden soll.
	 */
	RSA createPatternMatcher(String pattern);

	/**
	 * Pfeil-Trennzeichen für CFG
	 */
	String CFG_DELIMITER1 = "→";

	/**
	 * Oder-Trennzeichen für CFG
	 */
	String CFG_DELIMITER2 = "|";

	/**
	 * Erzeugt einen DFA. Die Zustände des Automaten sind 0-indiziert, dh der erste
	 * Zustand trägt den Wert 0. Der Startzustand ist der Zustannd mit dem Index 0.
	 */
	PDA createPDA(int numStates, Set<Character> inputSymbols, Set<Character> stackSymbols, Set<Integer> acceptingStates, Set<PDATransition> transitions);

	/**
	 * Erzeugt einen PDA ausgehend von der gegebenen Grammatik. Es sind nur Groß-
	 * und Kleinbuchstaben erlaubt. Großbuchstaben stellen Nichtterminale und
	 * Kleinbuchstaben Terminale dar. Die Regel sind der Form "S→aBcD|bde|ABA|SaS".
	 * Dh Auf das Nichtterminal links folgt ein "→" sowie weitere Terminale und
	 * Nichtterminale. Das leere Wort Epsilon wird als "" (dh leerer String)
	 * dargestellt. Eine entsprechende Regel wäre "S→" oder "S→A|"
	 */
	PDA getPDAFromCFG(char startSymbol, Set<String> rules);
}
