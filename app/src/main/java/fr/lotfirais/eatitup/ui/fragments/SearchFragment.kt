package fr.lotfirais.eatitup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import fr.lotfirais.eatitup.data.models.Meal
import fr.lotfirais.eatitup.data.models.Meals
import fr.lotfirais.eatitup.data.network.ServiceBuilder
import fr.lotfirais.eatitup.databinding.FragmentSearchBinding
import fr.lotfirais.eatitup.ui.adapters.ResultsAdapter
import fr.lotfirais.eatitup.ui.adapters.ResultsAlternateAdapter
import fr.lotfirais.eatitup.utils.Common
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val compositeDisposable = CompositeDisposable()

    private var searchString: String? = ""
    private var searchMode: Int? = 0

    private var categoryList: MutableList<String> = mutableListOf()
    private var selectedCategory: String? = ""
    private val allCategory: String = "All"

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

        searchString?.let {
            if (searchMode == 0) {
                binding.recyclerViewMeals.adapter = ResultsAdapter(requireContext())
                searchByNameRequest(it)
            } else {
                binding.recyclerViewMeals.adapter = ResultsAlternateAdapter(requireContext())
                searchByIngredientRequest(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun searchByNameRequest(text: String) {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .searchMealByName(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> onSearchResponse(response) },
                    { t -> Common.onFailure(requireContext(), t) })
        )
    }

    private fun searchByIngredientRequest(text: String) {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .searchMealByIngredient(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> onSearchResponse(response) },
                    { t -> Common.onFailure(requireContext(), t) })
        )
    }

    private fun onSearchResponse(response: Meals) {
        if(searchMode == 0) {
            binding.searchResultCount.text = String.format("No results found.")
            response.meals?.let { meals ->
                (binding.recyclerViewMeals.adapter as? ResultsAdapter)?.update(meals)
                fillDataPostRequest(meals)
                initCategoryFilter(meals)
            }
        } else {
            binding.searchResultCount.text = String.format("No results found.")
            response.meals?.let { meals ->
                (binding.recyclerViewMeals.adapter as? ResultsAlternateAdapter)?.update(meals)
                fillDataPostRequest(meals)
                initCategoryFilter(meals)
            }
        }
    }

    private fun fillDataPostRequest(meals: List<Meal>) {
        binding.searchResultText.text = String.format("Search results for : \"$searchString\"")
        binding.searchResultCount.text = String.format(meals.size.toString() + " results found.")
        meals.forEach { meal ->
            if (!meal.strCategory.isNullOrEmpty() && !categoryList.contains(meal.strCategory))
                categoryList.add(meal.strCategory)
        }
    }

    private fun initCategoryFilter(meals: List<Meal>) {
        if (categoryList.isNotEmpty()) {
            categoryList.add(0, allCategory)
            binding.searchResultCategory.visibility = View.VISIBLE
            binding.searchResultCategory.adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryList)
            binding.searchResultCategory.onItemSelectedListener =
                CategoryFilterItemSelectedListener(meals)
        } else {
            binding.searchResultCategory.visibility = View.GONE
        }
    }

    inner class CategoryFilterItemSelectedListener constructor(private val meals: List<Meal>) :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View, position: Int, id: Long
        ) {
            selectedCategory = categoryList[position]
            val filteredMeals = mutableListOf<Meal>()

            meals.forEach { meal ->
                if ((!meal.strCategory.isNullOrEmpty() && meal.strCategory == selectedCategory) || selectedCategory == allCategory)
                    filteredMeals.add(meal)
            }
            (binding.recyclerViewMeals.adapter as? ResultsAdapter)?.update(filteredMeals.toList())

        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            selectedCategory = ""
        }
    }
}