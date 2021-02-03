package ab2;

import java.util.Set;

public interface FA {
	/**
	 * @return Menge der möglichen Zeichen
	 */
	Set<Character> getSymbols();

	/**
	 * @return Menge der Endzustände
	 */
	Set<Integer> getAcceptingStates();

	/**
	 * @param s zu testender Zustand
	 * @throws IllegalArgumentException Wenn es den Zustand nicht gibt
	 */
	boolean isAcceptingState(int s) throws IllegalStateException;

	/**
	 * Liefert alle Transitionen.
	 */
	Set<? extends FATransition> getTransitions();

	/**
	 * Liefert die Anzahl der Zustände des Automaten
	 */
	int getNumStates();


	/**
	 * Erzeugt einen Automaten, der die Vereinigung des Automaten mit dem übergebenen Automaten darstellt.
	 *
	 * @param a der zu vereinigende Automat
	 */
	FA union(FA a);

	/**
	 * Erzeugt einen Automaten, der den Durchschnitt des Automaten mit dem übergebenen Automaten darstellt.
	 *
	 * @param a der zu schneidende Automat
	 */
	FA intersection(FA a);

	/**
	 * Erzeugt die Differenz zu dem übergebenen Automaten
	 */
	FA minus(FA a);

	/**
	 * Hängt den Automaten a an den Automaten an
	 *
	 * @param a der anzuhängende Automat
	 */
	FA concat(FA a);

	/**
	 * Erzeugt das Komplement des Automaten
	 */
	FA complement();

	/**
	 * Bildet den Kleene-Stern des Automaten
	 */
	FA kleeneStar();

	/**
	 * Erzeugt L(a)+
	 */
	FA plus();

	/**
	 * Erzeugt einen RSA, der die selbe Sprache akzeptiert
	 */
	RSA toRSA();

	/**
	 * Prüft, ob das Wort w durch den Automaten akzeptiert wird.
	 *
	 * @param w - das abzuarbeitende Wort
	 * @return true, wenn das Wort w akzeptiert wird, andernfalls false.
	 * @throws IllegalCharacterException falls das Wort aus nicht erlaubten Zeichen besteht
	 */
	boolean accepts(String w) throws IllegalCharacterException;

	/**
	 * Überprüft, ob der Automat nichts (die leere Menge) aktzeptiert.
	 */
	boolean acceptsNothing();

	/**
	 * Überprüft, ob der Automat nur das leere Wort aktzeptiert
	 */
	boolean acceptsEpsilonOnly();

	/**
	 * Überprft, ob der Automat das leere Wort akzeptiert (und womöglich auch mehr).
	 */
	boolean acceptsEpsilon();

	/**
	 * Überprüft, ob der Automat unendlich viele Wörter akzeptiert.
	 */
	boolean isInfinite();

	/**
	 * Überprüft, ob der Automat nur endlich viele Wörter akzeptiert.
	 */
	boolean isFinite();

	/**
	 * Überprüft, ob die Sprache des Automaten eine Teilmenge der Sprache des Automaten a ist
	 *
	 * @param a endlicher Automat
	 */
	boolean subSetOf(FA a);

	/**
	 * Überprüft, ob der übergebene Automat die selbe Sprache akzeptiert
	 */
	boolean equalTo(FA b);

	/**
	 * Überprüft, ob L+ = L*
	 */
	Boolean equalsPlusAndStar();
}
