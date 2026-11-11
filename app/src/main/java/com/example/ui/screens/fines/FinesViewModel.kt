package com.example.ui.screens.fines

import androidx.lifecycle.ViewModel
import com.example.domain.model.FineCalculationBreakdown
import com.example.domain.model.FineCategoryType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FinesViewModel : ViewModel() {

    private val _selectedFineType = MutableStateFlow(FineCategoryType.COMMERCIAL_LICENSE_DELAY)
    val selectedFineType: StateFlow<FineCategoryType> = _selectedFineType.asStateFlow()

    private val _daysDelayedInput = MutableStateFlow("")
    val daysDelayedInput: StateFlow<String> = _daysDelayedInput.asStateFlow()

    private val _calculationBreakdown = MutableStateFlow<FineCalculationBreakdown?>(null)
    val calculationBreakdown: StateFlow<FineCalculationBreakdown?> = _calculationBreakdown.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onFineTypeSelected(type: FineCategoryType) {
        _selectedFineType.value = type
        _calculationBreakdown.value = null
        _errorMessage.value = null
    }

    fun onDaysInputChanged(input: String) {
        if (input.all { it.isDigit() }) {
            _daysDelayedInput.value = input
            _errorMessage.value = null
        }
    }

    fun calculate(): Boolean {
        val days = _daysDelayedInput.value.toIntOrNull()
        if (days == null || days <= 0) {
            _errorMessage.value = "يرجى إدخال عدد أيام تأخير صحيح أكبر من الصفر"
            _calculationBreakdown.value = null
            return false
        }

        val type = _selectedFineType.value
        val base = type.baseFee
        val dailyTotal = days * type.dailyFee
        val total = base + dailyTotal

        _errorMessage.value = null
        _calculationBreakdown.value = FineCalculationBreakdown(
            fineType = type,
            daysDelayed = days,
            baseFee = base,
            dailyFeeTotal = dailyTotal,
            totalAmount = total
        )
        return true
    }

    fun reset() {
        _daysDelayedInput.value = ""
        _calculationBreakdown.value = null
        _errorMessage.value = null
    }
}
