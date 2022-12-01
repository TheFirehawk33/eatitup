package fr.lotfirais.eatitup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.lotfirais.eatitup.data.models.Meals
import fr.lotfirais.eatitup.data.network.ServiceBuilder
import fr.lotfirais.eatitup.databinding.FragmentRecipeBinding
import fr.lotfirais.eatitup.ui.adapters.IngredientsAdapter
import fr.lotfirais.eatitup.utils.Common
import fr.lotfirais.eatitup.utils.ImageDisplay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private val compositeDisposable = CompositeDisposable()

    private var recipeId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            recipeId = it.getString("recipeId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)

        binding.recyclerViewIngredients.adapter = IngredientsAdapter(requireContext())

        recipeId?.let{
            mealRequest(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun mealRequest(id: String) {
        compositeDisposable.add(
            ServiceBuilder.buildService()
                .getMealById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> onMealResponse(response)}, {t -> Common.onFailure(requireContext(), t) }))
    }

    private fun onMealResponse(response: Meals) {
        response.meals?.first()?.let { meal ->
            (binding.recyclerViewIngredients.adapter as? IngredientsAdapter)?.update(meal)
            binding.recipeName.text = meal.strMeal
            binding.recipeInstructions.text = meal.strInstructions?.replace("\r","\n")
            meal.strMealThumb?.let{ thumb ->
                ImageDisplay.loadImageViaUrl(requireContext(),binding.recipeThumb, thumb, ImageDisplay.recipeImageOptions)
            }
        }
    }
}