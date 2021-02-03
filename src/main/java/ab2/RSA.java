package ab2;

public interface RSA extends DFA {
	/**
	 * Erzeugt einen minimalen RSA, der die selbe Sprache akzeptiert
	 *
	 * @return minimaler Automat
	 */
	RSA minimize();
}
