package com.example.finalapp.presentation.screens.addpost_screen

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentAddPostBinding
import com.example.finalapp.domain.model.Image
import com.example.finalapp.domain.model.Post
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class AddPostFragment : Fragment() {


    var cameraUri: Uri? = null
    private var imageUri: Uri? = null
    private var selectedImage: Bitmap? = null
    private var imageFile: ParseFile? = null
    private var pickImageIntent: Intent? = null
    private var latestTmpUri: Uri? = null
    private val values: ContentValues by lazy {
        ContentValues()
    }

    private val getContent: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            binding.postImage.load(imageUri)

        }
    private val viewModel: AddPostViewModel by viewModels()

    private val binding: FragmentAddPostBinding by lazy {
        FragmentAddPostBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasPermissions(requireContext(), *PERMISSIONS)) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        binding.pickImage.setOnClickListener {
            openBottomSheet()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.createPost.setOnClickListener {
            createPost()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }


    private fun getImage() {
        Log.i("Get", "getImage ->")
        if (imageUri != null) {
            Picasso.get().load(imageUri).into(binding.postImage)
        }
    }

    private fun createPost() {
        if (imageFile == null) {
         requireContext().showToast("Все поля должны быть заполнены")
            return
        }
        val imageFile = imageFile ?: return
        val post = Post(
            postTitle = binding.postTitle.text.toString(),
            post_description = binding.postDescription.text.toString(),
            post_image = parseFileToImage(imageFile),
            user_name = ParseUser.getCurrentUser().username,
            user_id = ParseUser.getCurrentUser().objectId,
            post_cooktime = String(),
            post_preptime = String(),
            kkal = String(),
            extendIngridients = String(),
            instruction = String(),
            count_ingridients = String()
        )
        viewModel.createPost(post)
    }

    private fun checkReadExternalStoragePermission() = if (ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PermissionChecker.PERMISSION_GRANTED
    ) {
        resultPickReadExternalStorage.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        false
    } else true

    private val resultPickReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) return@registerForActivityResult
        }

//    private fun takePicture() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, REQUEST_CODE)
////        takeImageResult.launch(intent)
//    }

//    private val takeImageResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
//            imageUri = result?.data?.data ?: return@registerForActivityResult
//            getImage()
//        }


//    private fun pickFromGallery() {
//        pickImageIntent = Intent(
//            Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI
//        )
//        pickImageIntent?.type = "image/*"
//        pickImageIntent?.putExtra(Intent.EXTRA_MIME_TYPES, MIME_TYPES)
//        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE)
//    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        val cancelBtn = dialog.findViewById<MaterialButton>(R.id.cancel_btn)
        val takePicture = dialog.findViewById<MaterialCardView>(R.id.take_camera_card_view)
        val pickGallery = dialog.findViewById<MaterialCardView>(R.id.pick_gallery_card_view)
        binding.apply {
            cancelBtn?.setOnClickListener {
                dialog.dismiss()
            }
            takePicture?.setOnClickListener {
                if (!checkReadExternalStoragePermission()) return@setOnClickListener
                requireContext().showToast("Take picture")
                pickImageFileInCamera()
                dialog.dismiss()
            }
            pickGallery?.setOnClickListener {
            selectImageFromGallery()
                requireContext().showToast("Pick from gallery")
                dialog.dismiss()
            }
        }
        dialog.setCancelable(true)
        dialog.show()
    }


    private suspend fun uploadImage(byteArray:ByteArray) = withContext(Dispatchers.IO){
        val parseFile = ParseFile("image.png", byteArray)
        parseFile.saveInBackground(SaveCallback { e ->
            if (e == null) {
                requireContext().showToast("Image saved!")
                imageFile = parseFile
            } else {
                requireContext().showToast("Failed is image!")
            }
        })
    }


    private fun parseFileToImage(file: ParseFile): Image {
        return Image("File", file.name, file.url)

    }
    private fun pickImageFileInCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultCamera.launch(intent)
    }
    private val resultCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        val photo = result.data?.extras?.getByte("data") as? Bitmap ?: return@registerForActivityResult
        handlePickPosterResult(convertBitmapToByteArray(photo))
    }
    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
    private fun handlePickPosterResult(byteArray: ByteArray) {
        lifecycleScope.launch {
            uploadImage(byteArray)
        }
    }
    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { previewImage.setImageURI(uri) }
    }
    private val previewImage by lazy { binding.postImage }


    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val REQUEST_CODE = 200
        var PERMISSION_ALL = 1
        const val IMAGE_PICK_CODE = 100
        var PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        )
        val MIME_TYPES = arrayOf("image/jpeg", "image/png")
    }
}
fun Context.showToast(message:String){
    Toast
        .makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}