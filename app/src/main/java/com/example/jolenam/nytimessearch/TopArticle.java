package com.example.jolenam.nytimessearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jolenam on 6/23/16.
 */
public class TopArticle implements Serializable {

    String title;
    String thumbNail;
    String section;

    public String getTitle() {
        return title;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public String getSection() {
        return section;
    }


    public TopArticle(JSONObject jsonObject) {
        try {
            this.title = jsonObject.getString("title");
            this.section = jsonObject.getString("section");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                this.thumbNail = "";
            }
        } catch (JSONException e) {
        }
    }

    public static ArrayList<TopArticle> fromJSONArray(JSONArray array) {
        ArrayList<TopArticle> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new TopArticle(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}

