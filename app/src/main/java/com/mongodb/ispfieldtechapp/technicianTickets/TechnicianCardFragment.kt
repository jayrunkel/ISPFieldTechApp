package com.mongodb.ispfieldtechapp.technicianTickets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.ispfieldtechapp.ISPFieldTechApplication
import com.mongodb.ispfieldtechapp.data.CustomerViewModel
import com.mongodb.ispfieldtechapp.data.MetaDataViewModel
import com.mongodb.ispfieldtechapp.data.model.Technician
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

    private lateinit var binding : FragmentTechnicianCardBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterTechnicianCard: RecyclerView.Adapter<TechnicianCardRecyclerAdapter.ViewHolder>? = null

    var model : TechnicianCardViewModel? = null
    private var metaData : MetaDataViewModel? = null
    private var customers : CustomerViewModel? = null

    /*
    val model: TechnicianCardViewModel by viewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val realmApp = (requireActivity().application as ISPFieldTechApplication).techApp
                return TechnicianCardViewModel(realmApp) as T
            }
        }
    })
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {b ->
            technician = b.getString("technician")
            activity?.let {
                val realmApp = (it.application as ISPFieldTechApplication).techApp
                model = ViewModelProvider(it).get(TechnicianCardViewModel::class.java)
                model?.technician = technician

                model?.openRealm(realmApp) {
                    val messageObserver = Observer<List<Technician>?> {
                        Log.v("QUICKSTART", "Notify recycler that the data set has changed")
                        adapterTechnicianCard?.notifyDataSetChanged() //TODO: This is a hack. Should look at the change set
                    }

                    model?._technicianObject?.observe(viewLifecycleOwner, messageObserver)
                    adapterTechnicianCard?.notifyDataSetChanged()
                }

                metaData = ViewModelProvider(it).get(MetaDataViewModel::class.java)
                metaData?.openRealm(realmApp) {
                        Log.v("QUICKSTART", "Metadata realm opened")
                        //Log.v("QUICKSTART", "Ticket statuses: ${this.metaData?.getStatuses()}")
                    }
/*
                customers = ViewModelProvider(it).get(CustomerViewModel::class.java)
                customers?.openRealm(realmApp, technician!!) {
                    Log.v("QUICKSTART", "Customer realm opened")
                }
*/
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_technician_card, container, false)
        layoutManager = LinearLayoutManager(context)

        model?.let {

            adapterTechnicianCard =
                TechnicianCardRecyclerAdapter(it, requireActivity().application)
            binding = FragmentTechnicianCardBinding.inflate(inflater, container, false).apply {
                //lifecycleOwner = viewLifecycleOwner

                this.technicianCardInclude.recyclerView.layoutManager
                this.technicianCardInclude.recyclerView.adapter = adapterTechnicianCard

                //binding.contentMain.recyclerView.layoutManager = layoutManager
                //binding.contentMain.recyclerView.adapter = adapter
            }
        }
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