import java.util.*;

/**
 * This program distributes prizes from a list of prizes' values to a list of winners' names
 * in a fair manner, where fairness is defined as minimizing the difference in total prize values
 * among the winners as much as possible.
 */
public class LotteryDistributor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input prizes' values and winners' names
        System.out.println("Enter the comma-separated list of this week's prizes' values:");
        String prizesInput = scanner.nextLine();

        System.out.println("Enter the comma-separated names of this week's winners:");
        String winnersInput = scanner.nextLine();

        // Split input into arrays of prizes and winners
        String[] prizesArray = prizesInput.split(",");
        String[] winnersArray = winnersInput.split(",");

        // Convert prizes' strings to integers
        int[] prizes = new int[prizesArray.length];
        for (int i = 0; i < prizesArray.length; i++) {
            prizes[i] = Integer.parseInt(prizesArray[i]);
        }

        Arrays.sort(prizes); // Sort prizes in ascending order

        // Create a distribution map to track each winner's prizes
        Map<String, List<Integer>> distribution = new HashMap<>();
        for (String winner : winnersArray) {
            distribution.put(winner, new ArrayList<>());
        }

        // Distribute prizes among winners
        for (int prizeIndex = prizes.length - 1; prizeIndex >= 0; prizeIndex--) {
            String minTotalWinner = getWinnerWithMinTotal(distribution);
            distribution.get(minTotalWinner).add(prizes[prizeIndex]);
        }

        // Display the distribution of prizes for each winner
        for (String winner : winnersArray) {
            System.out.print(winner + ":");
            List<Integer> prizesForWinner = distribution.get(winner);
            for (int i = 0; i < prizesForWinner.size(); i++) {
                if (i > 0) {
                    System.out.print(",");
                }
                System.out.print(prizesForWinner.get(i));
            }
            System.out.println();
        }

        scanner.close();
    }

    /**
     * Get the winner with the minimum total prize value.
     * 
     * @param distribution A map containing winners and their allocated prizes.
     * @return The name of the winner with the minimum total prize value.
     */
    private static String getWinnerWithMinTotal(Map<String, List<Integer>> distribution) {
        int minTotal = Integer.MAX_VALUE;
        String minTotalWinner = null;
        for (Map.Entry<String, List<Integer>> entry : distribution.entrySet()) {
            int total = entry.getValue().stream().mapToInt(Integer::intValue).sum();
            if (total < minTotal) {
                minTotal = total;
                minTotalWinner = entry.getKey();
            }
        }
        return minTotalWinner;
    }
}
