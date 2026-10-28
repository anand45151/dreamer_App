package com.example.dreamer.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DaySpending(
    val day: String,
    val amount: Float,
    val fullDay: String
)

data class PaymentItem(
    val title: String,
    val subtitle: String,
    val amount: String,
    val date: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconTint: Color,
    val isIncome: Boolean = false
)

@Composable
fun HomePage(
    onViewAllClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Finance",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF172033)
            )
            Text(
                text = "Manage",
                color = Color(0xFF4B6CB7),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CardDetails()

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Spending Overview",
                color = Color(0xFF172033),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            MonthDropdown()
        }

        Spacer(modifier = Modifier.height(16.dp))

        WeeklySpendingBarGraph()

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transaction",
                color = Color(0xFF172033),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            ViewDropdown(onViewAllClick = onViewAllClick)
        }

        Spacer(modifier = Modifier.height(16.dp))

        PayMeantHistory()

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun PayMeantHistory(
    modifier: Modifier = Modifier,
    payments: List<PaymentItem> = listOf(
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
        )
    )
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            payments.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Payment Icon
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

                    // Title, Subtitle, and Date
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

                    // Dollars / Amount on the Right Hand Side
                    Text(
                        text = item.amount,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (item.isIncome) Color(0xFF10B981) else Color(0xFF172033)
                    )
                }

                if (index < payments.size - 1) {
                    HorizontalDivider(
                        color = Color(0xFFF1F5F9),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
fun WeeklySpendingBarGraph(
    modifier: Modifier = Modifier,
    spendingData: List<DaySpending> = listOf(
        DaySpending("Mon", 1450f, "Monday"),
        DaySpending("Tue", 2300f, "Tuesday"),
        DaySpending("Wed", 1800f, "Wednesday"),
        DaySpending("Thu", 3200f, "Thursday"),
        DaySpending("Fri", 4550f, "Friday"),
        DaySpending("Sat", 2900f, "Saturday"),
        DaySpending("Sun", 1600f, "Sunday")
    )
) {
    var selectedIndex by remember { mutableStateOf(4) } // Default selected: Friday
    val maxAmount = spendingData.maxOfOrNull { it.amount } ?: 5000f
    val totalSpent = spendingData.sumOf { it.amount.toDouble() }
    val avgSpent = if (spendingData.isNotEmpty()) totalSpent / spendingData.size else 0.0

    var isAnimated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isAnimated = true
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            // Selected Day Info Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Weekly Total",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "₹ ${"%,.2f".format(totalSpent)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF172033)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF4B6CB7).copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = Color(0xFF4B6CB7),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Avg: ₹ ${"%,.0f".format(avgSpent)}/day",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF4B6CB7)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tooltip banner for currently tapped bar
            val currentItem = spendingData.getOrNull(selectedIndex)
            if (currentItem != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF0F4FF))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${currentItem.fullDay}: ₹ ${"%,.2f".format(currentItem.amount)}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF182848)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bar Graph View
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                spendingData.forEachIndexed { index, item ->
                    val isSelected = index == selectedIndex
                    val targetFillFraction = (item.amount / maxAmount).coerceIn(0.12f, 1.0f)

                    val animatedFillFraction by animateFloatAsState(
                        targetValue = targetFillFraction,
                        animationSpec = tween(
                            durationMillis = 800,
                            delayMillis = index * 60,
                            easing = FastOutSlowInEasing
                        ),
                        label = "barAnimation"
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { selectedIndex = index }
                    ) {
                        // Rounded Bar Track & Active Bar
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            // Background Track
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFF1F5F9))
                            )

                            // Active Bar Fill
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .fillMaxHeight(animatedFillFraction)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (isSelected) {
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFF4B6CB7),
                                                    Color(0xFF182848)
                                                )
                                            )
                                        } else {
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFF94A3B8),
                                                    Color(0xFF64748B)
                                                )
                                            )
                                        }
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Day label below bar
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) Color(0xFF172033) else Color.Transparent
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.day,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViewDropdown(
    onViewAllClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf("View All ") }

    val options = listOf(
        "View All",
        "Last Week",
        "This Month",
        "Last 3 Months",
        "This Year"
    )

    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedMonth,
                color = Color(0xFF172033),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select month",
                tint = Color(0xFF172033)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(text = option)
                    },
                    onClick = {
                        selectedMonth = option
                        expanded = false
                        if (option == "View All") {
                            onViewAllClick()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MonthDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf("This Week") }

    val options = listOf(
        "This Week",
        "Last Week",
        "This Month",
        "Last 3 Months",
        "This Year"
    )

    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedMonth,
                color = Color(0xFF172033),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select month",
                tint = Color(0xFF172033)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(text = option)
                    },
                    onClick = {
                        selectedMonth = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CardDetails() {
    val cardShape = RoundedCornerShape(24.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(cardShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF182848),
                        Color(0xFF4B6CB7)
                    )
                )
            )
            .padding(22.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DREAMER BANK",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "VISA",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "TOTAL BALANCE",
                color = Color(0xFFCFD8F3),
                fontSize = 11.sp,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "₹ 84,250.00",
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bottom section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "CARD HOLDER",
                        color = Color(0xFFCFD8F3),
                        fontSize = 9.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "ANAND YADAV",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "••••  ••••  ••••  4829",
                        color = Color.White,
                        fontSize = 13.sp,
                        letterSpacing = 1.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "EXPIRES",
                        color = Color(0xFFCFD8F3),
                        fontSize = 9.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "12/28",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageUiPreview() {
    HomePage()
}

@Preview(showBackground = true)
@Composable
fun PayMeantHistoryPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        PayMeantHistory()
    }
}
