package com.example.backside

import BrowserAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class VoteBookProfileFragment : Fragment() {
    companion object {
        fun newInstance(): VoteBookProfileFragment {
            return VoteBookProfileFragment()
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
            val view = inflater.inflate(R.layout.fragment_vote_book_profile, container, false)

            val book = listOf<Books>(
                Books(R.drawable.gambar,
                    "Luka Kata",
                    "Candra Malik",
                    "Romance",
                    "1",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),
                Books(R.drawable.gambar5,
                    "Cantik Itu Luka",
                    "Eka Kurniawan",
                    "Romance",
                    "1",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),
                Books(R.drawable.gambar2,
                    "Salt To The Sea",
                    "Ruta Sepetys",
                    "Fiksi Sejarah",
                    "0",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),
                Books(R.drawable.gambar4,
                    "House Of Shadows",
                    "Nicola Cornick",
                    "Fiksi Sejarah",
                    "0",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),
                Books(R.drawable.gambarku,
                    "All The Light We Cannot See",
                    "Nicola Cornick",
                    "Fiksi Sejarah",
                    "0",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),
                Books(R.drawable.gambarku,
                    "All The Light We Cannot See",
                    "Nicola Cornick",
                    "Aksi",
                    "0",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),
                Books(R.drawable.gambarku,
                    "All The Light We Cannot See",
                    "Nicola Cornick",
                    "Fiksi Sejarah",
                    "0",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),
                Books(R.drawable.gambarku,
                    "All The Light We Cannot See",
                    "Nicola Cornick",
                    "Aksi",
                    "0",
                    true,
                    "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
                ),



                )

            val adapter = BrowserAdapter(requireContext(), book) // Gunakan requireContext()

            //Aksi pencarian

            val recyclerView: RecyclerView = view.findViewById(R.id.rvVoteProfile)
            recyclerView.adapter = adapter

            val layoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return 1
                }
            }


            recyclerView.layoutManager = layoutManager




            return view
        }



}