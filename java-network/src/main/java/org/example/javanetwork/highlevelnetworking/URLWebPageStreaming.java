package org.example.javanetwork.highlevelnetworking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLWebPageStreaming {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts?id=1");
            try (var inputStream = url.openStream()) {
                inputStream.transferTo(System.out);
            }

            URLConnection connection = url.openConnection();
            connection.getHeaderFields().forEach((key, value) -> System.out.println(key + ": " + value));
            print(connection.getInputStream());
            System.out.println("Content type: " + connection.getContentType());
            System.out.println(connection.getHeaderField("Content-Type"));
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
