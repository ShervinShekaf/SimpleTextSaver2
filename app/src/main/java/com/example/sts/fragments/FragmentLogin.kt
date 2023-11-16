package com.example.sts.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sts.R
import com.example.sts.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

const val KEY_PRE = "preferences"
const val KEY_NAME = "name"
const val KEY_BIRTHDAY = "birthday"
const val KEY_EMAIL = "email"
const val KEY_NUMBER = "NumberPhone"
const val KEY_PASSWORD = "password"

open class FragmentLogin : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize sharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(KEY_PRE, Context.MODE_PRIVATE)

        binding.btnGoToFragmentHome.setOnClickListener {

            val name = binding.edtName.text.toString()
            val birthday = binding.edtBirthday.text.toString()
            val number = binding.edtNumber.text.toString()
            val password = binding.edtPassword.text.toString()
            val email = binding.edtEmail.text.toString()

            if (name.isNotEmpty() && birthday.isNotEmpty() && number.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {

                // put

                val editor = sharedPreferences.edit()
                editor.putString(KEY_NAME, name)
                editor.putString(KEY_BIRTHDAY, birthday)
                editor.putString(KEY_NUMBER, number)
                editor.putString(KEY_EMAIL, email)
                editor.putString(KEY_PASSWORD, password)
                editor.apply()

                Snackbar.make(
                    binding.root,
                    "Save Your Information",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Ok") {
                        findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
                    }
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()

            } else {

                Snackbar.make(
                    binding.root,
                    "Please Enter Your Information",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Ok") {

                    }
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()

            }
        }
        // get

        val name = sharedPreferences.getString(KEY_NAME, "")
        binding.edtName.setText(name)
        val birthday = sharedPreferences.getString(KEY_BIRTHDAY, "")
        binding.edtBirthday.setText(birthday)
        val number = sharedPreferences.getString(KEY_NUMBER, "")
        binding.edtNumber.setText(number)
        val password = sharedPreferences.getString(KEY_PASSWORD, "")
        binding.edtPassword.setText(password)
        val email = sharedPreferences.getString(KEY_EMAIL, "")
        binding.edtEmail.setText(email)

        // contains Shared Preferences

        val isName = sharedPreferences.contains(KEY_NAME)
        val isBirthday = sharedPreferences.contains(KEY_BIRTHDAY)
        val isNumber = sharedPreferences.contains(KEY_NUMBER)
        val isPassword = sharedPreferences.contains(KEY_PASSWORD)
        val isEmail = sharedPreferences.contains(KEY_EMAIL)

        if (isName && isBirthday && isNumber && isPassword && isEmail) {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
        }
    }
}
