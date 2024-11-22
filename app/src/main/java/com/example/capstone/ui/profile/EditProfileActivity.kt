package com.example.capstone.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter

import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivityEditProfileBinding
import com.example.capstone.ui.main.MainActivity
import com.example.capstone.ui.utils.ViewModelFactory
import com.example.capstone.ui.utils.getImageUri

class EditProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityEditProfileBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupAction()
        observeViewModel()
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupAction() {
        viewModel.getProfile().observe(this) { data ->
            binding.apply {
                nameEditText.setText(data.name)
                emailEditText.setText(data.email)

            }
        }
        binding.btnEditImgProfile.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.pick_image_alert))
                setMessage(getString(R.string.message_pick_image))
                setPositiveButton(getString(R.string.gallery)) { _, _ ->
                    startGallery()
                }
                setNegativeButton(getString(R.string.title_camera)) { _, _ ->
                    startIntentCamera()
                }
                create()
                show()
            }
        }
        binding.btnSave.setOnClickListener {
            val image = currentImageUri.toString()
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
//            val gender = binding.genderEditText.text.toString()
//            val birth = binding.bi
            viewModel.setProfile(name, email, image)
        }
    }

    private fun observeViewModel() {
        viewModel.profileUpdate.observe(this) { success ->
            if (success) {
                showToast(getString(R.string.success_save))
                startActivity(Intent(this@EditProfileActivity, MainActivity::class.java))
            } else {
                showToast("Failed Save")
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
            showToast(getString(R.string.success_pick_image))
        } else {
            Log.d(TAG, getString(R.string.failed_pick_image))
            showToast(getString(R.string.failed_pick_image))
        }
    }

    private fun startIntentCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
            showToast(getString(R.string.success_pick_image))
        } else {
            Log.d(TAG, getString(R.string.failed_pick_image))
            showToast(getString(R.string.failed_pick_image))
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d(TAG, "showImage: $it")
            binding.imgProfile.setImageURI(it)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "ProfileEditActivity"
    }
}