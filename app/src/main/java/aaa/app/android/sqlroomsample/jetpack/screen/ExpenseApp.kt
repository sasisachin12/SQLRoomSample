package aaa.app.android.sqlroomsample.jetpack.screen

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.jetpack.screen.screens.ExpenseAppBar
import aaa.app.android.sqlroomsample.jetpack.screen.screens.ExpenseTabs
import aaa.app.android.sqlroomsample.jetpack.screen.theme.YellowTheme
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun ExpenseApp(finishActivity: () -> Unit) {
    YellowTheme {
        val tabs = remember { ExpenseTabs.entries.toTypedArray() }
        val navController = rememberNavController()
        Scaffold(
            topBar = { ExpenseAppBar(stringResource(R.string.expense)) },
            containerColor = MaterialTheme.colorScheme.background,
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
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEach { tab ->
                val selected = currentRoute == tab.route
                NavigationBarItem(
                    icon = { 
                        Icon(
                            painter = painterResource(tab.icon), 
                            contentDescription = null
                        ) 
                    },
                    label = { 
                        Text(
                            text = stringResource(tab.title),
                            style = MaterialTheme.typography.labelSmall
                        ) 
                    },
                    selected = selected,
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
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        indicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                    )
                )
            }
        }
    }
}
