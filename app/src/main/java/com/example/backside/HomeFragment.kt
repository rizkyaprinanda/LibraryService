package com.example.backside

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.adapters.BooksAdapter
import com.example.backside.databinding.FragmentHomeBinding
import com.example.backside.model.BooksCollections
import com.example.backside.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter1: BooksAdapter
    private lateinit var adapter2: BooksAdapter
    private lateinit var originalData: List<BooksCollections>
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBar2: ProgressBar
    private var doubleBackPressedOnce = false

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

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

        progressBar = binding.progressBar
        progressBar2 = binding.progressBar2

        adapter1 = BooksAdapter(requireContext(), arrayListOf())
        adapter2 = BooksAdapter(requireContext(), arrayListOf())

        val recyclerView1: RecyclerView = view.findViewById(R.id.recycler1)
        recyclerView1.adapter = adapter1
        recyclerView1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val recyclerView2: RecyclerView = view.findViewById(R.id.recycler2)
        recyclerView2.adapter = adapter2
        recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        progressBar.visibility = View.VISIBLE
        remoteGetBooks()

        return view
    }

    private fun remoteGetBooks() {
        val call = ApiClient.apiService.getBooks()
        call.enqueue(object : Callback<List<BooksCollections>> {
            override fun onResponse(call: Call<List<BooksCollections>>, response: Response<List<BooksCollections>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        val shuffledData = it.toMutableList().shuffled()
                        setDataToAdapters(shuffledData)
                    }
                }
            }

            override fun onFailure(call: Call<List<BooksCollections>>, t: Throwable) {
                progressBar.visibility = View.GONE
                // Handle kegagalan pengambilan data
            }
        })
    }

    private fun setDataToAdapters(data: List<BooksCollections>) {
        originalData = data
        adapter1.setData(data)
        adapter2.setData(data)
    }

    private fun navigateToDetailBook(selectedBook: BooksCollections) {
        val intent = Intent(requireContext(), DetailBookActivity::class.java)
        intent.putExtra("book", selectedBook)
        startActivity(intent)
    }
}
