package com.example.domain.model

enum class SplitMode(val titleAr: String) {
    EQUAL("بالتساوي"),
    CUSTOM_PERCENTAGE("مخصص (%)")
}

data class PersonEntry(
    val id: String,
    val name: String,
    val percentage: Double = 0.0
)

data class SplitPersonResult(
    val name: String,
    val shareAmount: Double,
    val percentage: Double
)

data class BillSplitResult(
    val baseAmount: Double,
    val tipOrFeeAmount: Double,
    val totalAmount: Double,
    val mode: SplitMode,
    val personResults: List<SplitPersonResult>
)
