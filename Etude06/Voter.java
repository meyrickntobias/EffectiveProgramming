import java.util.ArrayList;

public class Voter {

    // Keep track of all the candidates the voter voted for
    private ArrayList<String> candidates;
    // Keep track of which candidate is being counted in the candidate list
    private int currentVoteRound;
    private String currentCandidate;
    private boolean activeVote = true;

    public Voter(ArrayList<String> candidates) {
        this.candidates = candidates;
        this.currentVoteRound = 0;
        this.currentCandidate = candidates.get(0);
    }

    public void voteInactive() {
        this.activeVote = false;
    }

    public boolean isActive() {
        return this.activeVote;
    }

    /**
     * Increment the vote counter, e.g: if the previous candidate gets rolled
     * @return true if the vote has been incremented or false if the voter has reached the end of the candidate list
     */
    public boolean incrementCurrentVoteRound() {
        if (this.currentVoteRound >= candidates.size() - 1) return false;
        this.currentVoteRound++;
        this.currentCandidate = this.candidates.get(this.currentVoteRound);
        return true;
    }

    public String getVote() {
        return this.currentCandidate;
    }

    public ArrayList<String> getCandidates() {
        return this.candidates;
    }

    public boolean anyMoreVotes() {
        if (currentVoteRound == candidates.size()-1) return false;
        return true;
    }

    @Override
    public String toString() {
        // Print out all candidates in each round
        String toPrint = "";
        for (String c : this.candidates) toPrint = toPrint.concat(c + " ");
        return toPrint;
    }



}