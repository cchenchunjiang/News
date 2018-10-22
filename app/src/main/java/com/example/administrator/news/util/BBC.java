package com.example.administrator.news.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class BBC {
    private  String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject postRequestFromUrl(String url, String body) throws IOException, JSONException {
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(body);
        out.flush();

        InputStream instream = conn.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            instream.close();
        }
    }

    public  JSONObject getRequestFromUrl(String url) throws IOException, JSONException {
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        InputStream instream = conn.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            instream.close();
        }
    }
}
