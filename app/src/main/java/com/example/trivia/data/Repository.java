package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trivia.contoller.AppController;
import com.example.trivia.model.Question;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    ArrayList<Question> questionArrayList = new ArrayList<>();

    String url = "https://opentdb.com/api.php?amount=10&type=boolean";


    public List<Question> getQuestion( final AnswerListAsyncResponse callBack){

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

           Log.d("Json", "getQuestion: "+response);

           try {
               JSONArray jsonArray = response.getJSONArray("results");
               for (int i = 0; i < jsonArray.length(); i++) {

                   Question question = new Question(jsonArray.getJSONObject(i).get("question").toString(),
                           jsonArray.getJSONObject(i).getBoolean("correct_answer"));

                   questionArrayList.add(question);
                   //Log.d("TAG", "getQuestion: " + questionArrayList);


//               Log.d("TAG", "getQuestion: "+jsonArray);
//
//               Log.d("TAG", "getQuestion: "+jsonArray.getJSONObject(i).get("category"));
//               Log.d("TAG", "getQuestion: "+jsonArray.getJSONObject(i).get("question"));
//               Log.d("TAG", "getQuestion: "+jsonArray.getJSONObject(i).get("type"));
//               Log.d("TAG", "getQuestion: "+jsonArray.getJSONObject(i).get("difficulty"));
//               Log.d("TAG", "getQuestion: "+jsonArray.getJSONObject(i).getBoolean("correct_answer"));
           }
           } catch (JSONException e) {
               throw new RuntimeException(e);
           }
           if (null!= callBack) callBack.processFinished(questionArrayList);


       }, error -> {

       });


        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return questionArrayList;
    }
}
