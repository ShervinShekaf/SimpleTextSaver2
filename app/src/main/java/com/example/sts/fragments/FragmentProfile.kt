package com.example.sts.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sts.databinding.FragmentProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentProfile : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageView: ImageView
    private lateinit var button: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = requireActivity().getSharedPreferences(KEY_PRE, Context.MODE_PRIVATE)

        val name = sharedPreferences.getString(KEY_NAME, "")
        val birthday = sharedPreferences.getString(KEY_BIRTHDAY, "")
        val email = sharedPreferences.getString(KEY_EMAIL, "")
        val number = sharedPreferences.getString(KEY_NUMBER, "")
        val password = sharedPreferences.getString(KEY_PASSWORD, "")

        binding.txtName.text = name
        binding.txtBirthday.text = birthday
        binding.txtEmail.text = email
        binding.txtPhoneNumber.text = number
        binding.txtPassword.text = password

        imageView = binding.imgPhoto
        button = binding.fabAddPhoto

        button.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.btnGoToFragmentLogin.setOnClickListener {
            findNavController().navigate(com.example.sts.R.id.action_fragmentProfile_to_fragmentLogin)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView.setImageURI(data?.data)
    }
}
