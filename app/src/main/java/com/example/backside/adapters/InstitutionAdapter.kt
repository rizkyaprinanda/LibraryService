import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.backside.R
import com.example.backside.model.Institutions
import com.example.backside.view.RequestTokenActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class InstitutionsAdapter(
    private val context: Context,
    private var institutionsList: List<Institutions>,
    private var itemClickListener: ((Institutions) -> Unit)? = null
) : RecyclerView.Adapter<InstitutionsAdapter.InstitutionHolder>() {

    private var originalInstitutionsList: List<Institutions> = institutionsList.toList()

    class InstitutionHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvInstName = view.findViewById<TextView>(R.id.tvInstName)
        val tvInstDesc = view.findViewById<TextView>(R.id.tvInstDesc)
        val layoutCard = view.findViewById<LinearLayout>(R.id.layoutCardInstitution)
        val layoutImageInstitution = view.findViewById<LinearLayout>(R.id.layoutImageInstitution)
        val imgInstitution = view.findViewById<ImageView>(R.id.imgInstitution)
        val btnJoin = view.findViewById<Button>(R.id.btnJoin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.items_institution, parent, false)
        return InstitutionHolder(itemView)
    }


    override fun onBindViewHolder(holder: InstitutionHolder, position: Int) {
        val selectedInstitution = institutionsList[position]

        with(holder) {
            tvInstName.text = selectedInstitution.name
            tvInstDesc.text = selectedInstitution.description

            // Inisialisasi Firebase Storage
            val storage = Firebase.storage
            val storageRef = storage.reference

            // Referensi Firebase Storage untuk gambar
            val imageRef = storageRef.child(selectedInstitution.imgInstitution)

            // Dapatkan URL unduhan gambar
            Glide.with(view)
                .asBitmap()
                .load(selectedInstitution.imgInstitution)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        // Set the image to ImageView
                        imgInstitution.setImageBitmap(resource)

                        // Get the dominant color from the image
                        val dominantColor = getDominantColor(resource)

                        // Set the background of cvImageView with the dominant color
                        layoutImageInstitution.setBackgroundColor(dominantColor)

                        // Set layout parameters for imgInstitution
                        val layoutParams = imgInstitution.layoutParams
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                        imgInstitution.layoutParams = layoutParams
                        imgInstitution.scaleType = ImageView.ScaleType.FIT_XY
                    }
                })

            layoutCard.setOnClickListener {
                Toast.makeText(context, selectedInstitution.name, Toast.LENGTH_SHORT).show()
            }


            itemView.setOnClickListener {
                itemClickListener?.invoke(selectedInstitution)
            }

            btnJoin.setOnClickListener {
                val intent = Intent(itemView.context, RequestTokenActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = institutionsList.size

    fun filter(query: String) {
        val filteredList = if (query.isEmpty() || query == " ") {
            originalInstitutionsList.toList()
        } else {
            originalInstitutionsList.filter { institution ->
                institution.name.contains(query, ignoreCase = true) ||
                        institution.description.contains(query, ignoreCase = true) ||
                        institution.location.contains(query, ignoreCase = true)
            }
        }
        setData(filteredList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(filteredData: List<Institutions>) {
        institutionsList = filteredData
        notifyDataSetChanged()
    }

    private fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 1, 1, true) }
        return newBitmap?.getPixel(0, 0) ?: 0
    }
}