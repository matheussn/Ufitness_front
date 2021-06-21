package com.example.ufitness.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ufitness.R
import com.example.ufitness.databinding.FragmentHomeBinding


class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.personalRegister.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_personal)
        }
        binding.clientRegister.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_clientRegister)
        }
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}