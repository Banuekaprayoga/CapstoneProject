package com.example.capstone.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivityWelcomeBinding
import com.example.capstone.ui.login.LoginActivity
import com.example.capstone.ui.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        // Kita mulai dengan menambahkan Animasi pada gambar yang berjalan ke kanan dan ke kiri secara berulang.
        // Karena animasi berjalan secara horizontal, gunakanlah property bernama TRANSLATION_X.
        // TRANSITION_X adalah Property yang digunakan untuk berpindah dari titik x satu ke titik x lainnya.
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
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(500)

        // Setelah itu, aturlah animasi supaya bisa berjalan bersama dan bergantian.
        // Untuk memunculkan 2 Button dengan animasi bersamaan, gunakanlah playTogether.
        // Sedangkan untuk bagian lainnya, gunakan playSequentially sehingga animasinya muncul secara bergantian.
        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together)
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
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}