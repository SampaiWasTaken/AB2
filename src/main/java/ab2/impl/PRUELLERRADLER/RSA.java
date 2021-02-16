package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
import ab2.FA;
import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
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
    //jo i denk mal i werd des später im toRSA fixen dass kein fresszustand mehr rum fliegt XD
    /*
    public void removeSINGLE_UnreachableStates(){
        Set<ab2.DFATransition> transitionsCopy = new HashSet<>();
        for(DFATransition trans : transitions){
            transitionsCopy.add(trans);
        }
        for(DFATransition currentState : transitions){
            boolean removeState =true;
            for(DFATransition trans : transitions){
                if(currentState.from() != trans.from()){
                   // if(currentState.from()){

                    }
                }
            }
        }


    }

     */
    public RSA reOrderRSA_States (int startingNumber){
        Set<TransitionRename> newNames = new HashSet<>();
        int counter = startingNumber;
        boolean onlyOncePerState0 = true;
        for(DFATransition trans : transitions){
            if(trans.from() == 0 && onlyOncePerState0){
                newNames.add(new TransitionRename(trans.from(), counter++));
                onlyOncePerState0 = false;
            }
        }
        for(DFATransition trans : transitions){
            if(trans.from() != 0){
                boolean alreadyInside = false;
                for(TransitionRename tr : newNames){
                    if(tr.getFrom() == trans.from()) alreadyInside = true;

                }
               if(!alreadyInside) newNames.add(new TransitionRename(trans.from(), counter++));
            }
        }

        Set<Integer> newAcceptingStates = new HashSet<>();
        for(Integer i : acceptingStates){
            boolean onlyOncePerState = true;
            for(TransitionRename tr : newNames){
                if(tr.getFrom() == i && onlyOncePerState){
                    newAcceptingStates.add(tr.getTo());
                    onlyOncePerState = false;
                }
            }
        }

        Set<DFATransition> newTransitions = new HashSet<>();
        for(DFATransition trans : transitions){
            int newfrom = 0;
            int newto = 0;
            for(TransitionRename tr : newNames){
                if(trans.from() == tr.getFrom()){
                    newfrom = tr.getTo();
                }
                if(trans.to() == tr.getFrom()){
                    newto = tr.getTo();
                }

            }
            newTransitions.add(new ab2.impl.PRUELLERRADLER.DFATransition(newfrom, newto, trans.symbol()));
        }

        return new RSA(this.numStates, this.characters, newAcceptingStates, newTransitions);
    }

    @Override
    public ab2.RSA minimize()
    {
        //getting all the states that aren't accepting states
        Set<Integer> notAcceptingStates = new HashSet<>();
        for(ab2.DFATransition trans : transitions){
            boolean isAcceptingState = false;
            for(Integer acceptingState : acceptingStates){
                if(trans.from() == acceptingState){
                    isAcceptingState = true;

                }
            }
            if(!isAcceptingState)notAcceptingStates.add(trans.from());
        }

        //ceating the quantity with tuples of accapting and not accapting states
        Set<Set<Integer>> allCombinations = new HashSet<>();
        for(Integer i : notAcceptingStates){
            for(Integer j : notAcceptingStates){
                Set<Integer> combination = new HashSet<>();
                combination.add(i);
                combination.add(j);
                if(combination.size()!=1 && combination.size() < 3)allCombinations.add(combination);
            }
        }
        for(Integer i : acceptingStates){
            for(Integer j : acceptingStates){
                Set<Integer> combination = new HashSet<>();
                combination.add(i);
                combination.add(j);
                if(combination.size()!=1 && combination.size() < 3)allCombinations.add(combination);
            }
        }
               // System.out.println("accapting states " + acceptingStates);
       // System.out.println("not accapting states "+notAcceptingStates);
      //  System.out.println(allCombinations);

        Set<Set<Integer>> oldAllcombinations = new HashSet<>();
        Set<Set<Integer>> newSimplifiedTable = new HashSet<>();
        for(Set<Integer> i : allCombinations){
            oldAllcombinations.add(i);
        }
        boolean nothingToSimplify = false;
        while (!nothingToSimplify){

            newSimplifiedTable = simplifyMinimizingTable(oldAllcombinations);
            if(oldAllcombinations.equals(newSimplifiedTable)){
                nothingToSimplify =true;
            }else {
                oldAllcombinations = new HashSet<>();
                for(Set<Integer> i : newSimplifiedTable){
                    oldAllcombinations.add(i);
                }
            }
        }

      //  System.out.println("FFFFFFFFFFFFFFFFUUUUCK die zustände sind gleich "+newSimplifiedTable);
        Set<Set<Integer>> reDoneNewSimplifiedTable = new HashSet<>();
            for(Set<Integer> oneDoubleEntry : newSimplifiedTable){
                Set<Integer> newSimplifiedEntry = new HashSet<>();
                for(Set<Integer> oneDoubleEntry2 : newSimplifiedTable){
                    for (int oneEntry : oneDoubleEntry){
                        if(oneDoubleEntry2.contains(oneEntry)){
                            newSimplifiedEntry.addAll(oneDoubleEntry2);
                        }
                    }
                }
                reDoneNewSimplifiedTable.add(newSimplifiedEntry);
            }
        newSimplifiedTable = reDoneNewSimplifiedTable;


        ArrayList<ArrayList<Set<Integer>>> newTable = new ArrayList<>();

        for(Set<Integer> currentSteps : newSimplifiedTable){
            ArrayList<Set<Integer>> tableEntry = new ArrayList<>();

            for(char character : characters){
                Set<Integer> currentChar = new HashSet<>();
                for(Integer currentStep : currentSteps){
                    for(DFATransition trans : transitions){
                        if(trans.from() == currentStep && trans.symbol() == character){
                           // System.out.println(trans);
                            currentChar.add(trans.to());
                        }
                    }
                }
                tableEntry.add(currentChar);
                //System.out.println("current char"+currentChar);
            }
            newTable.add(tableEntry);
        }
       // System.out.println("FINAL ENTRY IN THE TABLE WHICH I NEED TO COMBINE" +newTable + newSimplifiedTable);

     //   if(newSimplifiedTable.size() == 0){
       //     return this;
      //  }

        //calculate new State numbers
        int newStepNames = transitions.size();

        for(Set<Integer> i : newSimplifiedTable){

        }

        //adding all transitions
        Set<DFATransition> finalTransitions = new HashSet<>();
        Set<Integer> finalAcceptingStates = new HashSet<>();


        Set<TransitionRename> renamedTransitions = new HashSet<>();
        for(DFATransition trans : transitions){
            boolean mustBeCombined = false;
            boolean alreadyCombined = false;
            for(Set<Integer> leftEntry : newSimplifiedTable){
                for(int oneEntry : leftEntry){
                    if(oneEntry == trans.from()){
                        mustBeCombined = true;
                    }
                }
            }

            int counter = numStates;
            if(mustBeCombined){
                int counterPlus = 0;
                for(Set<Integer> i: newSimplifiedTable){
                    if(i.contains(trans.from())){
                        renamedTransitions.add(new TransitionRename(trans.from(), counter+counterPlus));
                        for(int oneAcceptingState : acceptingStates){
                            if(oneAcceptingState == trans.from()){
                                finalAcceptingStates.add(counter+counterPlus);
                            }
                        }

                    }
                    counterPlus++;
                }

            }else {
                renamedTransitions.add(new TransitionRename(trans.from(), trans.from()));
                for(int oneAcceptingState : acceptingStates){
                    if(oneAcceptingState == trans.from()){
                        finalAcceptingStates.add(trans.from());
                    }
                }
            }

        }
        //check if 0 state got renamed XD ufff
        boolean ZeroRenamedUfff = false;
        for(TransitionRename tr : renamedTransitions){
            if(tr.getFrom() == 0){
                ZeroRenamedUfff = true;
            }
        }
        if(ZeroRenamedUfff){
            int oldZeroState = 0;
            for(TransitionRename tr : renamedTransitions){
                if(tr.getFrom() == 0){
                    oldZeroState = tr.getTo();
                }
            }
            for(TransitionRename tr : renamedTransitions){
                if(tr.getTo() == oldZeroState){
                    tr.setTo(0);
                }
            }
            //also renaming the "old" accepting state
            Set<Integer> newNewFinalLastAcceptedStates = new HashSet<>();
            for(Integer i : finalAcceptingStates){
               if(i == oldZeroState){
                   newNewFinalLastAcceptedStates.add(0);
               }else {
                   newNewFinalLastAcceptedStates.add(i);
               }
            }
            finalAcceptingStates = newNewFinalLastAcceptedStates;
        }


        for(DFATransition trans : transitions){
            int to = 0;
            int from = 0;
            for(TransitionRename tr : renamedTransitions){
                if(trans.from() == tr.getFrom()){
                    from = tr.getTo();
                }
                if(trans.to() == tr.getFrom()){
                    to = tr.getTo();
                }
            }
                finalTransitions.add(new ab2.impl.PRUELLERRADLER.DFATransition(from, to, trans.symbol()));
        }
       // System.out.println(renamedTransitions);
       // System.out.println("finalAcceptingStates"+finalAcceptingStates);
       // System.out.println(finalTransitions);

        //count the different states
        Set<Integer> allStates = new HashSet<>();
        for(ab2.DFATransition trans : finalTransitions){
            allStates.add(trans.to());
            allStates.add(trans.from());
        }

       // System.out.println("################################### final number of states: "+allStates.size());
        RSA returnRSA = new RSA(allStates.size(), characters, finalAcceptingStates, finalTransitions);
        if(this.transitions.equals(finalTransitions) && this.acceptingStates.equals(finalAcceptingStates) && this.numStates == allStates.size()){
            return returnRSA.reOrderRSA_States(0);
        }else {
            return returnRSA.reOrderRSA_States(0).minimize();
        }

    }

    private Set<Set<Integer>> simplifyMinimizingTable (Set<Set<Integer>> oldTable){

        ArrayList<ArrayList<Set<Integer>>> newTable = new ArrayList<>();

        for(Set<Integer> currentSteps : oldTable){
            ArrayList<Set<Integer>> tableEntry = new ArrayList<>();

            for(char character : characters){
                Set<Integer> currentChar = new HashSet<>();
                for(Integer currentStep : currentSteps){
                    for(DFATransition trans : transitions){
                        if(trans.from() == currentStep && trans.symbol() == character){
                           // System.out.println(trans);
                            currentChar.add(trans.to());
                        }
                    }
                }
                tableEntry.add(currentChar);
               // System.out.println("current char"+currentChar);
            }
            newTable.add(tableEntry);
        }


        ArrayList<Set<Integer>> oldTableCopy = new ArrayList<>();
        Set<Set<Integer>> newFinalUltimateTable = new HashSet<>();
        for(Set<Integer> content : oldTable){
            oldTableCopy.add(content);
        }

       // System.out.println("OLD TABLE   "+ oldTableCopy);

       // System.out.println("new TABLE CONTETN    "+newTable);
        for(int j = 0; j<oldTableCopy.size();j++){
            ArrayList<Boolean> isNotInsideBoolean2 = new ArrayList<>();
            for (int i = 0; i<newTable.get(j).size(); i++){
               // System.out.println("dings da "+newTable.get(j));
                ArrayList<Boolean> singlePartBoolean = new ArrayList<>();
                for(Set<Integer> mainStateLeftFirstColumn : oldTableCopy){
                        if((newTable.get(j).get(i).equals(mainStateLeftFirstColumn)) || newTable.get(j).get(i).size()==1){
                            singlePartBoolean.add(true);
                        }else {
                            singlePartBoolean.add(false);
                        }
                }
                boolean singlePartPart = false;
                for(boolean Part : singlePartBoolean){
                    singlePartPart = singlePartPart || Part;
                }
                isNotInsideBoolean2.add(singlePartPart);
            }
            //System.out.println("boolean "+isNotInsideBoolean2);
            boolean notInside = true;
            for(boolean Part : isNotInsideBoolean2){
                notInside = notInside && Part;
            }

            if(notInside){
                newFinalUltimateTable.add(oldTableCopy.get(j));
            }
        }

       // System.out.println("old Table copy Final    "+newFinalUltimateTable);

        Set<Set<Integer>> resutlSetSet = new HashSet<>();
        for(Set<Integer> i : newFinalUltimateTable){
            resutlSetSet.add(i);
        }
        return resutlSetSet;
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
        RSA secondFA = (RSA)a.toRSA().minimize();
        RSA firstFA = (RSA)this.minimize();
        secondFA = secondFA.reOrderRSA_States(firstFA.getNumStates()+1);

        firstFA = (RSA)firstFA.reOrderRSA_States(1);

        //neue transitions
        Set<FATransition> newTransitions = new HashSet<>();
        for(DFATransition tra: firstFA.transitions){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(tra.from(), tra.to(), ""+tra.symbol()));
        }
        for(DFATransition tra: secondFA.getTransitions()){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(tra.from(), tra.to(), ""+tra.symbol()));
        }

        //neuen EZ
        Set<Integer> newAcceptingStates = new HashSet<>();
        newAcceptingStates.addAll(firstFA.acceptingStates);
        newAcceptingStates.addAll(secondFA.acceptingStates);

        //neues alphabet
        Set<Character> newAlphabet = new HashSet<>();
        newAlphabet.addAll(firstFA.characters);
        newAlphabet.addAll(secondFA.getSymbols());

        //neuen Startzustnd + 2 epsilon Uebergeange
        newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(0, 1, ""));
        newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(0, firstFA.getNumStates()+1, ""));

        //neue numStates berechnen
        int newNumStates = 1;
        newNumStates += firstFA.numStates;
        newNumStates += secondFA.getNumStates();

        return new ab2.impl.PRUELLERRADLER.FA(newNumStates, newAlphabet, newAcceptingStates, newTransitions);
    }

    @Override
    public FA intersection(FA a)
    {
        return this.complement().union(a.complement()).complement();
    }

    @Override
    public FA minus(FA a)
    {
        return this.intersection(a.complement());
    }

    @Override
    public FA concat(FA a)
    {
        RSA secondFA = (RSA)a.toRSA().minimize();
        RSA firstFA = (RSA)this.minimize();
        secondFA = secondFA.reOrderRSA_States(firstFA.getNumStates());


        //neue transitions
        Set<FATransition> newTransitions = new HashSet<>();
        for(DFATransition tra: firstFA.transitions){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(tra.from(), tra.to(), ""+tra.symbol()));
        }
        for(DFATransition tra: secondFA.getTransitions()){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(tra.from(), tra.to(), ""+tra.symbol()));
        }

        //neuen EZ
        Set<Integer> newAcceptingStates = new HashSet<>();
        newAcceptingStates.addAll(secondFA.acceptingStates);

        //neues alphabet
        Set<Character> newAlphabet = new HashSet<>();
        newAlphabet.addAll(firstFA.characters);
        newAlphabet.addAll(secondFA.getSymbols());

        //neuen Startzustnd + 2 epsilon Uebergeange
        for(Integer acceptingState : firstFA.acceptingStates){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(acceptingState, firstFA.numStates, ""));
        }


        //neue numStates berechnen
        int newNumStates = 0;
        newNumStates += firstFA.numStates;
        newNumStates += secondFA.getNumStates();


        return new ab2.impl.PRUELLERRADLER.FA(newNumStates, newAlphabet, newAcceptingStates, newTransitions);
    }

    @Override
    public FA complement()
    {
        //creating new Accepting States
        Set<Integer> newAcceptingStates = new HashSet<>();
        for(DFATransition tr : transitions){
            if(!acceptingStates.contains(tr.from())){
                newAcceptingStates.add(tr.from());
            }
            if(!acceptingStates.contains(tr.to())){
                newAcceptingStates.add(tr.to());
            }
        }

        //create new Transitions which are FATransitions
        Set<FATransition> newTransitions = new HashSet<>();
        for(ab2.DFATransition tr : transitions){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(tr.from(), tr.to(),""+tr.symbol()));
        }

        return new ab2.impl.PRUELLERRADLER.FA(this.numStates, this.characters, newAcceptingStates, newTransitions);
    }

    @Override
    public ab2.FA kleeneStar()
    {
        RSA firstRSA = this.reOrderRSA_States(1);
        Set<ab2.FATransition> newTransitions = new HashSet<>();
        for(DFATransition tr : firstRSA.transitions){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(tr.from(), tr.to(), ""+tr.symbol()));
        }
        for(Integer i : firstRSA.acceptingStates){
            newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 1, ""));
        }

        newTransitions.add(new ab2.impl.PRUELLERRADLER.FATransition(0, 1, ""));

        Set<Integer> newAcceptingStates = new HashSet<>();
        newAcceptingStates.addAll(firstRSA.acceptingStates);
        newAcceptingStates.add(0);


        return new ab2.impl.PRUELLERRADLER.FA(this.numStates+1,characters, newAcceptingStates, newTransitions);
        /*
        Set<ab2.FATransition> _transitions = new HashSet<>();
        for (int i : acceptingStates)
        {
            _transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
            numStates++;
        }
        this.acceptingStates.add(0);
        return new ab2.impl.PRUELLERRADLER.FA(this.numStates, this.characters, this.acceptingStates, _transitions);

         */
    }

    @Override
    public ab2.FA plus()
    {
        //turn this.concat(this.kleeneStar());

        Set<ab2.FATransition> _transitions = new HashSet<>();
        for (int i : acceptingStates)
        {
            _transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
        }

        for(DFATransition tr: transitions){
            _transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(tr.from(), tr.to(), ""+tr.symbol()));
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

        while (charCounter < word.length)
        {
            boolean finishedThisTransition = false;
            for (DFATransition tr : transitions)
            {
                if(!finishedThisTransition){
                    if (tr.from() == currentState && tr.symbol() == word[charCounter])
                    {
                        currentState = tr.to();
                        charCounter++;
                        finishedThisTransition = true;

                    }
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
        if (acceptsEpsilon())
            return false;
        boolean accepting = false;
        if (acceptingStates.isEmpty())
            return true;
        for (int i : acceptingStates)
        {
            Set<ab2.impl.PRUELLERRADLER.DFATransition> trans = new HashSet<>();
            if (reaches(0, i, trans, false, 0))
            {
                accepting = false;
                break;
            }
            else
                accepting = true;
        }
        return accepting;
    }

    @Override
    public boolean acceptsEpsilon()
    {
        if (acceptingStates.contains(0))
            return true;
        else{
            return false;
        }


    }

    @Override
    public boolean acceptsEpsilonOnly()
    {
        if (!acceptsEpsilon())
            return false;
        if (acceptsNothing())
            return false;

        for (int i : acceptingStates)
        {
            if (i != 0)
            {
                if (reaches(0, i, new HashSet<>(), false, 0))
                {
                    return false;
                }
            }
        }
        for (char c : getSymbols())
        {
            for (DFATransition tr : transitions)
            {
                if (tr.equals(new ab2.impl.PRUELLERRADLER.DFATransition(0, 0, c)))
                    return false;
            }
        }

        return true;
    }

    @Override
    public boolean isInfinite()
    {
        boolean infinite = false;
        boolean loop = false;
        Set<Integer> possibleFroms = new HashSet<>();
        Set<Integer> alreadyREACHED = new HashSet<>();
        for (FATransition tr : transitions) {
            if(!alreadyREACHED.contains(tr.from())){
                alreadyREACHED.add(tr.from());
                if (reaches(tr.from(), tr.from(), new HashSet<>(), false, 0)) {
                    possibleFroms.add(tr.from());
                }
            }

        }

        //now checking if possibleFroms are reachable from 0 State
        Set<Integer> possibleFromsfrom0 = new HashSet<>();
        for (Integer i : possibleFroms)
            if (reaches(0, i, new HashSet<>(), false, 0)){
                possibleFromsfrom0.add(i);
            }

        //now checks if possibleFromsfrom0 can reach a accepting state
        for (Integer i : possibleFromsfrom0){
            for(Integer acceptingState : acceptingStates){
                if (reaches(i, acceptingState, new HashSet<>(), false, 0)){
                    return true;
                }
            }
        }
        return false;
        /* OLD SHITTY IS INFINITE
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
                if (reaches(0, i, new HashSet<>(), false, 0))
                    infinite = true;
        }
        return infinite;

         */
    }

    @Override
    public boolean isFinite()
    {
        return !isInfinite();
    }

    @Override
    public boolean subSetOf(FA a)
    {
        if (a.acceptsEpsilon())
            return true;
        if (a == this)
            return true;
        if (a.getTransitions().isEmpty() || this.transitions.isEmpty())
            return true;
        return false;
    }

    @Override
    public boolean equalTo(FA b) {
        RSA firstRSA = (RSA) this.minimize();
        RSA secondRSA = (RSA) b.toRSA().minimize();

        if (firstRSA.numStates != secondRSA.numStates) return false;
        if (firstRSA.acceptingStates.size() != secondRSA.acceptingStates.size()) return false;
        if (firstRSA.isFinite() != secondRSA.isFinite()) return false;
        if(!firstRSA.characters.equals(secondRSA.characters)) return false;
        if(firstRSA.transitions.size() != secondRSA.transitions.size()) return false;

        ArrayList<ab2.DFATransition> firstTransitions = new ArrayList<>();
        ArrayList<ab2.DFATransition> secondTransitions = new ArrayList<>();
        Set<TransitionRename> transRenam = new HashSet<>();
        /*
        for(DFATransition trans : firstRSA.transitions){
            boolean inside = false;
            for(DFATransition trans2 : secondRSA.transitions){
                    if(trans.equals(trans2)){
                        inside = true;
                    }
            }
            if(!inside)return false;
        }
        return true;
         */
        //first add 0 states


        transRenam.add(new TransitionRename(0, 0));
        boolean grabbingRenames = true;
        while (grabbingRenames) {
            Set<TransitionRename> oldTransR = new HashSet<>();
            oldTransR.addAll(transRenam);
            int oldRenameCount = oldTransR.size();
            for (int i = 0; i < firstRSA.numStates; i++) {
                for (char c : characters) {
                    for (ab2.DFATransition trans : firstRSA.transitions) {
                        if (trans.from() == i && trans.symbol() == c) {
                            for (ab2.DFATransition trans2 : secondRSA.transitions) {
                                for (TransitionRename tr : oldTransR) {
                                    if (tr.getFrom() == i && tr.getTo() == trans2.from() && trans.symbol() == c && trans2.symbol() == c) {
                                        if(trans.from() == trans.to() && trans2.from() == trans.from()){
                                            if(trans2.from() != trans2.to())return false;
                                        }
                                        if(trans.from() != trans.to() && trans2.from() == trans.from()){
                                            if(trans2.from() == trans2.to())return false;
                                        }
                                        transRenam.add(new TransitionRename(trans.to(), trans2.to()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (oldRenameCount == transRenam.size()) {
                grabbingRenames = false;
            }
        }

        //Transition Rename Set finished
        //generating new RSA with the newly named transitions

        Set<DFATransition> newtransi = new HashSet<>();
        for(DFATransition transi : secondRSA.getTransitions()){
            int from = 0;
            int to = 0;
            for(TransitionRename tr : transRenam){
                if(transi.from() == tr.getFrom()){
                    from = tr.getTo();
                }
                if(transi.to() == tr.getFrom()){
                    to = tr.getTo();
                }
            }
            newtransi.add(new ab2.impl.PRUELLERRADLER.DFATransition(from, to, transi.symbol()));
        }

        boolean allTransitionsTheSame = true;
        for(DFATransition transi : firstRSA.transitions){
            boolean singleTransi = false;
            for(DFATransition transi2 : newtransi){
                if(transi.from() == transi2.from() && transi.to() == transi2.to() && transi.symbol() == transi2.symbol())singleTransi = true;
            }
            allTransitionsTheSame = allTransitionsTheSame || singleTransi;
        }
        if(allTransitionsTheSame)return true; else return false;





    }

    @Override
    public Boolean equalsPlusAndStar()
    {

        return this.plus().toRSA().equalTo(this.kleeneStar());
    }

    public boolean reaches(int from, int to, Set<ab2.impl.PRUELLERRADLER.DFATransition> prevState, boolean reached, int count)
    {
        Set<ab2.impl.PRUELLERRADLER.DFATransition> copiedTransitions = new HashSet<>();
        if(count > 20)return false;
        for(DFATransition tra : prevState){
            copiedTransitions.add((ab2.impl.PRUELLERRADLER.DFATransition) tra);
        }
        for (DFATransition tr : transitions)
        {
            if (reached || count > 200)
                break;
            else if (tr.from() == from && tr.to() == to)
            {
                //System.out.println(tr.toString());
                return true;
            }
            else if (tr.from() == from && !copiedTransitions.contains(tr))
            {
                //System.out.println(tr.toString());
                count ++;
                copiedTransitions.add((ab2.impl.PRUELLERRADLER.DFATransition) tr);
                reached = reaches(tr.to(), to, copiedTransitions, reached, count);
            }
        }
        return false || reached;
    }
    public boolean reaches(int from, int to)
    {

        return reaches(from, to, new HashSet<>(), false, 0);
    }

    @Override
    public String toString() {
        String ReturnString = "RSA{" +
                "numStates=" + numStates +
                ", characters=" + characters +
                ", acceptingStates=" + acceptingStates +
                ", currentState=" + currentState;
        for(ab2.DFATransition tra : transitions){
            ReturnString += ""+tra;
        }
        return ReturnString;

    }
}
