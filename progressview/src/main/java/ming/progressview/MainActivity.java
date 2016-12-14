package ming.progressview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressView progressView = (ProgressView) findViewById(R.id.progressView);
        progressView.setNote("50%");
        progressView.setIcon(R.drawable.ic_download);
        progressView.setMax(100);
        progressView.setProgress(50);

        //设置背景
        progressView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
