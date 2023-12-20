package ma.ac.ensas.mini_projet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ma.ac.ensas.mini_projet.databinding.ItemFavorBinding
import ma.ac.ensas.mini_projet.databinding.ItemNewsBinding
import ma.ac.ensas.projet.MyAdapter

class FavoriesAdapter (var data: List<News>) : RecyclerView.Adapter<FavoriesAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: ItemFavorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriesAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavorBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriesAdapter.MyViewHolder, position: Int) {
        val article = data[position]
        holder.binding.tvTitle.text = article.titre
        holder.binding.tvDescription.text = article.description
        holder.binding.ivArticleImage.setImageResource(article.image)


    }

    override fun getItemCount(): Int = data.size


}