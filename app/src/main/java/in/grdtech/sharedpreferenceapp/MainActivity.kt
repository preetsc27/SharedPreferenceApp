package `in`.grdtech.sharedpreferenceapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init the UI
        val myName = findViewById<EditText>(R.id.myName)
        val insertBtn = findViewById<Button>(R.id.insertBtn)
        val showBtn = findViewById<Button>(R.id.showBtn)
        val outputData = findViewById<TextView>(R.id.outputData)

        // insert btn listener
        insertBtn.setOnClickListener {
            var name = myName.text.toString().trim()

            // empty check
            if (name.isEmpty()){
                Toast.makeText(applicationContext, "Please Enter A Name.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            name = encodeData(name)

            // shared preferences init
            val sharedPreferences = getSharedPreferences("SharedPref", 0)

            // getting the editor to edit data
            val editor = sharedPreferences.edit()

            // putting the data into the editor
            editor.putString("myName", name)

            // apply the cahnges
            editor.apply()

            // commit the changes
            editor.commit()


        }

        showBtn.setOnClickListener {
            // shared preferences init
            val sharedPreferences = getSharedPreferences("SharedPref", 0)

            val  nameEncoded = sharedPreferences.getString("myName", null)

            val name = decodeData(nameEncoded)

            // show the data
            outputData.text = "$nameEncoded \n $name"
        }

    }

    // function to encode and decode the data
    private fun encodeData(name: String): String{
        val encoded = Base64.encode(name.toByteArray(), Base64.DEFAULT)
        return String(encoded)
    }

    private fun decodeData(name: String): String{
        val encoded = Base64.decode(name.toByteArray(), Base64.DEFAULT)
        return String(encoded)
    }
}
