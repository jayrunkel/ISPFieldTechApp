package com.mongodb.ispfieldtechapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mongodb.ispfieldtechapp.data.model.MetaDataViewModel
import com.mongodb.ispfieldtechapp.data.model.TechnicianCardViewModel
import com.mongodb.ispfieldtechapp.data.model.TicketViewModel
import com.mongodb.ispfieldtechapp.databinding.FragmentEditTicketBinding
import android.R
import android.icu.text.SimpleDateFormat

import android.widget.ArrayAdapter
import io.realm.Realm


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditTicketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTicketFragment : Fragment() {

    private var technician: String? = null
    private var ticketNumber: Int? = null

    lateinit private var binding : FragmentEditTicketBinding

    var technicianModel : TechnicianCardViewModel? = null
    var ticketModel : TicketViewModel? = null
    var metaDataModel : MetaDataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            technician = it.getString("technician")
            ticketNumber = it.getInt("ticketNumber")
            activity?.let {
                technicianModel = ViewModelProvider(it).get(TechnicianCardViewModel::class.java)
                metaDataModel = ViewModelProvider(it).get(MetaDataViewModel::class.java)

                ticketModel = TicketViewModel(
                    technicianModel?.getTicket(ticketNumber!!), technician, ticketNumber, technicianModel?.realm)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_edit_ticket, container, false)
        binding = FragmentEditTicketBinding.inflate(inflater, container, false)

        val adapter : ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, metaDataModel!!.getStatuses())
        binding.ticketStatusSpinner.setAdapter(adapter)

        binding.submitButton.setOnClickListener() {
            val newTicketStatus : String = binding.ticketStatusSpinner.selectedItem as String
            val newComment : String = binding.commentValueView.getText().toString()

            ticketModel?.updateTicket(newTicketStatus, newComment)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dateFormat : SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val ticketStatuses : Array<String> = metaDataModel?.getStatuses() ?: Array<String>(0){""}

        binding.technicianNameValue.text = technician
        binding.ticketNumberValue.text = ticketNumber.toString()
        binding.creationDateValueView.text = dateFormat.format(ticketModel?.ticketObject?.createDate)
        if (ticketModel?.ticketObject?.completeDate != null) {
            binding.completionDateValueView.text =
                dateFormat.format(ticketModel?.ticketObject?.completeDate)
        }
        //binding.ticketStatusSpinner.setPrompt(ticketModel?.ticketObject?.status)
        binding.ticketStatusSpinner.setSelection(ticketStatuses.indexOf(ticketModel?.ticketObject?.status))
        binding.commentValueView.setText(ticketModel?.ticketObject?.description)

        Log.v("QUICKSTART", "Edit ticket for ${technicianModel?.technician}")
        Log.v("QUICKSTART", "Ticket statuses are: ${ticketStatuses}")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditTicketFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditTicketFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}