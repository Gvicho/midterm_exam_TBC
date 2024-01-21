package com.example.test7.presenter.screen.home.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.test7.databinding.FragmentProfileBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.event.home.profile.ProfileEvent
import com.example.test7.presenter.event.home.toppers.ToppersEvent
import com.example.test7.presenter.extension.showSnackBar
import com.example.test7.presenter.model.WorkerPresenter
import com.example.test7.presenter.state.home.ProfileState
import com.example.test7.presenter.state.home.ToppersState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel:ProfileViewModel by viewModels()

    override fun bindObservers() {
        bindProfileInfoObserver()
    }

    private fun bindProfileInfoObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateFlow.collect{
                    handleResult(it)
                }
            }
        }
    }

    private fun handleResult(profileState: ProfileState){

        profileState.errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(ProfileEvent.ResetErrorMessageToNull)
        }

        showOrHideProgressBar(profileState.isLoading)

        profileState.isSuccess?.let {
            bindProfileInfo(it)
        }
    }

    private fun bindProfileInfo(worker:WorkerPresenter){
        binding.apply {
            Glide.with(requireContext())
                .load(worker.avatar)
                .into(avatar)

            tvName.text = worker.name
            tvProfession.text = worker.profession
            tvRate.text = worker.rate.toString()
            tvAverageHours.text = worker.averageHoursWorked.toString()
        }
    }

    private fun showOrHideProgressBar(isLoading:Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}