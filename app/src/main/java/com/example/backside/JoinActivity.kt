package com.example.backside

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast

import com.example.backside.databinding.ActivityJoinBinding

import com.example.backside.adapters.RVAdapter

import com.example.backside.view.auth.LoginActivity
import com.example.backside.model.ResponseItem
import com.example.backside.model.ResponseModel

import com.example.backside.utils.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    private lateinit var adapter: RVAdapter
    private lateinit var mAuth: FirebaseAuth
    companion object {
        private const val RC_SIGN_IN = 123
    }

    private lateinit var progressBar: ProgressBar

    private lateinit var progressDialog: ProgressDialog // atau ProgressBar

    lateinit var googleSignInClient: GoogleSignInClient

    private val apiKey = "c91b07124c855f7577ce56962639cd93"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RVAdapter(this, arrayListOf())

        binding.rvMain.adapter = adapter
        binding.rvMain.setHasFixedSize(true)

        remoteGetPopularMovies()



        // ... (melanjutkan penanganan respons dan kegagalan seperti sebelumnya)

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

    // Ganti tipe data respons di metode ini
    fun remoteGetPopularMovies(){
        ApiClient2.apiService.getPopularMovies().enqueue(object : Callback<ResponseModel>{
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if(response.isSuccessful){
                    val data = response.body()?.results
                    Log.d("MovieResponse", data.toString())
                    data?.let { setDataToAdapter(it) }
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Error", "" + t.stackTraceToString())
            }
        })
    }


    fun setDataToAdapter(data: List<ResponseItem>){
        adapter.setData(data)
    }

}
