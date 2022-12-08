package com.app.a6dgrees.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment




abstract class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observables()
        onClick()
    }
    abstract fun onClick()
    abstract fun observables()
}