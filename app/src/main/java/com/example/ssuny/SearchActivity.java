package com.example.ssuny;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private List<String> items = Arrays.asList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //파일 이름 제목으로 안뜨게

        editTextSearch = findViewById(R.id.search_view);
       // SearchView searchView = findViewById(R.id.search_view);


        Button search_btn = findViewById(R.id.search_button);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
              //  startActivity(intent);
                System.out.println(editTextSearch.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(editTextSearch.getText().toString()); //쿼리로 변경
                    }
                }).start();
            }
        });



        Button imgButton = findViewById(R.id.search_img_button);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, CameraActivity.class);
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

    //------------
    StringBuilder output = new StringBuilder(); //새로 추가
    private String search(String query) { //포함된 단어만 보이게 필터링
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            if (item.toLowerCase().contains(query.toLowerCase())) {
                sb.append(item);
                if (i != items.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private String getResult() { // textview 보이기
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            sb.append(item);
            if (i != items.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public void request(String query) {
        try {
            URL url = new URL("http://52.78.100.143:8080/api/searchDrug/" + query);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection != null) {
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoInput(true);

                int resCode = connection.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }

                        output.append(line);
                    }
                    reader.close();
                    connection.disconnect();
                } else {
                    System.out.println(resCode); //현재 요청 상태
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString()); //예외 발생
        }

        System.out.println(output.toString()); //응답
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra("output", output.toString());
        startActivity(intent);
    }
}