package com.example.backside

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.example.backside.Adapters.RVAdapter
import com.example.backside.databinding.ActivityHomeBinding
import com.example.backside.ui.auth.LoginActivity
import com.example.backside.utils.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: RVAdapter
    private lateinit var mAuth: FirebaseAuth
    companion object {
        private const val RC_SIGN_IN = 123
    }

    private lateinit var progressBar: ProgressBar

    private lateinit var progressDialog: ProgressDialog // atau ProgressBar

    lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RVAdapter(this, arrayListOf())

        binding.rvMain.adapter = adapter
        binding.rvMain.setHasFixedSize(true)

        remoteGetUsers()

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

    fun remoteGetUsers(){
        ApiClient.apiService.getUsers().enqueue(object : Callback<ArrayList<ResponseModel>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseModel>>,
                response: Response<ArrayList<ResponseModel>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    setDataToAdapter(data!!)
                }
            }

            override fun onFailure(call: Call<ArrayList<ResponseModel>>, t: Throwable) {
                Log.d("Error", "" + t.stackTraceToString())
            }
        })
    }

    fun setDataToAdapter( data: ArrayList<ResponseModel>){
        adapter.setData(data)
    }

}
