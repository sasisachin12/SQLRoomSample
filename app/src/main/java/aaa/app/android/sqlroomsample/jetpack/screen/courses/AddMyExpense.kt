package aaa.app.android.sqlroomsample.jetpack.screen.courses

import aaa.app.android.sqlroomsample.jetpack.screen.model.Course
import aaa.app.android.sqlroomsample.jetpack.screen.model.courses
import aaa.app.android.sqlroomsample.jetpack.screen.theme.BlueTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddMyExpense(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        item {
            CoursesAppBar()
        }

    }
}


@Preview(name = "My Courses")
@Composable
private fun MyCoursesPreview() {
    BlueTheme {
        AddMyExpense(
            courses = courses,
            selectCourse = { }
        )
    }
}
