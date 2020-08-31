package com.jetbrains.handson.mpp.mobile

import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class ApplicationPresenter: ApplicationContract.Presenter() {

    private val dispatchers = AppDispatchersImpl()
    private val job: Job = SupervisorJob()

    private var departureStation: Station? = null
    private var arrivalStation: Station? = null

    private var _view: ApplicationContract.View? = null

    private val view: ApplicationContract.View
        get() = _view ?: throw RuntimeException("Tried to access non-existent view")

    override val coroutineContext: CoroutineContext
        get() = dispatchers.main + job

    override fun onViewTaken(view: ApplicationContract.View) {
        this._view = view

        view.setInstructionText("Select departure and arrival stations to view live train times")

        view.setStations(stations = stations.sortedBy { station ->
            station.stationName
        })

        updateButton()
    }

    override fun selectDepartureStation(station: Station?) {
        departureStation = station
        updateButton()
    }

    override fun selectArrivalStation(station: Station?) {
        arrivalStation = station
        updateButton()
    }

    override fun viewTrainsButtonSelected() {
        if (departureStation == null || arrivalStation == null) {
            throw RuntimeException("Missing one of departure and arrival station")
        }

        val url =
            "https://www.lner.co.uk/travel-information/travelling-now/live-train-times/depart" +
                    "/$departureStation/$arrivalStation/#LiveDepResults"


        view.openTrainTimesLink(url)
    }

    private fun updateButton() {
        updateButtonState()
        updateSummary()
    }

    private fun updateSummary() {
        val departureStation = this.departureStation
        val arrivalStation = this.arrivalStation

        if (departureStation == null || arrivalStation == null) {
            this.view.updateButtonText("Please select departure and arrival stations")
        } else if (departureStation.stationCode == arrivalStation.stationCode) {
            this.view.updateButtonText("Departure and arrival stations cannot be the same")
        } else {
            this.view.updateButtonText("View live trains")
        }
    }

    private fun updateButtonState() {
        val departureStation = this.departureStation
        val arrivalStation = this.arrivalStation

        if (departureStation == null || arrivalStation == null) {
            view.disableViewTrainsButton()
        } else if (departureStation.stationCode == arrivalStation.stationCode) {
            view.disableViewTrainsButton()
        } else {
            view.enableViewTrainsButton()
        }
    }
}
