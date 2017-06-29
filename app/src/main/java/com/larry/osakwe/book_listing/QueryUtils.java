package com.larry.osakwe.book_listing;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Larry Osakwe on 6/25/2017.
 */

public class QueryUtils {

    private QueryUtils() {

    }

    public static ArrayList<Book> extractBooks(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        ArrayList<Book> books = new ArrayList<>();


        try {

            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            JSONArray itemArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject item = itemArray.getJSONObject(i);

                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                String authors = formatListOfAuthors(authorArray);
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String image = imageLinks.getString("thumbnail");


                books.add(new Book(title, authors.toString(), image));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return books;
    }

    public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {

        String authorsListInString = null;

        if (authorsList.length() == 0) {
            return null;
        }

        for (int i = 0; i < authorsList.length(); i++) {
            if (i == 0) {
                authorsListInString = authorsList.getString(0);
            } else {
                authorsListInString += ", " + authorsList.getString(i);
            }
        }

        return authorsListInString;
    }


}
