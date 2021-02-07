package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class TransitionTable {
    private Set<Integer> currentState;
    private ArrayList<Set<Integer>> nextSteps;

    public TransitionTable(Set<Integer> currentState) {
        this.currentState = currentState;
    }

    public Set<Integer> getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Set<Integer> currentState) {
        this.currentState = currentState;
    }

    public ArrayList<Set<Integer>> getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(ArrayList<Set<Integer>> nextSteps) {
        this.nextSteps = nextSteps;
    }

    public void calculateSteps(Set<ab2.FATransition> transitions, Set<Character> characters){
        int j = 0;
        for(Character ch : characters){
            nextSteps.add(new TreeSet<Integer>());
            for(FATransition t : transitions){
                for(Integer i : currentState){
                    if(t.from() == i && (t.symbols().equals(ch)||t.symbols().equals(""))){
                        nextSteps.get(j).add(i);
                    }
                }
            }
            j++;
        }
    }
}
