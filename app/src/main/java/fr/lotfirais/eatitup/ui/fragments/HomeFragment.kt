package fr.lotfirais.eatitup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.lotfirais.eatitup.data.models.Meals
import fr.lotfirais.eatitup.data.network.ServiceBuilder
import fr.lotfirais.eatitup.databinding.FragmentHomeBinding
import fr.lotfirais.eatitup.utils.Common
import fr.lotfirais.eatitup.utils.ImageDisplay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val compositeDisposable = CompositeDisposable()

    private var searchMode : Int = 0
    private var searchText : String = ""
    private var randomMealId : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        randomMealRequest()

        binding.buttonByName.setOnClickListener{ searchMode = 0 }
        binding.buttonByIngredient.setOnClickListener{ searchMode = 1 }

        binding.searchText.doOnTextChanged { text, _, _, _ -> searchText = text.toString() }

        binding.buttonTryRandomMeal.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRecipeFragment(randomMealId)
            )
        }
        binding.searchButton.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment(searchText, searchMode)
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun randomMealRequest() {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onRandomResponse(response)}, {t -> Common.onFailure(requireContext(), t) }))
    }

    private fun onRandomResponse(response: Meals) {
        response.meals?.first()?.let { meal ->
            binding.randomMealText.text = response.meals?.first()?.strMeal

            if(meal.strMealThumb != null) ImageDisplay.loadImageViaUrl(requireContext(), binding.randomMealImage, meal.strMealThumb, ImageDisplay.homeImageOptions)
            randomMealId = response.meals?.first()?.idMeal!!
        }
    }
}