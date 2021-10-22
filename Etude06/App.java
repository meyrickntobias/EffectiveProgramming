import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    private static ArrayList<Voter> voters;
    private static ArrayList<Candidate> candidateList;

    public static void main(String[] args) {
        boolean unbreakableTieOccurrence = false;
        int currentRound = 0;
        readInput();
        while (candidateList.size() >= 1 && !unbreakableTieOccurrence) {
            Collections.sort(candidateList);
            currentRound++;
            System.out.println("Round " + currentRound);
            int totalVotes = 0;
            for (Candidate c : candidateList) {
                System.out.printf("%-12s", c.getCandidateName());
                System.out.println(c.getVotes());
                totalVotes += c.getVotes();
            }
            if (candidateList.get(0).getVotes() > (0.5 * totalVotes)) {
                System.out.println("Winner: " + candidateList.get(0).getCandidateName());
                break;
            }
            // Get loser of round
            Candidate loser = candidateList.get(candidateList.size()-1);
            int losers = 1;
            while (losers < candidateList.size()) {
                if (candidateList.get(candidateList.size()-(losers+1)).getVotes() == loser.getVotes()) {
                    losers++;
                    continue;
                } else {
                    break;
                }
            }
            if (losers > 1) {
                int losingIndex = breakTie(losers, currentRound);
                if (losingIndex == -1) {
                    System.out.println("Unbreakable Tie");
                    System.exit(0);
                }
                // The loser is the candidate at index = size - losingIndex (what happens if losing index = size?)
                loser = candidateList.remove(losingIndex);
            } else {
                candidateList.remove(candidateList.size()-1);
            }
            System.out.println("Eliminated: " + loser.getCandidateName());
            System.out.println();
            reassignVotes(loser);
            // Print out every candidate in the list and their votes
        } 
    }

    /**
     * Read the input into a candidates hashmap, and a voters arraylist (of voter objects), 
     * then convert candidates collection into list
     */
    private static void readInput() {
        HashMap<String, ArrayList<Integer>> candidates = new HashMap<String, ArrayList<Integer>>();
        voters = new ArrayList<Voter>();
        Scanner sc = new Scanner(System.in);
        int maxVotes = 0;
        while (sc.hasNextLine()) {
            Scanner line = new Scanner(sc.nextLine());
            ArrayList<String> votedFor = new ArrayList<String>();
            int currentVotes = 0;
            while (line.hasNext()) {
                String vote = line.next();
                candidates.put(vote, new ArrayList<Integer>());
                votedFor.add(vote);
                currentVotes++;
            }
            if (currentVotes > maxVotes) maxVotes = currentVotes;
            Voter v = new Voter(votedFor);
            voters.add(v);
        }
        for (Voter v : voters) {
            ArrayList<Integer> newVotesArray = candidates.get(v.getVote());
            if (newVotesArray.size() == 0) {
                newVotesArray.add(1);
            } else {
                newVotesArray.set(0, newVotesArray.get(0)+1);  
            }
            candidates.replace(v.getVote(), newVotesArray);
        }
        candidateList = new ArrayList<Candidate>();
        for (HashMap.Entry<String, ArrayList<Integer>> entry : candidates.entrySet()) {
            Candidate c = new Candidate(entry.getKey(), entry.getValue());
            candidateList.add(c);
        } 
    }

    /**
     * This method will tell us who wins from a tie by returning an integer 
     * For example the last in the array = 1, the second to last = 2, the third to last = 3,
     * If the tie is unbreakable, return -1
     * @param tyingElements the number of tying candidates
     * @param round the current round
     * @return the winner, return -1 if unbreakable
     */
    private static int breakTie(int tyingElements, int round) {
        /*
            Given a number of tying elements, let's say 2 - element 1 and element 0
            Given a round, say the third round, 
            Look at round 2, if candidate 1 has more votes, return 1
            If drawn, look at round 1, if candidate 1 has more votes, return 1
            If drawn
        */
        if (round == 0) return -1;
        // To iterate up the rounds:
        int roundCounter = round;
        roundCounter--;
        ArrayList<Candidate> tied = new ArrayList<Candidate>(candidateList.subList(candidateList.size()-tyingElements, candidateList.size()));
        // To avoid a concurrent modification exception being thrown, store
        // the candidates to be removed from the tied list in a list
        // of their own
        ArrayList<Candidate> toRemove = new ArrayList<Candidate>();
        while (roundCounter >= 0 && tied.size() > 1) {
            // Find which candidate wins, figure out which index the candidate
            // exists at, first keep track of the max vote
            // if candidate has lower vote, eliminate 
            Candidate highestVoted = tied.get(0);
            Candidate lowestVoted  = tied.get(0);
            // Work out who has the highest votes, and who has the lowest votes
            for (Candidate c : tied) {
                // System.out.println(roundCounter + " -> " + c.getCandidateName());
                if (c.getVotesInRound(roundCounter) > highestVoted.getVotesInRound(roundCounter)) {
                    highestVoted = c;
                } else if (c.getVotesInRound(roundCounter) < lowestVoted.getVotesInRound(roundCounter)) {
                    lowestVoted = c;
                }
            }
            if (highestVoted.equals(lowestVoted.getCandidateName())) {
                roundCounter--;
                continue;
            }
            // Any candidate higher than lowest vote can be taken out of the array
            for (Candidate c : tied) {
                if (c.getVotesInRound(roundCounter) > lowestVoted.getVotesInRound(roundCounter)) {
                    toRemove.add(c);
                    // System.out.println(tied.get(0));
                }
            }
            tied.removeAll(toRemove);
            roundCounter--;
        }
        if (tied.size() == 1) {
            // System.out.println("There is a loser");
            Candidate loser = tied.get(0);
            // System.out.println(tied.get(0));
            // System.out.println(candidateList.indexOf(loser));
            return candidateList.indexOf(loser);
        }
        return -1;
    }
    

    /**
     * This method will reassign the votes from the candidate who lost to
     * the candidates who were next in those voter's preference lists. 
     * @param loser the losing candidate in any given round
     */
    private static void reassignVotes(Candidate loser) {
        // Step 1 - identify the voters who voted for the losing candidate
        // Step 2 - find the next preference for each voter who voted for the losing candidate
        // Step 2b - if the voter who voted for the losing candidate has no more preferences,
            // remove them from voters
        // Step 3 - Add votes to the next preference for each voter who voted for the losing candidate
        ArrayList<String> votesAssignable = new ArrayList<String>();
        int index = 0;
        for (Voter v : voters) {
            if (!v.isActive()) {
                index++;
                continue;
            }
            // If the voter has voted for a loser, we need to reassign their votes
            if (v.getVote().equals(loser.getCandidateName())) {
                // Do they have any more preferences?
                if (!v.anyMoreVotes()) {
                    v.voteInactive();
                    index++;
                    continue;
                }
                // They do have more votes, so we increment their vote
                v.incrementCurrentVoteRound();
                // Check if their next candidate is still in the race
                while (!containsName(candidateList, v.getVote()) && v.anyMoreVotes()) v.incrementCurrentVoteRound();
                votesAssignable.add(v.getVote());
            }
            index++;
        }
        for (Candidate c : candidateList) {
            // Add a new round of votes
            c.addVotesInNewRound(true);
            if (votesAssignable.contains(c.getCandidateName())) {
                int votesToAdd = Collections.frequency(votesAssignable, c.getCandidateName());
                c.addVotes(votesToAdd);
            } 
        }
    }

    /**
     * A method for checking whether a candidate is in the race
     * @param list the list to check 
     * @param name the name to check
     * @return whether or not the arraylist contains a candidate with this name
     */
    public static boolean containsName(ArrayList<Candidate> list, String name) {
        return list.stream().anyMatch(o -> o.getCandidateName().equals(name));
    }

}