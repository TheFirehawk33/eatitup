package fr.lotfirais.eatitup.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import fr.lotfirais.eatitup.R
import fr.lotfirais.eatitup.data.models.Meal
import fr.lotfirais.eatitup.databinding.ItemResultBinding
import fr.lotfirais.eatitup.utils.ImageDisplay

class ResultsAdapter(
    private val context: Context
) : RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>() {

    private val items: ArrayList<Meal> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(ItemResultBinding.inflate(LayoutInflater.from(context), parent, false).root)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newMeals : List<Meal>) {
        items.clear()
        items.addAll(newMeals)
        notifyDataSetChanged()
    }

    inner class ResultsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemResultBinding = ItemResultBinding.bind(view)
        private var navController: NavController? = null
        private var bundle: Bundle = Bundle()

        init {
            binding.recipeItem.setOnClickListener{
                items[adapterPosition].idMeal?.let{
                    navController = Navigation.findNavController(view)
                    bundle.putString("recipeId", it)

                    navController!!.navigate(R.id.action_searchFragment_to_recipeFragment, bundle)
                    //SearchFragmentDirections.actionSearchFragmentToRecipeFragment(it)
                }
            }
        }

        fun bind(meal: Meal) {
            binding.run {
                mealName.text = meal.strMeal
                mealCategory.text = meal.strCategory
                recipeExtract.text = String.format(meal.strInstructions?.take(150) + "...")
                meal.strMealThumb?.let { ImageDisplay.loadImageViaUrl(
                    context,
                    mealThumb,
                    it,
                    ImageDisplay.thumbResultsOptions)
                }
            }
        }
    }
}