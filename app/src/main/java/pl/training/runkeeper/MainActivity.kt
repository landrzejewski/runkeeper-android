package pl.training.runkeeper

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val tag = MainActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initView()
        Log.d(tag, "### onCreate")
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.say_hello_button).setOnClickListener(::sayHello)
    }

    private fun sayHello(button: View) {
        findViewById<TextView>(R.id.message_text).text = getString(R.string.hello_android)
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "### onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "### onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "### onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "### onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "### onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "### onRestart")
    }

}