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
import coil.load
import com.example.finalapp.R
import com.example.finalapp.domain.model.MIMETYPE_IMAGES
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
import java.io.ByteArrayOutputStream
class AddPostFragment : Fragment() {


    var cameraUri: Uri? = null
    private var imageUri: Uri? = null
    private var selectedImage: Bitmap? = null
    private var imageFile: ParseFile? = null
    private var pickImageIntent: Intent? = null
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
            uploadImage()
        }
    }

    private fun createPost() {
//        if (Empty()) {
        val post = Post(
            postTitle = binding.postTitle.text.toString(),
            post_description = binding.postDescription.text.toString(),
            post_image = parseFileToImage(imageFile!!),
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
//        } else {
//            Toast.makeText(requireContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT)
//                .show()

//        }
    }

    private fun notEmpty(): Boolean {
        if (imageFile != null) {
            if (binding.postTitle.text!!.isNotEmpty() && binding.postDescription.text!!.isNotEmpty()) return true
        }
        return false
    }

    private fun Empty(): Boolean {
        if (imageFile == null) {
            if (binding.postTitle.text!!.isEmpty() && binding.postDescription.text!!.isEmpty()) return false
        }
        return true
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
            takePicture()
        }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE)
//        takeImageResult.launch(intent)
    }

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
                takePicture()
                Toast.makeText(requireContext(), "Take picture", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            pickGallery?.setOnClickListener {

                getContent.launch(MIMETYPE_IMAGES)
                Toast.makeText(requireContext(), "Pick from gallery", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        dialog.setCancelable(true)
        dialog.show()
    }


    private fun uploadImage() {
        val steam = ByteArrayOutputStream()
        selectedImage =
            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
        selectedImage?.compress(Bitmap.CompressFormat.PNG, 100, steam)
        val byteArray = steam.toByteArray()
        val parseFile = ParseFile("image.png", byteArray)
        parseFile.saveInBackground(SaveCallback { e ->
            if (e == null) {
                Toast.makeText(requireContext(), "Image saved!", Toast.LENGTH_SHORT).show()
                imageFile = parseFile
            } else {
                Toast.makeText(requireContext(), "Failed is image!", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val cPhoto = data!!.extras?.get("data") as Bitmap
            binding.postImage.setImageBitmap(cPhoto)
            imageUri = cameraUri
            getImage()
        }
    }

    private fun parseFileToImage(file: ParseFile): Image {
        return Image("File", file.name, file.url)

    }

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