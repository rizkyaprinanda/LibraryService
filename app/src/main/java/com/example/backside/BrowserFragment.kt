package com.example.backside

import BooksAdapter
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.backside.databinding.FragmentBrowserBinding
import com.example.backside.model.BooksCollections
import com.example.backside.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private fun List<BooksCollections>.performSearch(query: String): List<BooksCollections> {
    return this.filter { it.title?.contains(query, ignoreCase = true) == true ||
            it.author?.contains(query, ignoreCase = true) == true }
}

class BrowserFragment : Fragment() {

    private lateinit var binding: FragmentBrowserBinding
    private lateinit var adapter1: BooksAdapter
    private lateinit var progressBar: ProgressBar
    private var doubleBackPressedOnce = false
    private var originalData: List<BooksCollections> = emptyList()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    if (!doubleBackPressedOnce) {
                        Toast.makeText(requireContext(), "Press again to exit", Toast.LENGTH_SHORT)
                            .show()
                        doubleBackPressedOnce = true
                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackPressedOnce = false
                        }, 2000)
                    } else {
                        requireActivity().finish()
                    }
                }
            })

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            // Aksi refresh
            // Taruh logika refresh data di sini
            refreshData()
        }



        adapter1 = BooksAdapter(requireContext(), arrayListOf())

        progressBar = binding.progressBar


        val color = ContextCompat.getColor(requireContext(), R.color.secondary)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.indeterminateTintList = ColorStateList.valueOf(color)
        } else {
            val mode = PorterDuff.Mode.SRC_IN
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
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
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


    private fun remoteGetBooksCollections(
        call: Call<List<BooksCollections>>,
        adapter: BooksAdapter,
        progressBar: ProgressBar
    ) {
        call.enqueue(object : Callback<List<BooksCollections>> {
            override fun onResponse(
                call: Call<List<BooksCollections>>,
                response: Response<List<BooksCollections>>
            ) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("BooksResponse", data.toString())

                    // Shuffle data sebelum menetapkan ke adapter
                    data?.let {
                        val shuffledData = it.toMutableList().shuffled()
                        setDataToAdapter(adapter, shuffledData)
                    }
                }
            }

            override fun onFailure(call: Call<List<BooksCollections>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.d("Error", "" + t.stackTraceToString())
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
}