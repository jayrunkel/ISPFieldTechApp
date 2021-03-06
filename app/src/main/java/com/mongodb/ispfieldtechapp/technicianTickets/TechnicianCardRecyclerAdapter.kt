package com.mongodb.ispfieldtechapp.technicianTickets

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mongodb.ispfieldtechapp.R
import com.mongodb.ispfieldtechapp.data.model.Ticket
import io.realm.RealmList
import java.util.*

class TechnicianCardRecyclerAdapter (private val techCardViewModel: TechnicianCardViewModel, val app: Application) : RecyclerView.Adapter<TechnicianCardRecyclerAdapter.ViewHolder>() {
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
/*
    private val images = intArrayOf(
        R.drawable.android_image_1,
        R.drawable.android_image_2,
        R.drawable.android_image_3,
        R.drawable.android_image_4,
        R.drawable.android_image_5,
        R.drawable.android_image_6,
        R.drawable.android_image_7,
        R.drawable.android_image_8)
*/
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var ticketNumber : Int? = null

        init {
            itemImage = itemView.findViewById(R.id.itemImage)
            itemTitle = itemView.findViewById(R.id.itemTitle)
            itemDetail = itemView.findViewById(R.id.itemDetail)

            itemView.setOnClickListener { v: View ->
                val position: Int = bindingAdapterPosition

                Snackbar.make(v, "Click detected on item $position",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()

                val action : TechnicianCardFragmentDirections.ActionTechnicianCardFragmentToEditTicketFragment =
                    TechnicianCardFragmentDirections.actionTechnicianCardFragmentToEditTicketFragment(
                        techCardViewModel.technician!!,
                        ticketNumber!!
                    )
                Navigation.findNavController(v).navigate(action)

            }
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
        Log.v("QUICKSTART", "onBindViewHolder with ${tickets.size} tickets")
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
        viewHolder.itemTitle.text = app.resources.getString(R.string.ticketCardTitle, tickets[i]?.ticketNum, dateFormat.format(tickets[i]?.createDate), tickets[i]?.status)
        viewHolder.itemDetail.text = tickets[i]?.description
        viewHolder.ticketNumber = tickets[i]?.ticketNum
        when (tickets[i]?.status ?: "unknown") {
            "open" -> viewHolder.itemImage.setImageResource(R.drawable.open)
            "in_progress" -> viewHolder.itemImage.setImageResource(R.drawable.inprogress)
            "closed" -> viewHolder.itemImage.setImageResource(R.drawable.completed)
            else -> viewHolder.itemImage.setImageResource(R.drawable.android_image_1)
        }
    }

    override fun getItemCount(): Int {
        val tickets = getTickets()
        return tickets.size
    }

}