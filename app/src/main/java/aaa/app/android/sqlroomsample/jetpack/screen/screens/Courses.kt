package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.jetpack.screen.model.courses
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
    composable(CourseTabs.EXPENSE_LIST.route) { from ->
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

        ExpenseList(
            courses = courses,
            selectCourse = { id -> onCourseSelected(id, from) },
            modifier = modifier
        )
    }
    composable(CourseTabs.ADD_EXPENSE.route) { from ->
        AddMyExpenseScreen()
    }
    composable(CourseTabs.SETTINGS.route) {
        Settings(modifier)
    }
}


@Preview
@Composable
fun CoursesAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.height(40.dp)
    ) {
        Text("Expense List")

    }
}

enum class CourseTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    ADD_EXPENSE(R.string.expense, R.drawable.ic_grain, CoursesDestinations.EXPENSE_LIST),
    EXPENSE_LIST(R.string.expense_list, R.drawable.ic_featured, CoursesDestinations.ADD_EXPENSE),
    SETTINGS(R.string.settings, R.drawable.ic_search, CoursesDestinations.SETTINGS)
}


private object CoursesDestinations {
    const val ADD_EXPENSE = "courses/featured"
    const val EXPENSE_LIST = "courses/my"
    const val SETTINGS = "courses/search"
}
