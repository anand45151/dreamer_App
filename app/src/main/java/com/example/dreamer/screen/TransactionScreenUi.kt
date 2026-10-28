package com.example.dreamer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filterOptions = listOf("All", "Expenses", "Income", "Shopping", "Food")

    val allTransactions = listOf(
        PaymentItem(
            title = "Shopping Mall",
            subtitle = "Clothing & Accessories",
            amount = "- $128.50",
            date = "24 Oct 2024, 04:15 PM",
            icon = Icons.Default.ShoppingBag,
            iconBgColor = Color(0xFFFFEFEB),
            iconTint = Color(0xFFFF6B6B)
        ),
        PaymentItem(
            title = "Starbucks Coffee",
            subtitle = "Food & Beverages",
            amount = "- $14.20",
            date = "24 Oct 2024, 10:30 AM",
            icon = Icons.Default.Fastfood,
            iconBgColor = Color(0xFFFFF7ED),
            iconTint = Color(0xFFF97316)
        ),
        PaymentItem(
            title = "Netflix Subscription",
            subtitle = "Entertainment Plan",
            amount = "- $19.99",
            date = "22 Oct 2024, 09:00 AM",
            icon = Icons.Default.Movie,
            iconBgColor = Color(0xFFF0F4FF),
            iconTint = Color(0xFF4B6CB7)
        ),
        PaymentItem(
            title = "Salary Deposit",
            subtitle = "Monthly Income Credit",
            amount = "+ $3,450.00",
            date = "20 Oct 2024, 08:00 AM",
            icon = Icons.Default.AccountBalanceWallet,
            iconBgColor = Color(0xFFECFDF5),
            iconTint = Color(0xFF10B981),
            isIncome = true
        ),
        PaymentItem(
            title = "Uber Ride",
            subtitle = "Transportation",
            amount = "- $24.80",
            date = "19 Oct 2024, 11:45 PM",
            icon = Icons.Default.DirectionsCar,
            iconBgColor = Color(0xFFF3E8FF),
            iconTint = Color(0xFFA855F7)
        ),
        PaymentItem(
            title = "Freelance Project",
            subtitle = "Client Payment Received",
            amount = "+ $850.00",
            date = "18 Oct 2024, 02:15 PM",
            icon = Icons.Default.AccountBalanceWallet,
            iconBgColor = Color(0xFFECFDF5),
            iconTint = Color(0xFF10B981),
            isIncome = true
        )
    )

    val filteredTransactions = allTransactions.filter { item ->
        val matchesSearch = item.title.contains(searchQuery, ignoreCase = true) ||
                item.subtitle.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Expenses" -> !item.isIncome
            "Income" -> item.isIncome
            "Shopping" -> item.title.contains("Shopping", ignoreCase = true) || item.subtitle.contains("Clothing", ignoreCase = true)
            "Food" -> item.title.contains("Coffee", ignoreCase = true) || item.subtitle.contains("Food", ignoreCase = true)
            else -> true
        }
        matchesSearch && matchesFilter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        // Title
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF172033)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search transactions...", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF4B6CB7),
                unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filterOptions) { filter ->
                val isSelected = filter == selectedFilter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = {
                        Text(
                            text = filter,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF172033),
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = Color(0xFF64748B)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction List Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            if (filteredTransactions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No transactions found",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredTransactions.size) { index ->
                        val item = filteredTransactions[index]
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(item.iconBgColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = item.iconTint,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF172033)
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = item.subtitle,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.date,
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8),
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Text(
                                text = item.amount,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (item.isIncome) Color(0xFF10B981) else Color(0xFF172033)
                            )
                        }

                        if (index < filteredTransactions.size - 1) {
                            HorizontalDivider(
                                color = Color(0xFFF1F5F9),
                                thickness = 1.dp,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    TransactionScreen()
}
