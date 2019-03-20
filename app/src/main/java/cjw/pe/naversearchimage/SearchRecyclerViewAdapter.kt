package cjw.pe.naversearchimage

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.jakewharton.rxbinding.view.RxView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.item_image.view.*
import org.reactivestreams.Subscriber



class SearchRecyclerViewAdapter constructor(context: Context, items : ArrayList<SearchItem>)
    : RecyclerView.Adapter<SearchRecyclerViewAdapter.CustomViewHolder>() {

    private var context:Context
    private var items:ArrayList<SearchItem>

    init {
        this.context = context;
        this.items = items;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val searchItem:SearchItem = items.get(position)

        Glide.with(context)
            .load(searchItem.link)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progessBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progessBar.visibility = View.GONE
                    return false
                }
            })
            .apply(
                RequestOptions()
                .error(R.drawable.ic_error))
            .into(holder.itemImageView)

        holder.titleTextView.text = searchItem.title

        RxView
            .clicks(holder.cardViewLayout)
            .subscribe {
                val uris = Uri.parse(searchItem.link)
                val intents = Intent(Intent.ACTION_VIEW, uris)
                val b = Bundle()
                b.putBoolean("new_window", true)
                intents.putExtras(b)
                context.startActivity(intents)
            }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class CustomViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardViewLayout = itemView.cardViewLayout
        var itemImageView = itemView.itemImageView
        var titleTextView = itemView.titleTextView
        var progessBar = itemView.progressBar
    }
}