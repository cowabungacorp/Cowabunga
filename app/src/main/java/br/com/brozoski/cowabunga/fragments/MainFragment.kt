package br.com.brozoski.cowabunga.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class MainFragment : Fragment(){

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(br.com.brozoski.cowabunga.R.layout.fragment_main, container, false)
    }




}
