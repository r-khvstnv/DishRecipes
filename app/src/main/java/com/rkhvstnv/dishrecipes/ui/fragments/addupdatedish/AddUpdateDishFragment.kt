package com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.FragmentAddUpdateDishBinding
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.ui.activities.main.MainActivity
import com.rkhvstnv.dishrecipes.base.BaseFragment
import com.rkhvstnv.dishrecipes.di.OldViewModelFactory
import com.rkhvstnv.dishrecipes.ui.fragments.alldishes.AllDishesViewModel
import com.rkhvstnv.dishrecipes.utils.Constants
import com.rkhvstnv.dishrecipes.utils.appComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject


class AddUpdateDishFragment : BaseFragment() {
    private var _binding: FragmentAddUpdateDishBinding? = null
    private val binding get() = _binding!!

    /*private val viewModel: AddUpdateDishViewModel by activityViewModels {
        OldViewModelFactory(AddUpdateDishViewModel((activity?.application as DishApplication).repository))
    }*/

    @Inject
    lateinit var viewModel: AddUpdateDishViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    private val args: AddUpdateDishFragmentArgs by navArgs()

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

        args.let {
            if (args.dishId != Constants.DEF_ARGS_INT){
                viewModel.assignTmpDish(it.dishId)
            }
        }

        observeTmpDishDataIfExist()

        binding.flSelectImage.setOnClickListener {
            checkStoragePermission()
        }
        binding.btnAddDish.setOnClickListener {
            saveOrUpdateDish()
        }
    }

    /** Prepare adapters for dropdown menus*/
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
            galleryLauncher()
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
                galleryLauncher()
            }
            shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showSnackBarPermissionError()
            }
            else -> {
                requestStoragePermissionLauncher
                    .launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }


    private fun galleryLauncher(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResultLauncher.launch(intent)
    }
    private val galleryResultLauncher =
        registerForActivityResult(StartActivityForResult()
        ){ it ->
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.let {
                    Glide
                        .with(this)
                        .load(it)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
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
                                //save image bitmap
                                resource?.let {
                                    viewModel.dishBitmap = resource.toBitmap()
                                }
                                return false
                            }
                        })
                        .centerInside()
                        .into(binding.ivDishImage)
                }
            }
    }

    /**Method checks user input and show error message to him,
     * if some fields didn't filled*/
    private fun isUserInputIsValid(): Boolean{
        var result: Boolean
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

    /**Method saves image to internal package storage and assigns it's imagePath*/
    private fun saveImageToInternalStorage(bitmap: Bitmap){
        val wrapper = ContextWrapper(context?.applicationContext)

        var file = wrapper.getDir(Constants.IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        runCatching {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }.onFailure {
            it.printStackTrace()
        }
        viewModel.imagePath = file.absolutePath
    }

    /**Returns dishEntity using user inputs.
     * Can be called Only after Input Validation*/
    private fun getDishEntity(): Dish {
        with(binding) {
            return Dish(
                viewModel.imagePath,
                Constants.IMAGE_SOURCE_INTERNAL,
                etLabel.text.toString(),
                etType.text.toString(),
                etCategory.text.toString(),
                etIngredients.text.toString(),
                etCookingTime.text.toString().toInt(),
                etSteps.text.toString(),
                false
            )
        }
    }

    /**Method responsible for all process corresponding to saving/updating dish data
     * Steps:
     * - check validity of user inputs
     * - show UI progress
     * - choose way to save new or update existing dish based on tmpDish State
     * - prepare dish entity using user inputs
     * - insert/update dish
     * - hide UI progress and navigate to AllDishesFragment*/
    private fun saveOrUpdateDish(){
        if (isUserInputIsValid()){
            //show progressBar
            binding.pbIndicator.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO){
                //update
                if (viewModel.tmpDish != null){

                    /*Save new image, if user decided to change existing
                    * dishBitmap is always Null, if galleryResultLauncher has not been called*/
                    if (viewModel.dishBitmap != null){
                        deleteFile(viewModel.imagePath, viewModel.tmpDish!!.value!!.imageSource)
                        saveImageToInternalStorage(viewModel.dishBitmap!!)
                    }

                    //get dish data
                    val dish = getDishEntity()
                    dish.id = viewModel.tmpDish!!.value!!.id
                    dish.isFavoriteDish = viewModel.tmpDish!!.value!!.isFavoriteDish
                    viewModel.updateDishData(dish = dish)
                }
                //add
                else{
                    saveImageToInternalStorage(viewModel.dishBitmap!!)
                    val dish = getDishEntity()
                    viewModel.insertDishData(dish = dish)
                }

                withContext(Dispatchers.Main){
                    //hide progress bar
                    binding.pbIndicator.visibility = View.GONE
                    showSnackBarPositiveMessage("Done")
                    navigateToAllDishes(this@AddUpdateDishFragment)
                }
            }
        }
    }


    /** Observe dishData which was shown in dishDetails*/
    private fun observeTmpDishDataIfExist(){
        viewModel.tmpDish?.observe(viewLifecycleOwner){
            dish ->
            dish.let {
                with(binding){
                    Glide
                        .with(this@AddUpdateDishFragment)
                        .load(it.image)
                        .centerCrop()
                        .into(ivDishImage)

                    viewModel.imagePath = it.image

                    etLabel.setText(it.label)
                    etType.setText(it.type)
                    etCategory.setText(it.category)
                    etIngredients.setText(it.ingredients)
                    etCookingTime.setText(it.cookingTime.toString())
                    etSteps.setText(it.steps)
                    btnAddDish.text = getString(R.string.st_apply_changes)
                }

                /*Hiding bottomNavView prevents wrong navigation.
                Otherwise user will see old dishData, when he want to create new dish */
                (activity as MainActivity).hideNavView()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.tmpDish = null
        viewModel.dishBitmap = null
    }
}