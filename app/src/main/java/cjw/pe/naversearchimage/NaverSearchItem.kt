package cjw.pe.naversearchimage

import com.google.gson.annotations.SerializedName

data class NaverSearchItem(
    val lastBuildDate:String,
    val total:Int,
    val start:Int,
    val display:Int,
    val items: List<SearchItem>
)