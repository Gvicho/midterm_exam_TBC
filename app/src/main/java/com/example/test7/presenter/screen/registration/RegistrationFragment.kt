package com.example.test7.presenter.screen.registration

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.test7.databinding.FragmentRegistrationBinding
import com.example.test7.presenter.base.BaseFragment
import com.example.test7.presenter.event.register.RegisterEvent
import com.example.test7.presenter.extension.showSnackBar
import com.example.test7.presenter.state.register.RegisterState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    val viewModel:RegistrationViewModel by viewModels()

    override fun bindObservers() {
        observeRegistrationState()
        observeNavigationEvent()
    }

    private fun observeRegistrationState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.registrationState.collect{
                    handleResponse(it)
                }
            }
        }
    }

    private fun observeNavigationEvent(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.navigationEventFlow.collect{
                    when(it){
                        is RegistrationViewModel.NavigationEvent.NavigateBackToLoginPage -> {
                            returnResultAndFinishRegistration(it.email,it.password)
                            closeRegistrationFragment()
                        }
                    }
                }
            }
        }
    }

    private fun returnResultAndFinishRegistration(email: String,password: String){
        d("tag123","return Result FromRegistration")
        val result = Bundle().apply {
            putString("Email", email)
            putString("Password", password)
        }
        setFragmentResult("requestKey", result)
    }

    private fun closeRegistrationFragment() {
        findNavController().popBackStack()
    }

    override fun bindViewActionListeners() {
        binding.apply {
            registerBtn.setOnClickListener{
                registerUser()
            }
        }
    }

    private fun handleResponse(registerState: RegisterState){
        registerState.errorMessage?.let {
            errorWhileRegistration(it)
        }

        showOrHideProgressBar(registerState.isLoading)

        registerState.userAuthenticator?.let {
            successRegistration(it.email,it.password)
        }
    }

    private fun successRegistration(email:String,password: String){
        binding.root.showSnackBar("Successful Registration")
        viewModel.onEvent(RegisterEvent.MoveBackToLogin(email,password))
    }

    private fun showOrHideProgressBar(isLoading:Boolean){
        binding.apply {
            progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun errorWhileRegistration(errorMessage:String){
        binding.root.showSnackBar(errorMessage)
        viewModel.onEvent(RegisterEvent.ResetErrorMessage)
    }

    private fun registerUser(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val repeatedPassword = binding.repeatPasswordEditText.text.toString()

        val emptyField = tellEmptyFieldOrReturnEmptyString(email,password,repeatedPassword)
        if(emptyField.isNotEmpty()){
            binding.root.showSnackBar("please fill $emptyField")
            return
        }

        if(!checkPasswordsAreSame(password,repeatedPassword)){
            binding.root.showSnackBar("passwords must match")
            return
        }

        requestRegistrationToViewModel(email,password)

    }

    private fun requestRegistrationToViewModel(email: String,password: String){
        viewModel.onEvent(RegisterEvent.RegisterUser(email,password))
    }

    private fun checkPasswordsAreSame(password:String,repeatedPassword:String):Boolean{
        return password == repeatedPassword
    }

    private fun tellEmptyFieldOrReturnEmptyString(email:String,password: String,repeatedPassword: String):String{
        binding.apply {
            if(email.isEmpty())return "email field"
            if(password.isEmpty())return "password field"
            if(repeatedPassword.isEmpty())return "repeated password field"
        }
        return "" // empty string, so all fields are filled
    }
}