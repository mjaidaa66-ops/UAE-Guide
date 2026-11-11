package com.example.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.ads.HomeAdMobBanner
import com.example.domain.model.PaymentService
import com.example.domain.model.ServiceCategory
import com.example.ui.components.CategoryChipsRow
import com.example.ui.components.MandatoryDisclaimerCard
import com.example.ui.components.PortalConfirmBottomSheet
import com.example.ui.components.ServiceCardItem
import com.example.ui.components.launchOfficialBrowser
import com.example.ui.theme.UaeAlertRed
import com.example.ui.theme.UaeFlagGreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val favoriteServices by viewModel.favoriteServices.collectAsStateWithLifecycle()
    val recentServices by viewModel.recentServices.collectAsStateWithLifecycle()
    val filteredServices by viewModel.filteredServices.collectAsStateWithLifecycle()

    var selectedServiceForSheet by remember { mutableStateOf<PaymentService?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_container")
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Top Bar
            item {
                HomeHeader()
            }

            // Mandatory Disclaimer Card in every screen
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    MandatoryDisclaimerCard()
                }
            }

            // Search Bar
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = viewModel::onSearchQueryChanged,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_search_input"),
                        placeholder = {
                            Text(
                                text = "ابحث عن خدمة، بوابة، أو جهة رسمية...",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { viewModel.onSearchQueryChanged("") },
                                    modifier = Modifier.testTag("clear_search_btn")
                                ) {
                                    Icon(Icons.Default.Clear, contentDescription = "مسح")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                        )
                    )
                }
            }

            // Category Chips: الكل، مرور، كهرباء ومياه، اتصالات، حكومي، مواصلات، تعليم، صحة، بلدية
            item {
                CategoryChipsRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = viewModel::onCategorySelected
                )
            }

            // قسم "خدماتي المفضلة" (إذا وجدت)
            if (favoriteServices.isNotEmpty() && searchQuery.isBlank() && selectedCategory == ServiceCategory.ALL) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = UaeAlertRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "خدماتي المفضلة (${favoriteServices.size})",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        favoriteServices.forEach { service ->
                            ServiceCardItem(
                                service = service,
                                isFavorite = true,
                                onFavoriteToggle = { viewModel.toggleFavorite(service.id) },
                                onClick = { selectedServiceForSheet = service }
                            )
                        }
                    }
                }
            }

            // سجل آخر 5 خدمات مفتوحة (إذا وجدت)
            if (recentServices.isNotEmpty() && searchQuery.isBlank() && selectedCategory == ServiceCategory.ALL) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "آخر الخدمات المفتوحة",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        recentServices.forEach { service ->
                            ServiceCardItem(
                                service = service,
                                isFavorite = favoriteIds.contains(service.id),
                                onFavoriteToggle = { viewModel.toggleFavorite(service.id) },
                                onClick = { selectedServiceForSheet = service }
                            )
                        }
                    }
                }
            }

            // قائمة الخدمات الرئيسية
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "جميع الخدمات المتاحة (${filteredServices.size})",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            // Empty state
            if (filteredServices.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SearchOff, contentDescription = null, modifier = Modifier.size(30.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "لا توجد نتائج مطابقة لبحثك",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            } else {
                items(
                    items = filteredServices,
                    key = { it.id }
                ) { service ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ServiceCardItem(
                            service = service,
                            isFavorite = favoriteIds.contains(service.id),
                            onFavoriteToggle = { viewModel.toggleFavorite(service.id) },
                            onClick = { selectedServiceForSheet = service }
                        )
                    }
                }
            }
        }

        // Banner AdMob في الشاشة الرئيسية فقط كما هو مطلوب في السياسات
        HomeAdMobBanner()
    }

    // Confirmation Bottom Sheet upon clicking any service
    PortalConfirmBottomSheet(
        service = selectedServiceForSheet,
        onDismiss = { selectedServiceForSheet = null },
        onConfirmOpen = { service ->
            viewModel.recordOpenedService(service.id)
            selectedServiceForSheet = null
            launchOfficialBrowser(context, service.officialUrl, service.titleAr)
        }
    )
}

@Composable
private fun HomeHeader() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // UAE Flag Stripe
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.5.dp)
                    .clip(RoundedCornerShape(2.dp))
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxSize().background(UaeFlagGreen))
                Box(modifier = Modifier.weight(1f).fillMaxSize().background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)))
                Box(modifier = Modifier.weight(1f).fillMaxSize().background(UaeAlertRed))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "UAE Pay Guide",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = UaeFlagGreen,
                    letterSpacing = 0.5.sp
                )
            )
            Text(
                text = "دليل بوابات الدفع والخدمات الرسمية في دولة الإمارات",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.5.sp
                )
            )
        }
    }
}
