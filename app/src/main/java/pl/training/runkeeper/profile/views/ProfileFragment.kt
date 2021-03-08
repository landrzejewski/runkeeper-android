package pl.training.runkeeper.profile.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import pl.training.runkeeper.R
import pl.training.runkeeper.databinding.FragmentProfileBinding
import pl.training.runkeeper.profile.models.User
import pl.training.runkeeper.profile.viewmodels.ProfileViewModel
import java.util.*

class ProfileFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val disposableBag = CompositeDisposable()
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        binding.user = User("", "", "", false)
        val email = binding.profileEmail
        val dateOfBirth = binding.profileBirthDate
        email.textChanges()
            .map { viewModel.isEmailValid(it.toString()) }
            .subscribe {
                val color = if (it) R.color.text else R.color.invalid
                email.setTextColor(getColor(requireContext(), color))
            }
            .addTo(disposableBag)
        dateOfBirth.setOnClickListener { createDateDialog(requireContext()).show() }
        //TODO Uzupełnić formularz tak jak na iOS
        binding.profileSave.setOnClickListener {
            Log.d("###", binding.user?.subscriber.toString() ?: "")
            binding.user = User("Jan", "", "", true)
        }
    }

    private fun createDateDialog(context: Context): DatePickerDialog {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return DatePickerDialog(context, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        binding.profileBirthDate.setText("$dayOfMonth/$month/$year")
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableBag.clear()
    }

}