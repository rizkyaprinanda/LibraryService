package com.example.backside.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.backside.BooksActivity
import com.example.backside.GettingStartedActivity
import com.example.backside.R
import com.example.backside.databinding.ActivityLoginBinding
import com.example.backside.utils.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val isFirstTime = preferences.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            startActivity(Intent(this@LoginActivity, GettingStartedActivity::class.java))
            finish()
        } else {
            val sessionManager = SessionManager(this)

            if (sessionManager.isLogin()) {
                startActivity(Intent(this, BooksActivity::class.java))
                finish()
            } else {
                initializeAuthentication()
                setupGoogleSignIn()

                binding.btnlogin.setOnClickListener {
                    val email = binding.edtEmailLogin.text.toString()
                    val password = binding.edtPasswordLogin.text.toString()

                    sessionManager.sessionLogin(email)

                    if (validateCredentials(email, password)) {
                        loginFirebase(email, password)
                    }
                }

                binding.tvToRegister.setOnClickListener {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }

                binding.cvGoogle.setOnClickListener {
                    startGoogleSignIn()
                }
            }
        }
    }

    private fun initializeAuthentication() {
        auth = FirebaseAuth.getInstance()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmailLogin.error = "Email tidak valid"
            binding.edtEmailLogin.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            binding.edtPasswordLogin.error = "Password harus diisi"
            binding.edtPasswordLogin.requestFocus()
            return false
        }

        return true
    }

    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    showToast("Selamat datang $email")
                    startActivity(Intent(this, BooksActivity::class.java))
                    finish()
                } else {
                    showToast("${it.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, LENGTH_SHORT).show()
    }

    private fun startGoogleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(data)
        }
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            e.printStackTrace()
            showToast(e.localizedMessage)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val sessionManager = SessionManager(this)
        val email = idToken
        sessionManager.sessionLogin(email)

        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                startActivity(Intent(this, BooksActivity::class.java))
                finish()
            }
            .addOnFailureListener { error ->
                showToast(error.localizedMessage)
            }
    }
}
