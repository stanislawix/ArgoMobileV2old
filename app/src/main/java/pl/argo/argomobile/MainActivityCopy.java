package pl.argo.argomobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityCopy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("HomeArgoo");
        //R.id.hello;
        //R.id.button3;

        //Log.d("fail", "not working");
    }

    public void toggle(View view) {
        //view.getId();
        //view.setBackgroundColor();
        view.setEnabled(false);
        Log.d("dunno", "button disabled");
    }

    public void changeText(View v) {
        //v.setEnabled(false);
        //Button b = (Button) v;

        String s = ((EditText)findViewById(R.id.inputText)).getText().toString();
        TextView t = (TextView)findViewById(R.id.outputText);
        t.setText(s);
        //Log.d("msg", s);
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void getTextValue(View v) {
        EditText t = (EditText) v;
        Log.d("Grabbed text value: ", String.valueOf(t.getText()));
    }

    public void switchActivities(View v) {
        //launch a new activity

        Intent i = new Intent(this, SettingsActivity.class);
        //i.putExtra("nice_d", "bruh");
        i.putExtra("nice_d", ((TextView)findViewById(R.id.outputText)).getText());
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i = new Intent(this, SettingsActivity.class);
                i.putExtra("nice_d", ((TextView)findViewById(R.id.outputText)).getText());
                startActivity(i);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
}