import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.R
import com.example.backside.model.Books

class BrowserAdapter(private val context: Context, private var bookList: List<Books>)
    : RecyclerView.Adapter<BrowserAdapter.BrowserViewHolder>() {


    private var originalBookList: List<Books> = bookList.toList()
    private var itemClickListener: ((Books) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowserViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.items_browser, parent, false)
        return BrowserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BrowserViewHolder, position: Int) {
        val books = bookList[position]
        holder.bindView(books)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }


    fun filter(query: String) {
        val filteredList = if (query.isEmpty() || query == " ") {
            // Jika query kosong, tampilkan semua data
            originalBookList.toList()

        } else {
            // Jika query tidak kosong, filter data berdasarkan query
            originalBookList.filter { book ->
                book.judul.contains(query, ignoreCase = true) ||
                        book.penulis.contains(query, ignoreCase = true) ||
                        book.kategori.contains(query, ignoreCase = true)
            }
        }

        // Update data set dalam adapter
        setData(filteredList)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(filteredData: List<Books>) {
        bookList = filteredData
        notifyDataSetChanged()
    }

    inner class BrowserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgbrow = view.findViewById<ImageView>(R.id.gambarbrowser)
        private val judul = view.findViewById<TextView>(R.id.judulbrow)
        private val penulis = view.findViewById<TextView>(R.id.penulisbrow)
        private val kategori = view.findViewById<TextView>(R.id.kategoribrow)
        private val jumlah = view.findViewById<TextView>(R.id.jumlahvotebrow)
        private val mantepLayout = view.findViewById<LinearLayout>(R.id.mantep)




        fun bindView(book: Books) {
            imgbrow.setImageResource(book.imgBook)
            judul.text = book.judul
            penulis.text = book.penulis
            kategori.text = book.kategori
            jumlah.text = book.jumlah

            if(book.sudahVote == false){
                mantepLayout.setBackgroundResource(R.drawable.bgup)
            }else{
                mantepLayout.setBackgroundResource(R.drawable.bgupnew)
            }
            mantepLayout.setOnClickListener{




                if(book.sudahVote == false){
                    book.jumlah = (book.jumlah.toInt() + 1).toString()
                    jumlah.text = book.jumlah
                    book.sudahVote = true
                    val message = "Berhasil up vote buku ${book.judul}"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    mantepLayout.setBackgroundResource(R.drawable.bgupnew)
                }else{
                    book.jumlah = (book.jumlah.toInt() - 1).toString()
                    jumlah.text = book.jumlah
                    book.sudahVote = false
                    val message = "Yaah, kamu down vote buku ${book.judul}"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    mantepLayout.setBackgroundResource(R.drawable.bgup)
                }





            }
            itemView.setOnClickListener {
                itemClickListener?.invoke(book)
            }
        }
    }
}