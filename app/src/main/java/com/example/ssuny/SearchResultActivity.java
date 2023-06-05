package com.example.ssuny;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView descriptionTextView1;
    private TextView descriptionTextView2;
    private TextView descriptionTextView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //파일 이름 제목으로 안뜨게

        nameTextView = findViewById(R.id.medicine_name);
        descriptionTextView1 = findViewById(R.id.medicine_info1);
        descriptionTextView2 = findViewById(R.id.medicine_info2);
        descriptionTextView3 = findViewById(R.id.medicine_info3);


        Intent intent = getIntent();
        // 이름과 설명을 가져오는 로직
        String json = intent.getStringExtra("output"); //search 응답 받는 부분
        DBHelper dbHelper = new DBHelper(this, 1); //-------------------------
        dbHelper.saveJsonData(json); // json은 응답으로 받은 JSON 데이터입니다.

        String drugName;
        String entpName;
        String efcyQesitm;
        String useMethodQesitm;
        try {
            JSONObject jsonObject = new JSONObject(json);
            drugName = jsonObject.getString("drugName");
            entpName = jsonObject.getString("entpName");
            efcyQesitm = jsonObject.getString("efcyQesitm");
            useMethodQesitm = jsonObject.getString("useMethodQesitm");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String description = "";

        // TextView에 이름과 설명 설정
        nameTextView.setText("이름: " + drugName);
        descriptionTextView1.setText("제조사: " + entpName);
        descriptionTextView2.setText("효능: " + efcyQesitm);
        descriptionTextView3.setText("복용 방법: " + useMethodQesitm);

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
