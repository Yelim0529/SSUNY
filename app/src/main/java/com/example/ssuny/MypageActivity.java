package com.example.ssuny;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity {

    private SearchResultAdapter searchHistoryAdapter;

    DBHelper dbHelper = new DBHelper(this, 1); //버전 정체 찾기

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //파일 이름 제목으로 안뜨게

        Button to_alarm_btn = (Button) findViewById(R.id.to_alarm);
        to_alarm_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class); //화면 넘어가는 코드
                startActivity(intent);
            }
        });

        LinearLayout search_list = (LinearLayout) findViewById(R.id.search_list);

        ArrayList<String> searchResult = dbHelper.getSearchResult(); // 받아오기 성공 -> list에 넣기
        for (String a : searchResult) { //test용
            String drugName;
            try {
                JSONObject jsonObject = new JSONObject(a);
                drugName = jsonObject.getString("drugName");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            TextView t = new TextView(this);
            t.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MypageActivity.this, SearchResultActivity.class); //화면 넘어가는 코드
                    intent.putExtra("output", a.toString()); //데이터넣는거 json string db를 아웃풋 스트링에
                    startActivity(intent);
                }
            });
            t.setTextSize(30);
            t.setText(drugName);
            search_list.addView(t);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}