package ab2;

public interface PDATransition extends Transition {
	/**
	 * Nächstes Symbol des Eingabewortes
	 */
	Character symbolRead();

	/**
	 * Symbol, welches vom Stack gelesen wird
	 */
	Character symbolStackRead();

	/**
	 * Symbol, welches auf den Stack geschrieben wird
	 */
	Character symbolStackWrite();
}
