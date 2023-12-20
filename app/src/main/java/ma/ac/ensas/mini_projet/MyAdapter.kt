package ma.ac.ensas.projet

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ma.ac.ensas.mini_projet.News
import ma.ac.ensas.mini_projet.NewsFragmentDirections
import ma.ac.ensas.mini_projet.databinding.ItemNewsBinding


class MyAdapter(
    var data: List<News>,
    private val navController: NavController
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var originalData: List<News> = data
    inner class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val clickedItem = data[position]
        holder.binding.textTitle.text = clickedItem.titre
        holder.binding.imageView.setImageResource(clickedItem.image)

        holder.itemView.setOnClickListener {
            Log.d("MyAdapter", "Item clicked: ${clickedItem.id}")
            val action = NewsFragmentDirections.actionNewsFragmentToDetailsFragment(
                clickedItem.id,
                clickedItem.titre,
                clickedItem.description,
                clickedItem.image,
                clickedItem.category
            )
            navController.navigate(action)
        }

    }
    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<News>) {
        data = newData
        notifyDataSetChanged()
    }

}
