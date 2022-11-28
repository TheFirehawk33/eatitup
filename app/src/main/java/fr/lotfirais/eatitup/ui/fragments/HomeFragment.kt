package fr.lotfirais.eatitup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import fr.lotfirais.eatitup.common.ImageDisplay
import fr.lotfirais.eatitup.data.models.Meals
import fr.lotfirais.eatitup.data.network.ServiceBuilder
import fr.lotfirais.eatitup.databinding.FragmentHomeBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val compositeDisposable = CompositeDisposable()

    private var searchMode : Int = 0
    private var arrayPropositions : List<String> = listOf()

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

        binding.searchText.doOnTextChanged { text, start, before, count ->
            searchRequest(text.toString())
        }

        randomRequest()

        return binding.root
    }

    private fun searchRequest(text: String) {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .searchMealByName(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onSearchResponse(response)}, {t -> onFailure(t) }))
    }

    private fun randomRequest() {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onRandomResponse(response)}, {t -> onFailure(t) }))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun onSearchResponse(response: Meals) {
        response.meals?.first()?.strMeal?.let {
            arrayPropositions = listOf(it)
        }
    }

    private fun onRandomResponse(response: Meals) {
        response.meals?.first()?.strMealThumb?.let { imageUrl ->
            ImageDisplay.loadImageViaUrl(requireContext(), binding.randomMealImage, imageUrl)
        }

        binding.randomMealText.text = response.meals?.first()?.strMeal
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(requireContext(),t.message, Toast.LENGTH_SHORT).show()
    }
}