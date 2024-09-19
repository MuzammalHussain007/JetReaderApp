package com.dummy.jetreaderapp.data

import kotlin.random.Random

data class MBook(
    val id: String?="",
    var title : String?="",
    var authors : String?="",
    var notes : String?="",var thambnail : String?=""){

    fun getBooks(): MutableList<MBook> {
        return mutableListOf(
            MBook("1", "Teach Yourself Java  in 21 Days", "Charles L. Perkins", "ABC","http://books.google.com/books/content?id=2Q97QgAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api"),
            MBook("2", "Programming in Python 3", "Mark Summerfield", "ABC","http://books.google.com/books/content?id=H9emM_LGFDEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api"),
            MBook("3", "Featuring the ANSI C Standard", "BMike Banahan", "ABC","http://books.google.com/books/content?id=XXdyQgAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api"),
            MBook("4", "Android", "Scott La Counte", "ABC","http://books.google.com/books/content?id=rpBNEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
            MBook("5", "Angular Projects", "Aristeidis Bampakos", "ABC","http://books.google.com/books/content?id=huPLEAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api"),
            MBook("6", "Learning Node.js", "Marc Wandschneider", "ABC","http://books.google.com/books/content?id=AmMibho26OEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api"),
            MBook("7", "Android Development with Kotlin", "Marcin Moskala", "ABC","http://books.google.com/books/content?id=PJZGDwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api"),
            MBook("8", "Programming PHP", "Kevin Tatroe", "ABC","http://books.google.com/books/content?id=h-E1lVko-skC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")
        )
    }

    fun searchBookByTitle(title: String): List<MBook> {
        return getBooks().filter { it.title!!.contains(title, ignoreCase = true) }
    }

    fun getSingleBook(): MBook {
        val books = getBooks()
        return books[Random.nextInt(books.size)]
    }

}