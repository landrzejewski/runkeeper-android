package pl.training.runkeeper.forecast.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.training.runkeeper.R
import pl.training.runkeeper.commons.views.DialogBox
import pl.training.runkeeper.databinding.FragmentForecastDetailsBinding
import pl.training.runkeeper.forecast.viewmodels.ForecastViewModel

class ForecastDetailsFragment : Fragment() {

    private lateinit var binding: FragmentForecastDetailsBinding
    private val viewModel: ForecastViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentForecastDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO Stworzyć widok szczegółowy pogody
        var alert: AlertDialog? = null
            DialogBox().show(requireContext(), view)
            alert = AlertDialog.Builder(requireContext())
                .setMessage("Dialog example")
                .setPositiveButton(R.string.ok) { dialog, id ->  }
                .setNegativeButton(R.string.cancel) { dialog, id ->  }
                .create()
        binding.forecastDetailsImageBox.setOnClickListener {
            alert?.show()
        }
    }

}