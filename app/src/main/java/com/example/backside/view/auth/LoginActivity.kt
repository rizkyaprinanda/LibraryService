package com.example.backside.view.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Switch
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.backside.EditProfileActivity
import com.example.backside.ForgotPassword
import com.example.backside.MainMenuActivity
import com.example.backside.R
import com.example.backside.databinding.ActivityLoginBinding
import com.example.backside.utils.SessionManager
import com.example.backside.view.GettingStartedActivity
import com.example.backside.view.HomeBeforeJoinActivity
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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val isFirstTime = preferences.getBoolean(KEY_FIRST_TIME, true)
        val isJoined = preferences.getBoolean(KEY_JOINED, false)
        val isLogin = preferences.getBoolean(KEY_LOGIN, false)



        // Set status dark mode berdasarkan nilai yang tersimpan
        val isDarkModeEnabled = preferences.getBoolean(KEY_DARK_MODE, false)
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
        if (isFirstTime) {
            startActivity(Intent(this, GettingStartedActivity::class.java))
            finish()
        } else {

            if (isLogin && isJoined) {
                startActivity(Intent(this, MainMenuActivity::class.java))
                finish()
            } else if (isLogin && !isJoined) {
                startActivity(Intent(this, HomeBeforeJoinActivity::class.java))
                finish()
            } else {


                initializeAuthentication()
                setupGoogleSignIn()

                binding.btnlogin.setOnClickListener {
                    val email = binding.edtEmailLogin.text.toString()
                    val password = binding.edtPasswordLogin.text.toString()


                    if (validateCredentials(email, password)) {
                        loginFirebase(email, password)
                    }
                }

                binding.tvToRegister.setOnClickListener {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }

                binding.tvToForgot.setOnClickListener {
                    startActivity(Intent(this, ForgotPassword::class.java))
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
            binding.edtEmailLogin.error = "Invalid Email"
            binding.edtEmailLogin.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            binding.edtPasswordLogin.error = "Password must be filled in"
            binding.edtPasswordLogin.requestFocus()
            return false
        }

        return true
    }

    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    showToast("Welcome $email to Upbooks")

                    val isJoined = preferences.getBoolean(KEY_JOINED, false)

                    if (isJoined) {
                        startActivity(Intent(this, MainMenuActivity::class.java))
                        finish()
                    } else if (!isJoined) {
                        startActivity(Intent(this, HomeBeforeJoinActivity::class.java))
                        finish()
                    }

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
            val account =
                GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)!!
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
                startActivity(Intent(this, HomeBeforeJoinActivity::class.java))
                finish()
            }
            .addOnFailureListener { error ->
                showToast(error.localizedMessage)
            }
    }
}