package ab2.impl.PRUELLERRADLER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Erzeugt einen RSA, der zur Suche des übergebenen Patterns in Texten verwendet
 * werden kann. Das Muster darf nur aus Zeichen und den Symbolen * und .
 * bestehen. Zudem dürfen runde Klammern verwenndet werden. Geschachtelte Klammern sind erlaubt.
 * Das Symbol * bedeutet, dass das Zeichen bzw. der geklammerte Block davor beliebig oft
 * vorkommen kann. Das Symbol . bedeutet, dass ein beliebiges Zeichen gelesen
 * werden kann. Beispiele für Muster wären "abcd." oder "ab*cd". (ab)* bedeutet,
 * dass ab beliebig oft wiederholt werden kann. Der Automat
 * muss sich immer dann in einem akzeptierenden Zustand befinden, wenn der Text dem Muster entspricht.
 */
public class PatternMatcher
{
    private String pattern;

    private final String dot = ".";
    private final String star = "*";

    //(ab)*a
    public ArrayList<String> prepString(String word)
    {
        ArrayList<String> parts = new ArrayList<>();

        String[] tokens = word.split("[\\(\\)]");

        for (int i = 0; i < tokens.length; i++)
        {
            if (tokens[i].startsWith("*"))
            {
                tokens[i] = tokens[i].substring(1);
                tokens[i - 1] = tokens[i - 1] + "*";
            }
        }
        for (int i = 0; i < tokens.length; i++)
            if (!tokens[i].isBlank())
                parts.add(tokens[i]);

        return parts;
    }

    public RSA toRSA(ArrayList<String> parts, Set<Character> chars)
    {
        Set<ab2.DFATransition> transitions = new HashSet<>();
        Set<Integer> acceptingStates = new HashSet<>();
        int numstates = 0;
        int currentState = 0;
        int prevState = 0;
        int fress = 1;
        char[] _tokens;
        ArrayList<RSA> RSAList = new ArrayList<>();

        for (String s : parts)
        {
            transitions = new HashSet<>();
            acceptingStates = new HashSet<>();
            numstates = 0;
            currentState = 0;
            fress = 1;
            _tokens = s.toCharArray();
            if (s.endsWith("*"))
            {
                //Kleene-Star
                s = s.substring(0, s.length()-1);
                _tokens = s.toCharArray();
                numstates = s.length()+1;
                fress = numstates-1;
                acceptingStates.add(0);
                //z.B ab
                for (int i = 0; i < _tokens.length; i++)
                {
                    if (_tokens[i] == '.')
                    {
                        for (char c : chars)
                        {
                            if (i < _tokens.length - 1)
                            {
                                prevState = currentState;
                                transitions.add(new DFATransition(currentState, currentState + 1, c));
                            }
                            //last state wraps back to 1st
                            else
                            {
                                prevState = currentState;
                                transitions.add(new DFATransition(currentState, 0, c));
                            }
                        }
                            currentState++;
                    }
                    else
                    {
                        if (i < _tokens.length - 1)
                        {
                            prevState = currentState;
                            transitions.add(new DFATransition(currentState, ++currentState, _tokens[i]));
                        }
                        //last state wraps back to 1st
                        else
                        {
                            prevState = currentState;
                            transitions.add(new DFATransition(currentState, 0, _tokens[i]));
                        }
                        for (char c : chars)
                        {
                            if (c != _tokens[i])
                                transitions.add(new DFATransition(prevState, -1, c));
                        }
                    }
                }
            }
            else
            {
                //no Kleene-Star
                numstates = 2;
                for (int i = 0; i < _tokens.length; i++)
                {
                    if (_tokens[i] == '.')
                    {
                        numstates++;
                        fress++;
                        for (char c : chars)
                        {
                            transitions.add(new DFATransition(currentState, currentState + 1, c));
                            acceptingStates.clear();
                        }
                            acceptingStates.add(currentState++);
                    }
                    else
                    {
                        prevState = currentState;
                        transitions.add(new DFATransition(currentState, ++currentState, _tokens[i]));
                        acceptingStates.clear();
                        acceptingStates.add(currentState);
                        numstates++;
                        fress++;
                        for (char c : chars)
                        {
                            if (c != _tokens[i])
                                transitions.add(new DFATransition(prevState, -1, c));
                        }
                    }
                }
            }

            for (char c : chars)
            {
                transitions.add(new DFATransition(-1, -1, c));
            }
        }
        if (numstates > 0)
        {
            //concat RSA's somehow
            Set<ab2.DFATransition> copyTrans = new HashSet<>();
            for (ab2.DFATransition tr : transitions)
                copyTrans.add(tr);

            for (ab2.DFATransition tr : transitions)
            {
                if (tr.from() == -1 && tr.to() == -1)
                {
                    copyTrans.add(new DFATransition(fress, fress, tr.symbol()));
                    copyTrans.remove(tr);
                }
                else if (tr.to() == -1)
                {
                    copyTrans.add(new DFATransition(tr.from(), fress, tr.symbol()));
                    copyTrans.remove(tr);
                }
            }
            RSAList.add(new RSA(numstates, chars, acceptingStates, copyTrans));
        }
        System.out.println(RSAList.toString());
        RSA finalRSA = RSAList.get(0);

        for (RSA rsa : RSAList)
        {
            if (!rsa.equals(RSAList.get(0)))
                finalRSA = finalRSA.concat(rsa);
        }

        return finalRSA;
    }


    public static void main(String[] args)
    {
        PatternMatcher pm = new PatternMatcher();
        Set<Character> chars = new HashSet<>();
        chars.add('a');
        chars.add('b');

        //System.out.println(pm.prepString("((ab)*a)(ab.)*"));
        pm.toRSA(pm.prepString("(a.b.)*"), chars);
    }
}
