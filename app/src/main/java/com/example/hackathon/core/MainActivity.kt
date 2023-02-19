package com.example.hackathon.core

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.hackathon.page.Fragment2
import com.example.hackathon.R
import com.example.hackathon.utility.RxBus
import com.example.hackathon.databinding.ActivityMainBinding
import com.example.hackathon.page.Fragment1
import com.example.hackathon.page.Fragment3
import com.example.hackathon.utility.ActivityCollector
import com.example.hackathon.utility.FragmentUtility
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedItemIdOnResume: Int = 0
    private val _fragment1: Fragment1 by lazy { Fragment1.newInstance() }
    private val _fragment2: Fragment2 by lazy { Fragment2.newInstance() }
    private val _fragment3: Fragment3 by lazy { Fragment3.newInstance() }

    private lateinit var mContainer: FrameLayout


    private val mFinishClassesList by lazy {
        arrayOf<Class<*>>(
        )
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initData()
        registerRxBus()

        selectFooterItem(savedInstanceState)
    }



    private fun initData() {
        initialized = true
    }


    private fun initUI() {
        setBottomNavigationView()
    }

    private fun selectFooterItem(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            sFragmentId = R.id.navigation_1
            setSelectedItemId(sFragmentId)
        }
    }



    private fun setSelectedItemId(itemId: Int) {
        if (!isFragmentStateSaved()) {
            binding.bottomNavigation.selectedItemId = itemId
        } else {
            selectedItemIdOnResume = itemId
        }
    }

    private fun hideLastFragment() {
        if (!isFragmentStateSaved()) {
            FragmentUtility.hideFragment(this, fragmentTag)
        }
    }

    private val fragmentTag: String
        get() = when (sFragmentId) {
            R.id.navigation_1 -> "navigation_1"
            R.id.navigation_2 -> "navigation_2"
            R.id.navigation_3 -> "navigation_3"
            else -> ""
        }

    private val fragment: Fragment?
        get() = when (sFragmentId) {
            R.id.navigation_1 -> _fragment1
            R.id.navigation_2 -> _fragment2
            R.id.navigation_3 -> _fragment3
            else -> null
        }

    private fun setBottomNavigationView() {
        setBottomNavigationViewItemSelect()
        binding.bottomNavigation.visibility = View.VISIBLE
        binding.bottomNavigation.itemIconTintList = null

        binding.bottomNavigation.setOnItemSelectedListener(navigationItemSelectedListener)
        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.visibility = View.VISIBLE

    }
    private fun setBottomNavigationViewItemSelect() {
        binding.bottomNavigation.selectedItemId = sFragmentId
    }


    private val navigationItemSelectedListener by lazy {
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            try {
                hideLastFragment()
                sFragmentId = item.itemId
                val tag = fragmentTag
                intoPage(fragment, tag)
                return@OnNavigationItemSelectedListener true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            false
        }
    }



    private fun intoPage(fragment: Fragment?, tag: String) {
        if (!isFragmentStateSaved()) {
            FragmentUtility.addOrShowFragment(this, R.id.rl_base_container, fragment, tag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun handleReceiveEvent(selectedItemId: Int) {
        finishAllExistActivity()
        setSelectedItemId(selectedItemId)
    }

    private fun finishAllExistActivity() {
        for (className in mFinishClassesList) {
            finishExistActivity(className)
        }
    }

    private fun finishExistActivity(activityClass: Class<*>) {
        if (ActivityCollector.isActivityExist<Activity>(activityClass as Class<Activity>?)) {
            ActivityCollector.getActivity<Activity>(activityClass as Class<Activity>?).finish()
        }
    }





    private fun registerRxBus() {
        mCompositeDisposable.add((application as MyApplication)
            .bus()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { `object`: Any? ->
                if (`object` is Int) {
                    when (`object`) {
                        RxBus.PAGE_1 -> handleReceiveEvent(R.id.navigation_1)
                        RxBus.PAGE_2 -> handleReceiveEvent(R.id.navigation_2)
                        RxBus.PAGE_3 -> handleReceiveEvent(R.id.navigation_3)
                    }
                }
            })
    }


    companion object {

        @JvmField
        var sFragmentId = 0
        @JvmField
        var initialized = false
    }
}
