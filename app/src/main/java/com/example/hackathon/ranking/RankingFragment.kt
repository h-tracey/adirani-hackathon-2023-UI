package com.example.hackathon.ranking

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackathon.core.BaseFragment
import com.example.hackathon.utility.EventObserver
import com.example.hackathon.utility.MainRecyclerAdapter
import com.example.hackathon.databinding.FragmentRankingBinding
import com.example.hackathon.gson.RankingListModelSealed
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RankingFragment : BaseFragment(){
//    private var _binding: FragmentRankingBinding? = null
//    private var mIndex: Int? = null
//    private val mViewModel: TableViewModel by viewModel()
//    private var rankingGson: RankingListModelSealed.RankingListGson? = null
//    private val mRankingModelList: ArrayList<RankingListModelSealed.RankingBean> = ArrayList()
//    protected var rankingAdapter: MainRecyclerAdapter? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (arguments != null) {
//            mIndex = requireArguments().getInt("index")
//        }
//        mViewModel.mEvents.observe(this, eventObserver)
//        setRecyclerAdapter()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setRecyclerView()
//    }
//
//    private fun setRecyclerView() {
//        val layoutManager = LinearLayoutManager(this.context)
//        _binding!!.recycler.adapter = rankingAdapter
//        _binding!!.recycler.layoutManager = layoutManager
//    }
//
//    private fun setRecyclerAdapter() {
//        rankingAdapter = MainRecyclerAdapter(mRankingModelList)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        setListData(null)
//    }
//
//
//    private fun setListData(gsonData: RankingListModelSealed.RankingListResponseBean?) {
////        gsonData?.response?..let {
////        }
//        repeat(5){
//            mRankingModelList.add(RankingListModelSealed.RankingBean(mIndex.toString()))
//        }
//        rankingAdapter!!.notifyDataSetChanged()
//
//    }
//
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mViewModel.finish()
//    }
//
//    private val eventObserver: EventObserver
//        get() = EventObserver {
//            when (it.type) {
//                TableViewModel.MESSAGE -> {
//                    when (it.event1) {
//                    }
//                }
//            }
//        }
//
//
//    companion object {
//        @JvmStatic
//        fun newInstance(index:Int): RankingFragment {
//            val fragment = RankingFragment()
//            val args = Bundle()
//            args.putInt("index", index)
//            fragment.arguments = args
//            return fragment
//        }
//
//    }
}
