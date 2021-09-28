package com.mynews.metronews.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

import com.google.android.material.navigation.NavigationView
import com.mynews.metronews.R
import com.mynews.metronews.ui.profile.author.LogInActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var navController: NavController? = null
    private var mdrawerLayout: DrawerLayout? = null
    private var mToggle: ActionBarDrawerToggle? = null
    private var mNavigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //  val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        mNavigationView = findViewById(R.id.navigation_view)
        mdrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mdrawerLayout, R.string.open, R.string.close)


        //  supportActionBar!!.hide()



        // window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //  supportActionBar!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));


        mNavigationView!!.bringToFront()
        mdrawerLayout!!.addDrawerListener(mToggle!!)
        mToggle!!.syncState()

        mNavigationView!!.background.alpha = 150


        // supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
//        if (supportActionBar != null) {
//
//            Toast.makeText(this, "action", Toast.LENGTH_LONG).show()
//            supportActionBar!!.setDisplayShowHomeEnabled(true)
//        }


        navController = Navigation.findNavController(this, R.id.host_fragment)
        bottomNavigationView.setupWithNavController(navController!!)
        NavigationUI.setupActionBarWithNavController(this, navController!!, mdrawerLayout)

        mNavigationView!!.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController!!, mdrawerLayout)
    }

    // Navigation Drawer Listner
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //   Toast.makeText(this, "${item.itemId}", Toast.LENGTH_LONG).show()
        when (item.itemId) {


            R.id.login_drawer_menu -> {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                this@MainActivity.finish()
            }

            R.id.aboutUs_drawer_menu -> {
                val intent = Intent(this, OurTeam::class.java)
                startActivity(intent)
            }
        }

        //    mdrawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }


}