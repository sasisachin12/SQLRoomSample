package aaa.app.android.sqlroomsample.jetpack.screen.courses

import aaa.app.android.sqlroomsample.jetpack.screen.model.Course
import aaa.app.android.sqlroomsample.jetpack.screen.model.courses
import aaa.app.android.sqlroomsample.jetpack.screen.theme.BlueTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddMyExpense(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(

        modifier = modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        item {
            CoursesAppBar()
            ExpenseFiled()
            ExpenseAmountFiled()
            SaveExpenseButton { }
        }

    }
}

@Composable
fun ExpenseFiled() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Expense") }
    )
}

@Composable
fun ExpenseAmountFiled() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Expense Amount") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun SaveExpenseButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .padding(12.dp)
    ) {
        Text("Save")
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
