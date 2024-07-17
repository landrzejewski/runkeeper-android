package pl.training.runkeeper.weather.adapters.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.training.runkeeper.R
import pl.training.runkeeper.commons.setDrawable
import pl.training.runkeeper.databinding.ItemDayForecastBinding

class ForecastRecyclerViewAdapter(
    private var forecast: List<DayForecastViewModel> = emptyList(),
    var tapListener: (DayForecastViewModel) -> Unit = {}
) : RecyclerView.Adapter<ForecastRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View, private var tapListener: (DayForecastViewModel) -> Unit) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDayForecastBinding.bind(view)

        fun update(dayForecastViewModel: DayForecastViewModel) {
            binding.root.setOnClickListener { tapListener(dayForecastViewModel) }
            binding.itemIconImage.setDrawable(dayForecastViewModel.iconName)
            binding.itemTemperatureText.text = dayForecastViewModel.temperature
            binding.itemDateText.text = dayForecastViewModel.data
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_day_forecast, parent, false)
        return ViewHolder(view, tapListener)
    }

    override fun getItemCount() = forecast.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.update(forecast[position])

    fun update(forecast: List<DayForecastViewModel>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }
     
}