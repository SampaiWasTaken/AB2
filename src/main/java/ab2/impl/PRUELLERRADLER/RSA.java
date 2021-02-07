package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
import ab2.FA;
import ab2.IllegalCharacterException;

import java.util.Set;

public class RSA implements ab2.RSA {

    private int numStates;
    private Set<Character> characters;
    private Set<Integer> acceptingStates;
    private Set<ab2.DFATransition> transitions;
    private int startingState;  //keine ahnung wie ich sonst den startedent zustand speichern soll, wenn er mal nicht 0 ist
    private int currentState;


    public RSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<ab2.DFATransition> transitions, int startingState) {
        this.numStates = numStates;
        this.characters = characters;
        this.acceptingStates = acceptingStates;
        this.transitions = transitions;
        this.startingState = startingState;
        this.currentState = startingState;
    }

    @Override
    public ab2.RSA minimize() {
        return null;
    }

    @Override
    public void reset() {

        this.currentState = this.startingState;
    }

    @Override
    public int getActState() {
        return this.currentState;
    }

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
    @Override
    public int doStep(char c) throws IllegalArgumentException, IllegalStateException {
        return getNextState(currentState, c);
    }

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
    @Override
    public Integer getNextState(int s, char c) throws IllegalCharacterException, IllegalStateException {
        //checks if char c is part of our accepting characters
        boolean charNotFound = true;
        for(Character ch : characters){
            if(ch.equals(c))charNotFound = false;
        }
        if(charNotFound) throw new IllegalCharacterException();

        //checks if State s is in our "State table?"
        boolean stateNotFound = true;
        for(ab2.DFATransition ts : transitions){
            if(ts.to() == s || ts.from() == s)stateNotFound = false;
        }
            if(stateNotFound)throw new IllegalStateException();


        for(ab2.DFATransition ts : transitions){
            if (ts.from() == s){
                if(ts.symbol() == c)return ts.to();
            }
        }
        return null;
    }

    /**
     * Liefert true, wenn der aktuelle Zustand ein akzeptierender Zustand ist.
     *
     * @return true, wenn der Zustand s ein Endzustand ist. Ansonsten false.
     */
    @Override
    public boolean isAcceptingState() {
        for(Integer state : acceptingStates){
            if(state == currentState)return true;
        }
        return false;
    }

    @Override
    public Set<Character> getSymbols() {
        return getSymbols();
    }

    @Override
    public Set<Integer> getAcceptingStates() {
        return acceptingStates;
    }

    /**
     * @param s zu testender Zustand
     * @throws IllegalArgumentException Wenn es den Zustand nicht gibt
     */
    @Override
    public boolean isAcceptingState(int s) throws IllegalStateException {

        boolean stateNotFound = true;
        for(ab2.DFATransition ts : transitions){
            if(ts.to() == s || ts.from() == s)stateNotFound = false;
        }
        if(stateNotFound)throw new IllegalStateException();


        for(Integer state : acceptingStates){
            if(state == s)return true;
        }
        return false;
    }

    /**
     * Liefert die Transitionsmatrix. Jeder Eintrag der Matrix ist eine Menge,
     * welche die Wörter angibt, die für diesen Übergang definiert sind. Das Wort ""
     * entspricht dem leeren Wort.
     *
     * @return Die Transiationsmatrix mit allen Übergängen
     */
    @Override
    public Set<DFATransition> getTransitions() {
        return transitions;
    }

    @Override
    public int getNumStates() {
        return numStates;
    }

    @Override
    public FA union(FA a) {
        return null;
    }

    @Override
    public FA intersection(FA a) {
        return null;
    }

    @Override
    public FA minus(FA a) {
        return null;
    }

    @Override
    public FA concat(FA a) {
        return null;
    }

    @Override
    public FA complement() {
        return null;
    }

    @Override
    public FA kleeneStar() {
        return null;
    }

    @Override
    public FA plus() {
        return null;
    }


    //rsa to rsa? na echt nit
    @Override
    public ab2.RSA toRSA() {
        return null;
    }

    @Override
    public boolean accepts(String w) throws IllegalCharacterException {
        return false;
    }

    @Override
    public boolean acceptsNothing() {
        return false;
    }

    @Override
    public boolean acceptsEpsilonOnly() {
        return false;
    }

    @Override
    public boolean acceptsEpsilon() {
        return false;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public boolean isFinite() {
        return false;
    }

    @Override
    public boolean subSetOf(FA a) {
        return false;
    }

    @Override
    public boolean equalTo(FA b) {
        return false;
    }

    @Override
    public Boolean equalsPlusAndStar() {
        return null;
    }
}
