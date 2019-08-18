package com.hemdan.moviesapi;

import android.net.Uri;
import android.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class NetworkUtils {

    public static URL buildUrl(String fullUrl, List<Pair<String, String>> queryParams) {
        Uri.Builder uriBuilder = Uri.parse(fullUrl).buildUpon();
        for (Pair<String, String> param : queryParams) {
            uriBuilder.appendQueryParameter(param.first, param.second);
        }
        Uri builtUri = uriBuilder.build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("http://api.themoviedb.org", 80);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) {
            return true;
        }

    }
}
