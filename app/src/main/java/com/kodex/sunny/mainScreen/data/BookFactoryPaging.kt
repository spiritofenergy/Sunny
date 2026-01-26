package com.kodex.sunny.mainScreen.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.utils.firebase.FireStoreManagerPaging
import javax.inject.Inject


class BookFactoryPaging @Inject constructor(
    private val firestoreManagerPaging: FireStoreManagerPaging
): PagingSource<DocumentSnapshot, Book>() {
    override fun getRefreshKey(state: PagingState<DocumentSnapshot, Book>)
    : DocumentSnapshot? {
            return null
        }

    override suspend fun load(params: LoadParams<DocumentSnapshot>)
    : LoadResult<DocumentSnapshot, Book> {
        try {
            val currentPage = params.key
            val (snapshot, books) = firestoreManagerPaging.nextPage(
                pageSize = params.loadSize.toLong(),
                currentKey = currentPage
            )
            Log.d("MyLog", "Book List Size: ${books.size}")
            val prevKey = if (currentPage == null){
                null
            }else {
                snapshot.documents.firstOrNull()
            }
            val nextKey = snapshot.documents.lastOrNull()
            Log.d("MyLog", "prevKey: ${prevKey?.id}")
            Log.d("MyLog", "nextKey: ${nextKey?.id}")

            return LoadResult.Page(
                data = books,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }catch (e: Exception){
            return LoadResult.Error(e)
        }


    }
}
