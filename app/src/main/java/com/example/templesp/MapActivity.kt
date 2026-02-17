package com.example.templesp

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var btnFood: Button
    private lateinit var btnBus: Button
    private lateinit var btnAttraction: Button

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

        val startPoint = GeoPoint(13.9312, 100.4715)
        map.controller.setCenter(startPoint)
        map.controller.setZoom(15.0)

        // ปักหมุดวัดสะพานสูง
        val mainMarker = Marker(map)
        mainMarker.position = startPoint
        mainMarker.title = "วัดสะพานสูง"
        val icon = resizeMarkerIcon(R.drawable.temple, 50, 50)
        if (icon != null) mainMarker.icon = icon
        map.overlays.add(mainMarker)

        btnFood.setOnClickListener {
            if (foodMarkers.isNotEmpty()) {
                foodMarkers.forEach { map.overlays.remove(it) }
                foodMarkers.clear()
            } else {
                foodMarkers.add(createMarker(GeoPoint(13.93366, 100.47525), "บ้านสวนอิงน้ำ", "ริมคลอง บรรยากาศดี\nโทร: 080-569-6850", R.drawable.food_ingnam))
                foodMarkers.add(createMarker(GeoPoint(13.92429, 100.47183), "สิมิลันโภชนา", "อาหารทะเลสด รสเด็ด\nโทร: 062-439-5281", R.drawable.food_similan))
                foodMarkers.add(createMarker(GeoPoint(13.92733, 100.48232), "คำโต คิชเช่น", "คาเฟ่กระต่ายน่ารัก\nโทร: 085-953-4544", R.drawable.food_kumtoh))
                map.controller.setZoom(15.0)
            }
            map.invalidate()
        }
    }

    private fun createMarker(point: GeoPoint, title: String, snippet: String, imageRes: Int): Marker {
        val marker = Marker(map)
        marker.position = point
        marker.title = title
        marker.snippet = snippet
        marker.image = ContextCompat.getDrawable(this, imageRes)

        // ใช้ไอคอนหมุดมาตรฐาน (หรือจะใส่รูปเล็กๆ ก็ได้)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // --- จุดสำคัญ: เรียกใช้ Custom Info Window ---
        // ใช้ชื่อเต็ม com.example.templesp.R เพื่อแก้ปัญหาตัวแดงหาไฟล์ไม่เจอ
        marker.infoWindow = MyInfoWindow(com.example.templesp.R.layout.window_info, map)

        map.overlays.add(marker)

        return marker
    }

    // Class สำหรับจัดการหน้าต่าง Pop-up
    inner class MyInfoWindow(layoutResId: Int, mapView: MapView) : MarkerInfoWindow(layoutResId, mapView) {
        override fun onOpen(item: Any?) {
            closeAllInfoWindowsOn(mapView)
            val marker = item as Marker
            val view = mView // mView คือ View ของ Pop-up

            val img = view.findViewById<ImageView>(R.id.iw_image)
            val txtTitle = view.findViewById<TextView>(R.id.iw_title)
            val txtSnippet = view.findViewById<TextView>(R.id.iw_snippet)

            img?.setImageDrawable(marker.image)
            txtTitle?.text = marker.title
        txtSnippet?.text = marker.snippet
        }
        override fun onClose() {}
    }

    private fun resizeMarkerIcon(id: Int, w: Int, h: Int): Drawable? {
        val d = ContextCompat.getDrawable(this, id)
        return if (d is BitmapDrawable) BitmapDrawable(resources, Bitmap.createScaledBitmap(d.bitmap, w, h, false)) else null
    }

    override fun onResume() { super.onResume(); map.onResume() }
    override fun onPause() { super.onPause(); map.onPause() }
}