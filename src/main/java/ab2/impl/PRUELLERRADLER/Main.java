package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
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

        Set<Integer> acceptn1 = new TreeSet<>();
        Set<Character> charsn1 = new HashSet<>();
        charsn1.add('a');
        charsn1.add('b');
        charsn1.add('c');

        ab2.FA n1 = new FA(1, charsn1, acceptn1, Collections.emptySet());
        RSA RSAn1 = n1.toRSA();
        System.out.println("RSAn1 test"+RSAn1);
        RSAn1.minimize();
        System.out.println("RSAn1 minimized test"+RSAn1);




        //bsp 7.5 aus übung
        Set<FATransition> transitions75 = new HashSet<>();
        Set<Character> chars75 = new HashSet<>();
        chars75.add('a');
        chars75.add('b');
        //chars.add('c');

        Set<Integer> accept75 = new TreeSet<>();
        accept75.add(0);
        accept75.add(3);

        transitions75.add(factory.createTransition(0, 1, "a"));
        transitions75.add(factory.createTransition(0, 2, "b"));
        transitions75.add(factory.createTransition(1, 3, "b"));
        transitions75.add(factory.createTransition(1, 2, "a"));
        transitions75.add(factory.createTransition(2, 1, "a"));
        transitions75.add(factory.createTransition(2, 3, "b"));
        transitions75.add(factory.createTransition(3, 4, "b"));
        transitions75.add(factory.createTransition(3, 5, "a"));
        transitions75.add(factory.createTransition(4, 1, "a"));
        transitions75.add(factory.createTransition(4, 1, "b"));
        transitions75.add(factory.createTransition(5, 2, "a"));
        transitions75.add(factory.createTransition(5, 2, "b"));

        ab2.FA n75 = new FA(6, chars75, accept75, transitions75);
        RSA RSAn75 = n75.toRSA();
        System.out.println(RSAn75);
        System.out.println("################################## 75");
        RSAn75 = RSAn75.minimize();
        System.out.println(RSAn75.accepts(""));


        Set<Character> charsn3 = new HashSet<>();
        charsn3.add('a');
        charsn3.add('b');
        charsn3.add('c');

        Set<Integer> accept3 = new TreeSet<>();
        accept3.add(0);

        Set<FATransition> transitions3 = new HashSet<>();

        transitions3.add(factory.createTransition(0, 0, "a"));

        FA n3 = (FA) factory.createFA(1, charsn3, accept3, transitions3);
        System.out.println("##########################################123#  "+n3.acceptsEpsilonOnly());
        RSA RSAn3 = n3.toRSA();
        RSAn3 = RSAn3.minimize();
        //System.out.println(RSAn3.acceptsNothing());


        Set<Character> charsn7 = new HashSet<>();
        charsn7.add('a');
        charsn7.add('b');

        Set<Integer> acceptn7 = new TreeSet<>();
        acceptn7.add(4);

        Set<FATransition> transitionsn7 = new HashSet<>();

        transitionsn7.add(factory.createTransition(0, 1, "b"));
        transitionsn7.add(factory.createTransition(0, 2, "a"));
        transitionsn7.add(factory.createTransition(0, 3, "a"));
        transitionsn7.add(factory.createTransition(1, 2, "b"));
        transitionsn7.add(factory.createTransition(1, 4, "a"));
        transitionsn7.add(factory.createTransition(2, 2, "a"));
        transitionsn7.add(factory.createTransition(2, 4, "b"));
        transitionsn7.add(factory.createTransition(3, 2, "b"));

        FA n7 = new FA(5, charsn7, acceptn7, transitionsn7);
        RSA RSAn7 = n7.toRSA().minimize();
        System.out.println(RSAn7);
       // System.out.println(RSAn7.acceptsNothing());


        Set<Character> charsn9 = new HashSet<>();
        charsn9.add('a');
        charsn9.add('b');
        charsn9.add('c');
        Set<Integer> acceptn9 = new TreeSet<>();
        acceptn9.add(7);

        Set<FATransition> transitionsn9 = new HashSet<>();

        transitionsn9.add(factory.createTransition(5, 6, "a"));
        transitionsn9.add(factory.createTransition(5, 1, "b"));
        transitionsn9.add(factory.createTransition(6, 7, "b"));
        transitionsn9.add(factory.createTransition(6, 3, "a"));
        transitionsn9.add(factory.createTransition(7, 7, "b"));
        transitionsn9.add(factory.createTransition(7, 7, "a"));
        transitionsn9.add(factory.createTransition(3, 4, "b"));
        transitionsn9.add(factory.createTransition(3, 0, "a"));
        transitionsn9.add(factory.createTransition(0, 1, "b"));
        transitionsn9.add(factory.createTransition(0, 0, "a"));
        transitionsn9.add(factory.createTransition(2, 3, "a"));
        transitionsn9.add(factory.createTransition(2, 1, "b"));
        transitionsn9.add(factory.createTransition(1, 2, "a"));
        transitionsn9.add(factory.createTransition(1, 1, "b"));
        transitionsn9.add(factory.createTransition(4, 5, "b"));
        transitionsn9.add(factory.createTransition(4, 2, "a"));
        transitionsn9.add(factory.createTransition(1, 4, "c"));
        transitionsn9.add(factory.createTransition(2, 6, "c"));
        transitionsn9.add(factory.createTransition(5, 3, "c"));
        transitionsn9.add(factory.createTransition(1, 6, ""));
        transitionsn9.add(factory.createTransition(2, 4, ""));
        transitionsn9.add(factory.createTransition(7, 3, ""));

        FA n9 = new FA(8, charsn9, acceptn9, transitionsn9);
        RSA RSAn9 = n9.toRSA().minimize();
        System.out.println("");
        System.out.print(RSAn9);
        System.out.println(RSAn9.acceptsNothing());
        System.out.println(RSAn9.accepts("bcbab"));
        System.out.println(RSAn9);
       Set<ab2.DFATransition> transitionsRSAn9 = RSAn9.getTransitions();
        System.out.println("number of transitions RSAn9 " + transitionsRSAn9.size());


        Set<Integer> acceptn2 = new TreeSet<>();
        accept.add(0);

        Set<FATransition> transitionsn2 = new HashSet<>();

        transitionsn2.add(factory.createTransition(1, 2, "a"));
        transitionsn2.add(factory.createTransition(2, 3, "a"));
        transitionsn2.add(factory.createTransition(3, 4, "a"));
        transitionsn2.add(factory.createTransition(4, 0, "a"));

        Set<Character> charsn2 = new HashSet<>();
        charsn2.add('a');
        charsn2.add('b');
        charsn2.add('c');

        FA FAn2 = new FA(5, charsn2, acceptn2, transitionsn2);


        FA test = (FA) RSAn1.union(FAn2);
        System.out.println("RSAn1 num state "+RSAn1.getNumStates() + "    FAn2 " + FAn2.getNumStates());
        System.out.println("FA union TEST");
        System.out.println(test);
        System.out.println("number of states minimized" +test.toRSA().minimize().getNumStates());
        System.out.println(test.toRSA().minimize());
        int i = n1.union(FAn2).toRSA().minimize().getNumStates();
        System.out.println("DER SHIT DER NIT GEHT " + i);
        
        //n4
        Set<Integer> accept4 = new TreeSet<>();
        accept.add(0);
        Set<FATransition> transitions4 = new HashSet<>();
        transitions4.add(factory.createTransition(0, 0, "a"));
        transitions4.add(factory.createTransition(0, 0, "b"));
        ab2.FA n4 = new FA(5, charsn2, accept, transitions4);

        //n5
        Set<Integer> accept5 = new TreeSet<>();
        accept5.add(0);
        accept5.add(1);
        Set<FATransition> transitions5 = new HashSet<>();
        transitions5.add(factory.createTransition(0, 0, "a"));
        transitions5.add(factory.createTransition(0, 0, "b"));
        transitions5.add(factory.createTransition(1, 1, "c"));
        transitions5.add(factory.createTransition(0, 1, ""));
        ab2.FA n5 = new FA(2, charsn2, accept5, transitions5);
        n5.toRSA(); // sollte 3 zustände haben

        System.out.println(n4.union(n5).toRSA().minimize().getNumStates());




/*
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


        System.out.println(n2.reaches(0, 2));
        System.out.println(n2.isInfinite());

        System.out.println("reach");
        System.out.println(n2.reaches(0,3, 0, false, 0));
        System.out.println(n2.reaches(0,4, 0, false, 0));

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







        //n6
        Set<Integer> accept6 = new TreeSet<>();
        accept6.add(0);
        accept6.add(1);

        Set<FATransition> transitions6 = new HashSet<>();
        transitions6.add(factory.createTransition(0, 0, "a"));
        transitions6.add(factory.createTransition(0, 0, "b"));
        transitions6.add(factory.createTransition(1, 1, "c"));
        transitions6.add(factory.createTransition(0, 1, ""));
        transitions6.add(factory.createTransition(1, 0, ""));

        ab2.FA n6 = new FA(2, chars, accept6, transitions6);
        //RSA RSAn6 = n6.toRSA();
        //System.out.println(RSAn6);
       // RSAn6.minimize();
 */


    }


}