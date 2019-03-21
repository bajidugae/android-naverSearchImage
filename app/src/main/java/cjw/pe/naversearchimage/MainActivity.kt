package cjw.pe.naversearchimage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding.view.RxView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val clientId:String = "5VkLHFBqvvV3ASgy5bJI";
    val clientSecret:String = "YHUAw8RmK3";

    var itemList:ArrayList<SearchItem> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.adapter = SearchRecyclerViewAdapter(this,itemList)

        RxView.clicks(searchButton)
            .map { event -> searchEditText.text.toString() }
            .subscribe{ value ->
                val single:Single<NaverSearchItem> =
                RetroFitManager.naverService.searchImage(clientId,
                                                         clientSecret,
                                                         value.toString())
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
