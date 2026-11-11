package com.example.domain.model

enum class FineCategoryType(
    val titleAr: String,
    val descriptionAr: String,
    val baseFee: Double,
    val dailyFee: Double,
    val authorityName: String,
    val portalUrl: String
) {
    COMMERCIAL_LICENSE_DELAY(
        titleAr = "تأخير تجديد رخصة تجارية",
        descriptionAr = "تأخير تجديد السجل التجاري لدى الدوائر الاقتصادية",
        baseFee = 200.0,
        dailyFee = 8.0,
        authorityName = "دوائر التنمية الاقتصادية",
        portalUrl = "https://invest.dubai.ae/"
    ),
    VEHICLE_REGISTRATION_DELAY(
        titleAr = "تأخير تجديد ملكية مركبة",
        descriptionAr = "تأخير تجديد ترخيص المركبة بعد انتهاء فترة السماح",
        baseFee = 25.0,
        dailyFee = 1.5,
        authorityName = "هيئة الطرق والمواصلات / شرطة أبوظبي",
        portalUrl = "https://www.rta.ae/wps/portal/rta/ae/home/rta-services/service-details?serviceId=3704250"
    ),
    PARKING_VIOLATION_DELAY(
        titleAr = "تأخير سداد مخالفة مواقف",
        descriptionAr = "غرامة تأخير سداد تذكرة المواقف العامة بعد المهلة القانونية",
        baseFee = 150.0,
        dailyFee = 5.0,
        authorityName = "باركن دبي / مواقف أبوظبي",
        portalUrl = "https://www.parkin.ae/"
    ),
    RESIDENCY_OVERSTAY(
        titleAr = "تأخير تجديد تأشيرة الإقامة",
        descriptionAr = "البقاء في الدولة بعد انتهاء صلاحية التأشيرة والمهلة",
        baseFee = 0.0,
        dailyFee = 50.0,
        authorityName = "الهيئة الاتحادية للهوية والجنسية (ICP)",
        portalUrl = "https://smartservices.icp.gov.ae/"
    )
}

data class FineCalculationBreakdown(
    val fineType: FineCategoryType,
    val daysDelayed: Int,
    val baseFee: Double,
    val dailyFeeTotal: Double,
    val totalAmount: Double
)
