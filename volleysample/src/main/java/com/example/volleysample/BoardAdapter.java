package com.example.volleysample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHoler> {
    ArrayList<board> boardList;
    Context c; // MainActivity의 Context를 받아오기 위해서이다.
    int position;

    public BoardAdapter(ArrayList<board> boardList,Context c){
        this.boardList = boardList;
        this.c = c;
    }

    public BoardAdapter(){};


    @NonNull
    @Override
    public BoardViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item,parent,false);
        return new BoardViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHoler holder, int position) {
        // 데이터를 어떻게 레이아웃에 집어 넣어줄지를 결정한다.
        //String toCut = "Hello developer. please check this message for long length. max length to 50 characters.";
        // if(toCut.length() > 50){toCut = toCut.substring(0, 50) + '…';}
        // textView.setText(toCut);
        String dotTitle = boardList.get(position).getBoardTitle();
        if(dotTitle.length() > 25){
            dotTitle = dotTitle.substring(0,25) + "...."; // 0~24글자 까지만 제목에 표시해준다!!!
        }
        holder.title.setText("제목: "+dotTitle);

        String dotNickname = boardList.get(position).getBoardNickname();
        if(dotNickname.length() > 15){
            dotNickname = dotNickname.substring(0,15) + "...."; // 0~14글자 까지만 제목에 표시해준다!!!
        }
        holder.nickName.setText("닉네임: "+dotNickname);

        holder.date.setText("날짜: "+boardList.get(position).getBoardDate());
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    class BoardViewHoler extends RecyclerView.ViewHolder{
       public TextView title;
       public TextView nickName;
       public TextView date;
       public ImageView delete_img;

       public BoardViewHoler(@NonNull View itemView) {
           super(itemView);

           title = itemView.findViewById(R.id.title);
           nickName = itemView.findViewById(R.id.nickName);
           date = itemView.findViewById(R.id.date);
           delete_img = itemView.findViewById(R.id.btndel);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   // 전체 내용 보여주기 + 수정하기
                   position = getAdapterPosition(); // list의 몇 번째 아이템인지를 알게 해준다.

                   /*int id = boardList.get(position).getBoardId(); // DB에 있는 게시판 번호이다.
                   String boardTitle = boardList.get(position).getBoardTitle();
                   String boardNickname = boardList.get(position).getBoardNickname();
                   String boardDate = boardList.get(position).getBoardDate();
                   String boardContent = boardList.get(position).getBoardContent();
                   int boardAvailable = boardList.get(position).getBoardAvailable();
                   Toast.makeText(c,"id: "+id+"\n"+"boardTitle: "+boardTitle+"\n"+"boardNickname: "+boardNickname+"\n"+"boardDate: "+boardDate+'\n'+"boardContent: "+boardContent+"\n"+"boardAvailable"+boardAvailable
                         ,Toast.LENGTH_LONG).show(); // id : 1,2,3,4
                   */

                   Intent intent=new Intent(c,UpdateActivity.class);
                   intent.putExtra("boardId",boardList.get(position).getBoardId());
                   intent.putExtra("boardNickname",boardList.get(position).getBoardNickname());
                   intent.putExtra("boardDate",boardList.get(position).getBoardDate());
                   intent.putExtra("boardContent",boardList.get(position).getBoardContent());
                   intent.putExtra("boardTitle",boardList.get(position).getBoardTitle());
                   c.startActivity(intent);
               }
           });





       }
   }
}
