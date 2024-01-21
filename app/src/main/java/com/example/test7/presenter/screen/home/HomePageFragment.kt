package com.example.test7.presenter.screen.home



import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController

import androidx.navigation.ui.setupWithNavController
import com.example.test7.R
import com.example.test7.databinding.FragmentHomePageBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.event.home.HomeEvent
import com.example.test7.presenter.extension.safeNavigateWithArgs
import com.example.test7.presenter.extension.showSnackBar
import com.example.test7.presenter.screen.home.profile.ProfileFragment
import com.example.test7.presenter.screen.home.toppers.ToppersFragment
import com.example.test7.presenter.screen.home.workers.WorkersListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate){

    private val viewModel:HomePageViewModel by viewModels()

    override fun bind() {
        binding.apply {
            logOutBtn.setOnClickListener{
                viewModel.onEvent(HomeEvent.LogOutAndClearToken)
            }
        }
        setBottomNavigationBarSelectListener()
    }

    private fun setBottomNavigationBarSelectListener() {
        binding.apply {
            setDefaultFragment()
            bottomNavBar.setOnItemSelectedListener{ item ->
                when (item.itemId) {
                    R.id.navigation_workers_list -> {
                        navigatedFragmentHeader.text = getText(R.string.workers_list)
                        replaceFragment(WorkersListFragment())
                        true
                    }
                    R.id.navigation_profile -> {
                        navigatedFragmentHeader.text = getText(R.string.profile)
                        replaceFragment(ProfileFragment())
                        true
                    }
                    R.id.navigation_toppers -> {
                        navigatedFragmentHeader.text = getText(R.string.toppers)
                        replaceFragment(ToppersFragment())
                        true
                    }
                    else -> false
                }
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.homePageNavigationHostContainer, fragment)
            .commit()
    }

    private fun setDefaultFragment(){
        binding.bottomNavBar.selectedItemId = R.id.navigation_workers_list // I want the middle icon to be selected
        replaceFragment(WorkersListFragment())
    }

    override fun bindObservers() {
        bindNavigationEventObserver()
    }

    private fun bindNavigationEventObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.navigationEvent.collect{navigationEvent->
                    when(navigationEvent){
                        HomePageViewModel.HomeNavigationEvent.NavigateToLogin -> {
                            navigateToLogin()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToLogin(){
        findNavController().safeNavigateWithArgs(R.id.action_homePageFragment_to_loginFragment)
    }

}