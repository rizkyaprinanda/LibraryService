package com.example.backside.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.backside.MainMenuActivity
import com.example.backside.R
import com.example.backside.databinding.ActivityLoginBinding
import com.example.backside.databinding.ActivityRequestTokenBinding
import com.example.backside.model.Institutions
import com.example.backside.utils.SessionManager
import com.example.backside.view.auth.LoginActivity

class RequestTokenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestTokenBinding
    private lateinit var preferences: SharedPreferences
    companion object {
        private const val RC_SIGN_IN = 123
        private const val PREF_NAME = "MyPreferences"
        private const val KEY_FIRST_TIME = "isFirstTime"
        private const val KEY_DARK_MODE = "isDarkMode"
        private const val KEY_JOINED = "isJoined"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedInstitution: Institutions? =
            intent.getSerializableExtra("SELECTED_INSTITUTION") as Institutions

        selectedInstitution?.let {
            // Contoh penggunaan data instansi
            Log.d("SELECTED_INSTITUTION", "Name: ${it.name}, Description: ${it.description}")
        }

        preferences = getSharedPreferences(RequestTokenActivity.PREF_NAME, Context.MODE_PRIVATE)
        val isJoined = preferences.getBoolean(RequestTokenActivity.KEY_JOINED, false)
        val isLogin = preferences.getBoolean("isLogin", true)

        val sessionManager = SessionManager(this)
        binding.tvInstitutionName.text = selectedInstitution?.name

        binding.backButton.setOnClickListener{
            onBackPressed()
        }

        binding.btnJoinToHome.setOnClickListener {
            val enteredToken = binding.edtInstitutionToken.text.toString().trim()

            // Validasi Token
            if (enteredToken.isEmpty()) {
                binding.edtInstitutionToken.error = "Please enter institution token!"
                binding.edtInstitutionToken.requestFocus()
                return@setOnClickListener
            }

            // Memeriksa apakah token yang dimasukkan sesuai dengan yang diharapkan
            if (selectedInstitution != null && enteredToken == selectedInstitution.accessToken) {
                // Token benar, simpan status joined ke SharedPreferences
                preferences.edit().putBoolean(RequestTokenActivity.KEY_JOINED, true).apply()

                // Tampilkan pesan selamat bergabung
                val welcomeMessage = "Welcome to ${selectedInstitution.name}!"
                Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()

                // Pindah ke MainMenuActivity
                startActivity(Intent(this, MainMenuActivity::class.java))
                finish()
            } else {
                // Token salah, berikan pesan kesalahan atau tindakan sesuai kebutuhan
                // Misalnya, menampilkan pesan kesalahan kepada pengguna
                binding.edtInstitutionToken.error = "Token is incorrect"
                binding.edtInstitutionToken.requestFocus()
                Toast.makeText(this, "Token is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}