package cjw.pe.naversearchimage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jakewharton.rxbinding.view.RxView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val clientId:String = "5VkLHFBqvvV3ASgy5bJI";
    val clientSecret:String = "YHUAw8RmK3";

    var itemList:ArrayList<SearchItem> = ArrayList();

    var compositeDisposable:CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.adapter = SearchRecyclerViewAdapter(this,itemList)

        searchButton.setOnClickListener {
            val single:Single<NaverSearchItem> =
                RetroFitManager.naverService.searchImage(clientId,
                                                         clientSecret,
                                                         searchEditText.text.toString())
                single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: NaverSearchItem ->
                    itemList.clear()
                    itemList.addAll( t.items )
                    searchRecyclerView.adapter.notifyDataSetChanged()
                },{ t: Throwable ->
                    t.printStackTrace()
                })
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if( actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_GO ||
                    actionId == EditorInfo.IME_ACTION_NEXT ) {
                searchButton.callOnClick()
                true
            } else
                false
        }

    }
}
