package com.app.a6dgrees.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.app.a6dgrees.databinding.FragmentHomeBinding
import com.app.a6dgrees.presentation.BaseFragment
import android.provider.ContactsContract


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setToolbar()
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    override fun onClick() {
    }

    override fun observables() {
    }

    private fun setUpViews() {
        setToolbar()
    }

    private fun setToolbar() {
        // Get a reference to the ActionBar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Home"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}