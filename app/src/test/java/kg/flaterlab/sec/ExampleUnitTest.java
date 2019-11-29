package kg.flaterlab.sec;

import android.os.CountDownTimer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    int num = 100;
    @Test
    public void addition_isCorrect() {

        CountDownTimer cdt = new CountDownTimer(num, 1) {
            public void onTick(long millisUntilFinished) {
                num--;
                System.out.println(num);
            }
            public void onFinish() {
                System.out.println("Finish");
            }
        };
        cdt.start();

        assertEquals(4, 2 + 2);
    }
}