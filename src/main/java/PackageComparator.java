import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.Set;

public class PackageComparator {
    public JSONObject comparePackages(JSONArray packages1, JSONArray packages2) {
        Set<String> names1 = new HashSet<>();
        Set<String> names2 = new HashSet<>();
        Set<String> greaterVersions = new HashSet<>();

        // Сначала заполняем множества имен пакетов
        for (int i = 0; i < packages1.length(); i++) {
            JSONObject packageObj = packages1.getJSONObject(i);
            names1.add(packageObj.getString("name"));
        }

        for (int i = 0; i < packages2.length(); i++) {
            JSONObject packageObj = packages2.getJSONObject(i);
            names2.add(packageObj.getString("name"));
        }

        // Затем находим пакеты, которые присутствуют только в одном из списков
        JSONArray missingInSecond = new JSONArray();
        JSONArray missingInFirst = new JSONArray();

        for (String name : names1) {
            if (!names2.contains(name)) {
                missingInSecond.put(name);
            }
        }

        for (String name : names2) {
            if (!names1.contains(name)) {
                missingInFirst.put(name);
            }
        }

        // Наконец, находим пакеты, версия и/или выпуск которых больше в первом списке, чем во втором
        for (int i = 0; i < packages1.length(); i++) {
            JSONObject packageObj1 = packages1.getJSONObject(i);
            String name1 = packageObj1.getString("name");
            String version1 = packageObj1.getString("version");
            String release1 = packageObj1.getString("release");

            for (int j = 0; j < packages2.length(); j++) {
                JSONObject packageObj2 = packages2.getJSONObject(j);
                String name2 = packageObj2.getString("name");
                String version2 = packageObj2.getString("version");
                String release2 = packageObj2.getString("release");

                if (name1.equals(name2)) {
                    if (compareVersions(version1, version2) > 0 || compareVersions(release1, release2) > 0) {
                        greaterVersions.add(name1);
                    }
                }
            }
        }

        // Формируем JSON с результатами сравнения
        JSONObject result = new JSONObject();
        result.put("missing_in_second", missingInSecond);
        result.put("missing_in_first", missingInFirst);
        result.put("greater_versions", new JSONArray(greaterVersions));

        return result;
    }

    private int compareVersions(String v1, String v2) {
        // Простое сравнение версий, можно улучшить
        return v1.compareTo(v2);
    }
}
