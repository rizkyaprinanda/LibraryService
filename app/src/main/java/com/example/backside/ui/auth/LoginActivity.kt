package com.example.backside.ui.auth

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.backside.BrowserActivity
import com.example.backside.R
import com.example.backside.utils.SessionManager
import com.example.backside.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)

        if (sessionManager.isLogin()){
            val intent = Intent(this, BrowserActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            auth = FirebaseAuth.getInstance()

            var btnGoogle = binding.cvGoogle

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)


            binding.btnlogin.setOnClickListener{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            binding.btnlogin.setOnClickListener{

                val email = binding.edtEmailLogin.text.toString()
                val password = binding.edtPasswordLogin.text.toString()

                sessionManager.sessionLogin(email)

                // Validasi email
                if (email.isEmpty()){
                    binding.edtEmailLogin.error = "Email Harus diisi"
                    binding.edtEmailLogin.requestFocus()
                    return@setOnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.edtEmailLogin.error = "Email Tidak Valid"
                    binding.edtEmailLogin.requestFocus()
                    return@setOnClickListener
                }

                // Validasi Password
                if (password.isEmpty()){
                    binding.edtPasswordLogin.error = "Password Harus diisi"
                    binding.edtPasswordLogin.requestFocus()
                    return@setOnClickListener
                }

                LoginFirebase(email,password)

            }

            binding.tvToRegister.setOnClickListener{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

            btnGoogle.setOnClickListener{
                val signInIntent  = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }

    }


    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, BrowserActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            // Menangani proses google
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Jika berhasil
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException){
                e.printStackTrace()
                Toast.makeText(applicationContext, e.localizedMessage, LENGTH_SHORT).show()
            }
        }
    }

    fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val sessionManager = SessionManager(this)
        val email = idToken
        sessionManager.sessionLogin(email)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{
                startActivity(Intent(this, BrowserActivity::class.java))
                finish()
            }
            .addOnFailureListener{error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }
}