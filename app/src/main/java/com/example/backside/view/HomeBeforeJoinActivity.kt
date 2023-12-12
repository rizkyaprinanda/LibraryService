package com.example.backside.view

import InstitutionsAdapter
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.MainMenuActivity
import com.example.backside.R
import com.example.backside.databinding.ActivityHomeBeforeJoinBinding
import com.example.backside.model.Institutions
import com.example.backside.utils.ApiClient
import com.example.backside.utils.SessionManager
import com.example.backside.view.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class HomeBeforeJoinActivity : AppCompatActivity() {

    private lateinit var institutionsAdapter: InstitutionsAdapter
    private lateinit var originalData: List<Institutions>
    private lateinit var noResultTextView: TextView
    private lateinit var binding: ActivityHomeBeforeJoinBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
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

        // Tambahkan inisialisasi status join dari SharedPreferences
        preferences = getSharedPreferences(HomeBeforeJoinActivity.PREF_NAME, Context.MODE_PRIVATE)
        val sessionManager = SessionManager(this)
        val isLogin = preferences.getBoolean(HomeBeforeJoinActivity.KEY_LOGIN, false)

        preferences.edit().putBoolean(KEY_JOINED, false).apply()
        preferences.edit().putBoolean(KEY_LOGIN, true).apply()

        if (!isLogin) {
            // Pengguna tidak login dan tidak bergabung, kembali ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        FirebaseApp.initializeApp(this)
        binding = ActivityHomeBeforeJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi UI dan komponen lainnya
        val recyclerView: RecyclerView = findViewById(R.id.rvinstitution)
        val search: EditText = findViewById(R.id.edtSearch)
        noResultTextView = findViewById(R.id.tvNoResult)

        // Inisialisasi mAuth
        mAuth = FirebaseAuth.getInstance()

        // Inisialisasi progressDialog
        progressDialog = ProgressDialog(this)

        originalData = ArrayList()
        institutionsAdapter = InstitutionsAdapter(this, arrayListOf())

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = institutionsAdapter

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
                institutionsAdapter.filter(charSequence.toString())
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                val query = charSequence.toString()
                performSearch(query)
            }

            override fun afterTextChanged(editable: Editable) {
                institutionsAdapter.filter(editable.toString())
            }
        })

        // Inisialisasi GoogleSignInOptions
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Inisialisasi googleSignInClient
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLogout.setOnClickListener {
            val user = mAuth.currentUser
            if (user != null) {
                preferences.edit().putBoolean(KEY_LOGIN, false).apply()
                sessionManager.sessionLogout()
                signOutWithProgressBar()
            } else {
                // Pengguna tidak login, lakukan tindakan sesuai kebutuhan
                Toast.makeText(this, "Anda tidak sedang login", Toast.LENGTH_SHORT).show()
            }
        }

        // Call the method to fetch institutions
        remoteGetInstitutions()
    }

    private fun signOutWithProgressBar() {
        progressDialog.show()

        googleSignInClient.signOut().addOnCompleteListener { googleSignOutTask ->
            if (googleSignOutTask.isSuccessful) {
                mAuth.signOut()
                preferences.edit().putBoolean(KEY_LOGIN, false).apply()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Gagal sign out", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }

    private fun performSearch(query: String) {
        val trimmedQuery = query.trim()
        Log.d("PerformSearch", "Performing search with query: $query")

        val filteredData = originalData.filter {
            it.name.contains(trimmedQuery, ignoreCase = true) ||
                    it.description.contains(trimmedQuery, ignoreCase = true)
        }

        if (filteredData.isEmpty()) {
            noResultTextView.visibility = View.VISIBLE
            institutionsAdapter.setData(emptyList())
            binding.rvinstitution.visibility = View.GONE
            noResultTextView.text = "Sorry, the institution you are looking for does not exist."
        } else {
            noResultTextView.visibility = View.GONE
            binding.rvinstitution.visibility = View.VISIBLE
            institutionsAdapter.setData(filteredData)
        }
    }

    fun setDataToAdapter(data: List<Institutions>) {
        institutionsAdapter.setData(data)
        originalData = ArrayList(data)
    }

    private fun remoteGetInstitutions() {
        ApiClient.apiService.getInstitutions().enqueue(object : Callback<List<Institutions>> {
            override fun onResponse(
                call: Call<List<Institutions>>,
                response: Response<List<Institutions>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let { setDataToAdapter(it) }
                }
            }

            override fun onFailure(call: Call<List<Institutions>>, t: Throwable) {
                Log.d("Error", t.stackTraceToString())
            }
        })
    }
}