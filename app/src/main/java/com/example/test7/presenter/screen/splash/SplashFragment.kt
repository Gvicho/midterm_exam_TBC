package com.example.test7.presenter.screen.splash

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.test7.R
import com.example.test7.databinding.FragmentSplashBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.extension.safeNavigateWithArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel:SplashViewModel by viewModels()

    override fun bind() {
        viewModel.navigateToNextScreen()
    }

    override fun bindObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.navigationEvent.collect{ splashNavigationEvent->
                    when(splashNavigationEvent){
                        is SplashViewModel.SplashNavigationEvent.NavigateToHome ->{
                            navigateToHomePage()
                        }
                        is SplashViewModel.SplashNavigationEvent.NavigateToLogin ->{
                            navigateToLoginPage()
                        }
                    }
                }
            }
        }

    }

    private fun navigateToHomePage(){
        findNavController().safeNavigateWithArgs(R.id.action_splashFragment_to_homePageFragment)
    }

    private fun navigateToLoginPage(){
        findNavController().safeNavigateWithArgs(R.id.action_splashFragment_to_loginFragment)
    }

}