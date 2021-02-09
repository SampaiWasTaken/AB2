package ab2.impl.PRUELLERRADLER;

import ab2.FATransition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TransitionTable {
    private Set<Integer> currentState;
    private ArrayList<Set<Integer>> nextSteps = new ArrayList<>();

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
            Set<Integer> intSet =  new HashSet<>();
            nextSteps.add(intSet);
            for(FATransition t : transitions){
                for(Integer i : currentState){
                    if(t.from() == i && (t.symbols().toLowerCase().equals(""+ch))){  //||t.symbols().equals("")
                        nextSteps.get(j).add(t.to());
                    }
                }
            }
            j++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitionTable that = (TransitionTable) o;
        return currentState.equals(that.currentState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState);
    }

    @Override
    public String toString() {
        String returnString = "Current State " + currentState;
        for(int i = 0; i < nextSteps.size(); i++){
            returnString += " | " + nextSteps.get(i).toString();
        }
        return returnString;
    }
}
