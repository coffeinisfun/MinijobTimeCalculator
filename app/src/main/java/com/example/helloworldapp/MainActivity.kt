package com.example.helloworldapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.*

class MainActivity : AppCompatActivity() {



    //kackt rum bei double, also lieber bei int bleiben
    internal var hours  = 0.0
    internal var maxHours  = 46.0


    internal lateinit var add : Button
    internal lateinit var delete : Button
    internal lateinit var clear : Button
    internal lateinit var editText: EditText
    internal lateinit var listView: ListView
    internal lateinit var listheaderView : TextView

    var itemlist = ArrayList<String>()

    companion object {
        const val LIST_ITEMS = "LIST_ITEMS" // const key to save/read value from bundle

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing the array lists and the adapter

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)

        add = findViewById(R.id.add)
        delete = findViewById(R.id.delete)
        clear = findViewById(R.id.clear)

        editText = findViewById(R.id.editText)
        listView = findViewById(R.id.listView)
        listheaderView = findViewById(R.id.listheaderView)

        listView.adapter =  adapter

        if (savedInstanceState != null) {
            itemlist = savedInstanceState.getStringArrayList(LIST_ITEMS) as ArrayList<String>
            adapter.notifyDataSetChanged()
        }

        // Adding the items to the list when the add button is pressed
        add.setOnClickListener {


            itemlist.add(editText.text.toString())
            adapter.notifyDataSetChanged()

            // This is because every time when you add the item the input      space or the eidt text space will be cleared
            editText.text.clear()

            hours = 0.0
            itemlist.forEach {
                hours += it.toDouble()
            }
            listheaderView.text = getString(R.string.restHours, maxHours - hours)

        }


        // Selecting and Deleting the items from the list when the delete button is pressed
        delete.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    adapter.remove(itemlist.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()

            hours = 0.0
            itemlist.forEach {
                hours += it.toDouble()
            }
            listheaderView.text = getString(R.string.restHours, maxHours - hours)

        }

        // Clearing all the items in the list when the clear button is pressed
        clear.setOnClickListener {

            itemlist.clear()
            adapter.notifyDataSetChanged()

            hours = 0.0
            itemlist.forEach {
                hours += it.toDouble()
            }
            listheaderView.text = getString(R.string.restHours, maxHours - hours)

        }

    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        Log.i("MyTag", "onSaveInstanceState")
        outState.putStringArrayList(LIST_ITEMS, itemlist)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("MyTag", "onRestoreInstanceState")

        itemlist = savedInstanceState.getStringArrayList(LIST_ITEMS) as ArrayList<String>
    }

}