package ab2.impl.PRUELLERRADLER;

import java.util.Set;
import java.util.TreeSet;

public class Main
{
    public static void main(String[] args)
    {

        Set<Integer> testSet = new TreeSet<>(); //ahh thx jetzt was i was mir das set bringt XD
        testSet.add(5);
        testSet.add(5);
        testSet.add(5);
        testSet.add(5);
        testSet.add(5);
        testSet.add(4);
        testSet.add(1);
        testSet.add(5);
        testSet.add(1);
        testSet.add(4);

        for(Integer i : testSet){
            System.out.println(i);
        }
        
        
    }
}