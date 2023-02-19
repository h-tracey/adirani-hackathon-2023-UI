package com.example.hackathon.page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackathon.core.BaseFragment
import com.example.hackathon.databinding.Fragment3Binding
import com.example.hackathon.utility.GraphAdapter

open class Fragment3 constructor() : BaseFragment() {
    private var _binding: Fragment3Binding? = null
    private val _adapter: GraphAdapter by lazy { GraphAdapter(graphList) }
    private var graphList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
        setRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerAdapter() {
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this.context)
        _binding!!.recycler.adapter = _adapter
        _binding!!.recycler.layoutManager = layoutManager
    }


    override fun onResume() {
        super.onResume()
//        setListData()
    }


    private fun setListData() {
        val position = graphList.size
//        graphList.add("https://9da8-5-61-126-202.eu.ngrok.io/scatter-views?data=false")
//        _adapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment3 {
//            val fragment = Fragment3()
//            val args = Bundle()
//            args.putInt("index", index)
//            fragment.arguments = args
            return Fragment3()
        }
    }
}
