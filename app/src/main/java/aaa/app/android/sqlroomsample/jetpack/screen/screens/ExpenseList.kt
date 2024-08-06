package aaa.app.android.sqlroomsample.jetpack.screen.screens

import aaa.app.android.sqlroomsample.jetpack.screen.model.Course
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpenseList(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        CoursesAppBar()

    }
}

