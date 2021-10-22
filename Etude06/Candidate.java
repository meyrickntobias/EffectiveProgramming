import java.util.ArrayList;
import java.util.Collections;

/*
    The candidate class will be used to keep track of 
    candidate's data, such as their name, their votes in
    each round (through an arraylist)
    Methods -> Increment vote (in current round), add votes in new round,
    get the candidate name, compare one player to another for sorting purposes.
*/

public class Candidate implements Comparable<Candidate> {

    private String name;
    private ArrayList<Integer> votes;
    private int round = 0;

    public Candidate(String name) {
        this.name = name;
        this.votes = new ArrayList<Integer>();
        this.votes.add(0);
    }

    public Candidate(String name, ArrayList<Integer> votes) {
        this.name = name;
        this.votes = votes;
        if (this.votes.isEmpty()) this.votes.add(0);
        this.round = votes.size()-1;
    }

    @Override
    public String toString() {
        return this.name + " -> " + this.votes.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Candidate)) return false;
        if (o instanceof String) {
            String str = (String) o;
            return this.getCandidateName().equals(str);
        }
        Candidate c = (Candidate) o;
        return this.getCandidateName().equals(c.getCandidateName());
    }
    
    @Override 
    public int compareTo(Candidate otherCandidate) {
        if (otherCandidate.getVotes() == this.getVotes()) return this.getCandidateName().compareTo(otherCandidate.getCandidateName());
        return Integer.compare(otherCandidate.getVotes(), this.getVotes());
    }

    public int getVotes() {
        return this.votes.get(this.votes.size()-1);
    }

    public int getVotesInRound(int round) {
        return this.votes.get(round);
    }

    public void addVotesInNewRound(boolean lastRoundIdentity) {
        if (lastRoundIdentity) {
            this.votes.add(this.getVotes());
        } else {
            this.votes.add(1);
        }
        this.round++;
    } 

    public void incrementVote() {
        this.votes.set(round, this.votes.get(round)+1);
    }

    public void addVotes(int votesToAdd) {
        this.votes.set(round, this.votes.get(round)+votesToAdd);
    }

    public String getCandidateName() {
        return this.name;
    }

}