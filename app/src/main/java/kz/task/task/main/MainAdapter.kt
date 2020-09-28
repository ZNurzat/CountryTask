package kz.task.task.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.country_item.view.*
import kz.task.task.R
import kz.task.task.model.Country

class MainAdapter (val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val viewTypeLoading = 0
    private val viewTypeNormal = 1
    private var isLoaderVisible = false
    private val items: ArrayList<Country> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            viewTypeNormal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.country_item, parent, false)
                viewHolder = AdapterHolder(itemView)
            }
            viewTypeLoading -> {
                val v2 = inflater.inflate(R.layout.item_progress_vertical, parent, false)
                viewHolder = AdapterLoading(v2)
            }

        }
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == items.size - 1) viewTypeLoading else viewTypeNormal
        } else {
            viewTypeNormal
        }
    }

    fun clear() {
        isLoaderVisible = false
        items.clear()
    }

    fun add(response: Country) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addItem(response: Country) {
        items.add(response)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        add(Country())
    }

    fun getItems(): ArrayList<Country> {
        return items
    }

    fun remove(postItems: Country) {
        val position = items.indexOf(postItems)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun remove(position: Int) {
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = items.size - 1
        val item = getItem(position)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun isEmpty(): Boolean {
        return items.size == 0
    }

    fun getItem(position: Int): Country {
        return items.get(position)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            viewTypeNormal -> {
                val h = holder as AdapterHolder

                h.title.text = items[position].name
                h.bgAbove.setBackgroundColor(Color.parseColor("#000000"))
                h.bgAbove.background.alpha = 150
                h.id.text = context.getString(R.string.id) + " " + items[position].id
                h.time.text = items[position].time.substring(0,10) + " " + items[position].time.substring(11,19)
                if(items[position].image!=null) {
                    Glide.with(context)
                        .load(items[position].image)
                        .placeholder(R.drawable.bgdf)
                        .centerCrop()
                        .into(h.bgImage)
                }else
                    Glide.with(context)
                        .load(R.drawable.bgdf)
                        .centerCrop()
                        .into(h.bgImage)
                h.updateUI(position)
            }

        }
    }


    fun addAll(postItems: List<Country>) {
        items.addAll(postItems)
        notifyDataSetChanged()
    }

    inner class AdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        val v = view
        val bgAbove = view.bgImageAboveColor
        val bgImage = view.bgImage
        val title = view.title
        val id = view.countryId
        val time = view.time
        fun updateUI(position: Int) {
            v.setOnClickListener {

            }
        }
    }

    class AdapterLoading(view: View) : RecyclerView.ViewHolder(view)

}