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
    fun getAllFavesIds(
        onFaves: (List<String>) -> Unit
    ) {
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


    private fun getAllFavesBooks(
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

    private fun getFavesCategoryReference(): CollectionReference {
        return db.collection("users")
            .document(auth.uid ?: "")
            .collection("favorite")
    }

}