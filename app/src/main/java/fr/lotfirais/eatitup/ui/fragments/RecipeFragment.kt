package fr.lotfirais.eatitup.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings.PluginState
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import fr.lotfirais.eatitup.R
import fr.lotfirais.eatitup.data.db.AppDAO
import fr.lotfirais.eatitup.data.models.Meal
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
    private var recipeName: String? = ""
    private var mealData: Meal? = Meal()

    private val appDAO by lazy {
        AppDAO(requireContext())
    }

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

        addButtonToActionBar(viewLifecycleOwner)

        return binding.root
    }

    private fun addButtonToActionBar(viewLifecycleOwner : LifecycleOwner) {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_favorite, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        mealData?.let{ meal ->
                            addFavorite(meal)
                            Common.onAddedToFavorites(requireContext())
                        }
                        true
                    }
                    R.id.action_share -> {
                        val sharingIntent = Intent(Intent.ACTION_SEND)

                        sharingIntent.type = "text/plain"
                        val shareBody = "$recipeName\n\nI found this recipe on the app EatItUp!\nDownload it now on Google Play Store"
                        val shareSubject = "EatItUp! recipe"

                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                        startActivity(Intent.createChooser(sharingIntent, "Share using"))
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun addFavorite(meal : Meal) {
        compositeDisposable.add(
            appDAO.insertFavoriteMeal(meal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ }, { t -> Common.onFailure(requireContext(), t) })
        )
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
            recipeName = meal.strMeal
            mealData = meal

            (binding.recyclerViewIngredients.adapter as? IngredientsAdapter)?.update(meal)
            binding.recipeName.text = meal.strMeal
            binding.categoryName.text = meal.strCategory
            binding.recipeInstructions.text = meal.strInstructions?.replace("\r","\n")
            meal.strMealThumb?.let{ thumb ->
                ImageDisplay.loadImageViaUrl(requireContext(),binding.recipeThumb, thumb, ImageDisplay.recipeImageOptions)
            }
        }
    }
}