package com.example.emptyactivityapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner_title);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Chengalpattu");
        categories.add("Coimbatore");
        categories.add("Chennai");
        categories.add("Madurai");
        categories.add("Erode");
        categories.add("Kancheepuram");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        final StringBuffer inlinebuffer = new StringBuffer();
        try  {
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

            System.out.println("********After thread start call...");
            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inlinebuffer.toString());

            System.out.println(inlinebuffer.toString());

            //Get the required object from the above created object
            JSONObject tnObj = (JSONObject) data_obj.get("TN");
            JSONObject tnData = (JSONObject) tnObj.get("delta") ;

            //Get the required data using its key
            System.out.println("******I am parsing data returned by URL now");
            System.out.println("Today confirmed count: " + tnData.get("confirmed"));
            System.out.println("Today dead count: " + tnData.get("deceased"));
            final TextView covidcount = (TextView) findViewById(R.id.tncovidcount);
            covidcount.setText(covidcount.getText() + tnData.get("confirmed").toString());

            JSONObject distObject = (JSONObject)tnObj.get("districts");
            JSONObject chnObject = (JSONObject)distObject.get("Chennai");
            JSONObject chnData = (JSONObject)chnObject.get("delta");
            final TextView chncovidcount = (TextView) findViewById(R.id.chncovidcount);
            chncovidcount.setText(chncovidcount.getText() + chnData.get("confirmed").toString());
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        final StringBuffer inlinebuffer = new StringBuffer();
        try  {
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

            System.out.println("********After thread start call...");
            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inlinebuffer.toString());

            System.out.println(inlinebuffer.toString());

            //Get the required object from the above created object
            JSONObject tnObj = (JSONObject) data_obj.get("TN");
            JSONObject distObject = (JSONObject)tnObj.get("districts");
            JSONObject chnObject = (JSONObject)distObject.get(item);
            JSONObject chnData = (JSONObject)chnObject.get("delta");
            final TextView chncovidcount = (TextView) findViewById(R.id.chncovidcount);
            chncovidcount.setText(item +" covid count: " + chnData.get("confirmed").toString());
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}