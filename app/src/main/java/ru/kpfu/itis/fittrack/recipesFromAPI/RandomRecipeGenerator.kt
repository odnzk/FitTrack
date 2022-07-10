package ru.kpfu.itis.fittrack.recipesFromAPI

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kpfu.itis.fittrack.data.Recipe
import ru.kpfu.itis.fittrack.recipesFromAPI.randomRecipe.RandomRecipe
import ru.kpfu.itis.fittrack.recipesFromAPI.recipeNutrition.RecipeNutrition
import java.net.URL


class RandomRecipeGenerator {
    companion object {
        private const val API_KEY = "3ab0e7dbfbdc4c38888f59f128cd58ab"
        const val RANDOM_RECIPE_URL = "https://api.spoonacular.com/recipes/random?apiKey=$API_KEY"
        const val RECIPE_INSTRUCTION_URL_FIRST_HALF = "https://api.spoonacular.com/recipes/"
        const val RECIPE_INSTRUCTION_URL_SECOND_HALF = "/analyzedInstructions?apiKey=$API_KEY"
        const val RECIPE_NUTRITION_URL_FIRST_HALF = "https://api.spoonacular.com/recipes/"
        const val RECIPE_NUTRITION_URL_SECOND_HALF = "/nutritionWidget.json?apiKey=$API_KEY"
        const val INSTRUCTION_REGEX = "\"step\":\"([&:!a-zA-Z\\s,.0-9'()-]+)"
    }

    fun getRandomRecipe(result: (Recipe) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val recipe = getRandomRecipe()
            val id = recipe.recipes?.get(0)?.id
            val title = recipe.recipes?.get(0)?.title
            val imageURL = recipe.recipes?.get(0)?.image
            val instruction = getRecipeInstructions(id)
            val nutrition = getRecipeNutrition(id)
            val calories = nutrition.calories?.filter { it.isDigit() }?.toInt()
            val proteins = nutrition.protein?.filter { it.isDigit() }?.toFloat()
            val fats = nutrition.fat?.filter { it.isDigit() }?.toFloat()
            val carbohydrates = nutrition.carbs?.filter { it.isDigit() }?.toFloat()
            Log.e("idAdded", id.toString())
            if (title != null && imageURL != null && calories != null && proteins != null && fats != null && carbohydrates != null) {
                result(
                    Recipe(
                        0,
                        title,
                        imageURL,
                        instruction,
                        calories,
                        proteins,
                        fats,
                        carbohydrates
                    )
                )
            }
        }
    }

    private fun getRandomRecipe(): RandomRecipe {
        val responseRandomRecipe = URL(RANDOM_RECIPE_URL).readText()
        return Gson().fromJson(responseRandomRecipe, RandomRecipe::class.java)
    }

    private fun getRecipeInstructions(id: Int?): String {
        val responseRecipeInstruction =
            URL(RECIPE_INSTRUCTION_URL_FIRST_HALF + id.toString() + RECIPE_INSTRUCTION_URL_SECOND_HALF).readText()
        var result = ""
        getRegexResult(responseRecipeInstruction, INSTRUCTION_REGEX).forEach { it ->
            it.groupValues[1].forEach {
                result += "$it "
            }
        }
        return result
    }

    private fun getRecipeNutrition(id: Int?): RecipeNutrition {
        val responseRandomRecipe =
            URL(RECIPE_NUTRITION_URL_FIRST_HALF + id.toString() + RECIPE_NUTRITION_URL_SECOND_HALF).readText()
        return Gson().fromJson(responseRandomRecipe, RecipeNutrition::class.java)
    }

    private fun getRegexResult(responseBody: String, pattern: String): Sequence<MatchResult> {
        return pattern.toRegex().findAll(responseBody)
    }

}
