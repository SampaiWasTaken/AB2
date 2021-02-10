package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.RSA;
import com.sun.source.tree.Tree;

import javax.print.attribute.SetOfIntegerSyntax;
import java.util.*;

public class FA implements ab2.FA
{
    private int numStates;
    private Set<Character> characters;
    private Set<Integer> acceptingStates;
    private Set<ab2.FATransition> transitions;

    public FA(int numStates, Set<Character> characters, Set<Integer> acceptingStates, Set<ab2.FATransition> transitions)
    {
        this.numStates = numStates;
        this.characters = characters;
        this.acceptingStates = acceptingStates;
        this.transitions = transitions;
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

    @Override
    public boolean isAcceptingState(int s) throws IllegalStateException
    {
        if (s > acceptingStates.size() - 1)
            throw new IllegalStateException("State does not exist.");

        if (acceptingStates.contains(s))
            return true;
        return false;
    }

    @Override
    public Set<? extends FATransition> getTransitions()
    {
        return transitions;
    }

    @Override
    public int getNumStates()
    {
        return numStates;
    }

    @Override
    public ab2.FA union(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA intersection(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA minus(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA concat(ab2.FA a)
    {
        return null;
    }

    @Override
    public ab2.FA complement()
    {
        return null;
    }

    @Override
    public ab2.FA kleeneStar()
    {
        for (int i : acceptingStates)
        {
            transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
            numStates++;
        }
        this.acceptingStates.add(0);
        return new FA(this.numStates, this.characters, this.acceptingStates, this.transitions);
    }

    @Override
    public ab2.FA plus()
    {
        for (int i : acceptingStates)
        {
            transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(i, 0, ""));
            numStates++;
        }

        return new FA(this.numStates, this.characters, this.acceptingStates, this.transitions);
    }

    public static Set<Integer> getEpsilonQuant(int from, Set<FATransition> transitions){
        Set<Integer> resultQuantity = new HashSet<>();
        Set<Integer> alrInside = new HashSet<>();
        resultQuantity.add(from);
        for(FATransition t : transitions){
            if (t.from() == from && t.symbols().equals("")) {
                alrInside.add(from);
                resultQuantity.addAll(getEpsilonQuant(t.to(), transitions,alrInside));
            }
        }
        return resultQuantity;
    }

    public static Set<Integer> getEpsilonQuant(int from, Set<FATransition> transitions, Set<Integer> alreadyInside){
        Set<Integer> resultQuantity = new HashSet<>();
        resultQuantity.add(from);
        for(FATransition t : transitions){
            if (t.from() == from && t.symbols().equals("")) {
                    if(alreadyInside.contains(t.to())){

                    }else {
                        alreadyInside.add(t.to());
                        resultQuantity.addAll(getEpsilonQuant(t.to(), transitions, alreadyInside));
                    }


            }
        }
        return resultQuantity;
    }


    @Override
    public RSA toRSA()
    {
        this.splitTransition();

        //checks for epsilon transitions
        boolean hasEpsisonTransitions = false;
        for (ab2.FATransition trans : this.transitions)
        {
            if (trans.symbols().equals("")) hasEpsisonTransitions = true;
        }

        if (hasEpsisonTransitions)
        {
            //doing RSA convertion with Epsilon quantity

            //calculating Epsilon quantity
            Set<EpsilonQuantity> epsiQuant = new HashSet<>();
            for(FATransition tra : transitions){
                epsiQuant.add(new EpsilonQuantity(tra.from()));
                epsiQuant.add(new EpsilonQuantity(tra.to()));
            }

            for (EpsilonQuantity eq : epsiQuant) {
                eq.setTo(getEpsilonQuant(eq.getFrom(), transitions));
            }


            //for(EpsilonQuantity eq : epsiQuant){
             //  System.out.println(eq);
           // }
            //finished calculating epsilon quantity

            Set<TransitionTable> tt = new HashSet<>();
            Set<TransitionTable> finaltt = new HashSet<>();

            //startzustand hat immer index 0
            Set<Integer> start = new HashSet<>();
            start.add(0);


            int runtimeCounter = 0;
            tt.add(new TransitionTable(start));
            finaltt.add(new TransitionTable(start));

            for (TransitionTable t : tt)
            {
                t.calculateSteps(transitions, characters);
                //replace calculated steps with steps from epsilon quantity
                ArrayList<Set<Integer>> replaceArray = t.getNextSteps();
                for(int i = 0; i < replaceArray.size(); i++){
                    Set<Integer> replaceInt = new HashSet<>();
                    for(Integer y : replaceArray.get(i)){
                        for(EpsilonQuantity eq : epsiQuant){
                            if(y ==  eq.getFrom()){
                                replaceInt.addAll(eq.getTo());
                            }
                        }
                    }
                    replaceArray.set(i, replaceInt);
                }
                t.setNextSteps(replaceArray);

                for(TransitionTable finalttreplace : finaltt){
                        finalttreplace.setNextSteps(replaceArray);
                }

               // System.out.println(""+t);
                //1rst replace done

               // System.out.println("----------"+finaltt);

                ArrayList<Set<Integer>> tra = t.getNextSteps();
                for(int i = 0; i < tra.size(); i++){
                    if(tra.get(i).size() != 0){
                        tt.add(new TransitionTable(tra.get(i)));
                        finaltt.add(new TransitionTable(tra.get(i)));
                    }
                }

            }
           // System.out.println(",,,,,,,,"+tt);
            tt.remove(new TransitionTable(start));

           // System.out.println(".............." + tt);

            int j = finaltt.size();
            Iterator it = tt.iterator();
            while (j>0 && it.hasNext()){
                TransitionTable currenttt = (TransitionTable) it.next();
                currenttt.calculateSteps(transitions, characters);
               // System.out.println("##########!!!!!" + currenttt);

                ArrayList<Set<Integer>> replaceArray = currenttt.getNextSteps();
                for(int i = 0; i < replaceArray.size(); i++){
                    Set<Integer> replaceInt = new HashSet<>();
                    for(Integer y : replaceArray.get(i)){
                        for(EpsilonQuantity eq : epsiQuant){
                            if(y ==  eq.getFrom()){
                                replaceInt.addAll(eq.getTo());
                            }
                        }
                    }
                    replaceArray.set(i, replaceInt);
                }
                currenttt.setNextSteps(replaceArray);
               // System.out.println("RRRRRRRRRRRRRRRREEEEEEEEEEEEEE     "+replaceArray);
               // System.out.println(currenttt);
               // System.out.println(currenttt.getNextSteps());

                ArrayList<Set<Integer>> tra = currenttt.getNextSteps();
                for(int i = 0; i < tra.size(); i++){
                    if(tra.get(i).size() != 0) {
                        boolean alreadyInside = false;
                        for(TransitionTable t : finaltt){

                            if(t.equals(new TransitionTable(tra.get(i)))){
                                alreadyInside = true;
                            }

                        }
                        if(!alreadyInside){
                            tt.add(new TransitionTable(tra.get(i)));
                            finaltt.add(new TransitionTable(tra.get(i)));
                           // System.out.println("FINAAAAAAAAALLLLLLLL           "+finaltt);
                            j++;
                        }

                    }
                }
                tt.remove(currenttt);
                it = tt.iterator();
                j--;


            }

            ArrayList<TransitionTable> FinalTransitionTable = new ArrayList<>();

            for(TransitionTable t : finaltt){
                t.calculateSteps(transitions, characters);
                FinalTransitionTable.add(t);
                //BRUHHHHHHHHHHHHHHHHHHHHHH HIER GEHÖREN DIE ZUSTÄNDE NOCH AUF DIE DER EPSILON MENGE GEÄNDERT
                ArrayList<Set<Integer>> replaceArray = t.getNextSteps();
                for(int i = 0; i < replaceArray.size(); i++){
                    Set<Integer> replaceInt = new HashSet<>();
                    for(Integer y : replaceArray.get(i)){
                        for(EpsilonQuantity eq : epsiQuant){
                            if(y ==  eq.getFrom()){
                                replaceInt.addAll(eq.getTo());
                            }
                        }
                    }
                    replaceArray.set(i, replaceInt);
                }
                t.setNextSteps(replaceArray);
               // System.out.println(t.toString());
            }

            //checks if a "FRESSZUSTAND" is needed
            boolean fresszustand = false;
            for(TransitionTable t : FinalTransitionTable){
                if(!fresszustand){
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();
                    for(Set<Integer> singleStateNextStates : nextStates){
                        if (singleStateNextStates.size() == 0) fresszustand = true;
                    }
                }
            }

            Set<Integer> finalacceptedStates = new HashSet<>();
            //remap states
            ArrayList<TransitionTable> newFinalTT = new ArrayList<>();
            int stateNumber = 1;

            //in this for() i add the currentStates with a new number to a new TransitionTable
            for(int i = 0; i < FinalTransitionTable.size(); i++){
                Set<Integer> zeroSet = new HashSet<>();
                zeroSet.add(0);
                if(FinalTransitionTable.get(i).getCurrentState().equals(zeroSet)){
                    newFinalTT.add(new TransitionTable(zeroSet));
                    for(Integer acptdState : acceptingStates){
                        System.out.println("acptdState   " + acptdState);
                        if(FinalTransitionTable.get(i).getCurrentState().contains(acptdState)){
                            System.out.println(FinalTransitionTable.get(i).getCurrentState() +" --> " + "[0]");
                            finalacceptedStates.add(0);
                        }

                    }
                }else {
                    //keine ahnung wie gerade sonst ein set erstellen
                    Set<Integer> numberSet = new HashSet<>();
                    numberSet.add(stateNumber);
                    newFinalTT.add(new TransitionTable(numberSet));
                    for(Integer acptdState : acceptingStates){
                        if(FinalTransitionTable.get(i).getCurrentState().contains(acptdState)){
                           System.out.println(FinalTransitionTable.get(i).getCurrentState() +" --> " +numberSet);
                            finalacceptedStates.add(stateNumber);
                        }

                    }

                    stateNumber++;
                }
            }

            //System.out.println("YAAAAAAYEEEEEEEEEEEEEEEEEET final table");

           // for(TransitionTable t: finaltt){
                //System.out.println(t);
           // }
           // System.out.println();

            //now i add the nextSteps with the correct new number
            for(int i = 0; i < FinalTransitionTable.size(); i++){
                ArrayList<Set<Integer>> nextStepsArray = FinalTransitionTable.get(i).getNextSteps();

                ArrayList<Set<Integer>> finalNextStep = new ArrayList<>();
                int charCounter = 0;
                for(Character cha : characters){

                    for(int y = 0; y < FinalTransitionTable.size(); y++) {
                        Set<Integer> emptySet = new HashSet<>();
                        if(nextStepsArray.get(charCounter).equals(emptySet)){

                            finalNextStep.add(emptySet);
                            y=FinalTransitionTable.size();

                        }else {

                            if (FinalTransitionTable.get(y).getCurrentState().equals(nextStepsArray.get(charCounter))) {
                                Set<Integer> addSet = newFinalTT.get(y).getCurrentState();
                                finalNextStep.add(addSet);
                            }

                        }
                    }
                    charCounter++;

                }
                //System.out.println(finalNextStep);
                newFinalTT.get(i).setNextSteps(finalNextStep);

            }

            //System.out.println("TEST");
            //for(TransitionTable t : newFinalTT){
             //   System.out.println(t);
           // }


            //BIS HIER PASST ALLES !!!!!!!!!!!!!!!!!!!! BEIM ERSTELLEN DER TRANSITIONS PASST NOCH WAS NICHT


           // System.out.println("size FinalTransitionTable : " + FinalTransitionTable.size());
            //creating RSA
            Set<ab2.DFATransition> finalRsaTransitions = new HashSet<>();
            if(fresszustand){
                for(Character cha : characters){
                    finalRsaTransitions.add(new DFATransition(FinalTransitionTable.size(), FinalTransitionTable.size(), cha));
                }
            }

            for(TransitionTable t : newFinalTT){
                int i = 0;
                for(Character ch : characters){
                    if(i>=characters.size()) i=0;
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();
                    System.out.println("####################################");
                    System.out.println(t);

                    //AHHAAHHAHAHHAH HIEEER IST DER FEHLER BEIM EINFÜGEN AHAHAHAHAHA FIXED!!!!!!!!!!



                    if(nextStates.get(i).size() != 0){
                        for(Integer toInt : nextStates.get(i)){
                            Iterator fromIter = t.getCurrentState().iterator();
                            finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), toInt, ch));
                        }


                    }else {
                        Iterator fromIter = t.getCurrentState().iterator();
                        finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), FinalTransitionTable.size(), ch));
                    }


                    i++;
                }
            }

            //neue transitions und enzusteande sind fertig.
            if(fresszustand) {
                return new ab2.impl.PRUELLERRADLER.RSA(FinalTransitionTable.size() + 1, characters, finalacceptedStates, finalRsaTransitions);
            }else {
                return new ab2.impl.PRUELLERRADLER.RSA(FinalTransitionTable.size(), characters, finalacceptedStates, finalRsaTransitions);
            }


        }
        else
        {
            //doing simple RSA convertion without epsilon quantity

            Set<TransitionTable> tt = new HashSet<>();
            Set<TransitionTable> finaltt = new HashSet<>();

            //startzustand hat immer index 0
            Set<Integer> start = new HashSet<>();
            start.add(0);



            int runtimeCounter = 0;
            tt.add(new TransitionTable(start));
            finaltt.add(new TransitionTable(start));

            for (TransitionTable t : tt)
            {
                t.calculateSteps(transitions, characters);
                ArrayList<Set<Integer>> tra = t.getNextSteps();
                for(int i = 0; i < tra.size(); i++){
                    if(tra.get(i).size() != 0){
                        tt.add(new TransitionTable(tra.get(i)));
                        finaltt.add(new TransitionTable(tra.get(i)));
                    }
                }

            }
            tt.remove(new TransitionTable(start));

            int j = finaltt.size();
            Iterator it = tt.iterator();
            while (j>0 && it.hasNext()){
                TransitionTable currenttt = (TransitionTable) it.next();
                currenttt.calculateSteps(transitions, characters);
                ArrayList<Set<Integer>> tra = currenttt.getNextSteps();
                for(int i = 0; i < tra.size(); i++){
                    if(tra.get(i).size() != 0) {
                        boolean alreadyInside = false;
                        for(TransitionTable t : finaltt){

                            if(t.equals(new TransitionTable(tra.get(i)))){
                                alreadyInside = true;
                            }

                        }
                        if(!alreadyInside){
                            tt.add(new TransitionTable(tra.get(i)));
                            finaltt.add(new TransitionTable(tra.get(i)));
                            j++;
                        }

                    }
                }
                tt.remove(currenttt);
                it = tt.iterator();
                j--;


            }

            ArrayList<TransitionTable> FinalTransitionTable = new ArrayList<>();

            for(TransitionTable t : finaltt){
                t.calculateSteps(transitions, characters);
                FinalTransitionTable.add(t);
               // System.out.println(t.toString());
            }

            //checks if a "FRESSZUSTAND" is needed
            boolean fresszustand = false;
            for(TransitionTable t : FinalTransitionTable){
                if(!fresszustand){
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();
                    for(Set<Integer> singleStateNextStates : nextStates){
                        if (singleStateNextStates.size() == 0) fresszustand = true;
                    }
                }
            }

            Set<Integer> finalacceptedStates = new HashSet<>();
            //remap states
            ArrayList<TransitionTable> newFinalTT = new ArrayList<>();
            int stateNumber = 1;
            //in this for() i add the currentStates with a new number to a new TransitionTable
            for(int i = 0; i < FinalTransitionTable.size(); i++){
                Set<Integer> zeroSet = new HashSet<>();
                zeroSet.add(0);
                if(FinalTransitionTable.get(i).getCurrentState().equals(zeroSet)){
                    newFinalTT.add(new TransitionTable(zeroSet));
                    for(Integer acptdState : acceptingStates){
                        if(FinalTransitionTable.get(i).getCurrentState().contains(acptdState)){
                          //  System.out.println(FinalTransitionTable.get(i).getCurrentState() +" --> " + "[0]");
                            finalacceptedStates.add(0);
                        }

                    }
                }else {
                    //keine ahnung wie gerade sonst ein set erstellen
                    Set<Integer> numberSet = new HashSet<>();
                    numberSet.add(stateNumber);
                    newFinalTT.add(new TransitionTable(numberSet));
                    for(Integer acptdState : acceptingStates){
                        if(FinalTransitionTable.get(i).getCurrentState().contains(acptdState)){
                           // System.out.println(FinalTransitionTable.get(i).getCurrentState() +" --> " +numberSet);
                            finalacceptedStates.add(stateNumber);
                        }

                    }

                    stateNumber++;
                }
            }

            //now i add the nextSteps with the correct new number
            for(int i = 0; i < FinalTransitionTable.size(); i++){
                ArrayList<Set<Integer>> nextStepsArray = FinalTransitionTable.get(i).getNextSteps();

                ArrayList<Set<Integer>> finalNextStep = new ArrayList<>();
                int charCounter = 0;
                for(Character cha : characters){

                    for(int y = 0; y < FinalTransitionTable.size(); y++) {
                        Set<Integer> emptySet = new HashSet<>();
                        if(nextStepsArray.get(charCounter).equals(emptySet)){

                            finalNextStep.add(emptySet);
                            y=FinalTransitionTable.size();

                        }else {

                            if (FinalTransitionTable.get(y).getCurrentState().equals(nextStepsArray.get(charCounter))) {
                                Set<Integer> addSet = newFinalTT.get(y).getCurrentState();
                                finalNextStep.add(addSet);
                            }

                        }
                    }
                    charCounter++;
                }
                newFinalTT.get(i).setNextSteps(finalNextStep);

            }

           // System.out.println("TEST");
           // for(TransitionTable t : newFinalTT){
            //    System.out.println(t);
           // }



          //  System.out.println("size FinalTransitionTable : " + FinalTransitionTable.size());
            //creating RSA
            Set<ab2.DFATransition> finalRsaTransitions = new HashSet<>();
            if(fresszustand){
                for(Character cha : characters){
                    finalRsaTransitions.add(new DFATransition(FinalTransitionTable.size(), FinalTransitionTable.size(), cha));
                }
            }

            for(TransitionTable t : newFinalTT){
                int i = 0;
                for(Character ch : characters){
                    if(i>=characters.size()) i=0;
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();
                    if(nextStates.get(i).size() != 0){
                        for(Integer toInt : nextStates.get(i)){
                            Iterator fromIter = t.getCurrentState().iterator();
                            finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), toInt, ch));
                        }

                    }else {
                        Iterator fromIter = t.getCurrentState().iterator();
                        finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), FinalTransitionTable.size(), ch));
                    }
                    i++;
                }
            }

            //neue transitions und enzusteande sind fertig.
            if(fresszustand) {
                return new ab2.impl.PRUELLERRADLER.RSA(FinalTransitionTable.size() + 1, characters, finalacceptedStates, finalRsaTransitions);
            }else {
                return new ab2.impl.PRUELLERRADLER.RSA(FinalTransitionTable.size(), characters, finalacceptedStates, finalRsaTransitions);
            }


        }


    }

    //runs through each state in "currentStateQuantity" and stores the next step if the correct symbols is read
    private Set<Integer> nextSteps(Set<Integer> currentStateQuantity, String symbol)
    {
        Set<Integer> result = new TreeSet<>();
        for (FATransition tr : this.transitions)
        {
            for (Integer i : currentStateQuantity)
            {
                if (i == tr.from() && (tr.symbols().equals(symbol) || tr.symbols().equals("")))
                {
                    result.add(tr.to());
                }
            }
        }
        return result;
    }

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
        if (acceptsEpsilon())
            return false;
        boolean accepting = false;
        if (acceptingStates.isEmpty())
            return true;
        for (int i : acceptingStates)
        {
            if (reaches(0, i, 0, false, 0))
            {
                accepting = false;
            }
            else
                accepting = true;
        }
        return accepting;
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
                if (reaches(0, i, 0, false, 0))
                {
                    return false;
                }
            }
        }
        for (char c : getSymbols())
        {
            for (FATransition tr : transitions)
            {
                if (tr.equals(new ab2.impl.PRUELLERRADLER.FATransition(0, 0, "a")))
                    return false;
            }
        }

        return true;
    }

    @Override
    public boolean acceptsEpsilon()
    {
        if (acceptingStates.contains(0))
            return true;
        else return false;
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
                if (reaches(0, i, 0, false, 0))
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
    public boolean subSetOf(ab2.FA a)
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
    public boolean equalTo(ab2.FA b)
    {
        if (b == this)
            return true;
        return false;
    }

    @Override
    public Boolean equalsPlusAndStar()
    {
        return null;
    }

    public void splitTransition()
    {

        Set<ab2.FATransition> newTrans = new HashSet<>();

        for (FATransition tr : transitions)
        {
            if (tr.symbols().length() > 1)
            {
                char[] tokens = tr.symbols().toCharArray();

                for (int i = 0; i < tokens.length; i++)
                {

                    if (i == 0)
                    {
                        newTrans.add((FATransition) new ab2.impl.PRUELLERRADLER.FATransition(tr.from(), numStates, "" + tokens[i]));
                        numStates++;
                    }
                    if (i == tokens.length - 1)
                    {
                        newTrans.add((FATransition) new ab2.impl.PRUELLERRADLER.FATransition(numStates - 1, tr.to(), "" + tokens[i]));
                        numStates++;
                    }
                    else if (i != 0)
                    {
                        newTrans.add((FATransition) new ab2.impl.PRUELLERRADLER.FATransition(numStates - 1, numStates, "" + tokens[i]));
                        numStates++;
                    }
                }


                //this.transitions.remove((FATransition)tr);
            }
            else
            {
                newTrans.add((ab2.impl.PRUELLERRADLER.FATransition) tr);
            }


        }

        this.transitions = newTrans;
    }

    //checks if one state reaches another, breaks if loop detected and returns falsee
    public boolean reaches(int from, int to, int prevState, boolean reached, int count)
    {
        for (FATransition tr : transitions)
        {
            if (reached || count > 20)
                break;
            else if (tr.from() == from && tr.to() == to)
            {
                System.out.println(tr.toString());
                reached = true;
            }
            else if (tr.from() == from && tr.to() != prevState)
            {
                System.out.println(tr.toString());
                count ++;
                return reaches(tr.to(), to, from, reached, count);
            }
        }
        return reached;
    }
}

