package com.example.emptyactivityapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emptyactivityapplication.helper.CovidDataProcessor;
import com.example.emptyactivityapplication.model.CovidData;
import com.example.emptyactivityapplication.util.CovidConstants;
import com.example.emptyactivityapplication.util.MyJsonParser;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    CovidService covidService;
    MyJsonParser parser;
    CovidDataProcessor covidDataProcessor;
    CovidData covidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        covidService = new CovidService();
        covidDataProcessor = new CovidDataProcessor();
        StringBuffer inlinebuffer = covidService.fetchCovidData();

        List<String> districtList = covidDataProcessor.fetchDistrictList(inlinebuffer);
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner_title);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //Using the JSON simple library parse the string into a json object
        parser = new MyJsonParser();
        JSONObject data_obj = parser.parseJson(inlinebuffer.toString());
        System.out.println(inlinebuffer.toString());

        //Get the required object from the above created object
        JSONObject tnObj = (JSONObject) data_obj.get(CovidConstants.CODE_TAMILNADU);
        JSONObject tnData = tnObj.get(CovidConstants.CODE_TODAYSCOUNT) !=null?
                (JSONObject) tnObj.get(CovidConstants.CODE_TODAYSCOUNT):
                (JSONObject) tnObj.get(CovidConstants.CODE_TEMPCOUNT);

        //Get the required data using its key
        System.out.println("******I am parsing data returned by URL now");
        System.out.println("Today confirmed count: " + tnData.get(
                CovidConstants.CODE_CONFIRMED));
        System.out.println("Today dead count: " + tnData.get(CovidConstants.CODE_DECEASED));
        final TextView covidcount = (TextView) findViewById(R.id.tncovidcount);
        covidcount.setText(covidcount.getText() + tnData.get(
                CovidConstants.CODE_CONFIRMED).toString());

        JSONObject distObject = (JSONObject)tnObj.get(CovidConstants.CODE_DISTRICTS);
        JSONObject chnObject = (JSONObject)distObject.get(CovidConstants.CODE_CHENNAI);
        JSONObject chnData =chnObject.get(CovidConstants.CODE_TODAYSCOUNT)!=null
                ? (JSONObject)chnObject.get(CovidConstants.CODE_TODAYSCOUNT):
                (JSONObject)chnObject.get(CovidConstants.CODE_TEMPCOUNT);
        final TextView distcovidcount = (TextView) findViewById(R.id.distcovidcount);
        distcovidcount.setText(distcovidcount.getText() + chnData.get(
                CovidConstants.CODE_CONFIRMED).toString());

        final TextView distexpiredcount = (TextView) findViewById(R.id.distexpiredcount);
        distexpiredcount.setText(distexpiredcount.getText() + chnData.get(
                CovidConstants.CODE_CONFIRMED).toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner district
        String district = parent.getItemAtPosition(position).toString();
        covidDataProcessor = new CovidDataProcessor();

        StringBuffer inlinebuffer = new StringBuffer();
        covidService = new CovidService();
        inlinebuffer = covidService.fetchCovidData();

        covidData = covidDataProcessor.processTNDistrictData(district,inlinebuffer);

        final TextView distCovidCount = (TextView) findViewById(R.id.distcovidcount);
        distCovidCount.setText(district +" covid count: " + covidData.getInfectedCount());

        final TextView distexpiredcount = (TextView) findViewById(R.id.distexpiredcount);
        distexpiredcount.setText(district + " expired count: " + covidData.getDeceasedCount());
        // Showing selected spinner district
        Toast.makeText(parent.getContext(), "Selected: " + district, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}