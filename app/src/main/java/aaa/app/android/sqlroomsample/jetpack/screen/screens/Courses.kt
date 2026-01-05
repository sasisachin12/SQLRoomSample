package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.courses(
    modifier: Modifier = Modifier
) {
    composable(ExpenseTabs.EXPENSE_LIST.route) { from ->
        ExpenseListScreen()
    }
    composable(ExpenseTabs.ADD_EXPENSE.route) { from ->
        AddMyExpenseScreen(modifier)
    }
    composable(ExpenseTabs.SETTINGS.route) {
        SettingsScreen(modifier)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseAppBar(title: String) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

enum class ExpenseTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    ADD_EXPENSE(R.string.expense, R.drawable.ic_grain, Destinations.ADD_EXPENSE),
    EXPENSE_LIST(R.string.expense_list, R.drawable.ic_featured, Destinations.EXPENSE_LIST),
    SETTINGS(R.string.settings, R.drawable.ic_search, Destinations.SETTINGS)
}


private object Destinations {
    const val ADD_EXPENSE = "add_expense"
    const val EXPENSE_LIST = "expense_list"
    const val SETTINGS = "settings"
}
