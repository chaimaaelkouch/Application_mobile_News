package ma.ac.ensas.mini_projet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ma.ac.ensas.mini_projet.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var ratingsHelper: RatingsDBHelper
    private lateinit var myHelper: DBHelper
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var ratingBar: RatingBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        myHelper = DBHelper(requireContext())
        ratingsHelper = RatingsDBHelper(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setTitle("Affichage de l'article")
        val id = args.id
        val titre1 = args.titre
        val desc1 = args.description
        val img1 = args.image
        val cat = args.category
        binding.tvTitle.text = titre1
        binding.tvPublishedAt.text=desc1
        binding.articleImage.setImageResource(img1)
        // Définir le comportement lors du clic sur le bouton
        binding.fab.setOnClickListener {
            val newNews = News(id, titre1,desc1, img1, cat)
            myHelper.addNews(newNews,requireContext())

        }
        ratingBar = binding.ratingBar
        // Charger l'état d'évaluation depuis la base de données
        val savedRating = loadRatingFromDatabase(id)
        ratingBar.rating = savedRating

        // Ajouter un gestionnaire d'événements pour détecter les changements d'évaluation
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            // Mettre à jour la base de données avec la nouvelle évaluation
            saveRatingToDatabase(id, rating)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu1, menu)

        val savedIcon = menu.findItem(R.id.savedNewsFrag)
        savedIcon.isVisible = true

        val deleteIcon = menu.findItem(R.id.deleteAll)
        deleteIcon.isVisible = false

        val menuItem = menu.findItem(R.id.searchNews)
        menuItem.isVisible = false


    }
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.savedNewsFrag -> {
                view?.findNavController()?.navigate(R.id.action_detailsFragment_to_favoriesFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
    // Méthode pour charger l'état d'évaluation depuis la base de données
    private fun loadRatingFromDatabase(articleId: Int): Float {
        // Utilisez le RatingsDBHelper pour charger la notation de la base de données
        return ratingsHelper.loadRatingForArticle(articleId)
    }

    // Méthode pour sauvegarder l'évaluation dans la base de données
    private fun saveRatingToDatabase(articleId: Int, rating: Float) {
        // Utilisez le RatingsDBHelper pour sauvegarder la notation dans la base de données
        ratingsHelper.saveRatingForArticle(articleId, rating)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
