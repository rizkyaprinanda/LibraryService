package com.example.backside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.example.backside.databinding.ActivityMainBinding
import android.widget.Toast
import com.example.backside.view.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    companion object {
        private const val RC_SIGN_IN = 123
    }
    private lateinit var progressBar: ProgressBar
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressBar = binding.progressBar
        var btnLogout = binding.btnLogout

        mAuth = FirebaseAuth.getInstance()

        btnLogout.setOnClickListener{
            mAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }




        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.tvLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.tvLogin) {
            loginAkunGoogle()
        }
    }

    private fun loginAkunGoogle() {
        progressBar.visibility = View.VISIBLE

        val signInIntent = mGoogleSignInClient?.signInIntent
        signInIntent?.let {
            startActivityForResult(it, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = Tasks.await(completedTask)
            if (account != null) {
                firebaseAuthWithGoogle(account)
            }
        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            Toast.makeText(applicationContext, "Koneksi dengan akun Google gagal!", Toast.LENGTH_SHORT).show()
        }
    }



    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "Selamat Datang " + user?.displayName, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
