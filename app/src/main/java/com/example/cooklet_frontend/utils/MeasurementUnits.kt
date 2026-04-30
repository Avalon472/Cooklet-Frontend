package com.example.cooklet_frontend.utils

import com.example.cooklet_frontend.models.*

fun convertToMeasures(amount: Double, unit: String): newRecipeMeasures {

    val (us, metric) = when (unit) {

        "cup" -> Pair(
            newRecipeMeasurement(amount, "cup"),
            newRecipeMeasurement(amount * 240.0, "ml")
        )

        "tbsp" -> Pair(
            newRecipeMeasurement(amount, "tbsp"),
            newRecipeMeasurement(amount * 15.0, "ml")
        )

        "tsp" -> Pair(
            newRecipeMeasurement(amount, "tsp"),
            newRecipeMeasurement(amount * 5.0, "ml")
        )

        "lb" -> Pair(
            newRecipeMeasurement(amount, "lb"),
            newRecipeMeasurement(amount * 453.592, "g")
        )

        "oz" -> Pair(
            newRecipeMeasurement(amount, "oz"),
            newRecipeMeasurement(amount * 28.35, "g")
        )

        "g" -> Pair(
            newRecipeMeasurement(amount / 28.35, "oz"),
            newRecipeMeasurement(amount, "g")
        )

        "kg" -> Pair(
            newRecipeMeasurement(amount * 2.205, "lb"),
            newRecipeMeasurement(amount, "kg")
        )

        "ml" -> Pair(
            newRecipeMeasurement(amount / 240.0, "cup"),
            newRecipeMeasurement(amount, "ml")
        )

        "L" -> Pair(
            newRecipeMeasurement(amount * 4.227, "cup"),
            newRecipeMeasurement(amount, "L")
        )

        else -> Pair(
            newRecipeMeasurement(amount, unit),
            newRecipeMeasurement(amount, unit)
        )
    }

    return newRecipeMeasures(
        us = normalizeUS(us.amount, us.unit),
        metric = normalizeMetric(metric.amount, metric.unit)
    )
}

fun normalizeMetric(amount: Double, unit: String): newRecipeMeasurement {
    return when (unit) {
        "g" -> {
            if (amount >= 1000) {
                newRecipeMeasurement(amount / 1000.0, "kg")
            } else {
                newRecipeMeasurement(amount, "g")
            }
        }

        "ml" -> {
            if (amount >= 1000) {
                newRecipeMeasurement(amount / 1000.0, "L")
            } else {
                newRecipeMeasurement(amount, "ml")
            }
        }

        else -> newRecipeMeasurement(amount, unit)
    }
}

fun normalizeUS(amount: Double, unit: String): newRecipeMeasurement {
    return when (unit) {
        "oz" -> {
            if (amount >= 16) {
                newRecipeMeasurement(amount / 16.0, "lb")
            } else {
                newRecipeMeasurement(amount, "oz")
            }
        }

        else -> newRecipeMeasurement(amount, unit)
    }
}

fun round(amount: Double): Double {
    return String.format("%.2f", amount).toDouble()
}