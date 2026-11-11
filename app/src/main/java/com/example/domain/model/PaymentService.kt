package com.example.domain.model

enum class ServiceCategory(val id: String, val titleAr: String) {
    ALL("all", "الكل"),
    TRAFFIC("traffic", "مرور"),
    UTILITIES("utilities", "كهرباء ومياه"),
    TELECOM("telecom", "اتصالات"),
    GOVERNMENT("government", "حكومي"),
    TRANSPORT("transport", "مواصلات"),
    EDUCATION("education", "تعليم"),
    HEALTH("health", "صحة"),
    MUNICIPALITY("municipality", "بلدية")
}

data class PaymentService(
    val id: String,
    val titleAr: String,
    val authorityAr: String,
    val category: ServiceCategory,
    val descriptionAr: String,
    val officialUrl: String,
    val emirate: String = "جميع الإمارات",
    val isOfficial: Boolean = true
)
