import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class PackageComparatorCLI {
    public static void main(String[] args) throws IOException {
        // Check if the correct number of arguments is provided
        if (args.length != 2) {
            System.out.println("Usage: java PackageComparatorCLI <branch1> <branch2>");
            return;
        }

        // Extract branch names from command-line arguments
        String branch1 = args[0];
        String branch2 = args[1];

        // Initialize an instance of APIClient with the base URL
        APIClient apiClient = new APIClient("https://rdb.altlinux.org/api");

        try {
            // Print message indicating that packages are being received
            System.out.println("Packages received!");

            // Retrieve lists of binary packages for each branch using the APIClient
            JSONArray packages1 = apiClient.getBinaryPackages(branch1);
            JSONArray packages2 = apiClient.getBinaryPackages(branch2);

            // Check if packages are retrieved successfully
            if (!packages1.isEmpty() && !packages2.isEmpty()) {
                // Create an instance of PackageComparator to compare packages
                PackageComparator comparator = new PackageComparator();
                // Print message indicating that packages are being compared
                System.out.println("Packages are being compared...");

                // Call the comparePackages method to perform package comparison
                JSONObject comparisonResult = comparator.comparePackages(packages1, packages2);

                // Print the comparison result to the console
                System.out.println("Comparison Result:");
                System.out.println(comparisonResult.toString());
                System.out.println("Data received!!!");
            } else {
                // Print message indicating no packages are found
                System.out.println("No packages found.");
            }
        } catch (IOException e) {
            // Handle IOException if fetching packages fails
            System.out.println("Failed to fetch binary packages: " + e.getMessage());
        }
    }
}
