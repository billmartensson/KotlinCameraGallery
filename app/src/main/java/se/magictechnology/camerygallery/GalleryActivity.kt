package se.magictechnology.camerygallery

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {

    var adapter = GalleryAdapter()

    var imageUris : List<Uri>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        adapter.galleryactivity = this
        galleryRV.layoutManager = LinearLayoutManager(this)
        galleryRV.adapter = adapter

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            this.requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1)

        } else {
            loadImages()
        }


    }

    fun imageSelected(imagenumber : Int)
    {
        galleryImage.setImageURI(imageUris!![imagenumber])
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            Log.i("BILLDEBUG", "Permission has been denied by user")
        } else {
            Log.i("BILLDEBUG", "Permission has been granted by user")

            loadImages()
        }
    }

    fun loadImages()
    {
        val sortOrder = "${MediaStore.Video.Media.DATE_TAKEN} DESC"

        this.applicationContext.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, sortOrder)?.use { cursor ->

            // Available columns: [_id, _data, _size, _display_name, mime_type, title, date_added, date_modified, description, picasa_id, isprivate, latitude, longitude, datetaken, orientation, mini_thumb_magic, bucket_id, bucket_display_name, width, height]
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

            var iuri = mutableListOf<Uri>()

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                iuri.add(contentUri)
            }
            this.imageUris = iuri
        }
        adapter.notifyDataSetChanged()
    }
}