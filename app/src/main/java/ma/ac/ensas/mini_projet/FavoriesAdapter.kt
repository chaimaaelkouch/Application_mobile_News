package ma.ac.ensas.mini_projet

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ma.ac.ensas.mini_projet.databinding.ItemFavorBinding
import ma.ac.ensas.mini_projet.databinding.ItemNewsBinding
import ma.ac.ensas.projet.MyAdapter

class FavoriesAdapter (private val context: Context, var data: List<News>,private val onDeleteClickListener: (Int) -> Unit) : RecyclerView.Adapter<FavoriesAdapter.MyViewHolder>() {
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
        holder.binding.fav.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Supprimer l'article")
            builder.setMessage("Êtes-vous sûr de supprimer cette article")
            builder.setPositiveButton("Supprimer") { dialog, which ->
                onDeleteClickListener.invoke(article.id)
                Toast.makeText(context, "L'article supprimer", Toast.LENGTH_SHORT).show()
            }
            // Ajouter un bouton "Cancel" pour annuler la suppression
            builder.setNegativeButton("Annuler") { dialog, which ->

            }
            // Afficher la boîte de dialogue
            builder.show()
        }



    }
    fun updateNewsList(newList: List<News>) {
        data = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size



}