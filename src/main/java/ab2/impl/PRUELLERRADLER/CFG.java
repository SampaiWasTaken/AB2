package ab2.impl.PRUELLERRADLER;

import ab2.PDATransition;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CFG
{
    private final String CFG_DELIMITER1 = "→";
    private final String CFG_DELIMITER2 = " | ";
    private final String EPSILON = "";

    private final Set<Character> terminals;
    private final Set<String> nonTerminals;
    private final Set<String> rules;
    int acceptingState;

    public CFG(Set<Character> terminals, Set<String> nonTerminals, Set<String> rules)
    {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.rules = rules;
    }

    public Set<String> getRules()
    {
        return rules;
    }

    public void convert(PDA PDA)
    {
        rules.add("S");
        rules.add("S" + CFG_DELIMITER1 + "A" + 0 + PDA.getAcceptingStates().iterator().next());
        for (ab2.PDATransition transition : PDA.getTransitions())
        {
            terminals.add(transition.symbolRead());
        }

        for (int p = 0; p < PDA.getNumStates(); p++)
        {
            for (int q = 0; q < PDA.getNumStates(); q++)
            {
                nonTerminals.add("A" + p + q);
                if (p == q)
                {
                    String EPSILON = " ";
                    rules.add("A" + p + q + CFG_DELIMITER1 + EPSILON);
                }
                for (int r = 0; r < PDA.getNumStates(); r++)
                {
                    rules.add("A" + p + q + CFG_DELIMITER1 + "A" + p + r + "A" + r + q);
                    for (int s = 0; s < PDA.getNumStates(); s++)
                    {
                        for (PDATransition tr : PDA.getTransitions())
                        {
                            if (tr.from() == p && tr.to() == s)
                            {
                                 rules.add("A" + p + q + CFG_DELIMITER1 + " " + "A" + r + s + " ");
                            }
                        }
                    }
                }
            }
        }

        for (String s : rules)
        {
            System.out.println(s);
        }
    }

    public PDA toPDA(Set<String> rules, char startSymbol)
    {
        Set<String> splitRules = new HashSet<>();
        Set<Character> stackSymbols = new HashSet<>();
        Set<ab2.PDATransition> transitions = new HashSet<>();
        for (String s : rules)
        {
            String prefix = s.substring(0, 1) + CFG_DELIMITER1;
            String[] _rules = s.split("\\|");
            for (String rule : _rules)
            {
                if (!rule.contains(prefix.strip()))
                    splitRules.add(prefix + rule);
                else
                    splitRules.add(rule);
            }
        }

        //new PDA
        Set<PDATransition> newTransitions = new HashSet<>();
        Set<Character> inputSymbols= new HashSet<>();
        inputSymbols.add('a');
        inputSymbols.add('b');
        inputSymbols.add('c');
        Set<Integer> acceptingStates= new HashSet<>(Arrays.asList(1));
        int numstates = 2;

        //start
        newTransitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(0, 1, null, null, startSymbol));
        int countState = 0;

        for (String s : rules)
        {
            String[] tokens = s.split("→");
            char[] _tokens = tokens[1].toCharArray();

            //Aufbau

            for (char c : _tokens)
            {
                //newTransitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(1, ++countState, null, null, c));asd
            }
        }
            //Abbau
            for (char c : inputSymbols)
            {
                newTransitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(1, 1, c, c, null));

            }
//        {
//            newTransitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(1, 1, null, A,A>x));
//            newTransitions.add(new ab2.impl.PRUELLERRADLER.PDATransition(1, 1, a, a, null));
//
//        }

        System.out.println(Arrays.deepToString(splitRules.toArray()));

        return new PDA(numstates, inputSymbols, stackSymbols, new HashSet<>(Arrays.asList(1)), newTransitions);
    }

}
