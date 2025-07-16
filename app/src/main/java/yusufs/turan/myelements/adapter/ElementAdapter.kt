package yusufs.turan.myelements.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import yusufs.turan.myelements.R
import yusufs.turan.myelements.model.Element

class ElementAdapter(
    private val elementList: List<Element>,
    private val onItemClick: (Element) -> Unit
) : RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {

    class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewDetails: TextView = itemView.findViewById(R.id.textViewDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_item, parent, false)
        return ElementViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = elementList[position]

        holder.textViewName.text = element.name

        holder.textViewDetails.text = "Atom No: ${element.atomNumarasi}, Hal: ${element.hal}, Grup: ${element.grup}"

        holder.itemView.setOnClickListener {
            onItemClick(element)
        }
    }

    override fun getItemCount(): Int = elementList.size
}
