package com.example.myfirstkotlinproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookRecyclerAdapter(private val context: Context, private val books: List<Book>) :
    RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_book_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = books.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.textTitle?.text = book.title
        holder.textAuthor?.text = book.author
        holder.bookPosition = position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textTitle = itemView.findViewById<TextView?>(R.id.textTitle)
        val textAuthor = itemView.findViewById<TextView?>(R.id.textAuthor)
        val imgDelete = itemView.findViewById<ImageView?>(R.id.imgDelete)
        var bookPosition = 0
        init {
            itemView.setOnClickListener{
                val intent = Intent(context, BookActivity::class.java)
                intent.putExtra(BOOK_POSITION, bookPosition)
                context.startActivity(intent)
            }
            imgDelete?.setOnClickListener {
                DataManager.deleteBook(bookPosition)
                notifyItemRemoved(bookPosition)
                notifyItemRangeChanged(bookPosition, books.size)
            }
        }

    }
}