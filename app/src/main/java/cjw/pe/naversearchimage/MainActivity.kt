package cjw.pe.naversearchimage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val clientId:String = "5VkLHFBqvvV3ASgy5bJI";
    val clientSecret:String = "YHUAw8RmK3";

    var itemList:ArrayList<SearchItem> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.adapter = SearchRecyclerViewAdapter(this,itemList)

        searchButton.setOnClickListener {

            val currentWeather = RetroFitManager.naverService.searchImage(clientId,
                clientSecret,searchEditText.text.toString())

            currentWeather.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?,
                                        response: Response<JsonObject>?) {
                    if(response != null && response.isSuccessful) {
                        itemList.clear()
                        val jsonObject:JsonObject? = response.body();
                        val itemArray:JsonArray? = jsonObject?.getAsJsonArray("items");

                        itemArray?.let{
                            for(item in it) {
                                val itemObject:JsonObject = item.asJsonObject;
                                itemList.add( SearchItem( itemObject.get("title").asString,
                                    itemObject.get("link").asString,
                                    itemObject.get("thumbnail").asString,
                                    itemObject.get("sizeheight").asInt,
                                    itemObject.get("sizewidth").asInt))
                            }
                        }

                        searchRecyclerView.adapter.notifyDataSetChanged();
                    }
                }
                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                }
            })
        }

    }
}
