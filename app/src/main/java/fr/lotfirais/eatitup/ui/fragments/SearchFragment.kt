package fr.lotfirais.eatitup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.lotfirais.eatitup.data.models.Meals
import fr.lotfirais.eatitup.data.network.ServiceBuilder
import fr.lotfirais.eatitup.databinding.FragmentSearchBinding
import fr.lotfirais.eatitup.ui.adapters.ResultsAdapter
import fr.lotfirais.eatitup.utils.Common
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val compositeDisposable = CompositeDisposable()
    private var searchString: String? = ""
    private var searchMode: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            searchString = it.getString("searchString")
            searchMode = it.getInt("searchMode")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.recyclerViewMeals.adapter = ResultsAdapter(requireContext())
        binding.searchResultText.text = String.format("Search results for : \"$searchString\"")

        searchString?.let {
            searchRequest(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun searchRequest(text: String) {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .searchMealByName(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onSearchResponse(response)}, {t -> Common.onFailure(requireContext(), t) }))
    }

    private fun onSearchResponse(response: Meals) {
        binding.searchResultCount.text = String.format("No results found.")
        response.meals?.let {
            (binding.recyclerViewMeals.adapter as? ResultsAdapter)?.update(it)

            binding.searchResultCount.text = String.format(it.size.toString() + " results found.")
        }
    }
}