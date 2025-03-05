package org.example.javanetwork.highlevelnetworking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUrlConnectionExample {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts?id=1");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Chrome"); // Some apis can block requests without user agent
            connection.setRequestProperty("Accept", "application/json, text/html");
            connection.setReadTimeout(5000); // wait 5 seconds for response completion

            System.out.println(connection.getRequestMethod());
            System.out.println(connection.getResponseCode());
            System.out.println(connection.getResponseMessage());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Failed to get data");
                return;
            }

            print(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void print(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
