package com.example.grubapk.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.grubapk.R
import com.example.grubapk.models.Recipe
import com.squareup.picasso.Picasso

class MyAdapter(val context: Context, var list: List<Recipe>) :
    RecyclerView.Adapter<MyHolder>() {

    var row : Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
       return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (row==position){
            Toast.makeText(context,"Item Clicked",Toast.LENGTH_LONG).show()
        }
        holder.itemView.setTag(R.id.recycler_random, this)
        holder.textview.text = list[position].title
        holder.textview.isSelected = true
        Picasso.get().load(list[position].image).into(holder.imageFood)


    }

    fun updateData(filteredRecipes: List<Recipe>) {
        list = filteredRecipes
        notifyDataSetChanged()
    }
}
class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val random_list_container :CardView
    val textview : TextView
    val imageFood : ImageView


    init {
        random_list_container = itemView.findViewById(R.id.random_list_container)
        textview = itemView.findViewById(R.id.textview_title)
        imageFood = itemView.findViewById(R.id.imageView_food)

        random_list_container.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let{position->
           (itemView.context as? Activity)?.let{activity ->
                    val adapter = itemView.getTag(R.id.recycler_random)as? MyAdapter
               adapter?.let {
                   val recipe = it.list[position]
//                   Toast.makeText(activity,"Item Clicked $position",Toast.LENGTH_LONG).show()
                   val view : View = LayoutInflater.from(activity).inflate(R.layout.ingredientlayout,null)
                   val textView : TextView = view.findViewById(R.id.ingredientTvID)
                   val imagebtn : ImageView = view.findViewById(R.id.closeBtnID)
                   textView.text = recipe.instructions
                   val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
                   builder.setView(view)

                   val alertDialog : AlertDialog = builder.create()
                   alertDialog.show()
                   imagebtn.setOnClickListener {

                       alertDialog.cancel()
                   }

               }



           }

       }
    }


}