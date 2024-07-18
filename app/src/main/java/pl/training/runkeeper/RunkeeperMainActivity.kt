package pl.training.runkeeper

import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import pl.training.runkeeper.commons.components.AirplaneModeReceiver
import pl.training.runkeeper.commons.components.ForecastProvider.DatabaseHelper.Companion.CONDITIONS_COLUMN
import pl.training.runkeeper.commons.components.ForecastProvider.DatabaseHelper.Companion.DATE_COLUMN
import pl.training.runkeeper.commons.components.SchedulerService
import pl.training.runkeeper.databinding.ActivityMainBinding
import java.util.Date


@AndroidEntryPoint
class RunkeeperMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val airplaneModeReceiver = AirplaneModeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.TrackingFragment, R.id.ForecastFragment, R.id.ProfileFragment
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationBar.setupWithNavController(navController)

        examples()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.SettingsFragment -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.SettingsFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun examples() {
        // Broadcast receiver
        val airplaneModeIntent = IntentFilter(ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, airplaneModeIntent, RECEIVER_EXPORTED)

        // Content provider
        val values = ContentValues()
        values.put(DATE_COLUMN, Date().time)
        values.put(CONDITIONS_COLUMN, "Sunny")

        /*
        val uri = contentResolver.insert(CONTENT_URI, values)
        Log.i("###", uri.toString())

        val queryUri = Uri.parse("content://pl.training.runkeeper.commons.components.ForecastProvider/forecast")
        contentResolver.query(queryUri, null, null, null)
            ?.let {
                val dateColumnIdx = it.getColumnIndex(DATE_COLUMN)
                val conditionsIdx = it.getColumnIndex(CONDITIONS_COLUMN)
                while (it.moveToNext()) {
                    val date = Date(it.getLong(dateColumnIdx))
                    val conditions = it.getString(conditionsIdx)
                    Log.i("###", "$date:$conditions")
                }
                it.close()
            }
         */

        // Service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, SchedulerService::class.java))
        } else {
            startService(Intent(this, SchedulerService::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
        stopService(Intent(this, SchedulerService::class.java))
    }

}