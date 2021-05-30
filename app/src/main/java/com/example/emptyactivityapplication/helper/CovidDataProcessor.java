package com.example.emptyactivityapplication.helper;

import com.example.emptyactivityapplication.model.CovidData;
import com.example.emptyactivityapplication.util.CovidConstants;
import com.example.emptyactivityapplication.util.MyJsonParser;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CovidDataProcessor {
    CovidData covidData;
    MyJsonParser parser;
    public List<String> districtList;
    public List<String> fetchDistrictList(StringBuffer jsonData) {
        //Using the JSON simple library parse the string into a json object
        parser = new MyJsonParser();
        districtList = new ArrayList<String>();
        JSONObject data_obj = parser.parseJson(jsonData.toString());
        System.out.println(jsonData.toString());

        //Get the required object from the above created object
        JSONObject tnObj = (JSONObject) data_obj.get(CovidConstants.CODE_TAMILNADU);
        JSONObject allDistObject = (JSONObject)tnObj.get(CovidConstants.CODE_DISTRICTS);
        Iterator<String> keys = allDistObject.keySet().iterator();
        String key="";
        while (keys.hasNext()) {
            key = keys.next();
            districtList.add(key);
        }
        Collections.sort(districtList);
        return districtList;
    }

    public CovidData processTNDistrictData(String district, StringBuffer jsonData) {
        if (district==null) district = CovidConstants.CODE_CHENNAI;
        covidData = new CovidData();
        //Using the JSON simple library parse the string into a json object
        parser = new MyJsonParser();
        JSONObject data_obj = parser.parseJson(jsonData.toString());
        System.out.println(jsonData.toString());

        //Get the required object from the above created object
        JSONObject tnObj = (JSONObject) data_obj.get(CovidConstants.CODE_TAMILNADU);
        JSONObject allDistObject = (JSONObject)tnObj.get(CovidConstants.CODE_DISTRICTS);
        System.out.println("allDistrict data " + allDistObject.toJSONString());
        JSONObject distObject = (JSONObject)allDistObject.get(district);
        System.out.println("District data  " + distObject.toJSONString());
        JSONObject distData = distObject.get(CovidConstants.CODE_TODAYSCOUNT)!=null?
                (JSONObject)distObject.get(CovidConstants.CODE_TODAYSCOUNT):
                (JSONObject)distObject.get(CovidConstants.CODE_TEMPCOUNT);
        System.out.println("delta value " + distData);
        if (distData != null) {
            covidData.setLocation(district);
            covidData.setInfectedCount(distData.get(CovidConstants.CODE_CONFIRMED) != null ?
                    distData.get(CovidConstants.CODE_CONFIRMED).toString() : "0");
            covidData.setDeceasedCount(distData.get(CovidConstants.CODE_DECEASED) != null ?
                    distData.get(CovidConstants.CODE_DECEASED).toString() : "0");
        } else {
            covidData.setLocation(district);
            covidData.setInfectedCount("0");
            covidData.setDeceasedCount("0");
        }
        return covidData;
    }
}
