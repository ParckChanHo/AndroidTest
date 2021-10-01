package com.example.volleysample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class UpdateActivity extends AppCompatActivity {
    int boardId;
    String boardNickname,boardDate,boardContent,boardTitle;

    EditText nickName,date,content,title;
    FloatingActionButton btnsave;
    board board;
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nickName = (EditText)findViewById(R.id.nickName_edit);
        date = (EditText)findViewById(R.id.date_edit);
        content = (EditText)findViewById(R.id.contents_edit);
        title = (EditText)findViewById(R.id.title_edit);
        btnsave = findViewById(R.id.fab);
        board = new board();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Intent intent=getIntent();
        boardId = intent.getIntExtra("boardId",-1);
        boardNickname = intent.getStringExtra("boardNickname");
        boardDate = intent.getStringExtra("boardDate");
        boardContent = intent.getStringExtra("boardContent");
        boardTitle = intent.getStringExtra("boardTitle");


        final boardDB helper = new boardDB(getApplication());
        /*board = helper.getOneBoard(boardId);*/

        Toast.makeText(getApplicationContext(),boardId+boardTitle,Toast.LENGTH_LONG).show();

        nickName.setText("닉네임: "+boardNickname); //board.getBoardNickname()
        date.setText("날짜: "+boardDate); // +board.getBoardDate()
        content.setText(boardContent); //board.getBoardContent()
        title.setText(boardTitle); // board.getBoardTitle()

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box=new AlertDialog.Builder(UpdateActivity.this);
                box.setMessage("수정하시겠습니까?");
                box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updateTitle = title.getText().toString();
                        String updateContent = content.getText().toString();
                        helper.updateBoard(boardId,updateContent,updateTitle);

                        //Toast.makeText(UpdateActivity.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                box.setNegativeButton("닫기",null);
                box.show();
            }
        });





    }

    public void getOneBoard(int boardId){
        String url = "http://172.30.1.37:8080/CapstoneDesign/Oneboard.jsp?boardId="+boardId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(c,response,Toast.LENGTH_LONG).show();

                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            int boardId = jsonObject.getInt("boardId");
                            String boardTitle = jsonObject.getString("boardTitle");
                            String boardNickname = jsonObject.getString("boardNickname");
                            String boardDate = jsonObject.getString("boardDate");
                            String boardContent = jsonObject.getString("boardContent");
                            int boardAvailable = jsonObject.getInt("boardAvailable");

                            board.setBoardId(boardId);
                            board.setBoardTitle(boardTitle);
                            board.setBoardNickname(boardNickname);
                            board.setBoardDate(boardDate);
                            board.setBoardContent(boardContent);
                            board.setBoardAvailable(boardAvailable);

                            //Toast.makeText(c,boardId+boardTitle+boardNickname+boardDate+boardContent+boardAvailable,Toast.LENGTH_LONG).show();
                            //board = new board(boardId,boardTitle,boardNickname,boardDate,boardContent,boardAvailable);
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
