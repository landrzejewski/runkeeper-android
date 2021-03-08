package pl.training.runkeeper.tracking.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.training.runkeeper.RunKeeperApplication.Companion.applicationGraph
import pl.training.runkeeper.databinding.FragmentTrackingBinding

class TrackingFragment : Fragment() {

    private lateinit var binding: FragmentTrackingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        applicationGraph.inject(this)
        binding = FragmentTrackingBinding.inflate(inflater)
        return binding.root
    }

}