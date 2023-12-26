package ma.ac.ensas.mini_projet

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ma.ac.ensas.mini_projet.databinding.FragmentFavoriesBinding
import ma.ac.ensas.projet.MyAdapter
import androidx.navigation.fragment.findNavController

class FavoriesFragment : Fragment() {

    private lateinit var binding: FragmentFavoriesBinding
    private lateinit var rv: RecyclerView
    private lateinit var favAdapter: FavoriesAdapter
    private lateinit var myHelper: DBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriesBinding.inflate(inflater, container, false)
        myHelper = DBHelper(requireContext())
        setHasOptionsMenu(true) // Assurez-vous que cette ligne est présente
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Articles favoris"
        rv = binding.recyclerView
        val allNews = myHelper.getAllNews()
        rv.layoutManager =
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        favAdapter = FavoriesAdapter(allNews)
        rv.adapter = favAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu1, menu)
        val searchIcon = menu.findItem(R.id.searchNews)
        val savedIcon = menu.findItem(R.id.savedNewsFrag)
        searchIcon.isVisible = false
        savedIcon.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {

            R.id.deleteAll -> {
                // Afficher une boîte de dialogue de confirmation pour supprimer tous les articles sauvegardés
                showDeleteAllConfirmationDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(menuItem)
        }
    }

    private fun showDeleteAllConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Supprimer les articles")
        builder.setMessage("Êtes-vous sûr de supprimer tous les articles enregistrés")
        builder.setPositiveButton("Supprimer tout") { dialog, which ->
            // Appeler la fonction de suppression de tous les articles
            myHelper.deleteAllNews()
            Toast.makeText(context, "Supprimer tout", Toast.LENGTH_SHORT).show()
            // Naviguer vers l'écran des actualités en direct après la suppression
            view?.findNavController()?.navigate(R.id.action_favoriesFragment_to_newsFragment)
        }
        // Ajouter un bouton "Cancel" pour annuler la suppression
        builder.setNegativeButton("Annuler") { dialog, which ->

        }
        // Afficher la boîte de dialogue
        builder.show()
    }




}
