package com.example.templesp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
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
        map.controller.setZoom(15.0)

        // หมุดหลัก วัดสะพานสูง (พร้อมรูปจริงจากเน็ต)
        val mainMarker = createMarker(
            startPoint, 
            "วัดสะพานสูง", 
            "นมัสการหลวงปู่เอี่ยม ปฐมนาม\nแหล่งกำเนิดตะกรุดมหาโสฬสมงคล", 
            R.drawable.temple, 
            null,
            "https://paimon-travel.com/wp-content/uploads/2023/10/Wat-Saphan-Sung.jpg"
        )
        val mainIcon = resizeMarkerIcon(R.drawable.temple, 100, 100)
        if (mainIcon != null) mainMarker.icon = mainIcon

        btnFood.setOnClickListener {
            toggleMarkers(foodMarkers) {
                foodMarkers.add(createMarker(GeoPoint(13.93366, 100.47525), "บ้านสวนอิงน้ำ", "ริมคลอง บรรยากาศดี", R.drawable.food_ingnam, R.drawable.ic_restaurant))
                foodMarkers.add(createMarker(GeoPoint(13.92429, 100.47183), "สิมิลันโภชนา", "อาหารทะเลสด รสเด็ด", R.drawable.food_similan, R.drawable.ic_restaurant))
                foodMarkers.add(createMarker(GeoPoint(13.92733, 100.48232), "คำโต คิชเช่น", "คาเฟ่กระต่ายน่ารัก", R.drawable.food_kumtoh, R.drawable.ic_restaurant))
            }
        }

        btnBus.setOnClickListener {
            toggleMarkers(busMarkers) {
                busMarkers.add(createMarker(GeoPoint(13.9315, 100.4705), "ป้ายรถเมล์หน้าวัดสะพานสูง", "สาย 166, 505 และรถตู้หลายสาย", null, R.drawable.ic_bus))
                busMarkers.add(createMarker(GeoPoint(13.9319, 100.4710), "วินมอเตอร์ไซค์ หน้าวัด", "บริการรับ-ส่ง เข้าซอยและพื้นที่ใกล้เคียง", null, R.drawable.ic_motorcycle))
            }
        }

        btnAttraction.setOnClickListener {
            toggleMarkers(attractionMarkers) {
                // เพิ่มสถานที่ท่องเที่ยวพร้อม "URL รูปภาพจริง"
                attractionMarkers.add(createMarker(GeoPoint(13.9128, 100.4897), "วัดปรมัยยิกาวาส (เกาะเกร็ด)", "ชมเจดีย์เอียง สัญลักษณ์ของเกาะเกร็ด", null, R.drawable.ic_attraction, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Wat_Poramaiyikawas_01.jpg/800px-Wat_Poramaiyikawas_01.jpg"))
                attractionMarkers.add(createMarker(GeoPoint(13.9247, 100.4912), "วัดกู้ (พระนางเรือล่ม)", "วัดสำคัญทางประวัติศาสตร์ริมแม่น้ำเจ้าพระยา", null, R.drawable.ic_attraction, "https://thailandtourismdirectory.go.th/th/info/attraction/detail/itemid/3652/attachment/2"))
                attractionMarkers.add(createMarker(GeoPoint(13.9105, 100.4905), "วัดบ่อ", "วัดเก่าแก่ใจกลางเมืองปากเกร็ด", null, R.drawable.ic_attraction, "https://paimon-travel.com/wp-content/uploads/2023/10/Wat-Bo.jpg"))
                map.controller.animateTo(GeoPoint(13.9180, 100.4800))
                map.controller.setZoom(13.5)
            }
        }
    }

    private fun toggleMarkers(markerList: ArrayList<Marker>, addLogic: () -> Unit) {
        if (markerList.isNotEmpty()) {
            markerList.forEach { map.overlays.remove(it) }
            markerList.clear()
        } else {
            addLogic()
        }
        map.invalidate()
    }

    private fun createMarker(point: GeoPoint, title: String, snippet: String, imageRes: Int?, iconRes: Int? = null, imageUrl: String? = null): Marker {
        val marker = Marker(map)
        marker.position = point
        marker.title = title
        marker.snippet = snippet
        if (imageRes != null) marker.image = ContextCompat.getDrawable(this, imageRes)
        if (iconRes != null) marker.icon = ContextCompat.getDrawable(this, iconRes)
        
        // เก็บ URL รูปภาพไว้ใน relatedObject เพื่อเรียกใช้ใน InfoWindow
        marker.relatedObject = imageUrl
        
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.infoWindow = MyInfoWindow(com.example.templesp.R.layout.window_info, map)
        map.overlays.add(marker)
        return marker
    }

    inner class MyInfoWindow(layoutResId: Int, mapView: MapView) : MarkerInfoWindow(layoutResId, mapView) {
        override fun onOpen(item: Any?) {
            closeAllInfoWindowsOn(mapView)
            val marker = item as Marker
            val view = mView
            val img = view.findViewById<ImageView>(R.id.iw_image)
            
            val imageUrl = marker.relatedObject as? String
            
            if (imageUrl != null) {
                // ใช้ Glide โหลดรูปภาพจริงจาก URL
                img?.visibility = View.VISIBLE
                Glide.with(this@MapActivity)
                    .load(imageUrl)
                    .placeholder(R.drawable.temple) // รูปสำรองระหว่างโหลด
                    .error(R.drawable.temple)       // รูปกรณีโหลดพลาด
                    .into(img!!)
            } else if (marker.image != null) {
                img?.visibility = View.VISIBLE
                img?.setImageDrawable(marker.image)
            } else {
                img?.visibility = View.GONE
            }
            
            view.findViewById<TextView>(R.id.iw_title)?.text = marker.title
            view.findViewById<TextView>(R.id.iw_snippet)?.text = marker.snippet
            view.findViewById<ImageButton>(R.id.iw_btn_close)?.setOnClickListener { marker.closeInfoWindow() }
            view.findViewById<Button>(R.id.iw_btn_navigate).setOnClickListener {
                val gmmIntentUri = Uri.parse("google.navigation:q=${marker.position.latitude},${marker.position.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                } else {
                    val browserUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${marker.position.latitude},${marker.position.longitude}")
                    startActivity(Intent(Intent.ACTION_VIEW, browserUri))
                }
            }
        }
        override fun onClose() {}
    }

    private fun resizeMarkerIcon(id: Int, w: Int, h: Int): Drawable? {
        val d = ContextCompat.getDrawable(this, id)
        return if (d is BitmapDrawable) BitmapDrawable(resources, Bitmap.createScaledBitmap(d.bitmap, w, h, false)) else null
    }

    override fun onResume() { super.onResume(); myLocationOverlay.enableMyLocation(); map.onResume() }
    override fun onPause() { super.onPause(); myLocationOverlay.disableMyLocation(); map.onPause() }
}