package com.example.ssuny;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity2 extends AppCompatActivity {

    private Ringtone ringtone;
    //private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);

        // 알람음 재생
        Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), defaultRingtoneUri);
        if (ringtone != null) {
            ringtone.play();
        }
        /*this.mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        this.mediaPlayer.start();*/

        findViewById(R.id.btnClose).setOnClickListener(mClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // MediaPlayer release
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        /*if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }*/
    }

    /* 알람 종료 */
    private void close() {
        if (this.ringtone.isPlaying()) {
            this.ringtone.stop();
            this.ringtone = null;
        }
        /*if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }*/

        finish();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == R.id.btnClose) { // 알람 종료 버튼의 리소스 ID로 변경
                // 알람 종료
                close();
            }
        }
    };
}