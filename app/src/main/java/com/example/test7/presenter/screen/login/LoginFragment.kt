package com.example.test7.presenter.screen.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.test7.BuildConfig
import com.example.test7.R
import com.example.test7.databinding.FragmentLoginBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.event.login.LoginEvent
import com.example.test7.presenter.extension.safeNavigateWithArgs
import com.example.test7.presenter.extension.showSnackBar
import com.example.test7.presenter.state.login.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        private const val TEST_EMAIL = "admin@mail.com"
        private const val TEST_PASSWORD = "admin26"
    }

    override fun bind() {
        binding.apply {

            loginBtn.setOnClickListener{
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val rememberedChecked = rememberMeChkBox.isChecked
                viewModel.onEvent(LoginEvent.LoginUserEvent(email,password,rememberedChecked))
            }

            registerBtn.setOnClickListener{
                viewModel.onEvent(LoginEvent.MoveUserToRegistrationEvent)
            }
        }

        ifOnDebugModeFillFields()

        setRegistrationFragmentResultListener()
    }

    private fun ifOnDebugModeFillFields(){
        if(BuildConfig.DEBUG){
            binding.apply {
                emailEditText.setText(TEST_EMAIL)
                passwordEditText.setText(TEST_PASSWORD)
            }
        }
    }

    private fun handleResponse(loginState: LoginState){
        loginState.errorMessage?.let {
            errorWhileRegistration(it)
        }

        showOrHideProgressBar(loginState.isLoading)

        loginState.accessToken?.let {
            successRegistration()
        }
    }

    private fun successRegistration(){
        binding.root.showSnackBar("Successful Login")
    }

    private fun showOrHideProgressBar(isLoading:Boolean){
        binding.apply {
            progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun errorWhileRegistration(errorMessage:String){
        binding.root.showSnackBar(errorMessage)
        viewModel.onEvent(LoginEvent.ResetErrorStatus)
    }

    private fun setRegistrationFragmentResultListener(){
        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            val email = bundle.getString("Email")
            val password = bundle.getString("Password")

            binding.emailEditText.setText(email)
            binding.passwordEditText.setText(password)
        }
    }


    override fun bindObservers() {
        bindNavigationEventsObserver()
        bindResponseStateObserver()
    }

    private fun bindNavigationEventsObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loginPageNavigationEvent.collect{navigationEvent ->
                    when(navigationEvent){
                        LoginViewModel.LoginNavigationEvent.NavigateToHomePageEvent -> navigateToHomePage()
                        LoginViewModel.LoginNavigationEvent.NavigateToRegistrationEvent -> navigateToRegistrationPage()
                    }
                }
            }
        }
    }

    private fun bindResponseStateObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loginUIState.collect{loginState ->
                    handleResponse(loginState)
                }
            }
        }
    }

    private fun navigateToHomePage(){
        findNavController().safeNavigateWithArgs(R.id.action_loginFragment_to_homePageFragment)
    }

    private fun navigateToRegistrationPage(){
        findNavController().safeNavigateWithArgs(R.id.action_loginFragment_to_registrationFragment)
    }

}