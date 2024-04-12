import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        APIClient apiClient = new APIClient("https://rdb.altlinux.org/api");

        try {
            JSONArray package1 = apiClient.getBinaryPackages("sisyphus");
            JSONArray package2 = apiClient.getBinaryPackages("p10");
            if(!package1.isEmpty() && !package2.isEmpty()){
                System.out.println(new PackageComparator().comparePackages(package1, package2));
            }
            else{
                System.out.println("No packages found.");
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch binary packages: " + e.getMessage());
        }
    }
}
