package uz.group1.maxwayapp.presentation.screens.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.databinding.ScreenTestBinding

class TestScreen : Fragment(R.layout.screen_test) {
    private val binding by viewBinding(ScreenTestBinding::bind)
    private val viewModel: TestViewModel by viewModels<TestViewModelImpl> { TestViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.errorMessageLiveData.observe(this, errorMessageObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn.setOnClickListener {
            viewModel.register("+998901234560")
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }

    private val successObserver = Observer<Unit> {
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
    }

    private val errorMessageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val loadingObserver = Observer<Boolean> {

    }

}