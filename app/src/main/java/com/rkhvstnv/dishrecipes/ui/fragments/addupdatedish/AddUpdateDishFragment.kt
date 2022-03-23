package com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.FragmentAddUpdateDishBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*

//todo add label error
class AddUpdateDishFragment : Fragment() {
    private var _binding: FragmentAddUpdateDishBinding? = null
    private val binding get() = _binding!!
    var path: String = "" //todo change
    private var dishBitmap: Bitmap? = null

    companion object {
        fun newInstance() = AddUpdateDishFragment()
        const val IMAGE_DIRECTORY = "DishImageDir"
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

        prepareUI()

        binding.flSelectImage.setOnClickListener {
            checkStoragePermission()
        }
        binding.btnAddDish.setOnClickListener {
            /*if (dishBitmap != null){
                lifecycleScope.launch(Dispatchers.IO){
                    saveImageToInternalStorage(dishBitmap!!)
                    Log.i("Glide", path) //todo log
                }
            }*/
            validateUserInput()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddUpdateDishViewModel::class.java]
        // TODO: Use the ViewModel
    }

    private fun prepareUI(){
        val dishTypes = resources.getStringArray(R.array.dish_types)
        val dtAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, dishTypes)
        binding.etType.setAdapter(dtAdapter)

        val dishCategories = resources.getStringArray(R.array.dish_category)
        val dcAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, dishCategories)
        binding.etCategory.setAdapter(dcAdapter)
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
            resources.getString(R.string.st_permission_error),
            Snackbar.LENGTH_LONG
        )
        sb.setAction(resources.getString(R.string.st_settings)){
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
                    Glide
                        .with(this)
                        .load(it)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.e("Glide", "Image loading", e)
                                return false
                            }
                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let {
                                    dishBitmap = resource.toBitmap()
                                }
                                return false
                            }
                        })
                        .centerInside()
                        .into(binding.ivDishImage)
                }
            }

    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResultLauncher.launch(intent)
    }

    //todo implement image saving
    private suspend fun saveImageToInternalStorage(bitmap: Bitmap){
        val wrapper = ContextWrapper(context?.applicationContext)

        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        runCatching {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }.onFailure {
            it.printStackTrace()
        }
        path = file.absolutePath
    }

    //todo for test deleting
    private fun delDir(){
        val wrapper = ContextWrapper(context?.applicationContext)
        val file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file.deleteRecursively()
    }


    private fun validateUserInput(): Boolean{
        var result = false
        val errorMessage = resources.getString(R.string.st_fill_field)
        with(binding){
            when{
                TextUtils.isEmpty(etLabel.text) -> tilLabel.error = errorMessage
                TextUtils.isEmpty(etType.text) -> tilType.error = errorMessage
                TextUtils.isEmpty(etCategory.text) -> tilCategory.error = errorMessage
                TextUtils.isEmpty(etIngredients.text) -> tilIngredients.error = errorMessage
                TextUtils.isEmpty(etCookingTime.text) -> tilCookingTime.error = errorMessage
                TextUtils.isEmpty(etSteps.text) -> tilSteps.error = errorMessage
                else -> result = true
            }

            if (ivDishImage.drawable == null){
                tvImageError.visibility = View.VISIBLE
                result = false
            } else{
                tvImageError.visibility = View.GONE
                result = true
            }
        }


        return result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}