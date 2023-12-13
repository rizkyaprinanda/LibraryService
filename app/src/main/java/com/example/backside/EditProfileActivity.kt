package com.example.backside

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.backside.databinding.ActivityEditProfileBinding
import com.example.backside.utils.SessionManager
import com.example.backside.view.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var preferences: SharedPreferences

    companion object {
        private const val RC_SIGN_IN = 123
        private const val PREF_NAME = "MyPreferences"
        private const val KEY_FIRST_TIME = "isFirstTime"
        private const val KEY_DARK_MODE = "isDarkMode"
        private const val KEY_JOINED = "isJoined"
        private const val KEY_LOGIN = "isLogin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            // Inisialisasi FirebaseAuth
            mAuth = FirebaseAuth.getInstance()

            // Inisialisasi ProgressDialog
            progressDialog = ProgressDialog(this)

            // Inisialisasi SharedPreferences
            preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

            // Inisialisasi GoogleSignInOptions
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Ganti dengan Web Client ID Anda
                .requestEmail()
                .build()

            // Inisialisasi googleSignInClient
            googleSignInClient = GoogleSignIn.getClient(this, gso)

            // Inisialisasi switchButton
            val switchButton: Switch = binding.switchButton

            // Set status dark mode berdasarkan nilai yang tersimpan
            val isDarkModeEnabled = preferences.getBoolean(KEY_DARK_MODE, false)
            if (isDarkModeEnabled) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                preferences.getBoolean(KEY_DARK_MODE, true)
                switchButton.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                preferences.getBoolean(KEY_DARK_MODE, false)
                switchButton.isChecked = false
            }

            switchButton.setOnCheckedChangeListener { _, isChecked ->
                try {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        saveDarkModeStatus(true)
                        val intent = Intent(this, EditProfileActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        saveDarkModeStatus(false)
                        val intent = Intent(this, EditProfileActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            binding.kembali.setOnClickListener {
                onBackPressed()
            }

            binding.imgLogout.setOnClickListener {
                val user = mAuth.currentUser
                if (user != null) {
                    // Pengguna sedang login, lakukan logout
                    signOutWithProgressBar()
                } else {
                    // Pengguna tidak login, lakukan tindakan sesuai kebutuhan
                    Toast.makeText(this, "Anda tidak sedang login", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exception accordingly, e.g., display an error message
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDarkModeStatus(isDarkModeEnabled: Boolean) {
        preferences.edit().putBoolean(KEY_DARK_MODE, isDarkModeEnabled).apply()
    }

    private fun signOutWithProgressBar() {
        progressDialog.show()

        // Pastikan bahwa googleSignInClient sudah diinisialisasi dengan benar sebelum digunakan
        // googleSignInClient.signOut() akan menghapus token Google Sign-In
        googleSignInClient.signOut().addOnCompleteListener { googleSignOutTask ->
            if (googleSignOutTask.isSuccessful) {
                // Sign out dari Google berhasil, lanjut dengan sign-out dari FirebaseAuth
                mAuth.signOut()
                // Ubah status login di SharedPreferences menjadi false
                preferences.edit().putBoolean(KEY_JOINED, false).apply()
                preferences.edit().putBoolean(KEY_LOGIN, false).apply()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                finish()

            } else {
                // Gagal melakukan sign out dari Google, tangani sesuai kebutuhan
                Toast.makeText(this, "Gagal sign out", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }
}
