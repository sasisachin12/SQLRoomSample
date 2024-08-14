package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.courses(
    onCourseSelected: (Long, NavBackStackEntry) -> Unit,
    onboardingComplete: State<Boolean>, // https://issuetracker.google.com/174783110
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    composable(ExpenseTabs.EXPENSE_LIST.route) { from ->
        // Show onboarding instead if not shown yet.
        /*   LaunchedEffect(onboardingComplete) {
               if (!onboardingComplete.value) {
                   navController.navigate(MainDestinations.ONBOARDING_ROUTE)
               }
           }
           if (onboardingComplete.value) { // Avoid glitch when showing onboarding
               FeaturedCourses(
                   courses = courses,
                   selectCourse = { id -> onCourseSelected(id, from) },
                   modifier = modifier
               )
           }*/

        ExpenseListScreen()
    }
    composable(ExpenseTabs.ADD_EXPENSE.route) { from ->
        AddMyExpenseScreen()
    }
    composable(ExpenseTabs.SETTINGS.route) {
        SettingsScreen(modifier)
    }
}


@Preview
@Composable
fun ExpenseAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.height(40.dp)
    ) {
        Text("Expense List")

    }
}

enum class ExpenseTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    ADD_EXPENSE(R.string.expense, R.drawable.ic_grain, Destinations.EXPENSE_LIST),
    EXPENSE_LIST(R.string.expense_list, R.drawable.ic_featured, Destinations.ADD_EXPENSE),
    SETTINGS(R.string.settings, R.drawable.ic_search, Destinations.SETTINGS)
}


private object Destinations {
    const val ADD_EXPENSE = "add_expense"
    const val EXPENSE_LIST = "expense_list"
    const val SETTINGS = "settings"
}
