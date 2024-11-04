import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuickLinkShortener {
    private Map<String, String> urlMap;
    private static final String FILE_PATH = "url_mappings.txt";
    private static final String DOMAIN = "http://short.ly/";

    public QuickLinkShortener() {
        urlMap = new HashMap<>();
        loadFromFile();
    }

    public String shortenURL(String longURL) {
        if (urlMap.containsValue(longURL)) {
            for (Map.Entry<String, String> entry : urlMap.entrySet()) {
                if (entry.getValue().equals(longURL)) {
                    return DOMAIN + entry.getKey();
                }
            }
        }
        
        String shortCode = generateShortCode();
        urlMap.put(shortCode, longURL);
        System.out.println("Short URL created: " + DOMAIN + shortCode);
        return DOMAIN + shortCode;
    }

    public String retrieveOriginalURL(String shortURL) {
        String shortCode = shortURL.replace(DOMAIN, "");
        if (urlMap.containsKey(shortCode)) {
            return urlMap.get(shortCode);
        } else {
            System.out.println("Short URL not found.");
            return null;
        }
    }

    private String generateShortCode() {
        return Integer.toHexString(urlMap.size() + 1);
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : urlMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            System.out.println("URL mappings saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving URL mappings.");
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        urlMap.put(parts[0], parts[1]);
                    }
                }
                System.out.println("URL mappings loaded successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while loading URL mappings.");
            }
        }
    }

    public static void main(String[] args) {
        QuickLinkShortener shortener = new QuickLinkShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- QuickLink Shortener ---");
            System.out.println("1. Shorten URL");
            System.out.println("2. Retrieve Original URL");
            System.out.println("3. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter a long URL: ");
                    String longURL = scanner.nextLine();
                    shortener.shortenURL(longURL);
                    break;
                case 2:
                    System.out.print("Enter a short URL: ");
                    String shortURL = scanner.nextLine();
                    String originalURL = shortener.retrieveOriginalURL(shortURL);
                    if (originalURL != null) {
                        System.out.println("Original URL: " + originalURL);
                    }
                    break;
                case 3:
                    shortener.saveToFile();
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}