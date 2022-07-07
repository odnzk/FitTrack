package ru.kpfu.itis.fittrack

import ru.kpfu.itis.fittrack.data.Workout

object WorkoutRepository {
    val workoutList = arrayListOf<Workout>(
        Workout(0, "Walking",
            180,
            "https://cdn-icons-png.flaticon.com/512/7246/7246628.png",
            "Aerobic capacity"
        ),
        Workout(0, "Jogging",
            670,
            "https://cdn-icons-png.flaticon.com/512/1599/1599758.png",
            "Aerobic capacity"
        ),
        Workout(0,
            "Slow swimming",
            300,
            "https://cdn-icons-png.flaticon.com/512/2784/2784502.png",
            "Aerobic capacity"
        ),
        Workout(0,
        "Fast swimming",
        650,
            "https://cdn-icons-png.flaticon.com/512/7246/7246742.png",
            "Aerobic capacity"
        ),

        Workout(0,
        "Cycling",
        596,
        "https://cdn-icons-png.flaticon.com/512/923/923794.png",
        "Aerobic capacity"),

        Workout(0,"Skipping rope",
            600,
            "https://cdn-icons-png.flaticon.com/512/7246/7246746.png",
            "Aerobic capacity"
        ),
        Workout(0, "Yoga",
        180,
            "https://cdn-icons-png.flaticon.com/512/1599/1599754.png",
            "Stretching"
            ),
        Workout(0, "Pilates",
            280,
            "https://cdn-icons-png.flaticon.com/512/6381/6381912.png",
            "Stretching"
        ),
        Workout(0,
        "Elliptical trainer",
        450,
        "https://cdn-icons-png.flaticon.com/512/7922/7922240.png",
            "Aerobic capacity"
        ),
        Workout(0,
        "Stepper",
        350,
        "https://cdn-icons-png.flaticon.com/512/3349/3349205.png",
            "Aerobic capacity"),
        Workout(0,
        "Dancing",
        370,
        "https://cdn-icons-png.flaticon.com/512/6901/6901715.png",
            "Aerobic capacity"),
        Workout(0,
        "HIIT",
        560,
        "https://cdn-icons-png.flaticon.com/512/7246/7246609.png",
        "Strength"),
        Workout(0,
        "Functional strength training",
        600,
            "https://cdn-icons-png.flaticon.com/512/7246/7246684.png",
            "Strength",
        ),
        Workout(0,
        "ABS stretching",
        650,
        "https://cdn-icons-png.flaticon.com/512/7246/7246715.png",
            "Mixed"
        ),
        Workout(0,
        "Kickboxing",
            500,
            "https://cdn-icons-png.flaticon.com/512/3080/3080940.png",
            "Mixed"
        )
    )

}