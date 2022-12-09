package com.app.a6dgrees.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.a6dgrees.R
import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.common.showSnackBar
import com.app.a6dgrees.databinding.FragmentLoginBinding
import com.app.a6dgrees.domain.models.LoginFormData
import com.app.a6dgrees.presentation.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick() {
        binding.btnLogin.setOnClickListener {
            val email = binding.emailEntryText.text.toString().trim()
            val password = binding.passwordEntry.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailEntryText.error = "email cannot be empty"
            } else if (password.isEmpty()) {
                binding.passwordEntry.error = "password cannot be empty"
            } else {
                loadingState(true)
                viewModel.processLoginFormData(
                    LoginFormData(
                        email = email,
                        password = password
                    )
                )
            }
        }
    }

    override fun observables() {
        viewModel.loginUserState.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    loadingState(false)
                    showSnackBar(result.message.toString(), binding.root, Snackbar.LENGTH_LONG)
                    Log.e(LoginFragment::class.java.simpleName, "error login user in")
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    loadingState(false)
                    viewModel.mSharedPrefs.setUserId(result.data!!.user_id)
                    val isPhoneVerified = viewModel.mSharedPrefs.getIsUserPhoneVerified()
                    if (isPhoneVerified) {
                        navigateHome()
                    } else {
                        navigateToOptVerification()
                    }

                }
            }
        }.launchIn(lifecycleScope)

        viewModel.loginFormValidationResult.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    showSnackBar(result.message.toString(), binding.root, Snackbar.LENGTH_LONG)
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToOptVerification() {
        findNavController().navigate(R.id.action_FragmentLogin_to_FragmentOtp)
    }

    private fun navigateHome() {
        // Get a reference to the NavController
        val navController = findNavController()
        navController.navigate(R.id.action_FragmentLogin_to_FragmentOtp)
    }

    private fun loadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.mProgressbar.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.GONE
        } else {
            binding.mProgressbar.visibility = View.GONE
            binding.btnLogin.visibility = View.VISIBLE
        }
    }
}