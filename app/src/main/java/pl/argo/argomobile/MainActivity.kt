package pl.argo.argomobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.controlwear.virtual.joystick.android.JoystickView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "HomeArgoo"
        //R.id.hello;
        //R.id.button3;

        //Log.d("fail", "not working");


        /**
         * https://github.com/controlwear/virtual-joystick-android
         */
        //var joystick : JoystickView = findViewById()
    }

    fun toggle(view: View) {
        //view.getId();
        //view.setBackgroundColor();
        view.isEnabled = false
        Log.d("dunno", "button disabled")
    }

    fun changeText(v: View?) {
        //v.setEnabled(false);
        //Button b = (Button) v;
        val s = (findViewById<View>(R.id.inputText) as EditText).text.toString()
        val t = findViewById<View>(R.id.outputText) as TextView
        t.text = s
        //Log.d("msg", s);
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    fun getTextValue(v: View) {
        val t = v as EditText
        Log.d("Grabbed text value: ", t.text.toString())
    }

    fun switchActivities(v: View?) {
        //launch a new activity
        val i = Intent(this, SettingsActivity::class.java)
        //i.putExtra("nice_d", "bruh");
        i.putExtra("nice_d", (findViewById<View>(R.id.outputText) as TextView).text)
        startActivity(i)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                val i = Intent(this, SettingsActivity::class.java)
                i.putExtra(
                    "nice_d",
                    (findViewById<View>(R.id.outputText) as TextView).text
                )
                startActivity(i)
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.item2 -> {
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.subitem1 -> {
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.subitem2 -> {
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.example_menu, menu)
        return true
    }
}