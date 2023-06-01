package com.example.ssuny;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Date;

public class TimePickerActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button okBtn;
    private int hour, minute;
    private String am_pm;
    EditText edtname, edtday, edtmemo;

    private Date datePicker;
    private int stYear, stMonth, stDay;

    DBHelper dbHelper;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set_cycle);

        dbHelper = new DBHelper(TimePickerActivity.this, 1);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //파일 이름 제목으로 안뜨게


        timePicker = (TimePicker) findViewById(R.id.timePicker);

        // DatePicker 초기화
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        // OnDateChangedListener 등록
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                stYear = year;
                stMonth = monthOfYear+1;
                stDay = dayOfMonth;
            }
        });


        edtname = (EditText) findViewById(R.id.alarm_name);
        edtday = (EditText) findViewById(R.id.re_day);
        edtmemo = (EditText) findViewById(R.id.alarm_memo);

        okBtn = (Button) findViewById(R.id.time_save_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //안드로이드 버전별로 시간값 세팅을 다르게 해주어야 함. 여기선 Android API 30(R)
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                am_pm = AM_PM(hour);
                hour = timeSet(hour);

                String edName = edtname.getText().toString();
                int edDay = Integer.parseInt(edtday.getText().toString());
                String edMemo = edtmemo.getText().toString();

                Intent sendIntent = new Intent(TimePickerActivity.this, MainActivity.class);

                dbHelper.insert(edName, edDay, stYear, stMonth, stDay, hour, minute, am_pm, edMemo);
                sendIntent.putExtra("name", edName);
                sendIntent.putExtra("reday", edDay);
                sendIntent.putExtra("year", stYear);
                sendIntent.putExtra("month", stMonth);
                sendIntent.putExtra("day", stDay);
                sendIntent.putExtra("hour", hour);
                sendIntent.putExtra("minute", minute);
                sendIntent.putExtra("am_pm", am_pm);
                sendIntent.putExtra("memo", edMemo);
                setResult(RESULT_OK, sendIntent);

                finish();
            }
        });
    }

    //24시 시간제 바꿔줌
    private int timeSet(int hour) {
        if(hour > 12) {
            hour-=12;
        }
        return hour;
    }

    //오전, 오후 선택
    private String AM_PM(int hour) {
        if(hour >= 12) {
            am_pm = "오후";
        } else {
            am_pm = "오전";
        }
        return am_pm;
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
