import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class PackageComparatorCLI {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java PackageComparatorCLI <branch1> <branch2>");
            return;
        }

        String branch1 = args[0];
        String branch2 = args[1];
        APIClient apiClient = new APIClient("https://rdb.altlinux.org/api");
        try {
            System.out.println("packages received!");
            // Получаем списки пакетов для каждой ветки с использованием вашей библиотеки
            JSONArray packages1 = apiClient.getBinaryPackages(branch1);
            JSONArray packages2 = apiClient.getBinaryPackages(branch2);

            if(!packages1.isEmpty() && !packages2.isEmpty()){
                // Создаем экземпляр класса PackageComparator
                PackageComparator comparator = new PackageComparator();
                System.out.println("packages is compare...");
                // Вызываем метод comparePackages для сравнения пакетов
                JSONObject comparisonResult = comparator.comparePackages(packages1, packages2);

                // Выводим результат в консоль
                System.out.println("Comparison Result:");
                System.out.println(comparisonResult.toString());
            }
            else{
                System.out.println("No packages found.");
            }

        }catch (IOException e) {
            System.out.println("Failed to fetch binary packages: " + e.getMessage());
        }

    }

}
