package com.example.backside

import com.example.backside.adapters.PurchasedAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.model.Books
import com.example.backside.utils.ApiClientPurchased
import com.example.backside.utils.ApiServicePurchased
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response


class PurchasedFragment : Fragment() {
    private lateinit var apiService: ApiServicePurchased // Ubah variabel apiClient ke apiService
    private lateinit var apiClient: ApiClientPurchased
    private lateinit var adapter: PurchasedAdapter
    private var originalData: List<PurchasedBooks> = ArrayList() // Store original data
    companion object {
        fun newInstance(): PurchasedFragment {
            return PurchasedFragment()
        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_purchased, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvPurchase)
        apiService = ApiClientPurchased.apiService // Menginisialisasi apiService dari ApiClientPurchased
        apiService.getData().enqueue(object : Callback<List<PurchasedBooks>> {
            override fun onResponse(call: Call<List<PurchasedBooks>>, response: Response<List<PurchasedBooks>>) {
                if (response.isSuccessful) {
                    val books = response.body()
                    books?.let {
                        // Inisialisasi adapter dengan data yang diterima dari API
                        adapter = PurchasedAdapter(requireContext(), it)
                        recyclerView.adapter = adapter
                        originalData = it
                    }
                } else {
                    // Tangani ketika respons tidak sukses di sini
                }
            }

            override fun onFailure(call: Call<List<PurchasedBooks>>, t: Throwable) {
                // Tangani kegagalan jaringan atau permintaan di sini
            }
        })




        val search = view.findViewById<EditText>(R.id.search)


        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
                adapter.filter(charSequence.toString())
                println(charSequence.toString())
            }override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                println(charSequence.toString())
                adapter.filter(charSequence.toString())

            }
            override fun afterTextChanged(editable: Editable) {
                adapter.filter(editable.toString())
                println(editable.toString())
            }
        })
        // Spinner setup and logic
        val data = listOf("Semua","Romance", "Fiksi Sejarah", "Dongeng", "Aksi")
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val adapcher = ArrayAdapter(requireContext(), R.layout.custom_spinner, data)
        adapcher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapcher

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val selectedItem = data[position]
                Toast.makeText(requireContext(), "Kategori dipilih: $selectedItem", Toast.LENGTH_SHORT).show()

                // Filter data based on Spinner selection
                val filteredData = filterByCategory(selectedItem) // Update this line based on how you filter your data
                if (::adapter.isInitialized) {
                    // Filter data based on Spinner selection
                    val filteredData = filterByCategory(selectedItem)

                    // Set filtered data to the adapter
                    adapter.setData(filteredData)
                }
                // Set filtered data to the adapter
//                adapter.setData(filteredData)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Handle nothing selected if needed
            }
        }



        //Aksi pencarian

//
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }
    private fun filterByCategory(category: String): List<PurchasedBooks> {
        return if (category == "Semua") {
            originalData // Show all books
        } else {
            originalData.filter { it.kategori == category }
        }
    }
}
