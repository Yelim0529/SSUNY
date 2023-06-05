package com.example.ssuny;

import android.app.appsearch.SearchResult;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        listViewSearchHistory = (ListView) findViewById(R.id.search_list_view);
    //db에서 가져온 ArrayList의 한항목당 한줄씩 ListView에 추가하기
       /* ListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
         {

            @Override
            public void onItemClick(AdapterView<> parent, View vv, int position, long id) {
                // 클릭한 아이템의 위치(position)을 기반으로 JSON 데이터를 가져옵니다.
                String jsonData = dbHelper.getJsonData(position);

                // JSON 데이터를 사용하여 필요한 작업을 수행합니다.
                // 예시: JSON 파싱, 화면 업데이트 등
                Intent intent = new Intent(MypageActivity.this, SearchResultActivity.class); //화면 넘어가는 코드
                intent.putExtra("output", jsonData); //데이터넣는거 json string db를 아웃풋 스트링에
                startActivity(intent);

            }
    }*/

        ArrayList<String> searchResult = dbHelper.getSearchResult(); // 받아오기 성공 -> list에 넣기
        for( String a : searchResult) { //test용
            System.out.println(a); }

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