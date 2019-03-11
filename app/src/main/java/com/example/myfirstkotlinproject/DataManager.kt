package com.example.myfirstkotlinproject

import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where
import java.util.*
import kotlin.collections.ArrayList

object DataManager {
    val genres = Realm.getDefaultInstance().where<Genre>().findAll().distinct()
    var books = Realm.getDefaultInstance().where<Book>().findAll()

    init{
        initializeBooks()
        initializeGenres()
    }

    private fun initializeBooks() {
        Realm.getDefaultInstance().use { r ->
            r.executeTransaction {realm ->
                val book: Book = realm.copyToRealmOrUpdate(Book().apply {
                    title = "Harry Potter"
                    author = "Suzanne Collins"
                })
                realm.insertOrUpdate(Genre().apply{
                    title = "Fantasy"
                    books = RealmList<Book>().apply{
                        add(book)
                    }
                })
            }
        }
    }

    private fun initializeGenres() {
        val genreList = ArrayList<String>()
        //genreList.add("Fantasy")
        genreList.add("Western")
        genreList.add("Romance")
        genreList.add("Thriller")
        genreList.add("Mystery")
        genreList.add("Detective story")
        genreList.add("Dystopia")
        genreList.add("Memoir")
        genreList.add("Biography")
        genreList.add("Play")
        genreList.add("Musical")
        genreList.add("Satire")
        genreList.add("Haiku")
        genreList.add("Horror")
        genreList.add("DIY (Do It Yourself)")

        for(genre in genreList) {
            Realm.getDefaultInstance().executeTransaction { realm ->
                realm.insertOrUpdate(Genre().apply {
                    title = genre
                })
            }
        }
    }

    fun saveBook(position: Int, newTitle: String, newAuthor: String, newGenre: Genre) {
        val book = DataManager.books[position]
        Realm.getDefaultInstance().executeTransaction { realm ->
            realm.insertOrUpdate(Book().apply{
                id = book?.id
                title = newTitle
                author = newAuthor
                genre = newGenre
            })
        }
    }

    fun deleteBook(bookPosition: Int) {
        Realm.getDefaultInstance().executeTransaction {
            books.deleteFromRealm(bookPosition)
        }
    }

    fun insertNewBook(){
        Realm.getDefaultInstance().use { r ->
            r.executeTransaction {realm ->
                val book: Book = realm.copyToRealmOrUpdate(Book().apply {
                    title = ""
                    author = ""
                })
                realm.insertOrUpdate(Genre().apply{
                    title = "Fantasy"
                    books = RealmList<Book>().apply{
                        add(book)
                    }
                })
            }
        }
    }
}