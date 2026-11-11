package com.example.ads

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * مدير إعلانات Google AdMob متوافق 100% مع سياسات متجر Google Play.
 * 
 * القواعد الصارمة المطبقة:
 * 1. لا توجد أي إعلانات بينية (Interstitial) عند بدء تشغيل التطبيق أو تحميل الشاشات الأولية.
 * 2. الإعلان البيني يظهر فقط بعد إتمام المستخدم لإجراء مقصود (حساب غرامة أو تقسيم فاتورة).
 * 3. تم تفعيل مهلة أمان (Cooldown) بحد أدنى 3 دقائق بين أي إعلانين بينيين لمنع إزعاج المستخدم.
 * 4. يظهر البانر الإعلاني في الشاشة الرئيسية فقط.
 */
object AdMobManager {

    // معرفات اختبار رسمية من Google AdMob (Google Sample Test IDs)
    // استبدل هذه المعرفات بمعرفاتك الحقيقية من AdMob قبل النشر
    const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

    // مهلة الأمان بين الإعلانات البينية: 3 دقائق (180,000 مللي ثانية)
    private const val INTERSTITIAL_COOLDOWN_MS = 3 * 60 * 1000L
    private var lastInterstitialShowTime: Long = 0L

    private var isInitialized = false
    private var isInitializing = false
    private var interstitialAd: InterstitialAd? = null
    private var isAdLoading = false

    fun initialize(context: Context) {
        if (isInitialized || isInitializing) return
        isInitializing = true
        try {
            MobileAds.initialize(context) {
                isInitialized = true
                isInitializing = false
                Log.d("AdMobManager", "Google Mobile Ads SDK Initialized")
                // تحميل مسبق للإعلان البيني ليصبح جاهزاً للاستخدام لاحقاً
                preloadInterstitial(context)
            }
        } catch (e: Exception) {
            isInitializing = false
            Log.e("AdMobManager", "Error initializing MobileAds", e)
        }
    }

    fun isSdkInitialized(): Boolean = isInitialized

    private fun preloadInterstitial(context: Context) {
        if (interstitialAd != null || isAdLoading) return
        isAdLoading = true

        val adRequest = AdRequest.Builder().build()
        // استبدل هذه المعرفات بمعرفاتك الحقيقية من AdMob قبل النشر
        InterstitialAd.load(
            context,
            INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isAdLoading = false
                    Log.d("AdMobManager", "Preloaded Interstitial ad successfully")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    isAdLoading = false
                    Log.w("AdMobManager", "Failed to preload Interstitial ad: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * يُظهر إعلاناً بينياً بعد إتمام إجراء، شريطة مرور 3 دقائق كاملة منذ آخر إعلان بيني.
     */
    fun showInterstitialAfterAction(activity: Activity) {
        val now = SystemClock.elapsedRealtime()
        if (now - lastInterstitialShowTime < INTERSTITIAL_COOLDOWN_MS) {
            Log.d("AdMobManager", "Interstitial suppressed: Cooldown active (3 min)")
            return
        }

        val ad = interstitialAd
        if (ad != null && !activity.isFinishing && !activity.isDestroyed) {
            lastInterstitialShowTime = now
            ad.show(activity)
            interstitialAd = null
            // إعادة التحميل المسبق للمرة القادمة
            preloadInterstitial(activity.applicationContext)
        } else {
            // في حال عدم توفر الإعلان في الذاكرة، يتم طلبه لاحقاً
            preloadInterstitial(activity.applicationContext)
        }
    }
}

/**
 * بانر إعلاني خاص بالشاشة الرئيسية فقط مع فحص التهيئة والاتصال
 */
@Composable
fun HomeAdMobBanner(
    modifier: Modifier = Modifier
) {
    var isFailedToLoad by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .testTag("home_admob_banner"),
        contentAlignment = Alignment.Center
    ) {
        if (!AdMobManager.isSdkInitialized() || isFailedToLoad) {
            // عنصر بديل أنيق يوضح وجود مساحة الإعلان في بيئة الاختبار والمحاكي
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📢 مساحة إعلانية معتمدة من Google AdMob\n(استبدل هذه المعرفات بمعرفاتك الحقيقية من AdMob قبل النشر)",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        lineHeight = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                factory = { ctx ->
                    AdView(ctx).apply {
                        setAdSize(AdSize.BANNER)
                        // استبدل هذه المعرفات بمعرفاتك الحقيقية من AdMob قبل النشر
                        adUnitId = AdMobManager.BANNER_AD_UNIT_ID
                        adListener = object : AdListener() {
                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                isFailedToLoad = false
                            }

                            override fun onAdFailedToLoad(error: LoadAdError) {
                                super.onAdFailedToLoad(error)
                                isFailedToLoad = true
                            }
                        }
                        loadAd(AdRequest.Builder().build())
                    }
                }
            )
        }
    }
}
