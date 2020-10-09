package com.example.globars.ui.maps

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.globars.R
import com.example.globars.ui.adapter.TrackingListAdapter
import com.example.globars.data.SessionManager
import com.example.globars.data.pojo.units.TrackingUnit
import com.example.globars.viewmodel.TrackingViewModel
import com.example.globars.viewmodel.TrackingViewModelFactory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_google_maps.*
import kotlinx.android.synthetic.main.drawer_view_maps.*
import kotlinx.android.synthetic.main.main_view_maps.*

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var trackingListAdapter: TrackingListAdapter
    private lateinit var trackingViewModel: TrackingViewModel
    private var markers = HashMap<String, Marker>()
    private lateinit var sessionManager: SessionManager

    companion object {
        private val TAG: String = GoogleMapsActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        sessionManager = SessionManager(this)
        setupToolbar()
        setupNavigationDrawer()
        setupViewModels()

        trackingViewModel.getTrackingSessionsId("Bearer ${sessionManager.fetchAuthToken()}")
        trackingViewModel.traсkingSessionsResult.observe(this, Observer{
            val result = it ?:return@Observer
            if (result.error != null) {
                 Log.d(TAG, "error: ${result.error}")
            }
            if (result.success != null) {
                val id = result.success
                Log.d(TAG, "id: $id")
                trackingViewModel.getUnitsById("Bearer ${sessionManager.fetchAuthToken()}", id)
            }
        })

        trackingViewModel.trackingUnitsResult.observe(this, Observer {
            val result = it ?:return@Observer
            map.clear()
            markers.clear()
            if (result.success != null) {
                displayAllMarks(result.success)
                trackingListAdapter.setTrackingData(result.success)
                moveToMark(result.success[0])
            }
        })

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.getUiSettings().setZoomControlsEnabled(true);

//        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))


    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,
            drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        toggle.syncState()
    }

    private fun setupNavigationDrawer() {
        val layoutManager = LinearLayoutManager(this)
        trackingRecyclerView.layoutManager = layoutManager
        trackingListAdapter = TrackingListAdapter(null, this, )
        trackingRecyclerView.adapter = trackingListAdapter
    }

    private fun setupViewModels() {
        trackingViewModel = ViewModelProviders.of(this, TrackingViewModelFactory())
            .get(TrackingViewModel::class.java)
    }

    private fun addPlaceMarker(unit: TrackingUnit): Marker? {
        var alpha = if (unit.eye) 1f else 0.5f
        val marker = map.addMarker(MarkerOptions()
            .position(LatLng(unit.position.lt, unit.position.ln))
            .title(unit.name)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            .alpha(alpha))
        marker.showInfoWindow()
        marker.tag = unit.name
        unit.id?.let { markers.put(it, marker) }
        return marker
    }

    private fun displayAllMarks(units: List<TrackingUnit>) {
        for (unit in units) {
            if (unit.checked) {
                addPlaceMarker(unit)
            }
        }
    }

    private fun updateMapToLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
    }

    fun moveToMark(unit: TrackingUnit) {
        drawerLayout.closeDrawer(drawerView)
        val location = Location("")
        location.latitude = unit.position.lt
        location.longitude = unit.position.ln
        updateMapToLocation(location)
    }
}