package com.example.myfirstkotlinproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.content_book.*

class BookActivity : AppCompatActivity() {
    private var bookPosition = POSITION_NOT_SET
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        setSupportActionBar(toolbar)
        setupAdapters()

        bookPosition = savedInstanceState?.getInt(BOOK_POSITION, POSITION_NOT_SET)?:
            intent.getIntExtra(BOOK_POSITION, POSITION_NOT_SET)

        if(bookPosition != POSITION_NOT_SET) {
            displayBook()
        } else {
            DataManager.insertNewBook()
            bookPosition = DataManager.books.lastIndex
        }
    }

    private fun setupAdapters() {
        val adapterGenres = ArrayAdapter<Genre>(this,
            android.R.layout.simple_spinner_item,
            DataManager.genres.toList())
        adapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGenres.adapter = adapterGenres
    }

    private fun displayBook() {
        val book = DataManager.books[bookPosition]
        textBookTitle.setText(book?.title)
        textBookAuthor.setText(book?.author)

        if(book?.genre != null){
            val genrePosition = DataManager.genres.indexOf(book.genre)
            spinnerGenres.setSelection(genrePosition)
        }
    }

    override fun onPause() {
        super.onPause()
        saveBook()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BOOK_POSITION, bookPosition)
    }

    private fun saveBook() {
        DataManager.saveBook(
            position = bookPosition,
            newTitle = textBookTitle.text.toString(),
            newAuthor = textBookAuthor.text.toString(),
            newGenre = spinnerGenres.selectedItem as Genre
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            R.id.action_prev -> {
                movePrev()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++bookPosition
        displayBook()
        invalidateOptionsMenu()
    }
    private fun movePrev(){
        --bookPosition
        displayBook()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(bookPosition >= DataManager.books.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null){
                menuItem.isVisible = false
                menuItem.isEnabled = false
            }
        }
        if(bookPosition <= 0){
            val menuItem = menu?.findItem(R.id.action_prev)
            if(menuItem != null){
                menuItem.isEnabled = false
                menuItem.isVisible = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }
}
