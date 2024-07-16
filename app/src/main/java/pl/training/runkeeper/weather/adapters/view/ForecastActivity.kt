package pl.training.runkeeper.weather.adapters.view

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import pl.training.runkeeper.R
import pl.training.runkeeper.commons.ViewState
import pl.training.runkeeper.commons.ViewState.Failed
import pl.training.runkeeper.commons.ViewState.Initial
import pl.training.runkeeper.commons.ViewState.Loaded
import pl.training.runkeeper.commons.ViewState.Loading
import pl.training.runkeeper.commons.hideKeyboard
import pl.training.runkeeper.commons.setDrawable
import pl.training.runkeeper.databinding.ActivityForecastBinding

class ForecastActivity : AppCompatActivity() {

    private val viewModel: ForecastViewModel by viewModels()
    private val forecastRecyclerViewAdapter = ForecastRecyclerViewAdapter()
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.viewState.observe(this, ::updateView)
        val orientation =
            if (resources.configuration.orientation == SCREEN_ORIENTATION_PORTRAIT) HORIZONTAL else VERTICAL
        binding.nextDaysForecastRecycler.layoutManager =
            LinearLayoutManager(this, orientation, false)
        binding.nextDaysForecastRecycler.adapter = forecastRecyclerViewAdapter
        binding.checkButton.setOnClickListener(::onForecastCheck)
    }

    private fun updateView(viewState: ViewState) = when (viewState) {
        is Initial -> rest()
        is Loading -> {
            rest()
            binding.descriptionText.text = getString(R.string.loading)
        }
        is Loaded<*> -> showForecast(viewState.get())
        is Failed -> {
            rest()
            Toast.makeText(this, viewState.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showForecast(forecast: List<DayForecastViewModel>) {
        val currentForecast = forecast.first()
        binding.iconImage.setDrawable(currentForecast.iconName)
        binding.descriptionText.text = currentForecast.description
        binding.temperatureText.text = currentForecast.temperature
        binding.pressureText.text = currentForecast.pressure
        forecastRecyclerViewAdapter.update(forecast.drop(1))
    }

    private fun rest() {
        binding.iconImage.setImageDrawable(null)
        binding.descriptionText.text = getString(R.string.empty)
        binding.temperatureText.text = getString(R.string.empty)
        binding.pressureText.text = getString(R.string.empty)
        forecastRecyclerViewAdapter.update(emptyList())
    }

    private fun onForecastCheck(view: View) {
        val city = binding.cityNameEdit.text.toString()
        if (city.isNotEmpty()) {
            view.hideKeyboard()
            viewModel.refreshForecast(city)
        }
    }

}