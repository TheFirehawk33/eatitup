package fr.lotfirais.eatitup.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    inner class ResultsViewHolder(noteView: View) : RecyclerView.ViewHolder(noteView) {
        private val binding: ItemResultBinding = ItemResultBinding.bind(noteView)

        fun bind(meal: Meal) {
            binding.run {
                mealName.text = meal.strMeal
                mealCategory.text = meal.strCategory
                recipeExtract.text = String.format(meal.strInstructions?.take(150) + "...")
                meal.strMealThumb?.let { ImageDisplay.loadImageViaUrl(
                    context,
                    mealThumb,
                    "$it/preview",
                    ImageDisplay.thumbResultsOptions)
                }
            }
        }
    }
}