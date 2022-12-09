package com.app.a6dgrees.presentation.authentication

import android.os.Bundle
import android.os.CountDownTimer
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
import com.app.a6dgrees.data.remote.dto.AuthenticateOtpRequest
import com.app.a6dgrees.databinding.FragmentOtpBinding
import com.app.a6dgrees.presentation.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*


class OtpFragment : BaseFragment() {

    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AuthenticationViewModel>()

    lateinit var mPhoneID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onClick() {
        binding.btnAuthenticateOtp.setOnClickListener {
            viewModel.mSharedPrefs.setIsUserPhoneVerified(true)

            val isUserVerified = viewModel.mSharedPrefs.getIsUserPhoneVerified()
            if (!isUserVerified) {
                val otpCode = binding.pinEntryEdit.text.toString().trim()
                if (binding.pinEntryEdit.text.toString().isEmpty()) {
                    showSnackBar(
                        "Please Enter you OTP or request one",
                        binding.root,
                        Snackbar.LENGTH_LONG
                    )
                } else if (binding.pinEntryEdit.text?.length!! < 4) {
                    showSnackBar("please enter your full OTP", binding.root, Snackbar.LENGTH_LONG)
                } else if (mPhoneID == null) {
                    showSnackBar(
                        "Please wait while we send the OTP",
                        binding.root,
                        Snackbar.LENGTH_LONG
                    )

                } else {
                    viewModel.authenticateOtp(
                        AuthenticateOtpRequest(
                            methodId = mPhoneID,
                            otpCode = otpCode
                        )
                    )
                }
            }

        }

//        binding.resendCode.setOnClickListener {
//            viewModel.mSharedPrefs.setIsUserPhoneVerified(true)
//
//            viewModel.sendOtp(
//                PhoneOtp(
//                    phone_number = "+10000000000"
//                )
//            )
//            setTimer()
//            showSnackBar("New Code Requested", binding.root, Snackbar.LENGTH_LONG)
//
//        }
    }

    override fun observables() {
        viewModel.sendOtpState.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    Log.e(OtpFragment::class.java.simpleName, "error requesting otp")
                    showSnackBar(result.message.toString(), binding.root, Snackbar.LENGTH_LONG)
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    mPhoneID = result.data!!.phone_id
                    navigateToHome()
                }
            }

        }.launchIn(lifecycleScope)

        viewModel.authenticateOtpState.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    TODO()
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    navigateToHome()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_FragmentOtp_to_FragmentHome)
        findNavController().popBackStack(R.id.FragmentOtp, true)
    }

    private fun setupViews() {

    }

    private fun setTimer() {
        binding.resendCode.isClickable = false
        val timer = object : CountDownTimer(120000, 1000) {
            override fun onFinish() {
                binding.resendCode.isClickable = true
                binding.timer.setTextColor(resources.getColor(R.color.red_100))
                showSnackBar("Time ran out, request a new code", binding.root, Snackbar.LENGTH_LONG)
            }

            override fun onTick(milliseconds: Long) {
                val date = Calendar.getInstance()
                date.timeInMillis = milliseconds

                val formatter = SimpleDateFormat("m:ss")
                val formatted = formatter.format(date.time)

                binding.timer.text = formatted
            }
        }
        timer.start()
    }

}