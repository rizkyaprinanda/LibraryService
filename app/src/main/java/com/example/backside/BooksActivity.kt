package com.example.backside

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.example.backside.Adapters.BookAdapter
import com.example.backside.databinding.ActivityBooksBinding
import com.example.backside.ui.auth.LoginActivity
import com.example.backside.utils.ApiBookClient
import com.example.backside.utils.BookItem
import com.example.backside.utils.BookResponse
import com.example.backside.utils.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBooksBinding
    private lateinit var adapter: BookAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var googleSignInClient : GoogleSignInClient




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BookAdapter(this, arrayListOf())
        
        binding.rvBook.adapter = adapter
        binding.rvBook.setHasFixedSize(true)
        
        remoteGetBooks()

        val sessionManager = SessionManager(this)

        progressBar = binding.progressBar

        progressDialog = ProgressDialog(this) // atau ProgressBar(this)
        progressDialog.setMessage("Logging out...") // Pesan yang ditampilkan

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        
        binding.btnLogout.setOnClickListener{
            sessionManager.sessionLogout()
            signOutWithProgressBar()
        }
        
        
    }

    private fun remoteGetBooks() {

        ApiBookClient.apiService.getBooks().enqueue(object : Callback<BookResponse> {
            override fun onResponse(
                call: Call<BookResponse>,
                response: Response<BookResponse>
            ) {
                if(response.isSuccessful){
                    val data = response.body()?.items
                    data?.let { setDataToAdapter(it) }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.d("Error", "" + t.stackTraceToString())
            }
        })
    }

    fun setDataToAdapter(data: List<BookItem>){
        adapter.setData(data)
    }

    fun signOutWithProgressBar() {
        // Tampilkan ProgressDialog saat proses sign-out dimulai
        progressDialog.show()

        // Hapus token Google Sign-In
        googleSignInClient.signOut().addOnCompleteListener { googleSignOutTask ->
            // Handle sign-out completion from Google
            if (googleSignOutTask.isSuccessful) {

                val intent = Intent(this, LoginActivity::class.java)
                // Lakukan aksi selanjutnya, misalnya, kembali ke halaman login
                startActivity(intent)
                // Sign out dari Google berhasil, lanjut dengan sign-out dari FirebaseAuth
                mAuth.signOut() // Logout dari FirebaseAuth
                finish()
            } else {
                // Gagal melakukan sign out dari Google, tangani sesuai kebutuhan
                Toast.makeText(this, "Gagal sign out", Toast.LENGTH_SHORT).show()

                // Sembunyikan ProgressDialog karena proses selesai (meskipun dengan kesalahan)
                progressDialog.dismiss()
            }
        }
    }


}

