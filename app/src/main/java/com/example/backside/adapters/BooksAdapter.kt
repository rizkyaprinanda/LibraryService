import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.backside.R
import com.example.backside.model.Books
import com.example.backside.model.BooksCollections
import com.example.backside.view.RequestTokenActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BooksAdapter(
    private val context: Context,
    private var booksList: List<BooksCollections>,
    private var itemClickListener: ((BooksCollections) -> Unit)? = null
) : RecyclerView.Adapter<BooksAdapter.BookHolder>() {

    private var originalBooksList: List<BooksCollections> = booksList.toList()

    class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val bookRating: TextView = itemView.findViewById(R.id.tvRatingBooks)
        val layoutCard: CardView = itemView.findViewById(R.id.cvBookImage)
        val layoutImageBook: LinearLayout = itemView.findViewById(R.id.layoutCard)
        val imgBook: ImageView = itemView.findViewById(R.id.ivBookImage)
        val btnUpVote: LinearLayout = itemView.findViewById(R.id.btnUpVote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.items_books, parent, false)
        return BookHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val selectedBook = booksList[position]

        with(holder) {
            bookTitle.text = selectedBook.title
            bookAuthor.text = selectedBook.description
            bookRating.text = selectedBook.rating.toString()

            // Inisialisasi Firebase Storage
            val storage = Firebase.storage
            val storageRef = storage.reference

            // Referensi Firebase Storage untuk gambar
            val imageRef = storageRef.child(selectedBook.image)

            // Dapatkan URL unduhan gambar
            Glide.with(context)
                .asBitmap()
                .load(imageRef)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        // Set the image to ImageView
                        imgBook.setImageBitmap(resource)

                        // Get the dominant color from the image
                        val dominantColor = getDominantColor(resource)

                        // Set the background of cvImageView with the dominant color
                        layoutImageBook.setBackgroundColor(dominantColor)

                        // Set layout parameters for imgBook
                        val layoutParams = imgBook.layoutParams
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                        imgBook.layoutParams = layoutParams
                        imgBook.scaleType = ImageView.ScaleType.FIT_XY
                    }
                })

            layoutCard.setOnClickListener {
                Toast.makeText(context, selectedBook.title, Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener {
                itemClickListener?.invoke(selectedBook)
            }

            btnUpVote.setOnClickListener {
                val intent = Intent(context, RequestTokenActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = booksList.size

    fun filter(query: String) {
        val filteredList = if (query.isEmpty() || query == " ") {
            originalBooksList.toList()
        } else {
            originalBooksList.filter { book ->
                book.title.contains(query, ignoreCase = true) ||
                        book.description.contains(query, ignoreCase = true) ||
                        book.author.contains(query, ignoreCase = true)
            }
        }
        setData(filteredList)
    }

    fun setData(filteredData: List<BooksCollections>) {
        booksList = filteredData
        notifyDataSetChanged()
    }

    private fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 1, 1, true) }
        return newBitmap?.getPixel(0, 0) ?: 0
    }
}
