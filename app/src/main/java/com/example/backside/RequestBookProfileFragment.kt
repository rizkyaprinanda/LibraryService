package com.example.backside

import PurchasedAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RequestBookProfileFragment : Fragment() {
    companion object {
        fun newInstance(): RequestBookProfileFragment {
            return RequestBookProfileFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request_book_profile, container, false)
        val book = listOf<PurchasedBooks>(
            PurchasedBooks(
                R.drawable.gambar4,
                "House Of Shadows",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "50",
                false,
                true,

                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            ),
            PurchasedBooks(R.drawable.gambar2,
                "Salt To The Sea",
                "Ruta Sepetys",
                "Fiksi Sejarah",
                "5",
                true,
                true,
                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            ),
            PurchasedBooks(R.drawable.gambar,
                "Luka Kata",
                "Candra Malik",
                "Romance",
                "0",
                false,
                false,
                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            ),
            PurchasedBooks(R.drawable.gambar5,
                "Cantik Itu Luka",
                "Eka Kurniawan",
                "Romance",
                "10",
                true,
                true,
                "dfjsklasdjfklasdjl;fjasdl;jfsddkljasf;dljfasdkl;jf4fdkljfdlaskfelhahdfhasdjkfh4uhaksjdhfkajsdhflkj4hkljashdfjklahlkh34u8ytrytrfahsdkfhadksjfhasdjklfhlasdhfkladh"
            ),



            )

        val adapter = PurchasedAdapter(requireContext(), book) // Gunakan requireContext()


        //Aksi pencarian

        val recyclerView: RecyclerView = view.findViewById(R.id.rvPurchaseProfile)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        return view
    }



}