package com.kodex.sunny.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.kodex.spark.ui.addScreen.data.Favorite
import com.kodex.sunny.addScreen.data.Book
import javax.inject.Singleton
import kotlin.text.contains

@Singleton
class FirebaseManager(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    private fun getAllFavesIds(
        onFaves: (List<String>) -> Unit
    ) {
        Log.d("MyLog", "fun getAllFavesIds: Start")
        getFavesCategoryReference()
            .get()
            .addOnSuccessListener { task ->
                val idsList = task.toObjects(Favorite::class.java)
                val keysList = arrayListOf<String>()
                idsList.forEach {
                    keysList.add(it.key)
                }
                onFaves(keysList)
            }
            .addOnFailureListener {

            }
    }


     fun getAllFavesBooks(
        onBooks: (List<Book>) -> Unit
    ) {
        getAllFavesIds { idsList ->
            if (idsList.isNotEmpty()) {
                db.collection("books")
                    .whereIn(FieldPath.documentId(), idsList)
                    .get()
                    .addOnSuccessListener { task ->
                        val booksList = task.toObjects(Book::class.java).map {
                            if (idsList.contains(it.key)) {
                                it.copy(isFaves = true)
                            } else {
                                it
                            }
                        }
                        onBooks(booksList)
                    }
                    .addOnFailureListener { error ->
                        Log.d("MyLog", "error getAllBooks: ${error}")

                    }
            } else {
                onBooks(emptyList())
            }
        }
    }

    fun getAllBooksFromCategory(
        category: String,
        onBooks: (List<Book>) -> Unit
    ) {
        getAllFavesIds { idsList ->
            db.collection("books")
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener { task ->
                    val booksList = task.toObjects(Book::class.java).map {
                        if (idsList.contains(it.key)) {
                            it.copy(isFaves = true)
                        } else {
                            it
                        }
                    }
                    onBooks(booksList)
                    Log.d("MyLog", "fun getAllBooks: Success")
                }
                .addOnFailureListener { error ->
                    Log.d("MyLog", "error getAllBooks: ${error}")
                }
        }
    }


    fun getAllBooks(
        onBooks: (List<Book>) -> Unit
    ) {
        Log.d("MyLog", "fun getAllBooks: Start")
        getAllFavesIds { idsList ->
            db.collection("books")
                .get()
                .addOnSuccessListener { task ->
                    val booksList = task.toObjects(Book::class.java).map {
                        if (idsList.contains(it.key)) {
                            it.copy(isFaves = true)
                        } else {
                            it
                        }
                    }
                    onBooks(booksList)
                    Log.d("MyLog", "fun getAllBooks: Success")
                }
                .addOnFailureListener { error ->
                    Log.d("MyLog", "error getAllBooks: ${error}")

                }
        }
    }

    private fun onFaves(
        favorite: Favorite,
        isFaves: Boolean,
    ) {
        val favesDocRef = getFavesCategoryReference()
            .document(favorite.key)
        if (isFaves) {
                favesDocRef.set(favorite)
        } else {
               favesDocRef.delete()
        }
    }

    fun changeFavState(books: List<Book>, book: Book) : List<Book> {
        return  books.map { bk ->
            if (bk.key == book.key) {
                onFaves(
                    Favorite(book.key),
                    !bk.isFaves,
                )
                bk.copy(isFaves = !bk.isFaves)
            } else {
                bk
            }
        }
    }



     fun getFavesCategoryReference(): CollectionReference {
        return db.collection("users")
            .document(auth.uid ?: "")
            .collection("favorites")
    }

}