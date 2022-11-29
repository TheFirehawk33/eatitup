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
import fr.lotfirais.eatitup.utils.ImageDisplay
import fr.lotfirais.eatitup.utils.Common
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val compositeDisposable = CompositeDisposable()

    private var searchMode : Int = 0
    private var searchText : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.buttonByName.setOnClickListener{
            searchMode = 0
        }
        binding.buttonByIngredient.setOnClickListener{
            searchMode = 1
        }

        binding.searchText.doOnTextChanged { text, _, _, _ ->
            searchText = text.toString()
        }

        binding.searchButton.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment(searchText, searchMode)
            )
        }

        randomRequest()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun randomRequest() {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onRandomResponse(response)}, {t -> Common.onFailure(requireContext(), t) }))
    }

    private fun onRandomResponse(response: Meals) {
        response.meals?.first()?.strMealThumb?.let { imageUrl ->
            ImageDisplay.loadImageViaUrl(requireContext(), binding.randomMealImage, imageUrl, ImageDisplay.homeImageOptions)

            binding.randomMealText.text = response.meals?.first()?.strMeal
        }
    }
}