package com.jetbrains.handson.mpp.mobile

import kotlinx.coroutines.CoroutineScope

interface ApplicationContract {
    interface View {
        fun setInstructionText(text: String)
        fun setStations(stations: List<String>)
        fun updateButtonText(text: String)
        fun enableViewTrainsButton()
        fun disableViewTrainsButton()
        fun openTrainTimesLink(url: String)
    }

    abstract class Presenter: CoroutineScope {
        abstract fun onViewTaken(view: View)
        abstract fun selectDepartureStation(text: String?)
        abstract fun selectArrivalStation(text: String?)
        abstract fun viewTrainsButtonSelected()
    }
}
