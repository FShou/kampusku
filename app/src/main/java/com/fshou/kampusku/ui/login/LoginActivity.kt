package com.fshou.kampusku.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fshou.kampusku.R
import com.fshou.kampusku.databinding.ActivityLoginBinding
import com.fshou.kampusku.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    companion object {
        const val EMAIL = "1"
        const val PASSWORD = "123"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            if (!checkInputValid()) {
                binding.passLayout.error = "Invalid email or password"
                return@setOnClickListener
            }
            startActivity(
                Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )
            )
            finish()
        }
    }

    private fun checkInputValid(): Boolean {
        val email = binding.edEmail.text
        val password = binding.edPassword.text
        if (
            email.isNullOrEmpty() or
            password.isNullOrEmpty()
        ) return false


        if ((email contentEquals EMAIL) and (password contentEquals PASSWORD)) return true

        return false
    }
}