package com.example.ecommercedemo

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercedemo.Adapter.RecyclerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.shivansh.tablayoutrecyclerviewmediator.TabLayoutRecyclerViewMediator

class MainActivity : AppCompatActivity() {

    private lateinit var rv_list: RecyclerView
    private lateinit var tabs: TabLayout
    private lateinit var mAppBarLayout: AppBarLayout
    private lateinit var expandedImage: ImageView
    private lateinit var mToolBar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){
        rv_list = findViewById(R.id.rv_list)
        tabs = findViewById(R.id.tabs)
        expandedImage = findViewById(R.id.expandedImage)
        mAppBarLayout = findViewById(R.id.app_bar)
        mToolBar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolBar);
        mAppBarLayout.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when(state) {
                    State.COLLAPSED -> expandedImage.visibility = View.GONE
                    State.EXPANDED -> expandedImage.visibility = View.VISIBLE
                    State.IDLE -> { /* Do something */ }
                }
            }
        }
        )
        setupRecyclerViewWithTabs()
    }

    private fun setupRecyclerViewWithTabs(){

        // Data
        val itemList = listOf("Breakfast", "Pasta", "Beverage", "Sides", "Pasta",
                "Pizza", "Beverage", "Pasta","Cheese","Onion","Tomato", "SALAD","Veg","egg","Non veg","Corn","Panner")

        val headerList = listOf("Breakfast", "Sides", "Pizza", "SALAD")
        //val headerList = listOf("Asia", "Africa", "Europe")

        val isHeaderList = listOf(true, false, false, true, false, true, false, false,false,false,false, true,false,false,false,false,false)

        // Setup recycler view
        val adapter = RecyclerAdapter(itemList, isHeaderList)
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // processing data to map recyclerView items to tabs in tabLayout
        val headerMap = setHeaderMap(isHeaderList)
        // processing data to map tabs in tabLayout to view index of recyclerView
        val itemMap = setItemMap(isHeaderList)

        // Attach tabLayout to recyclerView
        val headerToItemMapping = { position: Int -> itemMap[position] }
        val itemToHeaderMapping = { position: Int -> headerMap[position] }
        TabLayoutRecyclerViewMediator(rv_list, headerList.size, tabs, headerToItemMapping, itemToHeaderMapping) { tab, position ->
            tab.text = headerList[position]
        }.attach()
    }

    private fun setItemMap(isHeader: List<Boolean>): Map<Int, Int> {
        val itemMap = mutableMapOf<Int, Int>()
        var headerIndex = -1
        for ((itemCount, hIndex) in isHeader.indices.withIndex()) {
            if (isHeader[hIndex]) {
                headerIndex++
                itemMap[headerIndex] = itemCount
            }
        }
        return itemMap
    }

    private fun setHeaderMap(headerList: List<Boolean>): Map<Int, Int> {
        var currentHeader = -1
        val headerMap = mutableMapOf<Int, Int>()
        for (index in headerList.indices) {
            if (headerList[index]) currentHeader++
            headerMap[index] = currentHeader
        }
        return headerMap
    }
}