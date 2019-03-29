package aveek.com.management.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory<VM : ViewModel> @Inject constructor(
        private val viewModel : Lazy<VM>
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) = viewModel.value as T
}