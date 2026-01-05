package aaa.app.android.sqlroomsample.jetpack.screen

import aaa.app.android.sqlroomsample.jetpack.screen.screens.AddMyExpenseScreen
import aaa.app.android.sqlroomsample.jetpack.screen.screens.ExpenseListScreen
import aaa.app.android.sqlroomsample.jetpack.screen.screens.ExpenseTabs
import aaa.app.android.sqlroomsample.jetpack.screen.screens.SettingsScreen
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

object MainDestinations {
    const val EXPENSE_ROUTE = "expense_root"
}

@Composable
fun NavGraph(
    finishActivity: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.EXPENSE_ROUTE,
    snackBarHostState: SnackbarHostState
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = MainDestinations.EXPENSE_ROUTE,
            startDestination = ExpenseTabs.ADD_EXPENSE.route
        ) {
            composable(ExpenseTabs.ADD_EXPENSE.route) {
                AddMyExpenseScreen(snackbarHostState = snackBarHostState)
            }
            composable(ExpenseTabs.EXPENSE_LIST.route) {
                ExpenseListScreen()
            }
            composable(ExpenseTabs.SETTINGS.route) {
                SettingsScreen(modifier)
            }
        }
    }
}
