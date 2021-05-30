package com.example.emptyactivityapplication;

import com.example.emptyactivityapplication.helper.CovidDataProcessor;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CovidService {
    StringBuffer inlinebuffer;
    public StringBuffer fetchCovidData() {
        inlinebuffer = new StringBuffer();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        System.out.println("*************I have started running.....");
                        URL url = new URL("https://api.covid19india.org/v4/min/data.min.json");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        int responsecode = conn.getResponseCode();
                        if (responsecode != 200) {
                            throw new RuntimeException("HttpResponseCode: " + responsecode);
                        }

                        Scanner scanner = new Scanner(url.openStream());

                        //Write all the JSON data into a string using a scanner
                        while (scanner.hasNext()) {
                            inlinebuffer.append(scanner.nextLine());
                        }
                        scanner.close();
                        System.out.println("***********Got data from the covid url");
                        System.out.println("Data is " + inlinebuffer.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        System.out.println("********Before thread start call...");
        thread.start();
        try {
            thread.join();
        }catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return inlinebuffer;
    }
}