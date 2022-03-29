package com.dzakyhdr.permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dzakyhdr.permission.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            getImageWithGlide()
        }

        binding.button2.setOnClickListener {
            requestPermissionLocation()
        }
    }

    fun getImageWithGlide() {
        Glide.with(this)
            .load("https://img.icons8.com/plasticine/2x/flower.png")
            .into(binding.imageView)
    }

    fun requestPermissionLocation(){
        val permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(binding.root, "Permission Location DIIZINKAN", Snackbar.LENGTH_LONG).show()
            getLongLat()
        } else {
            Snackbar.make(binding.root, "Permission Location DITOLAK", Snackbar.LENGTH_LONG).show()
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 201)
    }

    @SuppressLint("MissingPermission")
    fun getLongLat() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        binding.txtLatitude.text = getString(R.string.latitude, location?.latitude.toString())
        binding.txtLongitude.text = getString(R.string.longitude, location?.longitude.toString())


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    Snackbar.make(binding.root, "Permission for Location Permitted", Snackbar.LENGTH_LONG).show()
                    getLongLat()
                } else {
                    Snackbar.make(binding.root, "Permissions for Location Denied", Snackbar.LENGTH_LONG).show()

                }
            }
            else -> {
                Toast.makeText(this, "The request code doesn't match", Toast.LENGTH_LONG).show()
            }
        }
    }


}