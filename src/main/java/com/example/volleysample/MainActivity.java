package com.example.volleysample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<board> boardList;
    BoardAdapter boardAdapter;

    static RequestQueue requestQueue;
    public static Context c; // context 변수 선언
    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        c = this; // onCreate에서 this 할당
        //textView = (TextView)findViewById(R.id.TextView);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        boardList = new ArrayList<>();

        callVolley("http://172.30.1.37:8080/CapstoneDesign/Allboard.jsp");
        boardAdapter = new BoardAdapter(boardList,c);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(boardAdapter);
    }

    public void callVolley(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Allboard"); // Allboard Array
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject Object = jsonArray.getJSONObject(i);

                                int boardId = Object.getInt("boardId");
                                String boardTitle = Object.getString("boardTitle");
                                String boardNickname = Object.getString("boardNickname");
                                String boardDate = Object.getString("boardDate");
                                String boardContent = Object.getString("boardContent");
                                int boardAvailable = Object.getInt("boardAvailable");
                                boardList.add(new board(boardId,boardTitle,boardNickname,boardDate,boardContent,boardAvailable));
                                //textView.append(i+"번째 "+"제목: "+boardTitle+" 닉네임: "+boardNickname+" 날짜: "+boardDate+" 내용: "+boardContent + "\n");
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                }
        ) {
        }; //end new StringRequest
        // Add the request to the RequestQueue.

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    } // end callVolley()
}
