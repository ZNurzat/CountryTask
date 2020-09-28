package kz.task.task.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kz.task.task.R
import kz.task.task.model.Country
import kz.task.task.utils.Utils


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MainAdapter
    var countries: ArrayList<Country> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonFileString = Utils.getJsonFromAssets(this, "test.json")
        val gson = Gson()
        val listCountriesType =  object : TypeToken<ArrayList<Country>>() {}.type
        countries = gson.fromJson(jsonFileString, listCountriesType)
        adapter = MainAdapter(this)
        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = llm
        recyclerView.setHasFixedSize(true)
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter
        adapter.addAll(countries)
        Toast.makeText(this@MainActivity, "Swipe to left for delete ", Toast.LENGTH_LONG).show()

    }
    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            Toast.makeText(this@MainActivity, "on Move", Toast.LENGTH_SHORT).show()
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            Toast.makeText(this@MainActivity, "on Swiped ", Toast.LENGTH_SHORT).show()
            //Remove swiped item from list and notify the RecyclerView
            val position = viewHolder.adapterPosition
            countries.removeAt(position)
            adapter.remove(position)
        }
    }
}