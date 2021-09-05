package com.sygic.tracker.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sygic.tracker.R
import com.sygic.tracker.databinding.ActivityMainBinding
import com.sygic.tracker.manager.backpress.BackPressedManager
import com.sygic.tracker.manager.location.LocationRequestManager
import com.sygic.tracker.manager.permission.PermissionManager
import com.sygic.tracker.screen.tracking.TrackingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var locationRequestManager: LocationRequestManager

    @Inject
    lateinit var backPressedManager: BackPressedManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, TrackingFragment())
                .commit()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        locationRequestManager.onActivityResult(requestCode, resultCode)
    }

    override fun onBackPressed() {
        if (backPressedManager.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

}