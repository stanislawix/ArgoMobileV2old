/*
package pl.argo.argomobile

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.DataOutputStream
import java.io.PrintWriter
import java.net.Socket

class RoverControlActivity(soc:Socket): AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rover_control)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.example_menu, menu)
        return true
    }

    fun sendMessage(v : View?) {
        */
/*var s: Socket? = null
        var writer: PrintWriter? = null


        if(s == null) {
            //change it to your IP
            s = Socket("192.168.0.152", 1234)
            writer = PrintWriter(s.getOutputStream());
        }
        writer?.write("Hello")
        writer?.flush()

        Toast.makeText(this, "Hello!", Toast.LENGTH_LONG).show()*//*


        try{
            val soc = Socket("192.168.0.5", 11311)
            val dout = DataOutputStream(soc.getOutputStream())
            dout.writeUTF("1")

            dout.flush()
            dout.close()
            soc.close()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}

*/
