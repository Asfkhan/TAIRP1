package com.example.grubapk

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grubapk.adapter.MyAdapter
import com.example.grubapk.listeners.RandomRecipeApiResponseListener
import com.example.grubapk.models.RandomRecipeApiResponse

class MainActivity : AppCompatActivity() {
    lateinit var editText :EditText
    lateinit var adapter : MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        val context : MainActivity = this
        val manager = RequestManager(context)
        editText = findViewById(R.id.editTextText)

        Toast.makeText(this@MainActivity,"Greeting for the Day",Toast.LENGTH_LONG).show()

        manager.getrandomrecipe(object:randomreciperesponselistner{
            override fun didFetch(response: RandomRecipeApiResponse, message: String){
               val recylerview : RecyclerView = findViewById(R.id.recycler_random)
                recylerview.setHasFixedSize(true)
                recylerview.layoutManager = GridLayoutManager(context,1)
                adapter = MyAdapter(context,response.recipes)
                recylerview.adapter = adapter
                editText.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // Not is use
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val filteredRecipes = response.recipes.filter { recipe ->
                            recipe.title.contains(s.toString(), ignoreCase = true)
                        }
                        adapter.updateData(filteredRecipes)
                    }

                    override fun afterTextChanged(s: Editable?) {
                        //  Not in Use
                    }
                })
            }

            override fun didError(message: String) {
                Toast.makeText(context, "$message Error in RequestManager", Toast.LENGTH_LONG).show()
            }

        })
    }
    private interface randomreciperesponselistner: RandomRecipeApiResponseListener {
        override fun didFetch(response: RandomRecipeApiResponse, message: String)
        override fun didError(message: String)
    }
}