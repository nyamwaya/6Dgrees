package com.app.a6dgrees.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.a6dgrees.R
import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.common.showSnackBar
import com.app.a6dgrees.data.remote.dto.Name
import com.app.a6dgrees.data.remote.dto.UpdateUserRequest
import com.app.a6dgrees.databinding.FragmentSignupBinding
import com.app.a6dgrees.domain.models.FormData
import com.app.a6dgrees.domain.models.UserModel
import com.app.a6dgrees.presentation.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onClick() {
        binding.btnSignup.setOnClickListener {
            if (binding.firstNameTextInput.text.toString().isEmpty()) {
                binding.firstNameTextInput.error = "first name cannot be empty"
            } else if (binding.lastNameTextInput.text.toString().isEmpty()) {
                binding.lastNameTextInput.error = "last name cannot be empty"
            } else if (binding.emailTextInput.text.toString().isEmpty()) {
                binding.emailTextInput.error = "email cannot be empty"
            } else if (binding.phoneTextInput.text.toString().isEmpty()) {
                binding.phoneTextInput.error = "phone number cannot be empty"
            } else if (binding.passwordTextInput.text.toString().isEmpty()) {
                binding.passwordTextInput.error = "password cannot be empty"
            } else if (binding.confirmPasswordTextInput.text.toString().isEmpty()) {
                binding.confirmPasswordTextInput.error = "confirm password cannot be empty"
            } else {
                //All fields are not empty
                loadingState(true)
                viewModel.processFormData(
                    FormData(
                        firstName = binding.firstNameTextInput.text.toString().trim(),
                        lastName = binding.lastNameTextInput.text.toString().trim(),
                        email = binding.emailTextInput.text.toString().trim(),
                        phoneNumber = binding.phoneTextInput.text.toString(),
                        password = binding.passwordTextInput.text.toString().trim(),
                        confirmPassword = binding.confirmPasswordTextInput.text.toString().trim(),
                    )
                )
            }

        }
    }

    override fun observables() {
        viewModel.createUserState.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    loadingState(false)
                    Log.e(SignUpFragment::class.simpleName, "error creating user")
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    loadingState(false)
                    Log.e(SignUpFragment::class.simpleName, "created user successfully")
                    viewModel.mSharedPrefs.setUserModel(
                        UserModel(
                            email = result.data!!.user.emails[0],
                            isPhoneNumberVerified = false,
                            userId = result.data.user_id,
                        )
                    )

                    viewModel.updateUser(
                        // add phone number to this
                        result.data.user_id,
                        UpdateUserRequest(
                            name = Name(
                                first_name = binding.firstNameTextInput.text.toString().trim(),
                                last_name = binding.lastNameTextInput.text.toString().trim(),
                                middle_name = ""
                            )
                        )
                    )
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.updateUserState.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    loadingState(false)
                    Log.e(SignUpFragment::class.simpleName, "error updating user")
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    loadingState(false)
                    Log.e(SignUpFragment::class.simpleName, "updated user successfully")
                    navigateToTopScreen()
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.formValidationResult.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    showSnackBar(
                        result.message ?: "check your entries and try again",
                        binding.root,
                        Snackbar.LENGTH_LONG
                    )
                }
                is Resource.Loading -> {
                    // do nothing here
                }
                is Resource.Success -> {
                    // do nothing here
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToTopScreen() {
        findNavController().navigate(R.id.action_SignUpFragment_to_FragmentOtp)
        findNavController().popBackStack(R.id.SignUpFragment, true)
    }

    private fun loadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
            binding.btnSignup.isClickable = false

        } else {
            binding.progressCircular.visibility = View.GONE
            binding.btnSignup.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}