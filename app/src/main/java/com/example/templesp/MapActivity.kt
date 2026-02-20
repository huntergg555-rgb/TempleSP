package com.example.templesp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var btnFood: Button
    private lateinit var btnBus: Button
    private lateinit var btnAttraction: Button
    private lateinit var myLocationOverlay: MyLocationNewOverlay

    private val foodMarkers = ArrayList<Marker>()
    private val busMarkers = ArrayList<Marker>()
    private val attractionMarkers = ArrayList<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        setContentView(R.layout.activity_map)

        map = findViewById(R.id.mapview)
        btnFood = findViewById(R.id.btnFood)
        btnBus = findViewById(R.id.btnBus)
        btnAttraction = findViewById(R.id.btnAttraction)

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        myLocationOverlay.enableMyLocation()
        map.overlays.add(myLocationOverlay)
        
        val startPoint = GeoPoint(13.9312, 100.4715)
        map.controller.setCenter(startPoint)
        map.controller.setZoom(14.5)

        // หมุดหลัก วัดสะพานสูง
        createMarker(startPoint, "วัดสะพานสูง", "นมัสการหลวงปู่เอี่ยม ปฐมนาม\nแหล่งกำเนิดตะกรุดมหาโสฬสมงคล", null, R.drawable.temple, null, 55)

        btnFood.setOnClickListener { toggleMarkers(foodMarkers, "food") }
        btnBus.setOnClickListener { toggleMarkers(busMarkers, "bus") }
        btnAttraction.setOnClickListener { toggleMarkers(attractionMarkers, "attraction") }

        // ระบบเมนูเด้ง
        val hiddenMenu = findViewById<LinearLayout>(R.id.hiddenMenu)
        val btnNavMap = findViewById<ImageView>(R.id.btnNavMap)
        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)
        val btnNavSocial = findViewById<ImageView>(R.id.btnNavSocial)
        val btnNavProfile = findViewById<ImageView>(R.id.btnNavProfile)

        btnNavMap.setOnClickListener {
            hiddenMenu.visibility = if (hiddenMenu.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        btnNavHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        btnNavSocial.setOnClickListener { startActivity(Intent(this, SocialActivity::class.java)) }
        btnNavProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    private fun toggleMarkers(markerList: ArrayList<Marker>, type: String) {
        if (markerList.isNotEmpty()) {
            markerList.forEach { map.overlays.remove(it) }
            markerList.clear()
        } else {
            when (type) {
                "food" -> addFoodMarkers()
                "bus" -> addBusMarkers()
                "attraction" -> addAttractionMarkers()
            }
        }
        map.invalidate()
    }

    private fun addFoodMarkers() {
        foodMarkers.add(createMarker(GeoPoint(13.93366, 100.47525), "บ้านสวนอิงน้ำ", "ร้านอาหารริมคลอง บรรยากาศดี", null, R.drawable.ic_restaurant, null, 40))
        foodMarkers.add(createMarker(GeoPoint(13.92429, 100.47183), "สิมิลันโภชนา", "อาหารทะเลสด รสเด็ด", null, R.drawable.ic_restaurant, null, 40))
        foodMarkers.add(createMarker(GeoPoint(13.92733, 100.48232), "คำโต คิชเช่น", "คาเฟ่กระต่ายและอาหารไทย", null, R.drawable.ic_restaurant, null, 40))
        foodMarkers.add(createMarker(GeoPoint(13.9385, 100.4580), "ก๋วยเตี๋ยวดูดี คลองพระอุดม", "ร้านก๋วยเตี๋ยวชื่อดังในพื้นที่", null, R.drawable.ic_restaurant, null, 40))
        foodMarkers.add(createMarker(GeoPoint(13.9452, 100.4615), "ครัวจ่ากุ่ย", "อาหารป่า อาหารตามสั่งรสแซ่บ", null, R.drawable.ic_restaurant, null, 40))
        map.controller.animateTo(GeoPoint(13.9350, 100.4650))
    }

    private fun addBusMarkers() {
        busMarkers.add(createMarker(GeoPoint(13.9315, 100.4705), "ป้ายรถเมล์หน้าวัด", "จุดจอดรถเมล์และรถตู้", null, R.drawable.ic_bus, null, 40))
        busMarkers.add(createMarker(GeoPoint(13.9319, 100.4710), "วินมอเตอร์ไซค์ หน้าวัด", "บริการรับ-ส่งพื้นที่คลองพระอุดม", null, R.drawable.ic_motorcycle, null, 40))
        busMarkers.add(createMarker(GeoPoint(13.9410, 100.4620), "ท่าเรือคลองพระอุดม", "จุดต่อเรือข้ามฟากและท่องเที่ยว", null, R.drawable.ic_bus, null, 40))
        busMarkers.add(createMarker(GeoPoint(13.9485, 100.4555), "คิวรถสองแถว แยกคลองพระอุดม", "รถไปปากเกร็ด-ราชพฤกษ์", null, R.drawable.ic_bus, null, 40))
    }

    private fun addAttractionMarkers() {
        attractionMarkers.add(createMarker(GeoPoint(13.9128, 100.4897), "เกาะเกร็ด", "แหล่งท่องเที่ยวทางวัฒนธรรมชื่อดัง", null, R.drawable.ic_attraction, null, 40))
        attractionMarkers.add(createMarker(GeoPoint(13.9382, 100.4545), "วัดอินทาราม", "วัดสวยริมคลองพระอุดม", null, R.drawable.ic_attraction, null, 40))
        attractionMarkers.add(createMarker(GeoPoint(13.9455, 100.4725), "วัดสว่างอารมณ์", "ชมอุโบสถเก่าแก่และวิถีชาวบ้าน", null, R.drawable.ic_attraction, null, 40))
        attractionMarkers.add(createMarker(GeoPoint(13.9247, 100.4912), "วัดกู้ (พระนางเรือล่ม)", "วัดสำคัญทางประวัติศาสตร์", null, R.drawable.ic_attraction, null, 40))
        attractionMarkers.add(createMarker(GeoPoint(13.9105, 100.4905), "วัดบ่อ", "วัดเก่าแก่ใจกลางเมืองปากเกร็ด", null, R.drawable.ic_attraction, null, 40))
        map.controller.animateTo(GeoPoint(13.9300, 100.4750))
        map.controller.setZoom(13.8)
    }

    private fun createMarker(point: GeoPoint, title: String, snippet: String, imageRes: Int?, iconRes: Int?, imageUrl: String?, sizeDp: Int): Marker {
        val marker = Marker(map)
        marker.position = point
        marker.title = title
        marker.snippet = snippet
        
        val resId = iconRes ?: R.drawable.temple
        val drawable = ContextCompat.getDrawable(this, resId)
        if (drawable is BitmapDrawable) {
            val pixelSize = (sizeDp * resources.displayMetrics.density).toInt()
            val scaledBitmap = Bitmap.createScaledBitmap(drawable.bitmap, pixelSize, pixelSize, false)
            marker.icon = BitmapDrawable(resources, scaledBitmap)
        } else {
            marker.icon = drawable
        }
        
        marker.relatedObject = imageUrl
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.infoWindow = MyInfoWindow(R.layout.window_info, map)
        map.overlays.add(marker)
        return marker
    }

    inner class MyInfoWindow(layoutResId: Int, mapView: MapView) : MarkerInfoWindow(layoutResId, mapView) {
        override fun onOpen(item: Any?) {
            closeAllInfoWindowsOn(mapView)
            val marker = item as Marker
            val view = mView
            val img = view.findViewById<ImageView>(R.id.iw_image)
            img?.setImageResource(R.drawable.temple)
            view.findViewById<TextView>(R.id.iw_title)?.text = marker.title
            view.findViewById<TextView>(R.id.iw_snippet)?.text = marker.snippet
            view.findViewById<ImageButton>(R.id.iw_btn_close)?.setOnClickListener { marker.closeInfoWindow() }
            view.findViewById<Button>(R.id.iw_btn_navigate).setOnClickListener {
                val gmmIntentUri = Uri.parse("google.navigation:q=${marker.position.latitude},${marker.position.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
        override fun onClose() {}
    }

    override fun onResume() { super.onResume(); myLocationOverlay.enableMyLocation(); map.onResume() }
    override fun onPause() { super.onPause(); myLocationOverlay.disableMyLocation(); map.onPause() }
}