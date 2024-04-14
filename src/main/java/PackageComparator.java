import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PackageComparator {
    public JSONObject comparePackages(JSONArray packages1, JSONArray packages2) {
        // Initialize sets, maps, and arrays to store package information
        Set<String> names1 = new HashSet<>();
        Set<String> names2 = new HashSet<>();
        HashMap<String, String> versions = new HashMap<>();
        JSONArray greaterVersions = new JSONArray();
        HashMap<String, JSONObject> packageMap1 = new HashMap<>();
        HashMap<String, JSONObject> packageMap2 = new HashMap<>();

        // Extract necessary data from the first package list
        for (int i = 0; i < packages1.length(); i++) {
            JSONObject packageObj1 = packages1.getJSONObject(i);
            String name1 = packageObj1.getString("name");
            String version1 = packageObj1.getString("version");
            String release1 = packageObj1.getString("release");
            String versionRelease = version1 + "-" + release1;

            // Store package information in sets and maps
            names1.add(name1);
            packageMap1.put(name1, packageObj1);
            versions.put(name1, versionRelease);
        }

        // Extract necessary data from the second package list and compare versions
        for (int j = 0; j < packages2.length(); j++) {
            JSONObject packageObj2 = packages2.getJSONObject(j);
            String name2 = packageObj2.getString("name");
            String version2 = packageObj2.getString("version");
            String release2 = packageObj2.getString("release");
            String versionRelease2 = version2 + "-" + release2;

            // Store package information in sets and maps
            names2.add(name2);
            packageMap2.put(name2, packageObj2);

            // Compare versions and releases with the first package list
            if(names1.contains(name2)){
                String versionRelease1 = versions.get(name2);
                if(compareVersions(versionRelease1, versionRelease2) > 0) greaterVersions.put(packageMap1.get(name2));
            }
        }

        // Find packages missing in each list
        JSONArray missingInSecond = new JSONArray();
        JSONArray missingInFirst = new JSONArray();

        for (int i = 0; i < packages1.length(); i++) {
            String name1 = packages1.getJSONObject(i).getString("name");
            if (!names2.contains(name1)) {
                missingInSecond.put(packageMap1.get(name1));
            }
        }

        for (int i = 0; i < packages2.length(); i++) {
            String name2 = packages2.getJSONObject(i).getString("name");
            if (!names1.contains(name2)) {
                missingInFirst.put(packageMap2.get(name2));
            }
        }

        // Create JSON object with comparison results
        JSONObject result = new JSONObject();
        result.put("missing_in_second", missingInSecond);
        result.put("missing_in_first", missingInFirst);
        result.put("greater_versions", greaterVersions);

        return result;
    }

    // Method to compare version strings
    private int compareVersions(String v1, String v2) {
        return v1.compareTo(v2);
    }
}
