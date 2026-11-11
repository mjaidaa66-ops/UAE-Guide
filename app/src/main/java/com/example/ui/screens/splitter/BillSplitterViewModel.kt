package com.example.ui.screens.splitter

import androidx.lifecycle.ViewModel
import com.example.domain.model.BillSplitResult
import com.example.domain.model.PersonEntry
import com.example.domain.model.SplitMode
import com.example.domain.model.SplitPersonResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class BillSplitterViewModel : ViewModel() {

    private val _billAmountInput = MutableStateFlow("")
    val billAmountInput: StateFlow<String> = _billAmountInput.asStateFlow()

    private val _splitMode = MutableStateFlow(SplitMode.EQUAL)
    val splitMode: StateFlow<SplitMode> = _splitMode.asStateFlow()

    private val _persons = MutableStateFlow(
        listOf(
            PersonEntry(id = UUID.randomUUID().toString(), name = "الشخص 1", percentage = 50.0),
            PersonEntry(id = UUID.randomUUID().toString(), name = "الشخص 2", percentage = 50.0)
        )
    )
    val persons: StateFlow<List<PersonEntry>> = _persons.asStateFlow()

    private val _tipPercent = MutableStateFlow(0.0)
    val tipPercent: StateFlow<Double> = _tipPercent.asStateFlow()

    private val _splitResult = MutableStateFlow<BillSplitResult?>(null)
    val splitResult: StateFlow<BillSplitResult?> = _splitResult.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onBillAmountChanged(input: String) {
        if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
            _billAmountInput.value = input
            _errorMessage.value = null
        }
    }

    fun onSplitModeSelected(mode: SplitMode) {
        _splitMode.value = mode
        _splitResult.value = null
        _errorMessage.value = null
        // Re-distribute percentages evenly if switching to custom
        if (mode == SplitMode.CUSTOM_PERCENTAGE) {
            val count = _persons.value.size
            val equalShare = if (count > 0) 100.0 / count else 0.0
            _persons.value = _persons.value.map { it.copy(percentage = equalShare) }
        }
    }

    fun onTipPercentSelected(tip: Double) {
        _tipPercent.value = tip
    }

    fun addPerson() {
        val nextIndex = _persons.value.size + 1
        val updated = _persons.value.toMutableList()
        updated.add(PersonEntry(id = UUID.randomUUID().toString(), name = "الشخص $nextIndex", percentage = 0.0))
        _persons.value = updated
        _splitResult.value = null
    }

    fun removePerson(id: String) {
        if (_persons.value.size <= 2) {
            _errorMessage.value = "يجب أن يتوفر شخصان على الأقل للتقسيم"
            return
        }
        _persons.value = _persons.value.filter { it.id != id }
        _splitResult.value = null
    }

    fun updatePersonName(id: String, newName: String) {
        _persons.value = _persons.value.map {
            if (it.id == id) it.copy(name = newName) else it
        }
    }

    fun updatePersonPercentage(id: String, percent: Double) {
        _persons.value = _persons.value.map {
            if (it.id == id) it.copy(percentage = percent) else it
        }
        _splitResult.value = null
    }

    fun calculate(): Boolean {
        val baseAmount = _billAmountInput.value.toDoubleOrNull()
        if (baseAmount == null || baseAmount <= 0.0) {
            _errorMessage.value = "يرجى إدخال مبلغ إجمالي صحيح أكبر من الصفر"
            _splitResult.value = null
            return false
        }

        val people = _persons.value
        if (people.isEmpty()) {
            _errorMessage.value = "يرجى إضافة أشخاص للتقسيم"
            _splitResult.value = null
            return false
        }

        val tipOrFeeAmount = (baseAmount * _tipPercent.value) / 100.0
        val totalAmount = baseAmount + tipOrFeeAmount

        val mode = _splitMode.value
        val perPersonList = mutableListOf<SplitPersonResult>()

        if (mode == SplitMode.EQUAL) {
            val share = totalAmount / people.size
            val sharePercent = 100.0 / people.size
            people.forEach { person ->
                perPersonList.add(
                    SplitPersonResult(
                        name = person.name.ifBlank { "شخص" },
                        shareAmount = share,
                        percentage = sharePercent
                    )
                )
            }
        } else {
            // Custom percentage validation
            val totalPercent = people.sumOf { it.percentage }
            if (kotlin.math.abs(totalPercent - 100.0) > 0.5) {
                _errorMessage.value = "مجموع النسب المخصصة حالياً (${totalPercent.toInt()}%) يجب أن يساوي 100%"
                _splitResult.value = null
                return false
            }

            people.forEach { person ->
                val share = (totalAmount * person.percentage) / 100.0
                perPersonList.add(
                    SplitPersonResult(
                        name = person.name.ifBlank { "شخص" },
                        shareAmount = share,
                        percentage = person.percentage
                    )
                )
            }
        }

        _errorMessage.value = null
        _splitResult.value = BillSplitResult(
            baseAmount = baseAmount,
            tipOrFeeAmount = tipOrFeeAmount,
            totalAmount = totalAmount,
            mode = mode,
            personResults = perPersonList
        )
        return true
    }
}
