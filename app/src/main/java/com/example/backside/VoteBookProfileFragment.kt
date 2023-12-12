package com.example.backside


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.adapters.BooksAdapter
import com.example.backside.adapters.BrowserAdapter
import com.example.backside.model.Books
import com.example.backside.model.BooksCollections
import com.example.backside.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VoteBookProfileFragment : Fragment() {

    private lateinit var adapter: BooksAdapter
    private lateinit var originalData: List<BooksCollections>

    companion object {
        fun newInstance(): VoteBookProfileFragment {
            return VoteBookProfileFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vote_book_profile, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvBrowser)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

        adapter = BooksAdapter(requireContext(), arrayListOf()) { selectedBook ->
            navigateToDetailBook(selectedBook)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        progressBar.visibility = View.VISIBLE
        remoteGetBooks(adapter, progressBar)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager


        return view
    }

    private fun remoteGetBooks(adapter: BooksAdapter, progressBar: ProgressBar) {
        val call = ApiClient.apiService.getBooks()
        call.enqueue(object : Callback<List<BooksCollections>> {
            override fun onResponse(call: Call<List<BooksCollections>>, response: Response<List<BooksCollections>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        // Shuffle data sebelum menetapkan ke adapter
                        val shuffledData = it.toMutableList().shuffled()
                        setDataToAdapter(adapter, shuffledData)
                    }
                }
            }

            override fun onFailure(call: Call<List<BooksCollections>>, t: Throwable) {
                progressBar.visibility = View.GONE
                // Handle kegagalan pengambilan data
            }
        })
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
