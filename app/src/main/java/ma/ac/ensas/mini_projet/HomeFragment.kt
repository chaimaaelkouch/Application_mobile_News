package ma.ac.ensas.mini_projet

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ma.ac.ensas.mini_projet.databinding.FragmentHomeBinding
import androidx.appcompat.app.AppCompatActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Use 'by lazy' for better performance
    private val btn by lazy { binding.getStartedButton }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Accueil"

        // Use the lazy property for the click listener
        btn.setOnClickListener {
            (activity as AppCompatActivity).supportActionBar?.title = "Toutes les articles"
            findNavController().navigate(R.id.action_homeFragment_to_newsFragment)
        }

        setHasOptionsMenu(false) // Indiquer que ce fragment a un menu
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
