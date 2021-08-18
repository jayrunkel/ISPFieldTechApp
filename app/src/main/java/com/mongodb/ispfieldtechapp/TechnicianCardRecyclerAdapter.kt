package com.mongodb.ispfieldtechapp

import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.mongodb.ispfieldtechapp.data.model.Technician
import com.mongodb.ispfieldtechapp.data.model.TechnicianCardViewModel
import com.mongodb.ispfieldtechapp.data.model.Ticket
import io.realm.RealmList

class TechnicianCardRecyclerAdapter (val techCardViewModel: TechnicianCardViewModel) : RecyclerView.Adapter<TechnicianCardRecyclerAdapter.ViewHolder>() {
/*
    private val titles = arrayOf("Chapter One",
        "Chapter Two", "Chapter Three", "Chapter Four",
        "Chapter Five", "Chapter Six", "Chapter Seven",
        "Chapter Eight")

    private val details = arrayOf("Item one details", "Item two details",
        "Item three details", "Item four details",
        "Item five details", "Item six details",
        "Item seven details", "Item eight details")
*/
    private val images = intArrayOf(
        R.drawable.android_image_1,
        R.drawable.android_image_2,
        R.drawable.android_image_3,
        R.drawable.android_image_4,
        R.drawable.android_image_5,
        R.drawable.android_image_6,
        R.drawable.android_image_7,
        R.drawable.android_image_8)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.itemImage)
            itemTitle = itemView.findViewById(R.id.itemTitle)
            itemDetail = itemView.findViewById(R.id.itemDetail)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int):
            ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)

        return ViewHolder(v)
    }

    private fun getTickets() : RealmList<Ticket> {
        return techCardViewModel.getTickets()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val tickets : RealmList<Ticket> = getTickets()
        Log.v("QUICKSTART", "onBindViewHolder with ${tickets?.size} tickets")
        viewHolder.itemTitle.text =
            "${tickets[i]?.ticketNum}: ${tickets[i]?.createDate} [${tickets[i]?.status}]"
        viewHolder.itemDetail.text = tickets[i]?.description
        viewHolder.itemImage.setImageResource(images[1])
    }

    override fun getItemCount(): Int {
        val tickets = getTickets()
        return tickets.size
    }

}