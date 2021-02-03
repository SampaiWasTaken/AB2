package ab2;

public interface PDA {
	/**
	 * Prüft, ob eine Eingbe von dem PDA akzeptiert wird (dh Teil der Sprache des PDA ist)
	 *
	 * @throws IllegalArgumentException der Input aus nicht erlaubten Zeichen besteht
	 * @throws IllegalStateException    falls numStates, inputChars oder stackChars nicht gesetzt wurden
	 */
	boolean accepts(String input) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Erzeugt einen neuen PDA, indem an den PDA (this) der überegebene PDA
	 * angehängt wird, sodass die akzeptierte Sprache des zurückgegebenen PDAs der
	 * Konkatenation der Sprachen der beiden PDAs entspricht. Keiner der beiden PDAs
	 * darf verändert werden. es muss ein neuer PDA erzeugt werden.
	 *
	 * @throws IllegalArgumentException falls numStates, inputChars oder stackChars des übergebenen PDA nicht gesetzt wurden
	 * @throws IllegalStateException    falls numStates, inputChars oder stackChars nicht gesetzt wurden
	 */
	PDA append(PDA pda) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Erzeugt einen neuen PDA, indem der PDA (this) und der überegebene PDA
	 * vereinigt werden. Die Sprache des zurückgegebenen PDAs entspricht der
	 * Vereinigung der Sprachen der beiden PDAs. Keiner der beiden PDAs darf
	 * verändert werden. es muss ein neuer PDA erzeugt werden.
	 *
	 * @throws IllegalArgumentException falls numStates, inputChars oder stackChars des übergebenen PDA nicht gesetzt wurden
	 * @throws IllegalStateException    falls numStates, inputChars oder stackChars nicht gesetzt wurden
	 */
	PDA union(PDA pda) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Gibt an, ob der PDA ein DPDA ist.
	 *
	 * @throws IllegalStateException falls numStates, inputChars oder stackChars von zumindest einem PDA nicht gesetzt wurden
	 */
	boolean isDPDA() throws IllegalStateException;
}
