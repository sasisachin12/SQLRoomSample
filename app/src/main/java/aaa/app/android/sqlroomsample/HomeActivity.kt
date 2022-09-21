package aaa.app.android.sqlroomsample


import aaa.app.android.sqlroomsample.databinding.ActivityHomeBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.expenseListFragment,
            R.id.expenseFragment, R.id.navigation_notifications
        ).build()
        val navController = findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController, appBarConfiguration)
        setupWithNavController(navView, navController)
    }
}