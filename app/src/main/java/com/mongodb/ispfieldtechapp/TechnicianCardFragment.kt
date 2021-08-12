package com.mongodb.ispfieldtechapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.ispfieldtechapp.RecyclerAdapter
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
    private var param1: String? = null
    private var param2: String? = null

    lateinit private var binding : FragmentTechnicianCardBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_technician_card, container, false)
        layoutManager = LinearLayoutManager(context)
        adapter = RecyclerAdapter()
        binding = FragmentTechnicianCardBinding.inflate(inflater, container, false)
        binding.technicianCardInclude.recyclerView.layoutManager
        binding.technicianCardInclude.recyclerView.adapter = adapter


        //binding.contentMain.recyclerView.layoutManager = layoutManager
        //binding.contentMain.recyclerView.adapter = adapter

        return binding.root
    }

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