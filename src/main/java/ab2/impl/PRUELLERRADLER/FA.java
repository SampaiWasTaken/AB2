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
        transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(this.transitions.size(), 0, ""));
        this.acceptingStates.add(0);
        return new FA(this.numStates, this.characters, this.acceptingStates, this.transitions);
    }

    @Override
    public ab2.FA plus()
    {
        transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(this.transitions.size(), 0, ""));
        return new FA(this.numStates, this.characters, this.acceptingStates, this.transitions);
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
                for (int i = 0; i < tra.size(); i++)
                {
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
            for(TransitionTable t : finaltt){
                t.calculateSteps(transitions, characters);
                System.out.println(t.toString());
            }



        }


        //neue endzustände Beachten

        return null;
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

        for (FATransition tr : transitions)
        {
            for (int i : acceptingStates)
            {
                if (tr.to() == i);
            }
        }
        return false;
    }

    @Override
    public boolean acceptsEpsilonOnly()
    {
        if (numStates == 1 && acceptingStates.contains(0))
            return true;
        else return false;
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
        boolean loop = false;
        for (FATransition tr : transitions)
        {
            for (FATransition _tr : transitions)
            {
                if (tr.to() == _tr.from() && tr.from() == _tr.to())
                    loop = true;
            }
            if (loop)
            {

            }
        }


        return false;
    }

    @Override
    public boolean isFinite()
    {
        return false;
    }

    @Override
    public boolean subSetOf(ab2.FA a)
    {
        return false;
    }

    @Override
    public boolean equalTo(ab2.FA b)
    {
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

    public boolean reaches (int from, int to)
    {
        int currentState = from;
        boolean running = true;
        boolean found = false;
        while(running)
        {
            for (FATransition tr : transitions)
            {
                if (tr.from() == currentState)
                {
                    if (tr.from() == currentState && tr.to() == to)
                    {
                        found = true;
                        running = false;
                    }
                    currentState = tr.to();
                }
            }
            running = false;
        }
        return found;
    }
}
