package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;
import ab2.Factory;
import ab2.RSA;

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
        //chars.add('c');

        Set<Integer> accept = new TreeSet<>();
        accept.add(4);
        accept.add(3);

        transitions.add(factory.createTransition(0, 1, "a"));
        transitions.add(factory.createTransition(0, 2, "b"));
        transitions.add(factory.createTransition(1, 4, "b"));
        transitions.add(factory.createTransition(2, 2, "b"));
        transitions.add(factory.createTransition(2, 1, "a"));
        transitions.add(factory.createTransition(2, 3, "a"));
        transitions.add(factory.createTransition(4, 2, "b"));
        transitions.add(factory.createTransition(4, 3, "a"));


        transitions.add(factory.createTransition(1, 0, ""));
        transitions.add(factory.createTransition(4, 0, ""));
        transitions.add(factory.createTransition(3, 1, ""));
        transitions.add(factory.createTransition(4, 2, ""));



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
        RSA testRSA = n2.toRSA();
        System.out.println(testRSA);

        System.out.println("RSA EQUAL = "+testRSA.equalTo(n2));

    /*
        System.out.println(n2.reaches(0, 2));
        System.out.println(n2.isInfinite());
        */
        System.out.println(n2.reaches(0,3));
        System.out.println(n2.reaches(0,4));

        Set<Integer> accepts = new TreeSet<>();

        FA n1 = new FA(1, chars, accepts, Collections.emptySet());
        RSA testRSA2 = n1.toRSA();
        System.out.println("testRSA2   "+testRSA2);


        Set<Integer> accept33 = new TreeSet<>();
        accept.add(0);

        //n3
        Set<FATransition> transitions33 = new HashSet<>();
        transitions3.add(factory.createTransition(0, 0, "a"));
        ab2.FA n33 = new FA(1, chars, accept33, transitions33);
        n33.toRSA();

        //n4
        Set<Integer> accept4 = new TreeSet<>();
        accept.add(0);
        Set<FATransition> transitions4 = new HashSet<>();
        transitions4.add(factory.createTransition(0, 0, "a"));
        transitions4.add(factory.createTransition(0, 0, "b"));
        ab2.FA n4 = new FA(5, chars, accept, transitions4);
        n4.toRSA();

        //n5
        Set<Integer> accept5 = new TreeSet<>();
        accept.add(0);
        accept.add(1);
        Set<FATransition> transitions5 = new HashSet<>();
        transitions5.add(factory.createTransition(0, 0, "a"));
        transitions5.add(factory.createTransition(0, 0, "b"));
        transitions5.add(factory.createTransition(1, 1, "c"));
        transitions5.add(factory.createTransition(0, 1, ""));
        ab2.FA n5 = new FA(2, chars, accept, transitions5);
        n5.toRSA();

        //n6
        Set<Integer> accept6 = new TreeSet<>();
        accept.add(0);
        accept.add(1);

        Set<FATransition> transitions6 = new HashSet<>();
        transitions6.add(factory.createTransition(0, 0, "a"));
        transitions6.add(factory.createTransition(0, 0, "b"));
        transitions6.add(factory.createTransition(1, 1, "c"));
        transitions6.add(factory.createTransition(0, 1, ""));
        transitions6.add(factory.createTransition(1, 0, ""));

        ab2.FA n6 = new FA(2, chars, accept6, transitions6);
        n6.toRSA();

    }

}