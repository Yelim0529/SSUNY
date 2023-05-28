package com.example.ssuny;

import android.app.appsearch.SearchResult;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MypageActivity extends AppCompatActivity {

    private ListView listViewSearchHistory;
    private SearchResultAdapter searchHistoryAdapter;

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
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });
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