package aaa.app.android.sqlroomsample.jetpack.screen

import aaa.app.android.sqlroomsample.jetpack.screen.courses.CourseTabs
import aaa.app.android.sqlroomsample.jetpack.screen.theme.BlueTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import java.util.Locale

@Composable
fun ExpenseApp(finishActivity: () -> Unit) {
    BlueTheme {
        val tabs = remember { CourseTabs.entries.toTypedArray() }
        val navController = rememberNavController()
        Scaffold(
            backgroundColor = MaterialTheme.colors.primarySurface,
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
fun MyBottomBar(navController: NavController, tabs: Array<CourseTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: CourseTabs.ADD_EXPENSE.route

    val routes = remember { CourseTabs.entries.map { it.route } }
    if (currentRoute in routes) {
        BottomNavigation(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
            )
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
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
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = LocalContentColor.current,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}
