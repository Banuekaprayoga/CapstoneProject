package com.example.capstone.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivitySignupBinding
import com.example.capstone.ui.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        observeViewModel()
        playAnimation()
    }

    private fun playAnimation() {
        // Kita mulai dengan menambahkan Animasi pada gambar yang berjalan ke kanan dan ke kiri secara berulang.
        // Karena animasi berjalan secara horizontal, gunakanlah property bernama TRANSLATION_X.
        // Anggap saja nilai properti tersebut adalah -30f sampai 30f, artinya ia akan bergerak sejauh 60f.
        // Lalu agar dia bisa kembali ke titik semua, kita bisa menambahkan modifier repeatMode bernilai Reverse.
        // Sedangkan untuk membuat animasi terus berjalan, aturlah modifier repeatCountdengan nilai Infinity. Sehingga,
        // kodenya menjadi seperti ini:
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        // Selanjutnya kita akan menjalankan animasi untuk beberapa item di bawah gambar, yakni 2 TextView dan 2 Button.
        // Di sini, kita menggunakan properti ALPHA untuk fade in dengan value propertinya adalah 0f ke 1f.
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEdit = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)

        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEdit = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val sign = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val alreadyText = ObjectAnimator.ofFloat(binding.textViewAlready, View.ALPHA, 0f).setDuration(500)
        val loginHere = ObjectAnimator.ofFloat(binding.textLoginHere, View.ALPHA, 0f).setDuration(500)

        // Setelah itu, aturlah animasi supaya bisa berjalan bersama dan bergantian.
        // Untuk memunculkan 2 Button dengan animasi bersamaan, gunakanlah playTogether.
        // Sedangkan untuk bagian lainnya, gunakan playSequentially sehingga animasinya muncul secara bergantian.
//        val together = AnimatorSet().apply {
//            playTogether(title)
//        }

        AnimatorSet().apply {
            playSequentially(title, nameEdit, email, emailEdit, password, passwordEdit, sign, alreadyText, loginHere)
            startDelay = 100
            start()
        }
    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                else -> {
                    viewModel.register(email, password)
                }
            }
        }
    }
    private fun observeViewModel() {
        viewModel.registerResult.observe(this) { success ->
            if (success) {
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Akun berhasil dibuat. Silakan login.")
                    setPositiveButton("Login") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Gagal membuat akun",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}