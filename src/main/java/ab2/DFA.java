package ab2;

import java.util.Set;

public interface DFA extends FA {

	/**
	 * Setzt den Automaten wieder auf den Startzustand.
	 */
	void reset();

	/**
	 * @return den aktuell aktiven Zustand des Automaten
	 */
	int getActState();

	/**
	 * Veranlasst den Autoaten, ein Zeichen abzuarbeiten. Ausgehend vom aktuellen
	 * Zustand wird das Zeichen c abgearbeitet und der Automat befindet sich danach
	 * im Folgezustand. Ist das Zeichen c kein erlaubtes Zeichen, so wird eine
	 * IllegalArgumentException geworfen. Andernfalls liefert die Methode den neuen
	 * aktuellen Zustand. Ist kein Folgezustand definiert, wird eine
	 * IllegalStateException geworfen und der aktuell Zustand bleibt erhalten.
	 *
	 * @param c das abzuarbeitende Zeichen
	 * @return den aktuellen Zustand nach der Abarbeitung des Zeichens
	 * @throws IllegalArgumentException
	 */
	int doStep(char c) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Liefert den Zustand, der erreicht wird, wenn im Zustand s das Zeichen c
	 * gelesen wird. Ist das Zeichen c kein erlaubtes Zeichen, so wird eine
	 * IllegalArgumentException geworfen. Ist der Zustand s kein erlaubter Zustand,
	 * so wird eine IllegalStateException geworfen.
	 *
	 * @param s Zustand
	 * @param c Zeichen
	 * @return Folgezustand, oder null, wenn es keinen Folgezustand gibt
	 */
	Integer getNextState(int s, char c) throws IllegalCharacterException, IllegalStateException;

	/**
	 * Liefert true, wenn der aktuelle Zustand ein akzeptierender Zustand ist.
	 *
	 * @return true, wenn der Zustand s ein Endzustand ist. Ansonsten false.
	 */
	boolean isAcceptingState();

	/**
	 * Liefert die Transitionsmatrix. Jeder Eintrag der Matrix ist eine Menge,
	 * welche die Wörter angibt, die für diesen Übergang definiert sind. Das Wort ""
	 * entspricht dem leeren Wort.
	 *
	 * @return Die Transiationsmatrix mit allen Übergängen
	 */
	Set<DFATransition> getTransitions();
}
