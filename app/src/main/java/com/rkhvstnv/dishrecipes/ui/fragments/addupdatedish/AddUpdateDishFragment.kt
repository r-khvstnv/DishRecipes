package com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.FragmentAddUpdateDishBinding

//todo add label error
class AddUpdateDishFragment : Fragment() {
    private var _binding: FragmentAddUpdateDishBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AddUpdateDishFragment()
    }

    private lateinit var viewModel: AddUpdateDishViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUpdateDishBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flSelectImage.setOnClickListener {
            checkStoragePermission()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddUpdateDishViewModel::class.java]
        // TODO: Use the ViewModel
    }

    private val requestStoragePermissionLauncher =
        registerForActivityResult(RequestPermission()
        ){ isGranted: Boolean ->
        if (isGranted){
            pickImageFromGallery()
        } else{
            showSnackBarPermissionError()
        }

    }



    private fun checkStoragePermission(){
        when{
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                pickImageFromGallery()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showSnackBarPermissionError()
            }
            else -> {
                requestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun showSnackBarPermissionError(){
        val sb = Snackbar.make(
            activity?.findViewById(android.R.id.content)!!,
            context?.getString(R.string.st_permission_error)!!,
            Snackbar.LENGTH_LONG
        )
        sb.setAction(context?.getString(R.string.st_settings)){
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity?.packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        sb.show()
    }


    private val galleryResultLauncher =
        registerForActivityResult(StartActivityForResult()
        ){ it ->
            if (it.resultCode == Activity.RESULT_OK){
                it.data?.data?.let {
                    binding.ivDishImage.setImageURI(it)
                }
            }

    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResultLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}