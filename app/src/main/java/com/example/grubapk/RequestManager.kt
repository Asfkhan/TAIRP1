package com.example.grubapk

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.grubapk.listeners.RandomRecipeApiResponseListener
import com.example.grubapk.models.RandomRecipeApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RequestManager() {
    lateinit var context: Context
    constructor(context: Context) : this(){
            this.context = context
    }

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getrandomrecipe(listener:RandomRecipeApiResponseListener) {
        val callRandomRecipe : CallRandomRecipe = retrofit.create(CallRandomRecipe::class.java)
        val call : Call<RandomRecipeApiResponse> = callRandomRecipe.callrandomrecipe(context.getString(R.string.apiKey),"20")
        call.enqueue(object:Callback<RandomRecipeApiResponse>{
            override fun onResponse(p0: Call<RandomRecipeApiResponse>,p1: Response<RandomRecipeApiResponse>) {
                    if (!p1.isSuccessful){
                        listener.didError(p1.message())
                        return
                    }
                p1.body()?.let { listener.didFetch(it,p1.message()) }

            }

            override fun onFailure(p0: Call<RandomRecipeApiResponse>, p1: Throwable) {
                    Log.d("RequestManager Error",p1.message.toString())
                    Toast.makeText(context,p1.message+" Error in RequestManager",Toast.LENGTH_LONG).show()
            }

        })
    }
    private interface CallRandomRecipe{
            @GET("recipes/random")
            fun callrandomrecipe(@Query("apiKey")apiKey:String,@Query("number")number:String):Call<RandomRecipeApiResponse>
    }

}