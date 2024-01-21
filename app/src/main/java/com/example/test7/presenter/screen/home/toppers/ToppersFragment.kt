package com.example.test7.presenter.screen.home.toppers

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.test7.databinding.FragmentToppersBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.event.home.toppers.ToppersEvent
import com.example.test7.presenter.extension.showSnackBar
import com.example.test7.presenter.state.home.ToppersState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToppersFragment : BaseFragment<FragmentToppersBinding>(FragmentToppersBinding::inflate),TopperItemClickCallBack{
    private lateinit var myViewAdapter: TopWorkersListAdapter
    private val viewModel:ToppersViewModel by viewModels()

    override fun bind() {
        myViewAdapter = TopWorkersListAdapter(this)
        binding.apply {
            workersRecycler.apply {
                adapter = myViewAdapter
            }
        }
    }

    override fun bindObservers() {
        bindTopWorkersListObserver()
    }

    private fun bindTopWorkersListObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateFlow.collect{
                    handleResult(it)
                }
            }
        }
    }

    private fun handleResult(toppersState: ToppersState){

        toppersState.errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(ToppersEvent.ResetErrorMessageToNull)
        }

        showOrHideProgressBar(toppersState.isLoading)

        toppersState.isSuccess?.let {
            myViewAdapter.submitList(it)
        }
    }

    private fun showOrHideProgressBar(isLoading:Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClicked(profession: String) {
        binding.root.showSnackBar("$profession analytics unavailable for now")
    }
}