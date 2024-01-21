package com.example.test7.presenter.screen.home.workers

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.test7.R
import com.example.test7.databinding.FragmentWorkersListBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.event.home.workers_list.WorkersListEvent
import com.example.test7.presenter.extension.safeNavigateWithArgs
import com.example.test7.presenter.extension.safeNavigateWithArguments
import com.example.test7.presenter.extension.showSnackBar
import com.example.test7.presenter.model.WorkerPresenter
import com.example.test7.presenter.model.workers_list.ItemType
import com.example.test7.presenter.model.workers_list.WorkersListObjectPresenter
import com.example.test7.presenter.state.home.WorkersListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WorkersListFragment : BaseFragment<FragmentWorkersListBinding>(FragmentWorkersListBinding::inflate),ItemClickCallBack {
    private val viewModel:WorkersListViewModel by viewModels()
    lateinit var myAdapter: WorkersListRecyclerViewAdapter

    override fun bind() {
        myAdapter = WorkersListRecyclerViewAdapter(this)

        binding.apply {
            workersRecycler.apply {
                this.adapter = myAdapter
            }
        }
    }

    override fun bindObservers() {
        bindWorkersListStateObserver()
    }

    private fun bindWorkersListStateObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateFlow.collect{
                    handleResult(it)
                }
            }
        }
    }

    private fun handleResult(workersListState: WorkersListState){

        workersListState.errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(WorkersListEvent.ResetErrorMessageToNull)
        }

        showOrHideProgressBar(workersListState.isLoading)

        workersListState.isSuccess?.let {
            myAdapter.submitList(it)
        }
    }

    private fun showOrHideProgressBar(isLoading:Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClick(id: Int) {
        val bundle = bundleOf("id" to id)
        findNavController().safeNavigateWithArguments(R.id.action_homePageFragment_to_employDetailsFragment,bundle)
    }

    override fun onButtonClick(profession: String) {
        binding.root.showSnackBar("no endpoint for $profession")
    }

}