package com.example.emptyactivityapplication.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyJsonParser {
    JSONObject data_obj = null;
    public JSONObject parseJson(String inputString) {
        JSONParser parser = new JSONParser();
        try {
            data_obj = (JSONObject) parser.parse(inputString);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return data_obj;
    }
}
