package fr.lotfirais.eatitup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.lotfirais.eatitup.data.db.AppDAO
import fr.lotfirais.eatitup.data.models.Meal
import fr.lotfirais.eatitup.databinding.FragmentFavoriteBinding
import fr.lotfirais.eatitup.ui.adapters.FavoriteListAdapter
import fr.lotfirais.eatitup.utils.Common
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val compositeDisposable = CompositeDisposable()
    private val appDAO by lazy {
        AppDAO(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.recyclerViewFavorites.adapter = FavoriteListAdapter(requireContext()) { meal ->
            meal.idMeal?.let{
                appDAO.deleteFavoriteMealById(it.toInt())
            }
        }

        loadFavorites()


        return binding.root
    }

    private fun loadFavorites() {
        compositeDisposable.add(
            appDAO.getAllFavoriteMeals()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError{ t ->
                    Common.onFailure(requireContext(), t)
                }
                .doOnSuccess{
                    response -> onResponse(response)
                }.subscribe()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
    }

    private fun onResponse(response: ArrayList<Meal>) {
        println("ici")
        (binding.recyclerViewFavorites.adapter as? FavoriteListAdapter)?.update(response)
    }
}