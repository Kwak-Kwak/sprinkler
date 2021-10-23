package com.sprinkler.kwakkwak.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

@Service
public class RankingService {

    private final static Logger log = Logger.getGlobal();
    private final static String baseURL="https://api.github.com";

    @Scheduled(cron = "0 0 0/4 * * ?")
    public void test() {
        JSONArray ret = get("/users/kwonchanmi/events?per_page=100&page=1");
        JSONArray ret2 = get("/users/kwonchanmi/events?per_page=100&page=2");
        JSONArray ret3 = get("/users/kwonchanmi/events?per_page=100&page=3");

        if (ret3.length() == 100) {
            System.out.println(ret3.get(99));
        }
        System.out.println(ret.length());
        System.out.println(ret2.length());
        System.out.println(ret3.length());
        JSONObject test = ret3.getJSONObject(ret3.length() - 1);
        System.out.println(test.get("created_at"));


    }

    public JSONArray get(String link) {
        JSONArray ret = new JSONArray();
        HttpURLConnection conn = null;

        try {
            URL url = new URL(baseURL + link);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ret = readJson(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }


    public static JSONArray readJson(HttpURLConnection conn) {

        JSONArray ret = new JSONArray();

        try {
            int responseCode = conn.getResponseCode();

            if (responseCode == 400) {
                log.warning("BAD REQUEST");
            } else if (responseCode == 401) {
                log.warning("UNAUTHORIZED");
            } else if (responseCode == 500) {
                log.warning("SERVER ERROR");
            } else if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();

                String line = "";

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                String temp = sb.toString();


                ret = new JSONArray(temp);

            } else {
                log.warning("ERROR");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
