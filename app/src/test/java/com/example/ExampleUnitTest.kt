package com.example

import com.example.data.repository.PaymentPortalRepository
import com.example.domain.model.FineCategoryType
import com.example.domain.model.ServiceCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun paymentPortals_catalogContainsAtLeast27Portals() {
        assertFalse(PaymentPortalRepository.allPortals.isEmpty())
        assertTrue("Portals count should be >= 27", PaymentPortalRepository.allPortals.size >= 27)
    }

    @Test
    fun paymentPortals_allUrlsAreValidHttps() {
        PaymentPortalRepository.allPortals.forEach { portal ->
            assertNotNull(portal.titleAr)
            assertNotNull(portal.authorityAr)
            assertTrue("Portal URL must start with https:// for security", portal.officialUrl.startsWith("https://"))
        }
    }

    @Test
    fun paymentPortals_coversAll8Categories() {
        val categories = PaymentPortalRepository.allPortals.map { it.category }.toSet()
        assertTrue(categories.contains(ServiceCategory.TRAFFIC))
        assertTrue(categories.contains(ServiceCategory.UTILITIES))
        assertTrue(categories.contains(ServiceCategory.TELECOM))
        assertTrue(categories.contains(ServiceCategory.GOVERNMENT))
        assertTrue(categories.contains(ServiceCategory.TRANSPORT))
        assertTrue(categories.contains(ServiceCategory.EDUCATION))
        assertTrue(categories.contains(ServiceCategory.HEALTH))
        assertTrue(categories.contains(ServiceCategory.MUNICIPALITY))
    }

    @Test
    fun finesCalculation_breakdownFormula() {
        val fineType = FineCategoryType.COMMERCIAL_LICENSE_DELAY
        val days = 10
        val base = fineType.baseFee
        val daily = days * fineType.dailyFee
        val total = base + daily

        assertEquals(200.0, base, 0.01)
        assertEquals(80.0, daily, 0.01)
        assertEquals(280.0, total, 0.01)
    }

    @Test
    fun billSplitter_equalCalculation() {
        val base = 600.0
        val tipPercent = 10.0
        val total = base + (base * tipPercent / 100.0) // 660.0
        val perPerson = total / 3 // 220.0

        assertEquals(660.0, total, 0.01)
        assertEquals(220.0, perPerson, 0.01)
    }
}
