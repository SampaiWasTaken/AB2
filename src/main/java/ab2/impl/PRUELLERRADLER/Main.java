package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.Factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main
{
    public static void main(String[] args)
    {


        Factory factory = new FactoryImpl();

        Set<FATransition> transitions = new HashSet<>();

        Set<Character> readChars = new HashSet<>(Arrays.asList('0', '1', 'a', 'b', 'c', 'd', 'e', 'f', '|'));
        Set<Character> writeChars = new HashSet<>(Arrays.asList('0', '1', 'a', 'b', 'c', 'd', 'e', 'f'));

        Set<Character> chars = new HashSet<>();
        chars.add('a');
        chars.add('b');
        chars.add('c');

        Set<Integer> accept = new TreeSet<>();
        accept.add(0);

        transitions.add(factory.createTransition(1, 2, "a"));
        transitions.add(factory.createTransition(2, 3, "a"));
        transitions.add(factory.createTransition(3, 4, "a"));
        transitions.add(factory.createTransition(4, 0, "aba"));

        FA n2 = (FA) factory.createFA(5, chars, accept, transitions);

        Set<FATransition> tests = (Set<FATransition>) n2.getTransitions();
        for(FATransition t : tests){
            System.out.println(t.from() + " : "+t.symbols() +" : "+ t.to());
        }

        n2.splitTransition();

        System.out.println("SSHHHHHHEEEEEESH");
        Set<FATransition> tests2 = (Set<FATransition>) n2.getTransitions();
        for(FATransition t : tests2){
            System.out.println(t.from() + " : "+t.symbols() +" : "+ t.to());
        }

      //  System.out.println(n2.accepts("aaaaba"));
        
    }
}