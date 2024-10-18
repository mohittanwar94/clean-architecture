package com.example.data.model

import com.example.domain.model.Product
import com.google.gson.annotations.SerializedName


data class ProductDto (

  @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf(),
  @SerializedName("total"    ) var total    : Int?                = null,
  @SerializedName("skip"     ) var skip     : Int?                = null,
  @SerializedName("limit"    ) var limit    : Int?                = null

)

fun Products.toDomainProduct ()= Product(id = this.id, name = this.title,price=this.price)



