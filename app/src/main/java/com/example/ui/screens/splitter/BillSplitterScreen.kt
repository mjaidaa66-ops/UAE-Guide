package com.example.ui.screens.splitter

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ads.AdMobManager
import com.example.domain.model.SplitMode
import com.example.ui.components.MandatoryDisclaimerCard
import com.example.ui.theme.UaeAlertRed
import com.example.ui.theme.UaeFlagGreen
import java.text.DecimalFormat

@Composable
fun BillSplitterScreen(
    viewModel: BillSplitterViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val billAmountInput by viewModel.billAmountInput.collectAsStateWithLifecycle()
    val splitMode by viewModel.splitMode.collectAsStateWithLifecycle()
    val persons by viewModel.persons.collectAsStateWithLifecycle()
    val tipPercent by viewModel.tipPercent.collectAsStateWithLifecycle()
    val splitResult by viewModel.splitResult.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    val decimalFormat = DecimalFormat("#,##0.00")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("bill_splitter_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mandatory Disclaimer Card in every screen
        item {
            MandatoryDisclaimerCard()
        }

        // Header Title Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(UaeFlagGreen.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = UaeFlagGreen,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "أداة تقسيم الفواتير الذكية",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        )
                        Text(
                            text = "تقسيم فواتير الكهرباء أو الخدمات أو المطاعم بالتساوي أو بنسب مخصصة",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        // Form Inputs Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Bill Amount Input
                    Text(
                        text = "إجمالي الفاتورة (درهم إماراتي)",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )

                    OutlinedTextField(
                        value = billAmountInput,
                        onValueChange = viewModel::onBillAmountChanged,
                        placeholder = { Text("أدخل المبلغ (مثال: 450.00)") },
                        trailingIcon = {
                            Text(
                                text = "د.إ",
                                fontWeight = FontWeight.Bold,
                                color = UaeFlagGreen,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("bill_amount_field"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        )
                    )

                    // Mode Selection Tabs: بالتساوي vs مخصص
                    Text(
                        text = "طريقة تقسيم الفاتورة",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )

                    TabRow(
                        selectedTabIndex = if (splitMode == SplitMode.EQUAL) 0 else 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Tab(
                            selected = splitMode == SplitMode.EQUAL,
                            onClick = { viewModel.onSplitModeSelected(SplitMode.EQUAL) },
                            text = { Text("بالتساوي", fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = splitMode == SplitMode.CUSTOM_PERCENTAGE,
                            onClick = { viewModel.onSplitModeSelected(SplitMode.CUSTOM_PERCENTAGE) },
                            text = { Text("مخصص (نسبة %)", fontWeight = FontWeight.Bold) }
                        )
                    }

                    // Optional Tip / Fee
                    Text(
                        text = "رسوم خدمة إضافية أو بقشيش (اختياري)",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(0.0, 5.0, 10.0, 15.0).forEach { percent ->
                            FilterChip(
                                selected = tipPercent == percent,
                                onClick = { viewModel.onTipPercentSelected(percent) },
                                label = { Text("${percent.toInt()}%") },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    // Dynamic Persons List Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "الأشخاص المشاركون (${persons.size})",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )

                        Button(
                            onClick = viewModel::addPerson,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = UaeFlagGreen),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("add_person_btn")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "إضافة شخص", fontSize = 12.sp)
                        }
                    }

                    // List of Persons Fields
                    persons.forEachIndexed { index, person ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Person Name field
                            OutlinedTextField(
                                value = person.name,
                                onValueChange = { viewModel.updatePersonName(person.id, it) },
                                placeholder = { Text("اسم الشخص ${index + 1}") },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("person_name_${person.id}"),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                            )

                            // If custom percentage mode, show percentage field
                            if (splitMode == SplitMode.CUSTOM_PERCENTAGE) {
                                OutlinedTextField(
                                    value = if (person.percentage == 0.0) "" else person.percentage.toInt().toString(),
                                    onValueChange = { input ->
                                        val percent = input.toDoubleOrNull() ?: 0.0
                                        viewModel.updatePersonPercentage(person.id, percent)
                                    },
                                    placeholder = { Text("%") },
                                    trailingIcon = { Text("%", modifier = Modifier.padding(end = 6.dp)) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    modifier = Modifier
                                        .width(90.dp)
                                        .testTag("person_percent_${person.id}"),
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }

                            // Delete Person button
                            if (persons.size > 2) {
                                IconButton(
                                    onClick = { viewModel.removePerson(person.id) },
                                    modifier = Modifier.testTag("remove_person_${person.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "حذف",
                                        tint = UaeAlertRed
                                    )
                                }
                            }
                        }
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = UaeAlertRed,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Calculate Button
                    Button(
                        onClick = {
                            val success = viewModel.calculate()
                            if (success && activity != null) {
                                // إظهار إعلان بيني بعد إتمام إجراء المستخدم بحد أقصى مرة كل 3 دقائق
                                AdMobManager.showInterstitialAfterAction(activity)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("calculate_split_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = UaeFlagGreen)
                    ) {
                        Icon(Icons.Default.Payments, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "احسب نصيب كل شخص", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }

        // Formatted Results Card
        splitResult?.let { result ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("split_result_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(UaeFlagGreen),
                        width = 1.5.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Text(
                            text = "ملخص تقسيم الفاتورة",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = UaeFlagGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Per Person Breakdown Cards
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                result.personResults.forEach { personResult ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = personResult.name,
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            if (result.mode == SplitMode.CUSTOM_PERCENTAGE) {
                                                Text(
                                                    text = "نسبة: ${personResult.percentage.toInt()}%",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }

                                        Text(
                                            text = "${decimalFormat.format(personResult.shareAmount)} درهم",
                                            fontWeight = FontWeight.ExtraBold,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = UaeFlagGreen
                                        )
                                    }
                                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "المبلغ الإجمالي الكلي:",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "${decimalFormat.format(result.totalAmount)} درهم",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Formatted Share Button for WhatsApp / Any App
                        Button(
                            onClick = {
                                val summaryBuilder = StringBuilder()
                                summaryBuilder.append("🧾 ملخص تقسيم الفاتورة عبر UAE Pay Guide:\n")
                                summaryBuilder.append("━━━━━━━━━━━━━━━━━━\n")
                                summaryBuilder.append("• الإجمالي: ${decimalFormat.format(result.totalAmount)} درهم إماراتي\n")
                                summaryBuilder.append("• طريقة التقسيم: ${result.mode.titleAr}\n")
                                summaryBuilder.append("━━━━━━━━━━━━━━━━━━\n")
                                summaryBuilder.append("نصيب كل شخص:\n")
                                result.personResults.forEach { p ->
                                    summaryBuilder.append("👤 ${p.name}: ${decimalFormat.format(p.shareAmount)} درهم (${p.percentage.toInt()}%)\n")
                                }
                                summaryBuilder.append("━━━━━━━━━━━━━━━━━━\n")
                                summaryBuilder.append("تم الحساب عبر تطبيق UAE Pay Guide 🇦🇪")

                                shareFormattedText(context, summaryBuilder.toString())
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("share_formatted_split_btn"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = UaeFlagGreen)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "مشاركة النتيجة (واتساب / التطبيقات)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

private fun shareFormattedText(context: Context, text: String) {
    try {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "إرسال ملخص الفاتورة عبر")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "تعذر مشاركة النص", Toast.LENGTH_SHORT).show()
    }
}
