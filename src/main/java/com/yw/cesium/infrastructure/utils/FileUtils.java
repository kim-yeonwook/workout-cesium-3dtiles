package com.yw.cesium.infrastructure.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {

    public static String readUrl(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(6000);

        if (conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
            try (InputStreamReader isr = new InputStreamReader(conn.getInputStream()); BufferedReader br = new BufferedReader(isr)) {

                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                return sb.toString();
            }
        }
        return null;
    }

    public static String readFile(File file) throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            String s = "";
            StringBuilder sb = new StringBuilder();

            while((s=r.readLine())!=null) {
                sb.append(s);
            }

            return sb.toString();
        }
    }
}
