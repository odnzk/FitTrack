package ru.kpfu.itis.fittrack.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import ru.kpfu.itis.fittrack.R
import ru.kpfu.itis.fittrack.data.BaseEntity
import ru.kpfu.itis.fittrack.data.Product
import ru.kpfu.itis.fittrack.data.Recipe

fun Fragment.changeSharedPref(baseEntity: BaseEntity){
    val sharedPref = activity?.getSharedPreferences(
        getString(R.string.preferenceFileKey_UserData),
        Context.MODE_PRIVATE
    )
    var eatenCalories = sharedPref?.getInt(ProductDescriptionFragment.EATEN_CALORIES, 0)
    var eatenProteins = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_PROTEINS, 0f)
    var eatenCarbs = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_CARBS, 0f)
    var eatenFats = sharedPref?.getFloat(ProductDescriptionFragment.EATEN_FATS, 0f)
    val editor = sharedPref?.edit()
    if (baseEntity is Product ) {
        eatenCalories = eatenCalories?.plus(baseEntity.calories)
        eatenProteins = eatenProteins?.plus(baseEntity.proteins)
        eatenCarbs = eatenCarbs?.plus(baseEntity.carbohydrates)
        eatenFats = eatenFats?.plus(baseEntity.fats)
    }
    if (baseEntity is Recipe ) {
        eatenCalories = eatenCalories?.plus(baseEntity.calories)
        eatenProteins = eatenProteins?.plus(baseEntity.proteins)
        eatenCarbs = eatenCarbs?.plus(baseEntity.carbohydrates)
        eatenFats = eatenFats?.plus(baseEntity.fats)
    }
    editor?.putInt(ProductDescriptionFragment.EATEN_CALORIES, eatenCalories!!)?.apply()
    editor?.putFloat(ProductDescriptionFragment.EATEN_PROTEINS, eatenProteins!!)?.apply()
    editor?.putFloat(ProductDescriptionFragment.EATEN_CARBS, eatenCarbs!!)?.apply()
    editor?.putFloat(ProductDescriptionFragment.EATEN_FATS, eatenFats!!)?.apply()
}