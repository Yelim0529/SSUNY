package com.example.ssuny;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchResultActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //파일 이름 제목으로 안뜨게

        nameTextView = findViewById(R.id.medicine_name);
        descriptionTextView = findViewById(R.id.medicine_info);

        Intent intent = getIntent();
        // 이름과 설명을 가져오는 로직
        String json = intent.getStringExtra("output");
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
        nameTextView.setText("Name: " + drugName);
        descriptionTextView.setText("Description: " + useMethodQesitm);
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
