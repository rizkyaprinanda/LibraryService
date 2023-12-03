package com.example.backside

import BrowserAdapter
import HomeRekomendasiAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.model.Books


class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
//        return inflater.inflate(R.layout.fragment_home, container, false)

        val book = listOf<Books>(
            Books(R.drawable.gambar,
                "Luka Kata",
                "Candra Malik",
                "Romance",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambar5,
                "Cantik Itu Luka",
                "Eka Kurniawan",
                "Romance",
                "1",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambar2,
                "Salt To The Sea",
                "Ruta Sepetys",
                "Fiksi Sejarah",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambar4,
                "House Of Shadows",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambarku,
                "All The Light We Cannot See",
                "Nicola Cornick",
                "Fiksi Sejarah",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),
            Books(R.drawable.gambarku,
                "All The Light We Cannot See",
                "Nicola Cornick",
                "Aksi",
                "0",
                false,
                "sdfhasdkhfasdhfkhasdjkfhasdjkhfkdshfkjahsdfjkhasdjkhfkehkuckdrfdfasdfmadfdsjfklasdjlfjasdlkfjsdfjklsdjfklsdjklfjsdklfjsdklfjkldfjeukycsdjkfhjekreuhkshdfjkhdjf"
            ),



            )

        val adapter = HomeRekomendasiAdapter(requireContext(), book) // Gunakan requireContext()



        val recyclerView: RecyclerView = view.findViewById(R.id.recycler1)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)


        val recyclerView2: RecyclerView = view.findViewById(R.id.recycler2)
        recyclerView2.adapter = adapter
        recyclerView2.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}