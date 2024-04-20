package com.example.grubapk.listeners

import com.example.grubapk.models.RandomRecipeApiResponse

interface RandomRecipeApiResponseListener {
    fun didFetch(response: RandomRecipeApiResponse,message :String)
    fun didError(message: String)
}