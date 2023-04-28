import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LinkDetection {
    public static void main(ArrayList<String> locatedURLs) {
        if (locatedURLs.size() != 0) {
            for (String urls : locatedURLs) {
                // Check if the URL is HTTP or HTTPS (Change: Added check for HTTP/HTTPS URLs)
                if (urls.startsWith("http://") || urls.startsWith("https://")) {
                    try {
                        // Change: Moved validation logic to a validateUrl Method
                        validateUrl(urls);
                    } catch (IOException e) {
                        System.out.println("Error processing URL: " + urls + ". " + e.getMessage());
                    }
                } else {
                    System.out.println("Skipping non-HTTP/HTTPS URL: " + urls);
                }
            }
        } else {
            System.out.println("No links were detected in the file");
        }
    }

    // Change: Added new method to validate URLs
    private static void validateUrl(String urlStr) throws IOException {
        String codeStatus;
        URL url = new URL(urlStr);
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();

            if (code >= 100 && code < 200) {
                codeStatus = "informational response";
            } else if (code >= 200 && code < 300) {
                codeStatus = "Successful Response";
            } else if (code >= 300 && code < 400) {
                codeStatus = "Redirection";
            } else if (code >= 400 && code < 500) {
                codeStatus = "Client Error";
            } else if (code >= 500 && code < 600) {
                codeStatus = "Server Error";
            } else {
                codeStatus = "Unknown Status";
            }

            System.out.println(urlStr + " " + code + " " + codeStatus);
        } finally {
            // Change: Added code to safely close the connection
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

