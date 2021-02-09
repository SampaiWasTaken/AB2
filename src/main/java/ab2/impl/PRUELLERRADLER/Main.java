package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.Factory;

import java.util.*;

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
        accept.add(2);

        transitions.add(factory.createTransition(1, 2, "a"));
        transitions.add(factory.createTransition(2, 3, "a"));
        transitions.add(factory.createTransition(3, 4, "a"));
        transitions.add(factory.createTransition(4, 0, "a"));

        FA n2 = (FA) factory.createFA(5, chars, accept, transitions);


        Set<Integer> accept3 = new TreeSet<>();
        accept3.add(0);

        Set<FATransition> transitions3 = new HashSet<>();

        transitions3.add(factory.createTransition(0, 0, "a"));

        FA n3 = (FA) factory.createFA(1, chars, accept3, transitions3);


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
        //System.out.println(n2.accepts("aaaaba"));
        //System.out.println(n2.reaches(0,1));
        n2.toRSA();
    /*
        System.out.println(n2.reaches(0, 2));
        System.out.println(n2.isInfinite());
        System.out.println(n2.acceptsNothing());
        */

        boolean test = n3.acceptsEpsilonOnly();
        System.out.println(test + " AUTOMAT 3 FUCKING EPSILON ONLY");
    }

}