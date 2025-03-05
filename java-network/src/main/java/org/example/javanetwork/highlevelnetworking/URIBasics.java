package org.example.javanetwork.highlevelnetworking;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class URIBasics {
    public static void main(String[] args) {
        URI uri = URI.create("https://www.example.com:8080/path/to/resource?query=1#fr");
        info(uri);

        // with user info
        uri = URI.create("https://user:pass@www.example.com:8080/path/to/resource?query=1#fr");
        info(uri);

        try {
            URL url = uri.toURL();
            info(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        URI path = URI.create("/path/to/resource?query=1#fr");
        URI mainURI = URI.create("https://www.example.com:8080");

        URI resolvedURI = mainURI.resolve(path);
        info(resolvedURI);
    }

    private static void info(URI uri) {
        System.out.printf("""
                ----------------------------------------
                [schema:]scheme-specific-part[#fragment]
                ----------------------------------------
                Scheme: %s
                Scheme-specific part: %s
                    Authority: %s
                        User info: %s
                        Host: %s
                        Port: %d
                        Path: %s
                        Query: %s
                Fragment: %s
                """,
                uri.getScheme(),
                uri.getSchemeSpecificPart(),
                uri.getAuthority(),
                uri.getUserInfo(),
                uri.getHost(),
                uri.getPort(),
                uri.getPath(),
                uri.getQuery(),
                uri.getFragment()
        );
    }

    private static void info(URL url) {
        System.out.printf("""
                ----------------------------------------
                [schema:]scheme-specific-part[#fragment]
                ----------------------------------------
                Scheme: %s
                Scheme-specific part: %s
                    Authority: %s
                        User info: %s
                        Host: %s
                        Port: %d
                        Path: %s
                        Query: %s
                Fragment: %s
                """,
                url.getProtocol(),
                url.getHost(),
                url.getAuthority(),
                url.getUserInfo(),
                url.getHost(),
                url.getPort(),
                url.getPath(),
                url.getQuery(),
                url.getRef()
        );
    }
}
