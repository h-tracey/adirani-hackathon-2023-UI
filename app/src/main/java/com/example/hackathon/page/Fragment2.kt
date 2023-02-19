package com.example.hackathon.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.hackathon.R
import com.example.hackathon.charts.TableChartFragment
import com.example.hackathon.core.BaseFragment
import com.example.hackathon.core.MyApplication
import com.example.hackathon.databinding.Fragment2Binding
import com.example.hackathon.gson.TableDataSealed
import com.example.hackathon.gson.TableObject
import com.example.hackathon.utility.*
import com.example.hackathon.view_models.TableViewModel
import com.google.android.material.tabs.TabLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


open class Fragment2 constructor() : BaseFragment() {
    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding!!
    private val progressView by lazy {
        LayoutInflater.from(context).inflate(R.layout.progress, null, false)
    }
    private var mTabTitle: ArrayList<String?> =
        arrayListOf("Tables", "rank conversion rate", "rank views", "rank conversion rate total")
    private val _tableFrame by lazy { FrameLayout(requireContext()) }
    private val _rankingRV1 by lazy { RecyclerView(requireContext()) }
    private val _rankingRV2 by lazy { RecyclerView(requireContext()) }
    private val _rankingRV3 by lazy { RecyclerView(requireContext()) }
    private val mRankingModelList1: ArrayList<TableObject> by lazy { ArrayList() }
    private val mRankingModelList2: ArrayList<TableObject> by lazy { ArrayList() }
    private val mRankingModelList3: ArrayList<TableObject> by lazy { ArrayList() }
    private val rvLists by lazy { arrayListOf(_rankingRV1, _rankingRV2, _rankingRV3) }
    private var adapterList: ArrayList<MainRecyclerAdapter> = arrayListOf()
    val viewPagerDataList = ArrayList<View>()
    private var _scatterData: String = ""
    private var _touchData: String = ""
    private var _conversionData: String = ""
    private val modelLists by lazy {
        arrayListOf(
            mRankingModelList1, mRankingModelList2, mRankingModelList3
        )
    }
    private val mViewModel: TableViewModel by viewModel()

    open fun Fragment2() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabLayout()
        setViewPager()
        progressView.setOnClickListener { reload() }
        _tableFrame.addView(progressView)
        mViewModel.mEvents.observe(requireActivity(), eventObserver)
    }

    fun initData() {
        val rxbusDisposable =
            MyApplication.instance.bus().toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    when {
                    }
                }
        mCompositeDisposable.add(rxbusDisposable)
        reload()

    }

    private fun reload() {
        mViewModel.requestScatterDataRetrofit()
        mViewModel.requestConversionDataRetrofit()
        mViewModel.requestTouchDataRetrofit()
    }


    private fun setViewPager() {
        viewPagerDataList.add(_tableFrame)
        repeat(3) {
            viewPagerDataList.add(rvLists[it])
            setRankingRV(it)
        }
        _binding!!.viewPager.adapter = FragmentPagerAdapter(viewPagerDataList)
        _binding!!.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(position: Int) {
                _binding!!.viewPager.offscreenPageLimit = mTabTitle.size
                val tab = _binding!!.tabLayout.getTabAt(position)
                if (_binding!!.tabLayout.selectedTabPosition != position && tab != null) {
                    tab.select()
                }
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
        _binding!!.viewPager.currentItem = 0
    }


    private fun setTabLayout() {
        _binding!!.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        _binding!!.tabLayout.addOnTabSelectedListener(tabSelectedListener)
        repeat(mTabTitle.size) { i ->
            _binding!!.tabLayout.addTab(_binding!!.tabLayout.newTab().setText(mTabTitle[i]))
        }
        _binding!!.viewPager.offscreenPageLimit = mTabTitle.size
    }

    private val tabSelectedListener: TabLayout.OnTabSelectedListener
        get() = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                _binding!!.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }

    private fun setRankingRV(index: Int) {
        rvLists[index].layoutManager = LinearLayoutManager(requireContext())
        val rankingAdapter = MainRecyclerAdapter(modelLists[index])
        adapterList.add(rankingAdapter)
        rvLists[index].adapter = rankingAdapter
//        setRankingData(modelLists[index], index)
        rankingAdapter.notifyItemInserted(rankingAdapter.itemCount - 1)
    }

    private fun setRankingData(list: ArrayList<TableObject>, index: Int) {
        when (index) {
            0 -> list.sortWith(Comparator.comparing(TableObject::conversion_rate_per_user))
            1 -> list.sortWith(Comparator.comparing(TableObject::views_per_user))
            2 -> list.sortWith(Comparator.comparing(TableObject::conversion_rate_total))
        }
        adapterList[index].notifyDataSetChanged()

    }


    private val eventObserver: EventObserver
        get() = EventObserver { eventData ->
            when (eventData.type) {
                TableViewModel.MESSAGE -> {
                    when (eventData.event1) {
                    }
                }
                TableViewModel.SCATTER_DATA -> {
                    handleTableData(eventData.event1 as String)
                    val gsonList =
                        eventData.event1.toGson<TableDataSealed>(TableDataSealed::class.java)?.array as ArrayList<TableObject>
                    repeat(3) {
                        modelLists[it].addAll(gsonList)
                        setRankingData(modelLists[it], it)
                    }
                }
                TableViewModel.CONVERSION_DATA -> handleConversionData(eventData.event1 as String)
                TableViewModel.TOUCH_DATA -> handleTouchData(eventData.event1 as String)
            }
        }

    private fun checkAllResponded() {
        if (_scatterData.isEmpty() || _conversionData.isEmpty() || _touchData.isEmpty()) return
        else replaceFragmentIntoView()
    }

    private fun replaceFragmentIntoView() {
        _tableFrame.removeAllViews()
        _tableFrame.id = R.id.frame
        FragmentUtility.replaceFragment(
            requireActivity(),
            _tableFrame.id,
            TableChartFragment.newInstance(_scatterData, _conversionData, _touchData),
            "ScatterChartFragment",
            null,
            false
        )
    }

    private fun handleTableData(_data: String) {
        _scatterData = _data
        checkAllResponded()
    }

    private fun handleConversionData(_data: String) {
        _conversionData = _data
        checkAllResponded()
    }

    private fun handleTouchData(_data: String) {
        _touchData = _data
        checkAllResponded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.finish()
    }

    companion object {
        fun newInstance(): Fragment2 {
            return Fragment2()
        }
    }
}
