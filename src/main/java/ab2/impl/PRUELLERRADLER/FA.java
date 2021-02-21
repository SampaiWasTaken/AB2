package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.RSA;

import java.util.*;

public class FA implements ab2.FA
{
    private int numStates;
    private final Set<Character> characters;
    private final Set<Integer> acceptingStates;
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

        return acceptingStates.contains(s);
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
        return this.toRSA().union(a);
    }

    @Override
    public ab2.FA intersection(ab2.FA a)
    {
        return this.toRSA().intersection(a);
    }

    @Override
    public ab2.FA minus(ab2.FA a)
    {
        return this.toRSA().minus(a);
    }

    @Override
    public ab2.FA concat(ab2.FA a)
    {
        return this.toRSA().concat(a);
    }

    @Override
    public ab2.FA complement()
    {
        return this.toRSA().complement();
    }

    @Override
    public ab2.FA kleeneStar()
    {
        return this.toRSA().kleeneStar();
    }

    @Override
    public ab2.FA plus()
    {
        return this.toRSA().plus();
    }

    public static Set<Integer> getEpsilonQuant(int from, Set<FATransition> transitions)
    {
        Set<Integer> resultQuantity = new HashSet<>();
        Set<Integer> alrInside = new HashSet<>();
        resultQuantity.add(from);
        for (FATransition t : transitions)
        {
            if (t.from() == from && t.symbols().equals(""))
            {
                alrInside.add(from);
                resultQuantity.addAll(getEpsilonQuant(t.to(), transitions, alrInside));
            }
        }
        return resultQuantity;
    }

    public static Set<Integer> getEpsilonQuant(int from, Set<FATransition> transitions, Set<Integer> alreadyInside)
    {
        Set<Integer> resultQuantity = new HashSet<>();
        resultQuantity.add(from);
        for (FATransition t : transitions)
        {
            if (t.from() == from && t.symbols().equals(""))
            {
                if (alreadyInside.contains(t.to()))
                {

                }
                else
                {
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

        boolean hasEpsisonTransitions = false;
        for (ab2.FATransition trans : this.transitions)
        {
            if (trans.symbols().equals("")) hasEpsisonTransitions = true;
        }

        if (hasEpsisonTransitions)
        {
            Set<EpsilonQuantity> epsiQuant = new HashSet<>();
            for (FATransition tra : transitions)
            {
                epsiQuant.add(new EpsilonQuantity(tra.from()));
                epsiQuant.add(new EpsilonQuantity(tra.to()));
            }

            for (EpsilonQuantity eq : epsiQuant)
            {
                eq.setTo(getEpsilonQuant(eq.getFrom(), transitions));
            }

            Set<TransitionTable> tt = new HashSet<>();
            Set<TransitionTable> finaltt = new HashSet<>();

            Set<Integer> start = new HashSet<>();
            for (EpsilonQuantity eq : epsiQuant)
            {
                if (eq.getFrom() == 0)
                {
                    start.addAll(eq.getTo());
                }
            }

            int runtimeCounter = 0;
            tt.add(new TransitionTable(start));
            finaltt.add(new TransitionTable(start));

            for (TransitionTable t : tt)
            {
                t.calculateSteps(transitions, characters);
                ArrayList<Set<Integer>> errorCorrection = t.getNextSteps();
                for (Set<Integer> i : errorCorrection)
                { //das is neu
                    if (i.size() == 0)
                    {
                        for (FATransition trans : transitions)
                        {
                            if (t.getCurrentState().contains(trans.from()) && trans.symbols().equals(""))
                            {
                                i.add(trans.to());
                            }
                        }
                    }
                }
                ArrayList<Set<Integer>> replaceArray = t.getNextSteps();
                for (int i = 0; i < replaceArray.size(); i++)
                {
                    Set<Integer> replaceInt = new HashSet<>();
                    for (Integer y : replaceArray.get(i))
                    {
                        for (EpsilonQuantity eq : epsiQuant)
                        {
                            if (y == eq.getFrom())
                            {
                                replaceInt.addAll(eq.getTo());
                            }
                        }
                    }
                    replaceArray.set(i, replaceInt);
                }
                t.setNextSteps(replaceArray);
                ArrayList<Set<Integer>> tra = t.getNextSteps();
                for (int i = 0; i < tra.size(); i++)
                {
                    if (tra.get(i).size() != 0)
                    {
                        tt.add(new TransitionTable(tra.get(i)));
                        finaltt.add(new TransitionTable(tra.get(i)));
                    }
                }
            }
            tt.remove(new TransitionTable(start));


            int j = finaltt.size();
            Iterator it = tt.iterator();
            while (j > 0 && it.hasNext())
            {
                TransitionTable currenttt = (TransitionTable) it.next();
                currenttt.calculateSteps(transitions, characters);
                ArrayList<Set<Integer>> errorCorrection = currenttt.getNextSteps();
                for (Set<Integer> i : errorCorrection)
                {
                    if (i.size() == 0)
                    {
                        for (FATransition trans : transitions)
                        {
                            if (currenttt.getCurrentState().contains(trans.from()) && trans.symbols().equals(""))
                            {
                                i.add(trans.to());
                            }
                        }
                    }
                }

                ArrayList<Set<Integer>> replaceArray = currenttt.getNextSteps();
                for (int i = 0; i < replaceArray.size(); i++)
                {
                    Set<Integer> replaceInt = new HashSet<>();
                    for (Integer y : replaceArray.get(i))
                    {
                        for (EpsilonQuantity eq : epsiQuant)
                        {
                            if (y == eq.getFrom())
                            {
                                replaceInt.addAll(eq.getTo());
                            }
                        }
                    }
                    replaceArray.set(i, replaceInt);
                }
                currenttt.setNextSteps(replaceArray);

                ArrayList<Set<Integer>> tra = currenttt.getNextSteps();
                for (int i = 0; i < tra.size(); i++)
                {
                    if (tra.get(i).size() != 0)
                    {
                        boolean alreadyInside = false;
                        for (TransitionTable t : finaltt)
                        {

                            if (t.equals(new TransitionTable(tra.get(i))))
                            {
                                alreadyInside = true;
                            }
                        }
                        if (!alreadyInside)
                        {
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

            for (TransitionTable t : finaltt)
            {
                t.calculateSteps(transitions, characters);
                FinalTransitionTable.add(t);
                ArrayList<Set<Integer>> replaceArray = t.getNextSteps();
                for (int i = 0; i < replaceArray.size(); i++)
                {
                    Set<Integer> replaceInt = new HashSet<>();
                    for (Integer y : replaceArray.get(i))
                    {
                        for (EpsilonQuantity eq : epsiQuant)
                        {
                            if (y == eq.getFrom())
                            {
                                replaceInt.addAll(eq.getTo());
                            }
                        }
                    }
                    replaceArray.set(i, replaceInt);
                }
                t.setNextSteps(replaceArray);
            }

            boolean fresszustand = false;
            for (TransitionTable t : FinalTransitionTable)
            {
                if (!fresszustand)
                {
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();
                    for (Set<Integer> singleStateNextStates : nextStates)
                    {
                        if (singleStateNextStates.size() == 0)
                        {
                            fresszustand = true;
                            break;
                        }
                    }
                }
            }

            Set<Integer> finalacceptedStates = new HashSet<>();
            //remap states
            ArrayList<TransitionTable> newFinalTT = new ArrayList<>();
            int stateNumber = 1;

            for (int i = 0; i < FinalTransitionTable.size(); i++)
            {
                Set<Integer> zeroSet = new HashSet<>();
                Set<Integer> zeroAddSet = new HashSet<>();
                zeroAddSet.add(0);
                for (EpsilonQuantity eq : epsiQuant)
                {
                    if (eq.getFrom() == 0)
                    {
                        zeroSet.addAll(eq.getTo());
                    }
                }

                if (FinalTransitionTable.get(i).getCurrentState().equals(zeroSet))
                {
                    newFinalTT.add(new TransitionTable(zeroAddSet));
                    for (Integer acptdState : acceptingStates)
                    {
                        if (FinalTransitionTable.get(i).getCurrentState().contains(acptdState))
                        {
                            finalacceptedStates.add(0);
                        }
                    }
                }
                else
                {
                    Set<Integer> numberSet = new HashSet<>();
                    numberSet.add(stateNumber);
                    newFinalTT.add(new TransitionTable(numberSet));
                    for (Integer acptdState : acceptingStates)
                    {
                        if (FinalTransitionTable.get(i).getCurrentState().contains(acptdState))
                        {
                            finalacceptedStates.add(stateNumber);
                        }
                    }

                    stateNumber++;
                }
            }

            for (int i = 0; i < FinalTransitionTable.size(); i++)
            {
                ArrayList<Set<Integer>> nextStepsArray = FinalTransitionTable.get(i).getNextSteps();

                ArrayList<Set<Integer>> finalNextStep = new ArrayList<>();
                int charCounter = 0;
                for (Character cha : characters)
                {

                    for (int y = 0; y < FinalTransitionTable.size(); y++)
                    {
                        Set<Integer> emptySet = new HashSet<>();
                        if (nextStepsArray.get(charCounter).equals(emptySet))
                        {

                            finalNextStep.add(emptySet);
                            y = FinalTransitionTable.size();
                        }
                        else
                        {

                            if (FinalTransitionTable.get(y).getCurrentState().equals(nextStepsArray.get(charCounter)))
                            {
                                Set<Integer> addSet = newFinalTT.get(y).getCurrentState();
                                finalNextStep.add(addSet);
                            }
                        }
                    }
                    charCounter++;
                }
                newFinalTT.get(i).setNextSteps(finalNextStep);
            }

            Set<ab2.DFATransition> finalRsaTransitions = new HashSet<>();

            for (TransitionTable t : newFinalTT)
            {
                int i = 0;
                for (Character ch : characters)
                {
                    if (i >= characters.size()) i = 0;
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();

                    if (nextStates.get(i).size() != 0)
                    {
                        for (Integer toInt : nextStates.get(i))
                        {
                            Iterator fromIter = t.getCurrentState().iterator();
                            finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), toInt, ch));
                        }
                    }
                    else
                    {
                        Iterator fromIter = t.getCurrentState().iterator();
                        finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), FinalTransitionTable.size(), ch));
                    }

                    i++;
                }
            }

            //adding fresszustand;
            fresszustand = false;
            for (ab2.DFATransition trans : finalRsaTransitions)
            {
                if (trans.to() == FinalTransitionTable.size())
                {
                    fresszustand = true;
                }
            }
            if (fresszustand)
            {
                for (Character cha : characters)
                {
                    finalRsaTransitions.add(new DFATransition(FinalTransitionTable.size(), FinalTransitionTable.size(), cha));
                }
            }

            if (fresszustand)
            {
                return new ab2.impl.PRUELLERRADLER.RSA(FinalTransitionTable.size() + 1, characters, finalacceptedStates, finalRsaTransitions);
            }
            else
            {
                return new ab2.impl.PRUELLERRADLER.RSA(FinalTransitionTable.size(), characters, finalacceptedStates, finalRsaTransitions);
            }
        }
        else
        {
            if (this.numStates == 1 && this.transitions.size() == 0 && this.acceptingStates.size() == 0)
            {
                Set<ab2.DFATransition> newtransitio = new HashSet<>();
                for (Character c : this.characters)
                {
                    newtransitio.add(new DFATransition(0, 0, c));
                }
                Set<Integer> acceptn1 = new TreeSet<>();
                return new ab2.impl.PRUELLERRADLER.RSA(1, characters, acceptn1, newtransitio);
            }
            Set<TransitionTable> tt = new HashSet<>();
            Set<TransitionTable> finaltt = new HashSet<>();

            Set<Integer> start = new HashSet<>();
            start.add(0);

            int runtimeCounter = 0;
            tt.add(new TransitionTable(start));
            finaltt.add(new TransitionTable(start));

            for (TransitionTable t : tt)
            {
                t.calculateSteps(transitions, characters);
                ArrayList<Set<Integer>> tra = t.getNextSteps();
                for (int i = 0; i < tra.size(); i++)
                {
                    if (tra.get(i).size() != 0)
                    {
                        tt.add(new TransitionTable(tra.get(i)));
                        finaltt.add(new TransitionTable(tra.get(i)));
                    }
                }
            }
            tt.remove(new TransitionTable(start));

            int j = finaltt.size();
            Iterator it = tt.iterator();
            while (j > 0 && it.hasNext())
            {
                TransitionTable currenttt = (TransitionTable) it.next();
                currenttt.calculateSteps(transitions, characters);
                ArrayList<Set<Integer>> tra = currenttt.getNextSteps();
                for (int i = 0; i < tra.size(); i++)
                {
                    if (tra.get(i).size() != 0)
                    {
                        boolean alreadyInside = false;
                        for (TransitionTable t : finaltt)
                        {

                            if (t.equals(new TransitionTable(tra.get(i))))
                            {
                                alreadyInside = true;
                            }
                        }
                        if (!alreadyInside)
                        {
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

            for (TransitionTable t : finaltt)
            {
                t.calculateSteps(transitions, characters);
                FinalTransitionTable.add(t);
            }

            boolean fresszustand = false;
            for (TransitionTable t : FinalTransitionTable)
            {
                if (!fresszustand)
                {
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();
                    for (Set<Integer> singleStateNextStates : nextStates)
                    {
                        if (singleStateNextStates.size() == 0)
                        {
                            fresszustand = true;
                            break;
                        }
                    }
                }
            }

            Set<Integer> finalacceptedStates = new HashSet<>();
            //remap states
            ArrayList<TransitionTable> newFinalTT = new ArrayList<>();
            int stateNumber = 1;
            for (int i = 0; i < FinalTransitionTable.size(); i++)
            {
                Set<Integer> zeroSet = new HashSet<>();
                zeroSet.add(0);
                if (FinalTransitionTable.get(i).getCurrentState().equals(zeroSet))
                {
                    newFinalTT.add(new TransitionTable(zeroSet));
                    for (Integer acptdState : acceptingStates)
                    {
                        if (FinalTransitionTable.get(i).getCurrentState().contains(acptdState))
                        {
                            finalacceptedStates.add(0);
                        }
                    }
                }
                else
                {
                    Set<Integer> numberSet = new HashSet<>();
                    numberSet.add(stateNumber);
                    newFinalTT.add(new TransitionTable(numberSet));
                    for (Integer acptdState : acceptingStates)
                    {
                        if (FinalTransitionTable.get(i).getCurrentState().contains(acptdState))
                        {
                            finalacceptedStates.add(stateNumber);
                        }
                    }

                    stateNumber++;
                }
            }

            for (int i = 0; i < FinalTransitionTable.size(); i++)
            {
                ArrayList<Set<Integer>> nextStepsArray = FinalTransitionTable.get(i).getNextSteps();

                ArrayList<Set<Integer>> finalNextStep = new ArrayList<>();
                int charCounter = 0;
                for (Character cha : characters)
                {

                    for (int y = 0; y < FinalTransitionTable.size(); y++)
                    {
                        Set<Integer> emptySet = new HashSet<>();
                        if (nextStepsArray.get(charCounter).equals(emptySet))
                        {

                            finalNextStep.add(emptySet);
                            y = FinalTransitionTable.size();
                        }
                        else
                        {

                            if (FinalTransitionTable.get(y).getCurrentState().equals(nextStepsArray.get(charCounter)))
                            {
                                Set<Integer> addSet = newFinalTT.get(y).getCurrentState();
                                finalNextStep.add(addSet);
                            }
                        }
                    }
                    charCounter++;
                }
                newFinalTT.get(i).setNextSteps(finalNextStep);
            }
            Set<ab2.DFATransition> finalRsaTransitions = new HashSet<>();

            for (TransitionTable t : newFinalTT)
            {
                int i = 0;
                for (Character ch : characters)
                {
                    if (i >= characters.size()) i = 0;
                    ArrayList<Set<Integer>> nextStates = t.getNextSteps();
                    if (nextStates.get(i).size() != 0)
                    {
                        for (Integer toInt : nextStates.get(i))
                        {
                            Iterator fromIter = t.getCurrentState().iterator();
                            finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), toInt, ch));
                        }
                    }
                    else
                    {
                        Iterator fromIter = t.getCurrentState().iterator();
                        finalRsaTransitions.add(new DFATransition((Integer) fromIter.next(), FinalTransitionTable.size(), ch));
                    }
                    i++;
                }
            }
            fresszustand = false;
            for (ab2.DFATransition trans : finalRsaTransitions)
            {
                if (trans.to() == FinalTransitionTable.size())
                {
                    fresszustand = true;
                }
            }
            if (fresszustand)
            {
                for (Character cha : characters)
                {
                    finalRsaTransitions.add(new DFATransition(FinalTransitionTable.size(), FinalTransitionTable.size(), cha));
                }
            }

            //neue transitions und enzusteande sind fertig.
            if (fresszustand)
            {
                return new ab2.impl.PRUELLERRADLER.RSA(FinalTransitionTable.size() + 1, characters, finalacceptedStates, finalRsaTransitions);
            }
            else
            {
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
        return this.toRSA().accepts(w);
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
            accepting = !reaches(0, i, new HashSet<>(), false, 0);
        }
        return accepting;
    }

    @Override
    public boolean acceptsEpsilonOnly()
    {
        return this.toRSA().acceptsEpsilonOnly();
    }

    @Override
    public boolean acceptsEpsilon()
    {
        RSA someRandomRSA = this.toRSA();
        return someRandomRSA.acceptsEpsilon();
    }

    @Override
    public boolean isInfinite()
    {
        if (acceptsEpsilonOnly()) return false;
        boolean infinite = false;
        boolean loop = false;
        Set<Integer> possibleFroms = new HashSet<>();

        for (FATransition tr : this.getTransitions())
            if (reaches(tr.from(), tr.from(), new HashSet<>(), false, 0))
            {
                possibleFroms.add(tr.from());
            }

        //now checking if possibleFroms are reachable from 0 State
        Set<Integer> possibleFromsfrom0 = new HashSet<>();
        for (Integer i : possibleFroms)
            if (reaches(0, i, new HashSet<>(), false, 0))
            {
                possibleFromsfrom0.add(i);
            }

        //now checks if possibleFromsfrom0 can reach a accepting state
        for (Integer i : possibleFromsfrom0)
        {
            for (int acceptingState : acceptingStates)
            {
                if (reaches(i, acceptingState, new HashSet<>(), false, 0))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isFinite()
    {
        return !isInfinite();
    }

    @Override
    public boolean subSetOf(ab2.FA a)
    {
        if (a.acceptsEpsilonOnly() || this.equalTo(a) || this.kleeneStar().equalTo(a) || this.plus().equalTo(a))
            return true;

        ab2.RSA rsa = this.union(a).toRSA().minimize();
        if (rsa.equalTo(a.toRSA().minimize())) return true;

        return false;
    }

    @Override
    public boolean equalTo(ab2.FA b)
    {
        return this.toRSA().equalTo(b);
    }

    @Override
    public Boolean equalsPlusAndStar()
    {
        return this.toRSA().minimize().equalsPlusAndStar();
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
                        newTrans.add(new ab2.impl.PRUELLERRADLER.FATransition(tr.from(), numStates, "" + tokens[i]));
                        numStates++;
                    }
                    if (i == tokens.length - 1)
                    {
                        newTrans.add(new ab2.impl.PRUELLERRADLER.FATransition(numStates - 1, tr.to(), "" + tokens[i]));
                        numStates++;
                    }
                    else if (i != 0)
                    {
                        newTrans.add(new ab2.impl.PRUELLERRADLER.FATransition(numStates - 1, numStates, "" + tokens[i]));
                        numStates++;
                    }
                }
            }
            else
            {
                newTrans.add(tr);
            }
        }
        this.transitions = newTrans;
    }

    public boolean reaches(int from, int to, Set<FATransition> prevState, boolean reached, int count)
    {
        if (reached) return true;
        if (count > 40) return false;
        Set<FATransition> copiedTransitions = new HashSet<>();
        copiedTransitions.addAll(prevState);

        boolean isOK = false;
        for (FATransition tr : transitions)
        {
            if (tr.to() == to) isOK = true;
            if (tr.from() == from && tr.to() == to) return true;
        }
        if (!isOK) return false;

        for (FATransition tr : transitions)
        {
            if (reached)
                break;
            else if (tr.from() == from && tr.to() == to)
            {
                return true;
            }
            else if (tr.from() == from && !copiedTransitions.contains(tr))
            {
                count++;
                copiedTransitions.add(tr);
                reached = reaches(tr.to(), to, copiedTransitions, reached, count);
            }
        }
        return reached;
    }

    @Override
    public String toString()
    {
        String returnString = "FA{" +
                "numStates=" + numStates +
                ", characters=" + characters +
                ", acceptingStates=" + acceptingStates;
        for (ab2.FATransition tra : transitions)
        {
            returnString += "" + tra;
        }
        return returnString;
    }
}

