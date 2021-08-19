package com.mongodb.ispfieldtechapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.ispfieldtechapp.data.model.Technician
import com.mongodb.ispfieldtechapp.data.model.TechnicianCardViewModel
import com.mongodb.ispfieldtechapp.data.model.Ticket
import com.mongodb.ispfieldtechapp.databinding.FragmentTechnicianCardBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// This property is only valid between onCreateView and
// onDestroyView.


/**
 * A simple [Fragment] subclass.
 * Use the [TechnicianCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TechnicianCardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var technician: String? = null

    lateinit private var binding : FragmentTechnicianCardBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterTechnicianCard: RecyclerView.Adapter<TechnicianCardRecyclerAdapter.ViewHolder>? = null

    val model: TechnicianCardViewModel by viewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val realmApp = (requireActivity().application as ISPFieldTechApplication).techApp
                return TechnicianCardViewModel(realmApp) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            technician = it.getString("technician")
            model.setTechnician(technician!!)
            model.openRealm {
                val messageObserver = Observer<List<Technician>?> { _ ->
                    Log.v("QUICKSTART", "Notify recycler that the data set has changed")
                    adapterTechnicianCard?.notifyDataSetChanged() //TODO: This is a hack. Should look at the change set
                }

                model._technicianObject?.observe(viewLifecycleOwner, messageObserver)
                adapterTechnicianCard?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_technician_card, container, false)
        layoutManager = LinearLayoutManager(context)
        adapterTechnicianCard = TechnicianCardRecyclerAdapter(model)
        binding = FragmentTechnicianCardBinding.inflate(inflater, container, false).apply {
            //lifecycleOwner = viewLifecycleOwner
        }
        binding.technicianCardInclude.recyclerView.layoutManager
        binding.technicianCardInclude.recyclerView.adapter = adapterTechnicianCard

        //binding.contentMain.recyclerView.layoutManager = layoutManager
        //binding.contentMain.recyclerView.adapter = adapter

        return binding.root
    }
/*
    override fun onStart() {
        super.onStart()
 //     TODO: ADD CODE TO UPDATE RECYCLER WHEN REALM CHANGES

    }
*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TechnicianCardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TechnicianCardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}