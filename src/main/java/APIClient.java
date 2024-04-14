import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIClient {
    private String baseUrl;

    // Constructor to initialize the base URL
    public APIClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Method to fetch binary packages for a given branch
    public JSONArray getBinaryPackages(String branch) throws IOException {
        // Construct the URL for fetching binary packages based on the branch
        URL url = new URL(baseUrl + "/export/branch_binary_packages/" + branch);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get the response code from the connection
        int responseCode = connection.getResponseCode();

        // If the response code is OK, proceed to fetch the packages
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response from the connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Convert the response to a JSON object
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Extract the 'packages' array from the JSON response and return it
            return jsonResponse.getJSONArray("packages");
        } else {
            // If the response code is not OK, throw an IOException with the error message
            throw new IOException("Failed to fetch binary packages. Response code: " + responseCode);
        }
    }
}
