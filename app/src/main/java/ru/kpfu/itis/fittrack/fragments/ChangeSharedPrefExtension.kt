package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.*

fun Fragment.changeSharedPref(baseEntity: BaseEntity) {
    val sharedPref = activity?.getSharedPreferences(
        getString(R.string.preferenceFileKey_UserData),
        Context.MODE_PRIVATE
    )
    var eatenCalories = sharedPref?.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0)
    var eatenProteins = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f)
    var eatenCarbs = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_CARBS, 0f)
    var eatenFats = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_FATS, 0f)
    var burnedCalories = sharedPref?.getInt(ProductDescriptionFragment.BURNED_CALORIES,0)
    val editor = sharedPref?.edit()

    if (baseEntity is Food) {
        eatenCalories = eatenCalories?.plus(baseEntity.calories)
        eatenProteins = eatenProteins?.plus(baseEntity.proteins)
        eatenCarbs = eatenCarbs?.plus(baseEntity.carbohydrates)
        eatenFats = eatenFats?.plus(baseEntity.fats)
    }
    if (baseEntity is Dish) {
        eatenCalories = eatenCalories?.plus(baseEntity.calories)
        eatenProteins = eatenProteins?.plus(baseEntity.proteins)
        eatenCarbs = eatenCarbs?.plus(baseEntity.carbohydrates)
        eatenFats = eatenFats?.plus(baseEntity.fats)
    }
    if (baseEntity is Training) {
        burnedCalories = burnedCalories?.plus(baseEntity.calories)
    }
    with(editor!!) {
        putInt(ProductDescriptionFragment.EATEN_CALORIES, eatenCalories!!)?.apply()
        putFloat(ProductDescriptionFragment.EATEN_PROTEINS, eatenProteins!!)?.apply()
        putFloat(ProductDescriptionFragment.EATEN_CARBS, eatenCarbs!!)?.apply()
        putFloat(ProductDescriptionFragment.EATEN_FATS, eatenFats!!)?.apply()
        putInt(ProductDescriptionFragment.BURNED_CALORIES, burnedCalories!!)?.apply()
    }
}