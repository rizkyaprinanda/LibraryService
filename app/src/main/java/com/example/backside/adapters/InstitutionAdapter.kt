import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.backside.MainMenuActivity
import com.example.backside.R
import com.example.backside.model.Books
import com.example.backside.model.Institutions
import com.example.backside.view.RequestTokenActivity

class InstitutionsAdapter(private val context: Context, private var institutionsList: List<Institutions>)
    : RecyclerView.Adapter<InstitutionsAdapter.InstitutionViewHolder>() {

    private var originalInstitutionsList: List<Institutions> = institutionsList.toList()
    private var itemClickListener: ((Institutions) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.items_institution, parent, false)
        return InstitutionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstitutionViewHolder, position: Int) {
        val institutions = institutionsList[position]
        holder.bindView(institutions)
    }

    override fun getItemCount() = institutionsList.size

    fun filter(query: String) {
        val filteredList = if (query.isEmpty() || query == " ") {
            // Jika query kosong, tampilkan semua data
            originalInstitutionsList.toList()

        } else {
            // Jika query tidak kosong, filter data berdasarkan query
            originalInstitutionsList.filter { institution ->
                institution.name.contains(query, ignoreCase = true) ||
                        institution.description.contains(query, ignoreCase = true) ||
                        institution.location.contains(query, ignoreCase = true)
            }
        }

        // Update data set dalam adapter
        setData(filteredList)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(filteredData: List<Institutions>) {
        institutionsList = filteredData
        notifyDataSetChanged()
    }


    inner class InstitutionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgInstitution = itemView.findViewById<ImageView>(R.id.imgInstitution)
        private val institutionName = itemView.findViewById<TextView>(R.id.tvInstName)
        private val institutionDescription = itemView.findViewById<TextView>(R.id.tvInstDesc)
        private val join = itemView.findViewById<Button>(R.id.btnJoin)

        fun bindView(institutions: Institutions) {
            imgInstitution.setImageResource(institutions.imgInstitution)
            institutionName.text = institutions.name
            institutionDescription.text = institutions.description

            join.setOnClickListener {
                val intent = Intent(itemView.context, RequestTokenActivity::class.java)
                itemView.context.startActivity(intent)
            }




        }
    }









}

