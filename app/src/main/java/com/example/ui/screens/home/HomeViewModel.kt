package com.example.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.preferences.UserPreferencesRepository
import com.example.data.repository.PaymentPortalRepository
import com.example.domain.model.PaymentService
import com.example.domain.model.ServiceCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesRepository = UserPreferencesRepository(application)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ServiceCategory.ALL)
    val selectedCategory: StateFlow<ServiceCategory> = _selectedCategory.asStateFlow()

    val favoriteIds: StateFlow<Set<String>> = preferencesRepository.favoriteServiceIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val recentIds: StateFlow<List<String>> = preferencesRepository.recentServiceIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteServices: StateFlow<List<PaymentService>> = favoriteIds.combine(MutableStateFlow(PaymentPortalRepository.allPortals)) { favs, all ->
        all.filter { favs.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentServices: StateFlow<List<PaymentService>> = recentIds.combine(MutableStateFlow(PaymentPortalRepository.allPortals)) { recents, all ->
        recents.mapNotNull { id -> all.find { it.id == id } }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredServices: StateFlow<List<PaymentService>> = combine(
        _searchQuery,
        _selectedCategory
    ) { query, category ->
        PaymentPortalRepository.allPortals.filter { service ->
            val matchesCategory = category == ServiceCategory.ALL || service.category == category
            val matchesQuery = query.isBlank() ||
                service.titleAr.contains(query.trim(), ignoreCase = true) ||
                service.authorityAr.contains(query.trim(), ignoreCase = true) ||
                service.descriptionAr.contains(query.trim(), ignoreCase = true) ||
                service.emirate.contains(query.trim(), ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaymentPortalRepository.allPortals)

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategorySelected(category: ServiceCategory) {
        _selectedCategory.value = category
    }

    fun toggleFavorite(serviceId: String) {
        viewModelScope.launch {
            preferencesRepository.toggleFavorite(serviceId)
        }
    }

    fun recordOpenedService(serviceId: String) {
        viewModelScope.launch {
            preferencesRepository.recordRecentService(serviceId)
        }
    }
}
