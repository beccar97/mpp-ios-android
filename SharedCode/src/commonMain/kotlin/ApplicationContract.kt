package com.jetbrains.handson.mpp.mobile

import kotlinx.coroutines.CoroutineScope

interface ApplicationContract {
    interface View {
        fun setInstructionText(text: String)
        fun setStations(stations: List<Station>)
        fun updateButtonText(text: String)
        fun enableViewTrainsButton()
        fun disableViewTrainsButton()
        fun openTrainTimesLink(url: String)
    }

    abstract class Presenter: CoroutineScope {
        abstract fun onViewTaken(view: View)
        abstract fun selectDepartureStation(station: Station?)
        abstract fun selectArrivalStation(station: Station?)
        abstract fun viewTrainsButtonSelected()
    }
}
