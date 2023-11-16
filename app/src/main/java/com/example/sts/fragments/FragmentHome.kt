package com.example.sts.fragments

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
import com.example.sts.databinding.DialogAddFoodBinding
import com.example.sts.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()

        binding.btnAddFood.setOnClickListener {
            showAddFoodDialog()
        }
    }

    private fun setupRecyclerView() {
        val dataFood = arrayListOf(
            ItemData(
                "https://static1.varandaz.com/thumbnail/yRSamiwythlG/JXo-XV_t8wit7V-6kA03x905N2uKCH-J2hDqAbLSS8snGXR8qMIRgXjgQJ25YWYxO4ZhE8P_IXw,/+%D9%82%D8%B1%D9%85%D9%87+%D8%B3%D8%A8%D8%B2%DB%8C.jpg",
                "Ghorme Sabzi",
                "10$",
            ),
            ItemData(
                "https://www.digikala.com/mag/wp-content/uploads/2021/05/Gheymeh-Recipe.jpg",
                "Gheimeh",
                "10$",
            ),
            ItemData(
                "https://www.indianhealthyrecipes.com/wp-content/uploads/2023/05/red-sauce-pasta-recipe.jpg",
                "Pasta",
                "12$"
            ),
            ItemData(
                "https://noktechi.ir/wp-content/uploads/2022/04/kabab-koubide-arzan.jpg",
                "Kabab",
                "12$"
            ),
            ItemData(
                "https://ashmazi.com/wp-content/uploads/2021/04/167205621_441819387110065_856952460244549032_n.jpg",
                "Ab Gosht",
                "12$"
            ),
            ItemData(
                "https://blog.okcs.com/wp-content/uploads/2021/08/%D8%B7%D8%B1%D8%B2-%D8%AA%D9%87%DB%8C%D9%87-%D8%A8%D8%B1%DB%8C%D9%88%D9%86-%D8%A7%D8%B5%D9%81%D9%87%D8%A7%D9%86.jpg",
                "Beryani",
                "14$"
            ),
            ItemData(
                "https://harnika.ir/wp-content/uploads/2022/07/ash-reshte-min.jpg",
                "Ash",
                "6$"
            )
        )

        val myAdapter = HomeAdapter(dataFood)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun showAddFoodDialog() {
        val dialog = AlertDialog.Builder(requireActivity()).create()
        val dialogBinding = DialogAddFoodBinding.inflate(layoutInflater)

        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.btnYes.setOnClickListener {
            handleAddFood(dialogBinding, dialog)
        }

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun handleAddFood(dialogBinding: DialogAddFoodBinding, dialog: AlertDialog) {
        if (
            dialogBinding.edtFood.length() > 0 &&
            dialogBinding.edtPrice.length() > 0 &&
            dialogBinding.edtImg.length() > 0
        ) {

            val txtFood = dialogBinding.edtFood.text.toString()
            val txtPrice = dialogBinding.edtPrice.text.toString()
            val txtUrl = dialogBinding.edtImg.text.toString()

            val newFood = ItemData(txtUrl, txtFood, txtPrice)
            (binding.recyclerMain.adapter as HomeAdapter).addNewFood(newFood)

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
}
