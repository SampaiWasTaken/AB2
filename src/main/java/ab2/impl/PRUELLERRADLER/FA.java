package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.IllegalCharacterException;
import ab2.RSA;
import com.sun.source.tree.Tree;

import javax.print.attribute.SetOfIntegerSyntax;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
        transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(0, 0, ""));
        this.acceptingStates.add(transitions.size() - 1);
        return new FA(this.numStates, this.characters, this.acceptingStates, this.transitions);
    }

    @Override
    public ab2.FA plus()
    {
        transitions.add(new ab2.impl.PRUELLERRADLER.FATransition(this.transitions.size(), 0, ""));
        this.acceptingStates.add(transitions.size() - 1);
        return new FA(this.numStates, this.characters, this.acceptingStates, this.transitions);
    }

    @Override
    public RSA toRSA()
    {
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

            Set<TransitionTable> tt = new TreeSet<>();

            //startzustand hat immer index 0
            Set<Integer> start = new TreeSet<>();
            start.add(0);
            tt.add(new TransitionTable(start));
            for (TransitionTable t : tt)
            {
                //t.calculateSteps( zeugs );
            }

        }


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
        char[] word = w.toCharArray();

        return false;
    }

    @Override
    public boolean acceptsNothing()
    {
        if (acceptingStates.isEmpty())
            return true;
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
}
