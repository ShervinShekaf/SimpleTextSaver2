package com.example.sts.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sts.R
import com.example.sts.adapter.HomeAdapter
import com.example.sts.data.ItemData
import com.example.sts.databinding.DialogAddNoteBinding
import com.example.sts.databinding.DialogRemoveItemBinding
import com.example.sts.databinding.DialogUpdateNoteBinding
import com.example.sts.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FragmentHome : Fragment(), HomeAdapter.ClickItem {

    // ... (your existing code)
    private lateinit var binding: FragmentHomeBinding
    private val sharedPreferencesKey = "my_shared_preferences_key"
    private val gson = Gson()
    private lateinit var myAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val savedData = loadFromSharedPreferences()
        setupRecyclerView(savedData)

        binding.btnAddFood.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun setupRecyclerView(data: ArrayList<ItemData>) {
        myAdapter = HomeAdapter(data.clone() as ArrayList<ItemData>, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun showAddNoteDialog() {
        val dialog = AlertDialog.Builder(requireActivity()).create()
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)

        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.btnYes.setOnClickListener {
            handleAddNote(dialogBinding, dialog)
        }

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun handleAddNote(dialogBinding: DialogAddNoteBinding, dialog: AlertDialog) {
        val adapter = binding.recyclerMain.adapter as? HomeAdapter
        if (adapter != null && dialogBinding.edtTitle.length() > 0 && dialogBinding.edtDetail.length() > 0) {
            val txtTitle = dialogBinding.edtTitle.text.toString()
            val txtDetail = dialogBinding.edtDetail.text.toString()

            val newFood = ItemData(txtTitle, txtDetail)
            adapter.addNewItem(newFood)

            // Save the updated data to SharedPreferences
            saveToSharedPreferences(adapter.data)

            binding.recyclerMain.scrollToPosition(0)
            dialog.dismiss()
        } else {
            showSnackbarError()
        }
    }

    private fun showSnackbarError() {
        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG)
            .setActionTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green))
            .show()
    }

    private fun saveToSharedPreferences(data: ArrayList<ItemData>) {
        val json = gson.toJson(data)
        val editor = requireActivity().getPreferences(Context.MODE_PRIVATE).edit()
        editor.putString(sharedPreferencesKey, json)
        editor.apply()
    }

    // Function to load data from SharedPreferences
    private fun loadFromSharedPreferences(): ArrayList<ItemData>{
        val json = requireActivity().getPreferences(Context.MODE_PRIVATE)
            .getString(sharedPreferencesKey, null)

        val type = object : TypeToken<ArrayList<ItemData>>() {}.type

        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            ArrayList()
        }
    }

    override fun onNoteClicked(itemData: ItemData, position: Int) {

        val bindingUpdate = DialogUpdateNoteBinding.inflate(layoutInflater)
        bindingUpdate.edtTitle.setText(itemData.txtTitle)
        bindingUpdate.edtDetail.setText(itemData.txtDetail)

        val dialogUpdate = AlertDialog.Builder(requireContext()).create()
        dialogUpdate.setView(bindingUpdate.root)
        dialogUpdate.setCancelable(true)
        dialogUpdate.show()

        bindingUpdate.btnYes.setOnClickListener {

            dialogUpdate.dismiss()
            if (

                bindingUpdate.edtTitle.text!!.isNotEmpty() &&
                bindingUpdate.edtDetail.text!!.isNotEmpty()

            ) {

                val title = bindingUpdate.edtTitle.text.toString()
                val detail = bindingUpdate.edtDetail.text.toString()

                val newItem = ItemData(

                    title,
                    detail

                )

                saveToSharedPreferences(ArrayList<ItemData>())

                myAdapter.updateItem(newItem, position)

            }

        }

        bindingUpdate.btnNo.setOnClickListener {

            dialogUpdate.dismiss()

        }

    }

    override fun onNoteLongClicked(itemData: ItemData, position: Int) {

        val dialogRemoveBinding = DialogRemoveItemBinding.inflate(layoutInflater)
        val dialogRemove = AlertDialog.Builder(requireContext()).create()
        dialogRemove.setView(dialogRemoveBinding.root)
        dialogRemove.setCancelable(true)
        dialogRemove.show()

        dialogRemoveBinding.btnCancelRemoved.setOnClickListener {

            dialogRemove.dismiss()

        }

        dialogRemoveBinding.btnRemoveItem.setOnClickListener {

            dialogRemove.dismiss()
            saveToSharedPreferences(ArrayList<ItemData>())
            myAdapter.removeNote(itemData, position)

        }


    }
}
