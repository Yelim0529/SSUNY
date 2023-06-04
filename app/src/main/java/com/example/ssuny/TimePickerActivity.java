package com.example.ssuny;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimePickerActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button okBtn;
    private int hour, minute;
    private String am_pm;
    EditText edtname, edtday, edtmemo;

    private Date datePicker;
    private int stYear, stMonth, stDay;

    DBHelper dbHelper;

    private Calendar calendar;

    private List<Calendar> alarmTimes = new ArrayList<>();

    private void setAlarm() {
        EditText re_day = (EditText) findViewById(R.id.re_day);
        int rday = Integer.parseInt(re_day.getText().toString());

        // Receiver 설정
        Intent intent = new Intent(this, AlarmReceiver.class);
        // 알람 설정
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        for (int i = 0; i < rday; i++) {
            // 알람 시간 설정
            this.calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getHour());
            this.calendar.set(Calendar.MINUTE, this.timePicker.getMinute());
            this.calendar.set(Calendar.SECOND, 0);
            this.calendar.add(Calendar.DATE, i);

            // 현재일보다 이전이면 등록 실패
            if (this.calendar.before(Calendar.getInstance())) {
                Toast.makeText(this, "알람시간이 현재시간보다 이전일 수 없습니다.", Toast.LENGTH_LONG).show();
                return;
            }

            // 알람 시간을 리스트에 추가
            alarmTimes.add((Calendar) calendar.clone());


            //intent.putExtra("content", "알람 등록 테스트");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmTimes.size() - 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //        alarmManager.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);

            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(this.calendar.getTimeInMillis(), null), pendingIntent);
            //alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(this.calendar.getTimeInMillis() + 10000, null), pendingIntent1);

            // Toast 보여주기 (알람 시간 표시)
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Toast.makeText(this, "Alarm : " + rday + "일 주기로 Time : " + format.format(calendar.getTime()), Toast.LENGTH_LONG).show();
        }

    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set_cycle);

        this.calendar = Calendar.getInstance();

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

                setAlarm();

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
