package ru.kpfu.itis.fittrack

import ru.kpfu.itis.fittrack.data.Training

object WorkoutRepository {
    val workoutList = mutableListOf(
        Training(0, "Walking",
            180,
            "https://cdn-icons-png.flaticon.com/512/7246/7246628.png",
            "Aerobic"
        ),
        Training(1, "Jogging",
            670,
            "https://cdn-icons-png.flaticon.com/512/1599/1599758.png",
            "Aerobic"
        ),
        Training(2,
            "Slow swimming",
            300,
            "https://cdn-icons-png.flaticon.com/512/2784/2784502.png",
            "Aerobic"
        ),
        Training(3,
        "Fast swimming",
        650,
            "https://cdn-icons-png.flaticon.com/512/7246/7246742.png",
            "Aerobic"
        ),

        Training(4,
        "Cycling",
        596,
        "https://cdn-icons-png.flaticon.com/512/923/923794.png",
        "Aerobic"),

        Training(5,"Skipping rope",
            600,
            "https://cdn-icons-png.flaticon.com/512/7246/7246746.png",
            "Aerobic"
        ),
        Training(6, "Yoga",
        180,
            "https://cdn-icons-png.flaticon.com/512/1599/1599754.png",
            "Stretching"
            ),
        Training(7, "Pilates",
            280,
            "https://cdn-icons-png.flaticon.com/512/6381/6381912.png",
            "Stretching"
        ),
        Training(8,
        "Elliptical trainer",
        450,
        "https://cdn-icons-png.flaticon.com/512/7922/7922240.png",
            "Aerobic"
        ),
        Training(9,
        "Stepper",
        350,
        "https://cdn-icons-png.flaticon.com/512/3349/3349205.png",
            "Aerobic"
        ),
        Training(10,
        "Dancing",
        370,
        "https://cdn-icons-png.flaticon.com/512/6901/6901715.png",
            "Aerobic"
        ),
        Training(11,
        "HIIT",
        560,
        "https://cdn-icons-png.flaticon.com/512/7246/7246609.png",
        "Strength"
        ),
        Training(12,
        "Functional strength training",
        600,
            "https://cdn-icons-png.flaticon.com/512/7246/7246684.png",
            "Strength"
        ),
        Training(13,
        "ABS stretching",
        650,
        "https://cdn-icons-png.flaticon.com/512/7246/7246715.png",
            "Mixed"
        ),
        Training(14,
        "Kickboxing",
            500,
            "https://cdn-icons-png.flaticon.com/512/3080/3080940.png",
            "Mixed"
        )
    )

}