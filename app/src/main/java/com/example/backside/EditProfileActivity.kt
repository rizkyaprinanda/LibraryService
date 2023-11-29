package com.example.backside

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.backside.databinding.ActivityEditProfileBinding
import com.example.backside.utils.SessionManager
import com.example.backside.view.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

@kotlin.Suppress("DEPRECATION")
class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kembali.setOnClickListener {
            onBackPressed()
        }

        val sessionManager = SessionManager(this)

        // Inisialisasi FirebaseAuth
        mAuth = FirebaseAuth.getInstance()

        // Inisialisasi ProgressDialog
        progressDialog = ProgressDialog(this)

        // Inisialisasi GoogleSignInOptions
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Ganti dengan Web Client ID Anda
            .requestEmail()
            .build()

        // Inisialisasi googleSignInClient
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.imgLogout.setOnClickListener {
            val user = mAuth.currentUser
            if (user != null) {
                // Pengguna sedang login, lakukan logout
                sessionManager.sessionLogout()
                signOutWithProgressBar()

                // Ubah status login di SharedPreferences menjadi false
                val preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean("isJoined", false)
                editor.apply()
            } else {
                // Pengguna tidak login, lakukan tindakan sesuai kebutuhan
                Toast.makeText(this, "Anda tidak sedang login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signOutWithProgressBar() {
        progressDialog.show()

        // Pastikan bahwa googleSignInClient sudah diinisialisasi dengan benar sebelum digunakan
        // googleSignInClient.signOut() akan menghapus token Google Sign-In
        googleSignInClient.signOut().addOnCompleteListener { googleSignOutTask ->
            if (googleSignOutTask.isSuccessful) {
                // Sign out dari Google berhasil, lanjut dengan sign-out dari FirebaseAuth
                mAuth.signOut()
                val preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean("isJoined", false)
                editor.apply()
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
