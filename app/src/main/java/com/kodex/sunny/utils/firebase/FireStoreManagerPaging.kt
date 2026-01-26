package com.kodex.sunny.utils.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.addScreen.data.Favorite
import com.kodex.sunny.detailScreen.data.RatingData
import com.kodex.sunny.utils.Categories
import com.kodex.sunny.utils.firebase.FilterData
import com.kodex.sunny.utils.firebase.FirebaseConst
import com.kodex.sunny.utils.firebase.FirebaseConst.BOOK_RATING
import com.kodex.sunny.utils.firebase.FirebaseConst.MODERATION_RATING
import com.kodex.sunny.utils.firebase.FirebaseConst.POSTS
import com.kodex.sunny.utils.firebase.FirebaseConst.RATING
import dagger.Provides
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

const val IS_BASE_64 = true

@Singleton
class FireStoreManagerPaging(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    //private val contentResolver: ContentResolver
   // private val storage: FirebaseStorage,
) {
    var categoryIndex = Categories.ALL
    var searchText = ""
    var filterData = FilterData()

/*
    var minPrice = 0
    var maxPrice = 5000
    var isTitleFilter = false
    var isPriceFilter = false
*/

    suspend fun nextPage(
        pageSize: Long,
        currentKey: DocumentSnapshot?,
    ): Pair<QuerySnapshot, List<Book>> {
        var query: Query = db.collection(FirebaseConst.POSTS)
            .limit(pageSize)
            .orderBy(filterData.filterType)

        val keysFavesList = getIdsFavesList()

        query = when (categoryIndex) {
            Categories.ALL -> query
            Categories.FAVORITES -> query.whereIn(FieldPath.of(FirebaseConst.KEY), keysFavesList)
            else -> query.whereEqualTo(FirebaseConst.CATEGORY_INDEX, categoryIndex)
        }

        if (searchText.isNotEmpty()){
            query = query.whereGreaterThanOrEqualTo(FirebaseConst.SEARCH_TITLE, searchText.lowercase())
                .whereLessThan(FirebaseConst.SEARCH_TITLE,"${searchText.lowercase()}\uF7FF") // "test"
        }

        if (filterData.filterType == FirebaseConst.PRICE
            && filterData.minPrice != 0
            && filterData.maxPrice != 0
            && filterData.minPrice <= filterData.maxPrice
        ) {
            query = query.whereGreaterThanOrEqualTo(FirebaseConst.PRICE, filterData.minPrice)
                .whereLessThanOrEqualTo(FirebaseConst.PRICE, filterData.maxPrice)
        }

      /*  if (!isPriceFilter) {
            query = query.whereGreaterThanOrEqualTo(FirebaseConst.PRICE, minPrice)
                .whereLessThanOrEqualTo(FirebaseConst.PRICE, maxPrice)
        }*/
        if (currentKey != null) {
            query = query.startAfter(currentKey)
        }
        val querySnapshot = query.get().await()
        val books = querySnapshot.toObjects(Book::class.java)
        val updatedBooks = books.map {
            if (keysFavesList.contains(it.key)) {
                it.copy(isFaves = true)
            } else {
                it
            }
        }
        return Pair(querySnapshot, updatedBooks)
    }


    private suspend fun getIdsFavesList(): List<String> {
        var snapshot = getFavesCategoryReference().get().await()
        val idsList = snapshot.toObjects(Favorite::class.java)
        val keysList = arrayListOf<String>()

        idsList.forEach {
            keysList.add(it.key)
        }
        return if (keysList.isEmpty()) listOf("-1") else keysList
    }

    fun getFavesCategoryReference(): CollectionReference {
        return db.collection(FirebaseConst.USERS)
            .document(auth.uid ?: "")
            .collection(FirebaseConst.FAVES)
    }

    fun onFaves(
        favorite: Favorite,
        isFav: Boolean,
    ) {
        val favesDokRef = getFavesCategoryReference()
            .document(favorite.key)
        if (isFav) {
            favesDokRef.set(favorite)
        } else {
            favesDokRef.delete()
        }
    }

    fun changeFavState(books: List<Book>, book: Book): List<Book> {
        return books.map { bk ->
            if (bk.key == book.key) {
                onFaves(
                    Favorite(bk.key),
                    !bk.isFaves
                )
                bk.copy(isFaves = !bk.isFaves)
            } else {
                bk
            }
        }
    }

    fun deleteBook(
        book: Book,
        onDeleted: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        db.collection(FirebaseConst.POSTS)
            .document(book.key)
            .delete()
            .addOnSuccessListener {
                onDeleted()
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Error deleting book")

            }
    }

    fun saveBookToFireStore(
        book: Book,
        onSaved: () -> Unit,
        onError: (String) -> Unit,
    ) {
        val db = db.collection(FirebaseConst.POSTS)
        val key = if (book.key.isEmpty()) db.document().id else book.key
        db.document(key)
            .set(
                book.copy(key = key)
            ).addOnSuccessListener {
                onSaved()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Error saved book")
            }
        onError
    }

    private fun uploadImageToFirestore(
        oldImageUrl: String,
        uri: Uri?,
        book: Book,
        onSaved: () -> Unit,
        onError: (String) -> Unit,
    ) {
        val timeStamp = System.currentTimeMillis()
        val storageRef = if (oldImageUrl.isEmpty()) {
           // storage!!.reference
             //   .child("spark_posts")
             //   .child("image_$timeStamp.jpg")
        } else {
           // storage?.getReferenceFromUrl(oldImageUrl)
        }
        if (uri == null) {
            saveBookToFireStore(
                book.copy(imageUrl = oldImageUrl),
                onSaved = {
                    onSaved()
                },
                onError = { massage ->
                    onError(massage)
                }
            )
            return
        }
//        val imageBytes = ImageUtils.uriToBiteArray(uri, contentResolver)
//        val uploadTask = storageRef?.putBytes(imageBytes)
//        uploadTask?.addOnSuccessListener{
//            storageRef.downloadUrl.addOnSuccessListener{url ->
//                saveBookToFireStore(
//                    book.copy(imageUrl = url.toString()),
//                    onSaved = {
//                        onSaved()
//                    },
//                    onError = {massage->
//                        onError(massage)
//                    }
//                )
//            }
//        }
    }

    fun saveBookImage(
        oldImageUrl: String,
        uri: Uri?,
        book: Book,
        onSaved: () -> Unit,
        onError: (String) -> Unit,
    ) {
        if (IS_BASE_64) {
            saveBookToFireStore(
                book,
                onSaved = {
                    onSaved()
                },
                onError = {
                    onError("Error save Image1 ")
                },
            )
        } else {
            uploadImageToFirestore(
                oldImageUrl = oldImageUrl,
                uri = uri,
                book = book,
                onSaved = {
                    onSaved()
                },
                onError = {
                    onError("Error save Image2")
                }
            )
        }
    }
    fun insertUserComment(ratingData: RatingData, bookId: String){
        if (auth.uid == null) return
        db.collection(MODERATION_RATING)
            .document(auth.uid!!)
            .set(ratingData.copy(
                name = auth.currentUser?.email ?: "Unknown",
                uid = auth.uid!!,
                bookId = bookId))
    }

   suspend fun insertModerationRating(ratingData: RatingData){
        if (auth.uid == null) return
        db.collection(BOOK_RATING)
            .document(ratingData.bookId)
            .collection(RATING)
            .document(ratingData.uid)
            .set(ratingData)

       val book: Book = db.collection(POSTS)
           .document(ratingData.bookId)
           .get().await().toObject(Book::class.java)?: return
       val ratingsList = book.ratingsList.toMutableList()
       if (ratingData.lastRating == 0){
           ratingsList.add(ratingData.lastRating)

       }else {
           val index = ratingsList.indexOf(ratingData.lastRating)
           ratingsList[index] = ratingData.rating
       }
       db.collection(POSTS)
           .document(ratingData.bookId)
           .update("ratingsList", ratingsList)
    }

   suspend fun getCommentsToModerate(): List<RatingData>{
        val querySnapshot = db.collection(MODERATION_RATING)
            .get().await()
       val commentsList = querySnapshot.toObjects(RatingData::class.java)
      return commentsList
    }

   suspend fun deleteComment(uid: String){
       db.collection(MODERATION_RATING)
           .document(uid)
           .delete().await()
   }

   suspend fun getRating(bookId: String): Pair<Double, List<RatingData>>{
        val querySnapshot =
        db.collection(BOOK_RATING)
            .document(bookId)
            .collection(RATING)
            .get().await()
       val ratingsList = querySnapshot.toObjects(RatingData::class.java)
       val averageRating = ratingsList.map { it.rating }.average()
      return Pair(averageRating, ratingsList)
    }

   suspend fun getUserRating(bookId: String): RatingData?{
       if(auth.uid == null) return null
        val querySnapshot = db.collection(BOOK_RATING)
            .document(bookId)
            .collection(RATING)
            .document(auth.uid!!)
            .get().await()
     return  querySnapshot.toObject(RatingData::class.java)

    }
}
