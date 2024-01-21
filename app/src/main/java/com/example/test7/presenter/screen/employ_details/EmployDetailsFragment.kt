package com.example.test7.presenter.screen.employ_details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.test7.databinding.FragmentEmployDetailsBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.event.details.WorkerDetailsEvent
import com.example.test7.presenter.event.home.workers_list.WorkersListEvent
import com.example.test7.presenter.extension.showSnackBar
import com.example.test7.presenter.state.details.WorkersDetailsState
import com.example.test7.presenter.state.home.WorkersListState
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.KeyStore

@AndroidEntryPoint
class EmployDetailsFragment : BaseFragment<FragmentEmployDetailsBinding>(FragmentEmployDetailsBinding::inflate) {

    private val viewModel:EmployDetailsViewModel by viewModels()
    lateinit var myAdapter :DetailsRecyclerViewAdapter

    val args: EmployDetailsFragmentArgs by navArgs()
    private var userId :Int? = null

    override fun initData(savedInstanceState: Bundle?) {
        userId = args.id
    }

    override fun bind() {
        viewModel.onEvent(WorkerDetailsEvent.LoadUserDetails(userId?:1))
    }

    override fun bindObservers() {
        bindWorkersDetailsObserver()
    }

    private fun bindWorkersDetailsObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateFlow.collect{
                    handleResult(it)
                }
            }
        }
    }

    private fun handleResult(workersDetailsState: WorkersDetailsState){

        workersDetailsState.errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(WorkerDetailsEvent.ResetErrorMessageToNull)
        }

        showOrHideProgressBar(workersDetailsState.isLoading)

        workersDetailsState.isSuccess?.let {
            myAdapter = DetailsRecyclerViewAdapter(it)
            binding.detailsRecycler.adapter = myAdapter
        }
    }

    private fun showOrHideProgressBar(isLoading:Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

}