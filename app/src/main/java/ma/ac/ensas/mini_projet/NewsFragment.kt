package ma.ac.ensas.mini_projet

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ma.ac.ensas.mini_projet.databinding.FragmentNewsBinding
import ma.ac.ensas.projet.MyAdapter
import java.util.*

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var rv: RecyclerView
    private lateinit var liste: ArrayList<News>
    private lateinit var newsAdapter: MyAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialiser la liste avant de l'utiliser
        liste = ArrayList()
        dataIn()
        val layoutManager = LinearLayoutManager(context)
        rv = binding.rvNews
        rv.layoutManager = layoutManager

        newsAdapter = MyAdapter(liste, findNavController())
        rv.adapter = newsAdapter

        binding.allNews.setOnClickListener {
            (activity as AppCompatActivity).supportActionBar?.setTitle("Toute les articles")
            updateNewsList(liste)
        }

        binding.economieImage.setOnClickListener {
            (activity as AppCompatActivity).supportActionBar?.title = "Economies"
            val economieNews = getNewsByCategory("Economies")
            updateNewsList(economieNews)
        }

        binding.sportsImage.setOnClickListener {
            (activity as AppCompatActivity).supportActionBar?.title = "Sports"
            val sportsNews = getNewsByCategory("Sports")
            updateNewsList(sportsNews)
        }

        binding.santeImage.setOnClickListener {
            (activity as AppCompatActivity).supportActionBar?.title = "Santés"
            val santeNews = getNewsByCategory("Santés")
            updateNewsList(santeNews)
        }
    }


    private fun updateNewsList(newData: List<News>) {
        newsAdapter.updateData(newData)
    }

    private fun getNewsByCategory(category: String): List<News> {
        return liste.filter { it.category == category }
    }

    private fun dataIn() {
        liste.addAll(
            listOf(
                News(1, getString(R.string.Titre_News1_sport),getString( R.string.Desc_News1_sport), R.drawable.img1, "Sports"),
                News(2, getString(R.string.Titre_News2_sport),getString( R.string.Desc_News2_sport), R.drawable.img2, "Sports"),
                News(3, getString(R.string.Titre_News3_sport), getString(R.string.Desc_News3_sport), R.drawable.img3, "Sports"),
                News(4, getString(R.string.Titre_News4_sport), getString(R.string.Desc_News4_sport), R.drawable.img4, "Sports"),
                News(5, getString(R.string.Titre_News5_sport), getString(R.string.Desc_News5_sport), R.drawable.img5, "Sports"),
                News(6, getString(R.string.Titre_News1_economie), getString(R.string.Desc_News1_economie), R.drawable.eco1, "Economies"),
                News(7, getString(R.string.Titre_News2_economie), getString(R.string.Desc_News2_economie), R.drawable.eco2, "Economies"),
                News(8, getString(R.string.Titre_News3_economie), getString(R.string.Desc_News3_economie), R.drawable.eco3, "Economies"),
                News(9, getString(R.string.Titre_News4_economie), getString(R.string.Desc_News4_economie), R.drawable.eco4, "Economies"),
                News(10, getString(R.string.Titre_News5_economie), getString(R.string.Desc_News5_economie), R.drawable.eco5, "Economies"),
                News(11, getString(R.string.Titre_News1_sante), getString(R.string.Desc_News1_sante), R.drawable.sant1, "Santés"),
                News(12, getString(R.string.Titre_News2_sante), getString(R.string.Desc_News2_sante), R.drawable.sant2, "Santés"),
                News(13, getString(R.string.Titre_News3_sante), getString(R.string.Desc_News3_sante), R.drawable.sant3, "Santés"),
                News(14, getString(R.string.Titre_News4_sante), getString(R.string.Desc_News4_sante), R.drawable.sant4, "Santés"),
                News(15, getString(R.string.Titre_News5_sante), getString(R.string.Desc_News5_sante), R.drawable.sant5, "Santés"),
                )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).supportActionBar?.title = "Toutes les articles"
        inflater.inflate(R.menu.menu1, menu)
        val savedIcon = menu.findItem(R.id.savedNewsFrag)
        savedIcon.isVisible = true
        val deleteIcon = menu.findItem(R.id.deleteAll)
        deleteIcon.isVisible = false
        val menuItem = menu.findItem(R.id.searchNews)
        menuItem.isVisible = true
        val searchView = menuItem.actionView as SearchView
        searchView.setOnSearchClickListener {
            savedIcon.isVisible = false
        }
        searchView.queryHint = "Rechercher des actualités "

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchNews(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchNews(query: String) {
        val filteredList = liste.filter { news ->
            news.titre.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
        }
        updateNewsList(filteredList)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.savedNewsFrag -> {
                view?.findNavController()?.navigate(R.id.action_newsFragment_to_favoriesFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
