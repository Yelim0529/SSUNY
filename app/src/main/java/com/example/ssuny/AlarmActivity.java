package com.example.ssuny;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class AlarmActivity extends AppCompatActivity {

    public static final int REQUEST_CODE1 = 1000;
    public static final int REQUEST_CODE2 = 1001;
    private AdapterActivity arrayAdapter;
    private Button tpBtn, removeBtn;
    private ListView listView;
    private int hour;
    private int minute, year, month, day;
    private String name, am_pm;
    private int adapterPosition;

    private int position;
    DBHelper dbHelper;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list);

        dbHelper = new DBHelper(AlarmActivity.this, 1);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //파일 이름 제목으로 안뜨게

        arrayAdapter = new AdapterActivity(AlarmActivity.this);

        listView = (ListView) findViewById(R.id.alarm_list);
        listView.setAdapter(arrayAdapter);
        //List에 있는 항목들 눌렀을 때 시간변경 가능
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterPosition = position;
                arrayAdapter.removeItem(position);
                Intent intent = new Intent(AlarmActivity.this, TimePickerActivity.class);
                startActivityForResult(intent,REQUEST_CODE2);
            }
        });

        //TimePicker의 시간 셋팅값을 받기 위한 startActivityForResult()
        tpBtn = (Button) findViewById(R.id.add_btn);
        tpBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent tpIntent = new Intent(AlarmActivity.this, TimePickerActivity.class);
                startActivityForResult(tpIntent, REQUEST_CODE1);
            }
        });

        removeBtn = (Button) findViewById(R.id.remove_btn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                arrayAdapter.removeItem();
                arrayAdapter.notifyDataSetChanged();

            }
        });
    }

    //TimePicker 셋팅값 받아온 결과를 arrayAdapter에 추가
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //시간 리스트 추가
//            if(requestCode == REQUEST_CODE1 && resultCode == RESULT_OK && data != null) {
//                name = data.getStringExtra("name");
//                hour = data.getIntExtra("hour", -1);
//                minute = data.getIntExtra("minute", -2);
//                am_pm = data.getStringExtra("am_pm");
//                year = data.getIntExtra("year",-1);
//                month = data.getIntExtra("month",-1);
//                day = data.getIntExtra("day",-1);
//                arrayAdapter.addItem(name, hour, minute, am_pm, year, month, day);
//                arrayAdapter.notifyDataSetChanged();
//            }
//            //시간 리스트 터치 시 변경된 시간값 저장
//            if(requestCode == REQUEST_CODE2 && resultCode == RESULT_OK && data != null) {
//                name = data.getStringExtra("name");
//                hour = data.getIntExtra("hour", 1);
//                minute = data.getIntExtra("minute", 2);
//                am_pm = data.getStringExtra("am_pm");
//                year = data.getIntExtra("year",-1);
//                month = data.getIntExtra("month",-1);
//                day = data.getIntExtra("day",-1);
//                arrayAdapter.addItem(name, hour, minute, am_pm, year, month, day);
//                arrayAdapter.notifyDataSetChanged();
//            }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Time> al = dbHelper.getResult();
        arrayAdapter.addItem(al);
        arrayAdapter.notifyDataSetChanged();
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