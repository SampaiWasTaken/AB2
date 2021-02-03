package ab2;

public interface Transition {

	/**
	 * Index des Zustandes, von dem die Transition weg führt
	 */
	int from();

	/**
	 * Index des Zustandes, zu dem die Transition weg führt
	 */
	int to();
}
