package ab2.impl.PRUELLERRADLER;

import java.util.ArrayList;
import java.util.Arrays;
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

    private final String dot = ".";
    private final String star = "*";

    public PatternMatcher(String pattern)
    {
    }

    public boolean checkString(String word)
    {
        return word.matches("[\\w\\.\\*\\(\\)]*");
    }

    //(ab)*a
    public ArrayList<String> prepString(String word)
    {
        ArrayList<String> parts = new ArrayList<>();
        ArrayList<String> brackets = new ArrayList<>();

        String[] tokens;
        tokens = word.split("[\\(\\)]");

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
                brackets.add(tokens[i]);

            for (String s : brackets)
            {
                if (s.matches("[\\w]*[\\.]*[\\*]{1}"))
                    parts.add(s);
                else if (s.contains("*"))
                {
                    parts.add(s.substring(0, s.indexOf("*") - 1));
                    parts.add(s.substring(s.indexOf("*") - 1, s.indexOf("*") + 1));
                    parts.add(s.substring(s.indexOf("*") + 1));
                    parts.remove(s);
                }
                else
                    parts.add(s);
            }
        ArrayList<String> stringList = new ArrayList<>();
        for (String s : parts)
        {
            if (!s.isBlank())
                stringList.add(s);
        }
        return stringList;
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
        boolean kleeneStarOneChar = false;

        for (String s : parts)
        {
            transitions = new HashSet<>();
            acceptingStates = new HashSet<>();
            numstates = 0;
            currentState = 0;
            fress = 1;
            _tokens = s.toCharArray();
            kleeneStarOneChar = false;
            if (s.endsWith("*"))
            {
                //Kleene-Star
                s = s.substring(0, s.length() - 1);
                _tokens = s.toCharArray();
                numstates = s.length() + 1;
                fress = numstates - 1;
                acceptingStates.add(0);
                //z.B ab
                if (s.length() == 1)
                {
                    numstates = 1;
                    fress = 1;
                    kleeneStarOneChar = true;
                    for (char c : chars)
                    {
                        transitions.add(new DFATransition(0, 0, c));
                    }
                }
                else
                {
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
                        acceptingStates.add(++currentState);
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
                            {
                                transitions.add(new DFATransition(prevState, -1, c));
                            }
                        }
                    }
                }
            }
            if (!kleeneStarOneChar)
            {
                for (char c : chars)
                {
                    transitions.add(new DFATransition(-1, -1, c));
                    for (int i : acceptingStates)
                        transitions.add(new DFATransition(i, -1, c));
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
               // System.out.println(new RSA(numstates, chars, acceptingStates, copyTrans));
            }
        }
        RSA finalRSA = RSAList.get(0);
        for (int i = 1; i < RSAList.size(); i++)
        {
            finalRSA = finalRSA.concat((RSA) RSAList.get(i).minimize());
        }
        return (RSA) finalRSA.minimize();
    }

    public static void main(String[] args)
    {
        PatternMatcher pm = new PatternMatcher("a.*a");
        Set<Character> chars = new HashSet<>();
        chars.add('a');
        chars.add('b');

        System.out.println(pm.prepString("a.b.."));
        System.out.println(pm.toRSA(pm.prepString("ab.b.."), chars));
    }
}
