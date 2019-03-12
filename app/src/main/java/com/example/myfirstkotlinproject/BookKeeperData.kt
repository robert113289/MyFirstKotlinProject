package com.example.myfirstkotlinproject

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Genre : RealmObject() {
    @PrimaryKey
    var id: String? = UUID.randomUUID().toString()
    var title: String? = null
    var books: RealmList<Book>? = null
    override fun toString(): String {
        return title?: "unknown genre"
    }
}

open class Book : RealmObject() {
    @PrimaryKey
    var id: String? = UUID.randomUUID().toString()
    var title: String? = null
    var author: String? = null
    var genre: Genre? = null


    override fun toString(): String {
        return title?: "untitled"
    }
}
