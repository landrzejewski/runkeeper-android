package pl.training.runkeeper.forecast.views

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.training.runkeeper.R
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
        /*var alert: AlertDialog? = null
            DialogBox().show(requireContext(), view)
            alert = AlertDialog.Builder(requireContext())
                .setMessage("Dialog example")
                .setPositiveButton(R.string.ok) { dialog, id ->  }
                .setNegativeButton(R.string.cancel) { dialog, id ->  }
                .create()
        binding.forecastDetailsImageBox.setOnClickListener {
            alert?.show()
        }*/
        initViews()
        bindViews()
    }

    private fun initViews() {

    }

    private fun bindViews() {
        binding.forecastDetailsStart.setOnClickListener {
            /*val animator = ValueAnimator.ofFloat(0f, -400f)
            animator.addUpdateListener {
                val value = it.animatedValue as Float
                binding.forecastDetailsImageBox.translationY = value
            }
            animator.interpolator = AccelerateInterpolator(1.5f) // LinearInterpolator()
            animator.duration = 1_000
            //animator.start()


            val backgroundAnimator = ObjectAnimator.ofObject(binding.forecastDetailsLayout, "backgroundColor", ArgbEvaluator(),
                    Color.BLUE, Color.GRAY)
            backgroundAnimator.repeatCount = 2
            backgroundAnimator.repeatMode = ValueAnimator.REVERSE
            backgroundAnimator.duration = 2_000
            //backgroundAnimator.addListener()

            //backgroundAnimator.start()

            val animators = AnimatorSet()
            animators.play(animator).with(backgroundAnimator)
            animators.start()*/

            /*binding.forecastDetailsImageBox.animate()
                .translationY(-400f)
                .rotationBy(360f)
                .setDuration(2_000)
                .start()*/


            val animators = AnimatorInflater.loadAnimator(requireContext(), R.animator.custom_animation) as AnimatorSet
            animators.setTarget(binding.forecastDetailsImageBox)
            animators.start()
        }
    }

}