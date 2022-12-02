package fr.lotfirais.eatitup.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.lotfirais.eatitup.data.models.Ingredients
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

    private var searchMode: Int = 0
    private var searchText: String = ""
    private var randomMealId: String = ""

    private val searchByNameId = 0;
    private val searchByIngredientId = 1;
    private val proposedChoices = 5;
    private var autocompleteIngredientData: MutableList<String> = mutableListOf()
    private var autocompleteMealsData: MutableList<String> = mutableListOf()
    private lateinit var autocompleteMealsArrayAdapter: ArrayAdapter<String>
    private lateinit var autocompleteIngredientsArrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()
        initListener()

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
                .subscribe(
                    { response -> onRandomResponse(response) },
                    { t -> Common.onFailure(requireContext(), t) })
        )
    }

    private fun onRandomResponse(response: Meals) {
        response.meals?.first()?.let { meal ->
            binding.randomMealText.text = response.meals?.first()?.strMeal

            if (meal.strMealThumb != null) ImageDisplay.loadImageViaUrl(
                requireContext(),
                binding.randomMealImage,
                meal.strMealThumb,
                ImageDisplay.homeImageOptions
            )
            randomMealId = response.meals?.first()?.idMeal!!
        }
    }

    private fun init() {
        getAutocompleteIngredientsData()
        randomMealRequest()
        autocompleteMealsArrayAdapter = AutocompleteArrayAdapter(autocompleteMealsData)
        autocompleteIngredientsArrayAdapter = AutocompleteArrayAdapter(autocompleteIngredientData)

        binding.searchText.setAdapter(autocompleteMealsArrayAdapter)
    }

    private fun initListener() {
        binding.buttonByName.setOnClickListener {
            searchMode = searchByNameId
            binding.searchText.text.clear()
            binding.searchText.setAdapter(autocompleteMealsArrayAdapter)
        }
        binding.buttonByIngredient.setOnClickListener {
            searchMode = searchByIngredientId
            binding.searchText.text.clear()
            binding.searchText.setAdapter(autocompleteIngredientsArrayAdapter)
        }

        binding.searchText.doOnTextChanged { text, _, _, _ ->

            if (text.toString().isNotEmpty() && searchMode == searchByNameId) {
                searchText = text.toString()
                getAutocompleteMealsData(text.toString())
            }

        }
        binding.searchText.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.searchButton.performClick()
                return@OnKeyListener true
            }
            false
        })

        binding.buttonRandomizer.setOnClickListener {
            randomMealRequest()
        }
        binding.randomMealImage.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRecipeFragment(randomMealId)
            )
        }
        binding.buttonTryRandomMeal.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRecipeFragment(randomMealId)
            )
        }
        binding.searchButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment(searchText, searchMode)
            )
        }
    }

    private fun getAutocompleteIngredientsData() {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .getAllIngredients()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> fillAutocompleteIngredientsData(response) },
                    { t -> Common.onFailure(requireContext(), t) })
        )
    }

    private fun fillAutocompleteIngredientsData(response: Ingredients) {
        response.meals?.forEach {
            if (!it.strIngredient.isNullOrEmpty())
                autocompleteIngredientData.add(it.strIngredient)
        }
    }

    private fun getAutocompleteMealsData(wholeWord: String) {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .searchMealByName(wholeWord)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> fillAutocompleteMealsData(response) },
                    { t -> Common.onFailure(requireContext(), t) })
        )
    }

    private fun fillAutocompleteMealsData(response: Meals) {
        autocompleteMealsData.clear()
        response.meals?.forEach {
            if (!it.strMeal.isNullOrEmpty())
                autocompleteMealsData.add(it.strMeal)
        }
        binding.searchText.setAdapter(AutocompleteArrayAdapter(autocompleteMealsData))
    }

    inner class AutocompleteArrayAdapter(private val autocompleteData: MutableList<String>) :
        ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            autocompleteData
        ) {
        override fun getCount(): Int {
            if (super.getCount() > proposedChoices)
                return proposedChoices
            return super.getCount()
        }
    }
}