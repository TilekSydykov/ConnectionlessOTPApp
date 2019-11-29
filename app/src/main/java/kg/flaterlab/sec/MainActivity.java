package kg.flaterlab.sec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar p;
    private TextView b;
    private MainActivity t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.label);
        p = findViewById(R.id.progressBar);

        t = this;
        start(new HmacSha1Signature());
    }

    private void start(HmacSha1Signature g){
        String h = g.gen("hello", "1234");
        long seconds = g.seconds;
        b.setText(h);
        int num = (int) Math.round(((seconds / 10.0) % 1) * 10);
        num = 20 - num * 2;
        p.setProgress(num);
        CountDownTimer cdt = new CountDownTimer(num * 1000 , 1000) {
            public void onTick(long millisUntilFinished) {
                Log.d("HELL", "onFinish: " + millisUntilFinished / 100);
                p.setProgress((int)millisUntilFinished / 200);
            }
            public void onFinish() {
                t.start(new HmacSha1Signature());
                Log.d("HELL", "onFinish: Finish");
            }
        };
        cdt.start();
    }
}
