package pl.argo.argomobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ConfigActivity extends Activity {
    int frequency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        SeekBar messageSendingFrequencySeekBar = findViewById(R.id.messageSendingFrequencySeekBar);
        //TextView messageSendingFrequencyInfo = findViewById(R.id.messageSendingFrequencyInfo);
        TextView messageSendingFrequencyValue = findViewById(R.id.messageSendingFrequencyValue);

        messageSendingFrequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                frequency = progress;
                messageSendingFrequencyValue.setText(frequency);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //nic
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //nic
            }
        });
    }

}
