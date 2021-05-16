package pl.argo.argomobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //R.id.hello;
        //R.id.button3;
    }

    public void toggle(View view) {
        view.getId();
        view.setBackgroundColor();
    }
}