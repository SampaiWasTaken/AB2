package ab2.impl.PRUELLERRADLER;

import ab2.DFATransition;
import ab2.FA;
import ab2.IllegalCharacterException;

import java.util.Set;

public class RSA implements ab2.RSA {
    @Override
    public ab2.RSA minimize() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public int getActState() {
        return 0;
    }

    @Override
    public int doStep(char c) throws IllegalArgumentException, IllegalStateException {
        return 0;
    }

    @Override
    public Integer getNextState(int s, char c) throws IllegalCharacterException, IllegalStateException {
        return null;
    }

    @Override
    public boolean isAcceptingState() {
        return false;
    }

    @Override
    public Set<Character> getSymbols() {
        return null;
    }

    @Override
    public Set<Integer> getAcceptingStates() {
        return null;
    }

    @Override
    public boolean isAcceptingState(int s) throws IllegalStateException {
        return false;
    }

    @Override
    public Set<DFATransition> getTransitions() {
        return null;
    }

    @Override
    public int getNumStates() {
        return 0;
    }

    @Override
    public FA union(FA a) {
        return null;
    }

    @Override
    public FA intersection(FA a) {
        return null;
    }

    @Override
    public FA minus(FA a) {
        return null;
    }

    @Override
    public FA concat(FA a) {
        return null;
    }

    @Override
    public FA complement() {
        return null;
    }

    @Override
    public FA kleeneStar() {
        return null;
    }

    @Override
    public FA plus() {
        return null;
    }

    @Override
    public ab2.RSA toRSA() {
        return null;
    }

    @Override
    public boolean accepts(String w) throws IllegalCharacterException {
        return false;
    }

    @Override
    public boolean acceptsNothing() {
        return false;
    }

    @Override
    public boolean acceptsEpsilonOnly() {
        return false;
    }

    @Override
    public boolean acceptsEpsilon() {
        return false;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public boolean isFinite() {
        return false;
    }

    @Override
    public boolean subSetOf(FA a) {
        return false;
    }

    @Override
    public boolean equalTo(FA b) {
        return false;
    }

    @Override
    public Boolean equalsPlusAndStar() {
        return null;
    }
}
