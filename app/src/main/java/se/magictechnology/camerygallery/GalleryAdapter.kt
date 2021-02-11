package se.magictechnology.camerygallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter() : RecyclerView.Adapter<GalleryViewHolder>() {

    lateinit var galleryactivity : GalleryActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val gvh = GalleryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.gallery_item,
                parent,
                false
            )
        )

        return gvh
    }

    override fun getItemCount(): Int {
        galleryactivity.imageUris?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {


        holder.galleryitemimage.setImageURI(galleryactivity.imageUris!![position])

        holder.itemView.setOnClickListener {
            galleryactivity.imageSelected(position)
        }

    }

}

class GalleryViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    var galleryitemimage = view.findViewById<ImageView>(R.id.galleryItemImage)

}