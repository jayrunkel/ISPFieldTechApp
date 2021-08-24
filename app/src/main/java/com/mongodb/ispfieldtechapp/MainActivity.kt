package com.mongodb.ispfieldtechapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mongodb.ispfieldtechapp.data.model.MetaDataViewModel
import com.mongodb.ispfieldtechapp.data.model.Technician
import com.mongodb.ispfieldtechapp.data.model.TechnicianCardViewModel
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    val model: TechnicianCardViewModel by viewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                //val realmApp = (this@MainActivity.application as ISPFieldTechApplication).techApp
                return TechnicianCardViewModel() as T
            }
        }
    })

    val metaData: MetaDataViewModel by viewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                //val realmApp = (this@MainActivity.application as ISPFieldTechApplication).techApp
                return MetaDataViewModel() as T
            }
        }
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
    }

/*
    override fun onFragmentInteraction(uri: Uri) {

    }
 */
}