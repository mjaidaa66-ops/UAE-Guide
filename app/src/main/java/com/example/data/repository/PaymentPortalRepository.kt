package com.example.data.repository

import com.example.domain.model.PaymentService
import com.example.domain.model.ServiceCategory

object PaymentPortalRepository {

    val allPortals: List<PaymentService> = listOf(
        // 1. مرور (Traffic)
        PaymentService(
            id = "traffic_rta_dubai",
            titleAr = "غرامات مرور دبي (RTA)",
            authorityAr = "هيئة الطرق والمواصلات - دبي",
            category = ServiceCategory.TRAFFIC,
            descriptionAr = "الاستعلام الفوري وسداد الغرامات المرورية ورسوم تجديد ملكية المركبات ورخص القيادة بدبي.",
            officialUrl = "https://www.rta.ae/wps/portal/rta/ae/home/rta-services/service-details?serviceId=3704250",
            emirate = "دبي"
        ),
        PaymentService(
            id = "traffic_dubai_police",
            titleAr = "مخالفات شرطة دبي",
            authorityAr = "القيادة العامة لشرطة دبي",
            category = ServiceCategory.TRAFFIC,
            descriptionAr = "البوابة الرسمية لشرطة دبي للاستعلام عن المخالفات وخصم النقاط المرورية والتقسيط.",
            officialUrl = "https://www.dubaipolice.gov.ae/wps/portal/home/services/individualservicescontent/trafficfinespayment",
            emirate = "دبي"
        ),
        PaymentService(
            id = "traffic_tamm_ad",
            titleAr = "غرامات مرور أبوظبي (تم)",
            authorityAr = "منظومة خدمات أبوظبي الحكومية - تم",
            category = ServiceCategory.TRAFFIC,
            descriptionAr = "دفع مخالفات القيادة العامة لشرطة أبوظبي والاستفادة من مبادرة التخفيض المبكر للمخالفات.",
            officialUrl = "https://www.tamm.abudhabi/ar-AE/traffic-fines",
            emirate = "أبوظبي"
        ),
        PaymentService(
            id = "traffic_moi_federal",
            titleAr = "المخالفات الاتحادية (وزارة الداخلية)",
            authorityAr = "وزارة الداخلية - دولة الإمارات",
            category = ServiceCategory.TRAFFIC,
            descriptionAr = "سداد المخالفات المرورية المسجلة عبر إمارات الشارقة وعجمان وأم القيوين ورأس الخيمة والفجيرة.",
            officialUrl = "https://moi.gov.ae/ar/eservices/eservice.294.aspx",
            emirate = "اتحادي"
        ),

        // 2. كهرباء ومياه (Utilities)
        PaymentService(
            id = "util_dewa_dubai",
            titleAr = "فاتورة كهرباء ومياه دبي (DEWA)",
            authorityAr = "هيئة كهرباء ومياه دبي",
            category = ServiceCategory.UTILITIES,
            descriptionAr = "خدمة السداد الفوري لفواتير الكهرباء والمياه السكنية والتجارية مع إمكانية التحقق برقم الحساب.",
            officialUrl = "https://www.dewa.gov.ae/ar/consumer/billing/bill-enquiry",
            emirate = "دبي"
        ),
        PaymentService(
            id = "util_addc_ad",
            titleAr = "فاتورة مياه وكهرباء أبوظبي (ADDC)",
            authorityAr = "شركة أبوظبي للتوزيع (طاقة)",
            category = ServiceCategory.UTILITIES,
            descriptionAr = "الدفع السريع لفواتير الماء والكهرباء السكنية والتجارية في إمارة أبوظبي.",
            officialUrl = "https://www.addc.ae/ar-AE/residential/pay-bill",
            emirate = "أبوظبي"
        ),
        PaymentService(
            id = "util_sewa_shj",
            titleAr = "فاتورة كهرباء ومياه الشارقة (SEWA)",
            authorityAr = "هيئة كهرباء ومياه وغاز الشارقة",
            category = ServiceCategory.UTILITIES,
            descriptionAr = "السداد الإلكتروني لفواتير الكهرباء والمياه والغاز الطبيعي في إمارة الشارقة.",
            officialUrl = "https://www.sewa.gov.ae/ar/paybill/",
            emirate = "الشارقة"
        ),
        PaymentService(
            id = "util_etihad_we",
            titleAr = "الاتحاد للماء والكهرباء (Etihad WE)",
            authorityAr = "شركة الاتحاد للماء والكهرباء",
            category = ServiceCategory.UTILITIES,
            descriptionAr = "خدمة الاستعلام والسداد لفواتير الكهرباء والمياه في عجمان، رأس الخيمة، الفجيرة، وأم القيوين.",
            officialUrl = "https://www.etihadwe.ae/ar/",
            emirate = "المناطق الشمالية"
        ),

        // 3. اتصالات (Telecom)
        PaymentService(
            id = "tel_etisalat_ae",
            titleAr = "فاتورة اتصالات (e& الإمارات)",
            authorityAr = "مجموعة إي آند (e&)",
            category = ServiceCategory.TELECOM,
            descriptionAr = "الدفع السريع لفواتير خطوط الفاتورة وباقات الإنترنت المنزلي وشحن رصيد واصل مسبق الدفع.",
            officialUrl = "https://www.etisalat.ae/b2c/quickpay.html",
            emirate = "جميع الإمارات"
        ),
        PaymentService(
            id = "tel_du_ae",
            titleAr = "فاتورة دو (du Quick Pay)",
            authorityAr = "شركة الإمارات للاتصالات المتكاملة (دو)",
            category = ServiceCategory.TELECOM,
            descriptionAr = "دفع فواتير الهاتف المتحرك وباقات الفايبر المنزلي وإعادة تعبئة خطوط الدفع المسبق فوراً.",
            officialUrl = "https://quickpay.du.ae/",
            emirate = "جميع الإمارات"
        ),
        PaymentService(
            id = "tel_virgin_ae",
            titleAr = "فيرجن موبايل (Virgin Mobile)",
            authorityAr = "فيرجن موبايل الإمارات",
            category = ServiceCategory.TELECOM,
            descriptionAr = "إدارة باقات الهواتف الذكية وتجديد الخطوط الرقمية داخل دولة الإمارات.",
            officialUrl = "https://www.virginmobile.ae/ar-ae/",
            emirate = "جميع الإمارات"
        ),

        // 4. حكومي (Government)
        PaymentService(
            id = "gov_uae_pass",
            titleAr = "الهوية الرقمية الموحدة (UAE PASS)",
            authorityAr = "حكومة الإمارات الرقمية",
            category = ServiceCategory.GOVERNMENT,
            descriptionAr = "منصة الوصول الرقمي والتوقيع المعتمد لكافة بوابات الدفع والمعاملات الحكومية.",
            officialUrl = "https://selfcare.uaepass.ae/",
            emirate = "اتحادي"
        ),
        PaymentService(
            id = "gov_icp_identity",
            titleAr = "رسوم الإقامة والهوية (ICP)",
            authorityAr = "الهيئة الاتحادية للهوية والجنسية والجمارك وأمن المنافذ",
            category = ServiceCategory.GOVERNMENT,
            descriptionAr = "تجديد بطاقات الهوية الإماراتية، أذونات الدخول، تأشيرات الإقامة، وسداد غرامات التأخير.",
            officialUrl = "https://smartservices.icp.gov.ae/",
            emirate = "اتحادي"
        ),
        PaymentService(
            id = "gov_moi_general",
            titleAr = "خدمات ورسوم وزارة الداخلية (MOI)",
            authorityAr = "وزارة الداخلية - دولة الإمارات",
            category = ServiceCategory.GOVERNMENT,
            descriptionAr = "إصدار شهادات بحث الحالة الجنائية (حسن سيرة وسلوك)، تصاريح السلاح، وتراخيص المنشآت.",
            officialUrl = "https://moi.gov.ae/",
            emirate = "اتحادي"
        ),
        PaymentService(
            id = "gov_invest_dubai",
            titleAr = "تجديد الرخصة التجارية (دبي)",
            authorityAr = "دائرة الاقتصاد والسياحة - استثمر في دبي",
            category = ServiceCategory.GOVERNMENT,
            descriptionAr = "التجديد الفوري للرخص التجارية، سداد رسوم الغرفة التجارية وتعديل الأنشطة الاقتصادية.",
            officialUrl = "https://invest.dubai.ae/",
            emirate = "دبي"
        ),
        PaymentService(
            id = "gov_tamm_business_ad",
            titleAr = "تراخيص الأعمال (تم أبوظبي)",
            authorityAr = "دائرة التنمية الاقتصادية - أبوظبي",
            category = ServiceCategory.GOVERNMENT,
            descriptionAr = "تجديد وسداد رسوم الرخص التجارية والمهنية والصناعية للشركات في إمارة أبوظبي.",
            officialUrl = "https://www.tamm.abudhabi/ar-AE/economic-licenses",
            emirate = "أبوظبي"
        ),
        PaymentService(
            id = "gov_moec_uae",
            titleAr = "وزارة الاقتصاد (خدمات الشركات)",
            authorityAr = "وزارة الاقتصاد - دولة الإمارات",
            category = ServiceCategory.GOVERNMENT,
            descriptionAr = "تسجيل العلامات التجارية، الوكالات التجارية، ومصنفات الملكية الفكرية وسداد رسومها.",
            officialUrl = "https://www.moec.gov.ae/ar/services",
            emirate = "اتحادي"
        ),

        // 5. مواصلات (Transport)
        PaymentService(
            id = "trans_salik_dubai",
            titleAr = "شحن رصيد سالك (Salik)",
            authorityAr = "شركة سالك ش.م.ع / RTA دبي",
            category = ServiceCategory.TRANSPORT,
            descriptionAr = "شحن حساب سالك للتعرفة المرورية في دبي بالبطاقات البنكية أو بطاقات إعادة التعبئة.",
            officialUrl = "https://www.salik.ae/ar/pay-and-recharge/recharge",
            emirate = "دبي"
        ),
        PaymentService(
            id = "trans_nol_dubai",
            titleAr = "تعبئة بطاقة نول (Nol Top-Up)",
            authorityAr = "هيئة الطرق والمواصلات - دبي",
            category = ServiceCategory.TRANSPORT,
            descriptionAr = "تعبئة رصيد بطاقات نول الفضية والذهبية للمترو والترام والحافلات والمواقف بدبي.",
            officialUrl = "https://www.rta.ae/wps/portal/rta/ae/public-transport/nol/topup",
            emirate = "دبي"
        ),
        PaymentService(
            id = "trans_darb_ad",
            titleAr = "درب التعرفة المرورية (Darb)",
            authorityAr = "مركز النقل المتكامل - أبوظبي",
            category = ServiceCategory.TRANSPORT,
            descriptionAr = "إدارة المركبات وسداد وشحن رصيد بوابات التعرفة المرورية لدرب في إمارة أبوظبي.",
            officialUrl = "https://darb.itc.gov.ae/",
            emirate = "أبوظبي"
        ),
        PaymentService(
            id = "trans_mawaqif_ad",
            titleAr = "مواقف أبوظبي (Mawaqif)",
            authorityAr = "مركز النقل المتكامل - أبوظبي",
            category = ServiceCategory.TRANSPORT,
            descriptionAr = "حجز تذاكر المواقف السطحية العامة وسداد الاشتراكات السكنية والمخالفات في أبوظبي.",
            officialUrl = "https://darb.itc.gov.ae/",
            emirate = "أبوظبي"
        ),
        PaymentService(
            id = "trans_parkin_dubai",
            titleAr = "مواقف دبي (Parkin)",
            authorityAr = "شركة باركن ش.م.ع - دبي",
            category = ServiceCategory.TRANSPORT,
            descriptionAr = "دفع تذاكر المواقف العامة في دبي وتجديد البطاقات الموسمية وسداد تصاريح المواقف.",
            officialUrl = "https://www.parkin.ae/",
            emirate = "دبي"
        ),

        // 6. تعليم (Education)
        PaymentService(
            id = "edu_moe_federal",
            titleAr = "رسوم وزارة التربية والتعليم (MOE)",
            authorityAr = "وزارة التربية والتعليم - دولة الإمارات",
            category = ServiceCategory.EDUCATION,
            descriptionAr = "سداد رسوم معادلة المؤهلات الدراسية والشهادات والامتحانات الوطنية المعتمدة.",
            officialUrl = "https://www.moe.gov.ae/ar/eservices/",
            emirate = "اتحادي"
        ),
        PaymentService(
            id = "edu_uaeu_portal",
            titleAr = "رسوم جامعة الإمارات (UAEU)",
            authorityAr = "جامعة الإمارات العربية المتحدة",
            category = ServiceCategory.EDUCATION,
            descriptionAr = "سداد الأقساط الجامعية والسكن الطلابي ورسوم التسجيل في جامعة المستقبل الوطنية.",
            officialUrl = "https://www.uaeu.ac.ae/ar/enrollment/fees.shtml",
            emirate = "أبوظبي"
        ),
        PaymentService(
            id = "edu_khda_dubai",
            titleAr = "هيئة المعرفة والتنمية (KHDA)",
            authorityAr = "هيئة المعرفة والتنمية البشرية - دبي",
            category = ServiceCategory.EDUCATION,
            descriptionAr = "تصديق الشهادات المدرسية والجامعية ورسوم التراخيص التعليمية في إمارة دبي.",
            officialUrl = "https://www.khda.gov.ae/",
            emirate = "دبي"
        ),

        // 7. صحة (Health)
        PaymentService(
            id = "health_daman_ae",
            titleAr = "التأمين الصحي (ضمان / Daman)",
            authorityAr = "الشركة الوطنية للضمان الصحي (ضمان)",
            category = ServiceCategory.HEALTH,
            descriptionAr = "تجديد وثائق التأمين الصحي للأفراد والعائلات والمؤسسات وسداد الاشتراكات المعتمدة.",
            officialUrl = "https://www.damanhealth.ae/",
            emirate = "جميع الإمارات"
        ),
        PaymentService(
            id = "health_dha_dubai",
            titleAr = "هيئة الصحة بدبي (DHA)",
            authorityAr = "هيئة الصحة بدبي",
            category = ServiceCategory.HEALTH,
            descriptionAr = "سداد رسوم اللياقة الطبية للإقامة (الفحص الطبي) ورخص المهن الصحية والمنشآت بدبي.",
            officialUrl = "https://www.dha.gov.ae/",
            emirate = "دبي"
        ),
        PaymentService(
            id = "health_ehs_federal",
            titleAr = "مؤسسة الإمارات للخدمات الصحية (EHS)",
            authorityAr = "مؤسسة الإمارات للخدمات الصحية",
            category = ServiceCategory.HEALTH,
            descriptionAr = "سداد رسوم الفحوصات الطبية والعلاجية في المراكز والمستشفيات الاتحادية بالدولة.",
            officialUrl = "https://www.ehs.gov.ae/ar/services",
            emirate = "اتحادي"
        ),

        // 8. بلدية (Municipality)
        PaymentService(
            id = "mun_dubai_municipality",
            titleAr = "بلدية دبي (Dubai Municipality)",
            authorityAr = "بلدية دبي",
            category = ServiceCategory.MUNICIPALITY,
            descriptionAr = "سداد رسوم الإسكان ورسوم تصديق عقود الإيجار (إيجاري) والتراخيص الهندسية.",
            officialUrl = "https://www.dm.gov.ae/",
            emirate = "دبي"
        ),
        PaymentService(
            id = "mun_tamm_ad_municipality",
            titleAr = "بلديات أبوظبي (تم)",
            authorityAr = "دائرة البلديات والنقل - أبوظبي",
            category = ServiceCategory.MUNICIPALITY,
            descriptionAr = "سداد رسوم عقود توثيق الإيجارية وشهادات الأملاك والتراخيص البلدية في أبوظبي.",
            officialUrl = "https://www.tamm.abudhabi/ar-AE/municipalities",
            emirate = "أبوظبي"
        ),
        PaymentService(
            id = "mun_shj_municipality",
            titleAr = "بلدية الشارقة (Sharjah Municipality)",
            authorityAr = "بلدية مدينة الشارقة",
            category = ServiceCategory.MUNICIPALITY,
            descriptionAr = "تصديق عقود الإيجار السكنية والتجارية وسداد رسوم الرخص والخدمات البلدية بالشارقة.",
            officialUrl = "https://shjmun.gov.ae/",
            emirate = "الشارقة"
        )
    )

    fun getById(id: String): PaymentService? = allPortals.find { it.id == id }
}
