package com.example.backside

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.backside.adapters.BooksAdapter
import com.example.backside.databinding.FragmentBrowserBinding
import com.example.backside.model.BooksCollections
import com.example.backside.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Fungsi ekstensi untuk melakukan pencarian pada List<BooksCollections>
private fun List<BooksCollections>.performSearch(query: String): List<BooksCollections> {
    return this.filter { it.title?.contains(query, ignoreCase = true) == true ||
            it.description?.contains(query, ignoreCase = true) == true ||
            it.author?.contains(query, ignoreCase = true) == true }
}

class BrowserFragment : Fragment() {

    private lateinit var genreList: MutableList<String>
    private lateinit var binding: FragmentBrowserBinding
    private lateinit var adapter1: BooksAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var doubleBackPressedOnce = false
    private var originalData: List<BooksCollections> = emptyList()

    companion object {
        fun newInstance(): BrowserFragment {
            return BrowserFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!doubleBackPressedOnce) {
                    Toast.makeText(requireContext(), "Press again to exit", Toast.LENGTH_SHORT).show()
                    doubleBackPressedOnce = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackPressedOnce = false
                    }, 2000)
                } else {
                    requireActivity().finish()
                }
            }
        })

        genreList = mutableListOf()

        val spinner: Spinner = binding.spinner

        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner, genreList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                // Aksi yang diambil saat item dipilih
                val selectedGenre = genreList[position]
                Toast.makeText(requireContext(), "Genre dipilih: $selectedGenre", Toast.LENGTH_SHORT).show()

                // Filter data sesuai dengan nilai Spinner
                val filteredData = if (selectedGenre == "Semua Genre") {
                    originalData // Tampilkan semua data
                } else if (genreList.contains(selectedGenre)) {
                    originalData.filter { it.genre == selectedGenre }
                } else {
                    emptyList() // Tampilkan data kosong jika tidak ada genre yang dipilih
                }

                adapter1.setData(filteredData)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Handle nothing selected if needed
            }
        }


        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            // Aksi refresh
            // Taruh logika refresh data di sini
            refreshData()
        }

        adapter1 = BooksAdapter(requireContext(), arrayListOf()) { selectedBook ->
            navigateToDetailBook(selectedBook)
        }

        progressBar = binding.progressBar

        val color = ContextCompat.getColor(requireContext(), R.color.secondary)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            progressBar.indeterminateTintList = ContextCompat.getColorStateList(requireContext(), R.color.secondary)
        } else {
            val mode = android.graphics.PorterDuff.Mode.SRC_IN
            progressBar.indeterminateDrawable.setColorFilter(color, mode)
        }

        binding.rvBrowser.adapter = adapter1
        binding.rvBrowser.setHasFixedSize(true)

        progressBar.visibility = View.VISIBLE

        remoteGetBooks(adapter1, progressBar)

        val search = binding.edtSearch
        val noResultTextView = binding.tvNoResult
        noResultTextView.visibility = View.GONE

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                val query = charSequence.toString()
                performSearch(query)
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        // Simpan data asli untuk pencarian
        originalData = ArrayList()
    }

    private fun refreshData() {
        remoteGetBooks(adapter1, progressBar)

        // Setelah selesai refresh, hentikan ikon loading
        swipeRefreshLayout.isRefreshing = false
    }

    private fun performSearch(query: String) {
        val filteredData = originalData.performSearch(query)
        val searchText = binding.edtSearch

        if (filteredData.isEmpty()) {
            binding.tvNoResult.visibility = View.VISIBLE
            adapter1.setData(emptyList())
            binding.rvBrowser.visibility = View.GONE

            if (searchText.text.isEmpty()) {
                binding.containerSpinner.visibility = View.VISIBLE
                adapter1.setData(emptyList())
            }

        } else {
            binding.tvNoResult.visibility = View.GONE
            binding.containerSpinner.visibility = View.GONE
            binding.rvBrowser.visibility = View.VISIBLE
            adapter1.setData(filteredData)

            if (searchText.text.isEmpty()) {
                binding.containerSpinner.visibility = View.VISIBLE
                adapter1.setData(filteredData)
            }
        }
    }

    private fun remoteGetBooksCollections(call: Call<List<BooksCollections>>, adapter: BooksAdapter, progressBar: ProgressBar) {
        call.enqueue(object : Callback<List<BooksCollections>> {
            override fun onResponse(call: Call<List<BooksCollections>>, response: Response<List<BooksCollections>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        // Shuffle data sebelum menetapkan ke adapter
                        val shuffledData = it.toMutableList().shuffled()
                        setDataToAdapter(adapter, shuffledData)

                        // Ambil daftar genre dari data
                        genreList = it.map { book -> book.genre }.distinct().toMutableList()

                        // Tambahkan opsi "Semua Genre"
                        genreList.add(0, "Semua Genre")

                        // Update adapter spinner dengan daftar genre
                        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner, genreList)
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinner.adapter = spinnerAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<BooksCollections>>, t: Throwable) {
                progressBar.visibility = View.GONE
                // Handle kegagalan pengambilan data
            }
        })
    }

    private fun remoteGetBooks(adapter: BooksAdapter, progressBar: ProgressBar) {
        val call = ApiClient.apiService.getBooks()
        remoteGetBooksCollections(call, adapter, progressBar)
    }

    private fun setDataToAdapter(adapter: BooksAdapter, data: List<BooksCollections>) {
        originalData = data
        adapter.setData(data)
    }

    private fun navigateToDetailBook(selectedBook: BooksCollections) {
        val intent = Intent(requireContext(), DetailBookActivity::class.java)
        intent.putExtra("book", selectedBook)
        startActivity(intent)
    }
}
