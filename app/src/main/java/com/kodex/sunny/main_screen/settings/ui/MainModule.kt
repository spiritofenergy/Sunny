package com.kodex.sunny.main_screen.settings.ui

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.kodex.sunny.utils.AuthManager
import com.kodex.sunny.utils.firebase.FireStoreManagerPaging
import com.kodex.sunny.utils.firebase.StoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    /*@Provides
    @Singleton
    fun provideYandexAdsManager(
        app: Application
    ): YandexAdsManager {
        return YandexAdsManager(app)

    }*/

    @Provides
    @Singleton
    fun provideFirebasePagingManager(
        db: FirebaseFirestore,
        auth: FirebaseAuth,
        //   storage: FirebaseStorage,
        //  app: Application
    ): FireStoreManagerPaging {
        return FireStoreManagerPaging(
            db, auth, //storage, app.contentResolver

        )
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideAuthManager(
        auth: FirebaseAuth
    ): AuthManager {
        return AuthManager(auth)
    }

    @Provides
    @Singleton
    fun provideStoreManager(
        app: Application
    ): StoreManager {
        return StoreManager(app)
    }
}


