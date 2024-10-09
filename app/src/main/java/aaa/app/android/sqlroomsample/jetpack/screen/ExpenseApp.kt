package aaa.app.android.sqlroomsample.jetpack.screen

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.jetpack.screen.screens.ExpenseAppBar
import aaa.app.android.sqlroomsample.jetpack.screen.screens.ExpenseTabs
import aaa.app.android.sqlroomsample.jetpack.screen.theme.YellowTheme
import aaa.app.android.sqlroomsample.jetpack.screen.theme.yellow200
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import java.util.Locale

@Composable
fun ExpenseApp(finishActivity: () -> Unit) {
    YellowTheme {
        val tabs = remember { ExpenseTabs.entries.toTypedArray() }
        val navController = rememberNavController()
        Scaffold(
            topBar = { ExpenseAppBar(stringResource(R.string.expense)) },
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            bottomBar = { MyBottomBar(navController = navController, tabs) }
        ) { innerPaddingModifier ->
            NavGraph(
                finishActivity = finishActivity,
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier)
            )
        }
    }
}

@Composable
fun MyBottomBar(navController: NavController, tabs: Array<ExpenseTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: ExpenseTabs.ADD_EXPENSE.route

    val routes = remember { ExpenseTabs.entries.map { it.route } }
    if (currentRoute in routes) {
        NavigationBar(
            modifier = Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
            ),
            containerColor = yellow200
        ) {
            tabs.forEach { tab ->
                NavigationBarItem(
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    label = { Text(stringResource(tab.title).uppercase(Locale.getDefault())) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    modifier = Modifier.navigationBarsPadding(),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
