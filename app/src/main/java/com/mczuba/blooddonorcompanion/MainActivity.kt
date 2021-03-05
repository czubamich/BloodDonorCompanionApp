package com.mczuba.blooddonorcompanion

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mczuba.blooddonorcompanion.util.InjectorUtils
import com.mczuba.blooddonorcompanion.vm.BlankFragmentDirections
import com.mczuba.blooddonorcompanion.vm.SummaryFragmentDirections
import com.mczuba.blooddonorcompanion.vm.records.HistoryFragmentDirections
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navView: SmoothBottomBar
    private lateinit var fab: FloatingActionButton
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedPreferences: SharedPreferences

    private val navChangedListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        when(destination.id) {
            R.id.summaryFragment -> fab.visibility = View.VISIBLE
            R.id.historyFragment -> fab.visibility = View.VISIBLE
            else -> fab.visibility = View.INVISIBLE
        }
        when (destination.id) {
            R.id.newRecordFragment -> navView.visibility = View.GONE;
            R.id.scheduleRecordFragment -> navView.visibility = View.GONE;
            else -> navView.visibility = View.VISIBLE;
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        navView.visibility = View.INVISIBLE

        //Check repository
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        sharedPreferences = application.getSharedPreferences(getString(R.string.pref_donorprefs), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt(getString(R.string.pref_currentuser_key), -1)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            when(navController.currentDestination?.id)
            {
                R.id.summaryFragment -> navController.navigate(SummaryFragmentDirections.actionNavigationSummaryToNewRecordFragment())
                R.id.historyFragment ->  navController.navigate(HistoryFragmentDirections.actionHistoryFragmentToNewRecordFragment())
                else -> navController.navigate(R.id.newRecordFragment)
            }
        }

        val menu = PopupMenu(this@MainActivity, null).menu
        menuInflater.inflate(R.menu.main_nav_menu, menu)
        navView.setupWithNavController(menu!!, navController)
        navController.addOnDestinationChangedListener(navChangedListener)

        MainScope().launch {
            val repo = InjectorUtils.getUserRepository(application)
            if (userId==-1 || !repo.checkUserExists(userId)) {
                val intent = Intent(this@MainActivity, GreetingActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, "new")
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                ActivityNavigator.applyPopAnimationsToPendingTransition(this@MainActivity)
                startActivity(intent, ActivityOptions.makeBasic().toBundle())
                finishAffinity()
                overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
            } else {
                val direction = BlankFragmentDirections.actionBlankFragmentToNavigationSummary()
                navController.graph.startDestination = R.id.summaryFragment;
                navView.visibility = View.VISIBLE
                navController.navigate(direction)
            }
        }
    }
}