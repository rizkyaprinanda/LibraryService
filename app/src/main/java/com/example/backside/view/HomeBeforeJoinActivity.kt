package com.example.backside.view

import InstitutionsAdapter
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
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
import com.example.backside.utils.SessionManager
import com.example.backside.view.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeBeforeJoinActivity : AppCompatActivity() {

    private lateinit var institutionsAdapter: InstitutionsAdapter
    private lateinit var originalData: List<Institutions>
    private lateinit var noResultTextView: TextView
    private lateinit var binding: ActivityHomeBeforeJoinBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBeforeJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.rvinstitution)
        val search: EditText = findViewById(R.id.edtSearch)
        noResultTextView = findViewById(R.id.tvNoResult)

        // Inisialisasi mAuth
        mAuth = FirebaseAuth.getInstance()

        // Inisialisasi progressDialog
        progressDialog = ProgressDialog(this)

        // Contoh data institusi
        val institutionList: List<Institutions> = listOf(
            Institutions("1", R.drawable.itb1, "Universitas A", "Bandung", "Perpustakaan Umum Kampus A", "access_token_1"),
            Institutions("2", R.drawable.ipb1, "Universitas B", "Bogor", "Perpustakaan Umum Kampus B", "access_token_2"),
            Institutions("3", R.drawable.sma1a, "SMA 1 A", "Jakarta", "Perpustakaan Umum SMA A", "access_token_3"),
            Institutions("4", R.drawable.sma1b, "SMA 1 B", "Jakarta", "Perpustakaan Umum SMA B", "access_token_4"),
            Institutions("5", R.drawable.itb1, "Universitas A", "Bandung", "Perpustakaan Umum Kampus A", "access_token_1"),
            Institutions("6", R.drawable.ipb1, "Universitas B", "Bogor", "Perpustakaan Umum Kampus B", "access_token_2"),
            Institutions("7", R.drawable.sma1a, "SMA 1 A", "Jakarta", "Perpustakaan Umum SMA A", "access_token_3"),
            Institutions("8", R.drawable.sma1b, "SMA 1 B", "Jakarta", "Perpustakaan Umum SMA B", "access_token_4"),
            // Tambahkan data institusi lainnya sesuai kebutuhan
        )

        originalData = institutionList
        institutionsAdapter = InstitutionsAdapter(this, institutionList)

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

        val sessionManager = SessionManager(this)



        // Tambahkan inisialisasi status join dari SharedPreferences
        val preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val isJoined = preferences.getBoolean("isJoined", false)

        if (isJoined == true) {
            // Jika pengguna belum bergabung, arahkan ke HomeBeforeJoinActivity atau LoginActivity
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
            return
        }


        // Inisialisasi GoogleSignInOptions
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Ganti dengan Web Client ID Anda
            .requestEmail()
            .build()
        // Inisialisasi googleSignInClient
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLogout.setOnClickListener {
            val user = mAuth.currentUser
            if (user != null) {
                // Pengguna sedang login, lakukan logout
                sessionManager.sessionLogout()
                signOutWithProgressBar()
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
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Gagal melakukan sign out dari Google, tangani sesuai kebutuhan
                Toast.makeText(this, "Gagal sign out", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }

    private fun performSearch(query: String) {
        val trimmedQuery = query.trim()
        Log.d("PerformSearch", "Performing search with query: $query")

        // ... (Tambahkan log untuk originalData dan filteredData)



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
}
