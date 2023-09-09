/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90 
 * part Patch
 * 
 * @author Chris D'Mello 1566105
 * @author Jayin Nejal 1537199 
 * assignment group 9
 * 
 * assignment copyright Kees Huizing
 */

import java.util.ArrayList;

class Patch {
    private boolean strategy; // strategy of patch : true for C and false for D

    private double score = 0; // stores score of each patch

    private boolean justSwitch = false; // checks whether patch has just switched strategy

    private ArrayList<Patch> neighbours = new ArrayList<Patch>(); // contains neighbours of the patches

    // returns true if and only if patch is cooperating
    boolean isCooperating() {
        return this.strategy;
    }

    // adds neighbours of a patch to an array list
    void addNeighbours(Patch neighbourPatch) {
        this.neighbours.add(neighbourPatch);
    }

    // set strategy to C if isC is true and to D if false
    void setCooperating(boolean isC) {
        this.strategy = isC;
    }

    // change strategy from C to D and vice versa
    void toggleStrategy() {
        this.strategy = !this.strategy;
    }

    // returns an array list of strategies of highest scoring patches in the
    // neighbourhood
    ArrayList<Boolean> getNeighbourStrategy(double max) {
        ArrayList<Boolean> strategies = new ArrayList<Boolean>(); // stores strategies of max score neighbours
        for (Patch z : this.neighbours) {
            if (max == z.getScore()) {
                strategies.add(z.isCooperating());
            }
        }
        return strategies;
    }

    // calculates the score of the patch in the current round
    void calcScore(double alpha) {
        for (Patch z : this.neighbours) {
            if (z.isCooperating()) {
                this.score++;
            }
        }
        if (!this.isCooperating()) {
            this.score = (double) (this.score * alpha);
        }
    }

    // return score of this patch in current round
    double getScore() {
        return this.score;
    }

    // resets the score of a patch to 0 after each round
    void resetScore() {
        this.score = 0;
    }

    // returns the maximum score amongst the patch and its neighbours
    double getMax() {
        double max = 0; // stores the maximum score in the neigbourhood
        for (Patch z : this.neighbours) {
            if (z.getScore() >= max) {
                max = z.getScore();
            }
        }
        if (this.getScore() >= max) {
            max = this.getScore();
        }
        return max;
    }

    // returns true if patch has switched from C to D or vice versa
    boolean switched() {
        return this.justSwitch;
    }

    // assigns justSwitch to true if it has just switched its strategy and false otherwise
    void setSwitch(boolean isS) {
        this.justSwitch = isS;
    }
}