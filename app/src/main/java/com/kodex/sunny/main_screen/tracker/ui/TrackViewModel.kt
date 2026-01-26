package com.kodex.gpstracker.main_screen.tracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.kodex.sunny.custom.db.MainDb
import com.kodex.sunny.main_screen.tracker.data.TrackData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val mainDb: MainDb
) : ViewModel() {
    val trackList = mainDb.trackDao.getAllTracks()
    var trackToDelete: TrackData? = null

    fun deleteTrack() = viewModelScope.launch(Dispatchers.IO) {
        trackToDelete?.let{ mainDb.trackDao.deleteTrack(it) }
    }
}