import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.mobile.R

class MyListAdapter(private val data: List<String>) :
    RecyclerView.Adapter<MyListAdapter.MyViewHolder>() {
    // Provide reference to the views for each data item
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var dataItem: String
        private var number: Int? = null

        fun bindItems(dataItem: String, position: Int) {
            this.dataItem = dataItem
            this.number = position

            val number = view.findViewById<TextView>(R.id.itemNumber)
            val label = view.findViewById<TextView>(R.id.itemLabel)

            number.text = position.toString()
            label.text = dataItem
        }
    }

    // Create new views (invoked by layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    // Replace contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Get correct element from the dataset, pass to the corresponding view
        holder.bindItems(data[position], position + 1)
    }

    // Return the size of the data set (invoked by the layout manager)
    override fun getItemCount() = data.size

}
