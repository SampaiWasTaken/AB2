package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
import ab2.FA;
import ab2.FATransition;
import ab2.IllegalCharacterException;

import java.util.HashSet;
import java.util.Set;

public class RSA implements ab2.RSA
{

    private int numStates;
    private Set<Character> characters;
    private Set<Integer> acceptingStates;
    private Set<ab2.DFATransition> transitions;
    private int currentState;


    public RSA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<ab2.DFATransition> transitions)
    {
        this.numStates = numStates;
        this.characters = characters;
        this.acceptingStates = acceptingStates;
        this.transitions = transitions;
    }

    @Override
    public ab2.RSA minimize()
    {

        Set<Set<Integer>> minimizeTable = new HashSet<>(); // so nit

        //getting all the states that aren't accepting states
        Set<Integer> notAcceptingStates = new HashSet<>();
        for(ab2.DFATransition trans : transitions){
            for(Integer acceptingState : acceptingStates){
                if(trans.from() != acceptingState){
                    notAcceptingStates.add(trans.from());
                }
            }
        }

        //ceating the quantity with tuples of accapting and not accapting states
        Set<Set<Integer>> allCombinations = new HashSet<>();
        for(Integer i : notAcceptingStates){
            for(Integer j : notAcceptingStates){
                Set<Integer> combination = new HashSet<>();
                combination.add(i);
                combination.add(j);
                allCombinations.add(combination);
            }
        }




        return null;
    }

    @Override
    public void reset()
    {
        this.currentState = 0;
    }

    @Override
    public int getActState()
    {
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
    public int doStep(char c) throws IllegalArgumentException, IllegalStateException
    {
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
    public Integer getNextState(int s, char c) throws IllegalCharacterException, IllegalStateException
    {
        //checks if char c is part of our accepting characters
        boolean charNotFound = true;
        for (Character ch : characters)
        {
            if (ch.equals(c)) charNotFound = false;
        }
        if (charNotFound) throw new IllegalCharacterException();

        //checks if State s is in our "State table?"
        boolean stateNotFound = true;
        for (ab2.DFATransition ts : transitions)
        {
            if (ts.to() == s || ts.from() == s) stateNotFound = false;
        }
        if (stateNotFound) throw new IllegalStateException();


        for (ab2.DFATransition ts : transitions)
        {
            if (ts.from() == s)
            {
                if (ts.symbol() == c) return ts.to();
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
    public boolean isAcceptingState()
    {
        for (Integer state : acceptingStates)
        {
            if (state == currentState) return true;
        }
        return false;
    }

    @Override
    public Set<Character> getSymbols()
    {
        return characters;
    }

    @Override
    public Set<Integer> getAcceptingStates()
    {
        return acceptingStates;
    }

    /**
     * @param s zu testender Zustand
     * @throws IllegalArgumentException Wenn es den Zustand nicht gibt
     */
    @Override
    public boolean isAcceptingState(int s) throws IllegalStateException
    {
        if (s > acceptingStates.size() - 1)
            throw new IllegalStateException("State does not exist.");

        if (acceptingStates.contains(s))
            return true;
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
    public Set<DFATransition> getTransitions()
    {
        return transitions;
    }

    @Override
    public int getNumStates()
    {
        return numStates;
    }

    @Override
    public FA union(FA a)
    {
        return null;
    }

    @Override
    public FA intersection(FA a)
    {
        return null;
    }

    @Override
    public FA minus(FA a)
    {
        return null;
    }

    @Override
    public FA concat(FA a)
    {
        return null;
    }

    @Override
    public FA complement()
    {
        return null;
    }

    @Override
    public ab2.FA kleeneStar()
    {
        Set<ab2.FATransition> _transitions = new HashSet<>();
        for (int i : acceptingStates)
        {
            _transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
            numStates++;
        }
        this.acceptingStates.add(0);
        return new ab2.impl.PRUELLERRADLER.FA(this.numStates, this.characters, this.acceptingStates, _transitions);
    }

    @Override
    public ab2.FA plus()
    {
        Set<ab2.FATransition> _transitions = new HashSet<>();
        for (int i : acceptingStates)
        {
            _transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
            numStates++;
        }

        return new ab2.impl.PRUELLERRADLER.FA(this.numStates, this.characters, this.acceptingStates, _transitions);
    }

    //rsa to rsa? na echt nit XD
    @Override
    public ab2.RSA toRSA()
    {
        return this;
    } //genau so geht des XD

    @Override
    public boolean accepts(String w) throws IllegalCharacterException
    {
        if (w.contains("\\.[]{}()<>*+-=!?^$|"))
            throw new IllegalCharacterException();

        if (w == "")
            return acceptsEpsilon();

        char[] word = w.toCharArray();
        int currentState = 0;
        int charCounter = 0;

        while (charCounter < word.length - 1)
        {
            for (FATransition tr : transitions)
            {

                if (tr.from() == currentState && (tr.symbols().equals("") || tr.symbols().equals("" + word[charCounter])))
                {
                    currentState = tr.to();
                    charCounter++;
                }

            }
            if (charCounter == 0) return false;
        }
        if (acceptingStates.contains(currentState))
            return true;
        return false;
    }

    @Override
    public boolean acceptsNothing()
    {
        if (acceptingStates.isEmpty())
            return true;
        for (int i : acceptingStates)
        {
            if (reaches(0, i))
                return true;
        }
        return false;
    }

    @Override
    public boolean acceptsEpsilon()
    {
        if (acceptingStates.contains(0))
            return true;
        else return false;
    }

    @Override
    public boolean acceptsEpsilonOnly()
    {
        if (acceptingStates.contains(0))
            for (int i = 0; i < numStates; i++)
            {
                if (!reaches(0, i))
                    return true;
            }
        return false;
    }

    @Override
    public boolean isInfinite()
    {
        boolean infinite = false;
        boolean loop = false;
        for (FATransition tr : transitions)
        {
            for (FATransition _tr : transitions)
            {
                if (tr.to() == _tr.from() && tr.from() == _tr.to())
                    loop = true;
            }
        }
        if (loop)
        {
            for (int i : acceptingStates)
                if (reaches(0, i))
                    infinite = true;
        }
        return infinite;
    }

    @Override
    public boolean isFinite()
    {
        return !isInfinite();
    }

    @Override
    public boolean subSetOf(FA a)
    {
        return false;
    }

    @Override
    public boolean equalTo(FA b)
    {
        return true;
    }

    @Override
    public Boolean equalsPlusAndStar()
    {
        return null;
    }

    public boolean reaches(int from, int to)
    {
        int currentState = from;
        boolean found = false;
        FATransition prevState;
        for (FATransition tr : transitions)
        {
            if (tr.from() == currentState)
            {
                prevState = tr;
                for (FATransition _tr : transitions)
                {
                    if (currentState == _tr.from() && to == _tr.to())
                        found = true;
                }
                currentState = tr.to();
            }
        }
        return found;
    }

    @Override
    public String toString() {
        String ReturnString = "RSA{" +
                "\nnumStates=" + numStates +
                "\n, characters=" + characters +
                "\n, acceptingStates=" + acceptingStates +
                "\n, currentState=" + currentState;
        for(ab2.DFATransition tra : transitions){
            ReturnString += "\n"+tra;
        }
        return ReturnString;

    }
}
