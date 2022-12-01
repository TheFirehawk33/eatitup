package fr.lotfirais.eatitup.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.lotfirais.eatitup.data.models.IngredientsHolder
import fr.lotfirais.eatitup.data.models.Meal
import fr.lotfirais.eatitup.databinding.ItemIngredientBinding
import fr.lotfirais.eatitup.utils.Common

class IngredientsAdapter(
    private val context: Context
) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredients: ArrayList<IngredientsHolder> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(ItemIngredientBinding.inflate(LayoutInflater.from(context), parent, false).root)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newMeal : Meal) {
        ingredients = Common.ingredientsToList(newMeal)

        notifyDataSetChanged()
    }

    inner class IngredientsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemIngredientBinding = ItemIngredientBinding.bind(view)

        fun bind(ingredients: IngredientsHolder) {
            binding.run{
                ingredient.text = ingredients.strIngredient
                measure.text = ingredients.strMeasure
            }
        }
    }
}