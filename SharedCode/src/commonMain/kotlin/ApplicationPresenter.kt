package com.jetbrains.handson.mpp.mobile

import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext
import com.soywiz.klock.*

class ApplicationPresenter: ApplicationContract.Presenter() {

    private val dispatchers = AppDispatchersImpl()
    private val job: Job = SupervisorJob()

    private var departureStation: String? = null
    private var arrivalStation: String? = null


    private var _view: ApplicationContract.View? = null

    private val view: ApplicationContract.View
        get() = _view ?: throw RuntimeException("Tried to access non-existent view")

    override val coroutineContext: CoroutineContext
        get() = dispatchers.main + job

    override fun onViewTaken(view: ApplicationContract.View) {
        this._view = view

        view.setInstructionText("Select departure and arrival stations to view live train times")

        val stations = listOf("KGX", "PBO", "EDB", "OXF", "MYB")

        view.setStations(stations)
        updateButton()
    }

    override fun selectDepartureStation(text: String?) {
        departureStation = text
        updateButton()
    }

    override fun selectArrivalStation(text: String?) {
        arrivalStation = text
        updateButton()
    }

    override fun viewTrainsButtonSelected() {
        if (departureStation.isNullOrBlank() || arrivalStation.isNullOrBlank()) {
            throw RuntimeException("Missing one of departure and arrival station")
        }

        val currentDateTime = DateTime.now().plus(DateTimeSpan(minutes = 1))
        val dateFormat = DateFormat("YYYY-MM-dd'T'HH:mm:ss.SSSXXX")
        val url = "https://mobile-api-dev.lner.co.uk/v1/fares?" +
                "originStation=$departureStation" +
                "&destinationStation=$arrivalStation" +
                "&numberOfAdults=1" +
                "&journeyType=single" +
                "&outboundDateTime=${currentDateTime.toString(dateFormat)}" +
                "&outboundIsArriveBy=false"

        println("URL: $url")

        view.openTrainTimesLink(url)
    }

    private fun updateButton() {
        updateButtonState()
        updateSummary()
    }

    private fun updateSummary() {
        if (departureStation.isNullOrBlank() || arrivalStation.isNullOrBlank()) {
            this.view.updateButtonText("Please select departure and arrival stations")
        } else if (departureStation == arrivalStation) {
            this.view.updateButtonText("Departure and arrival stations cannot be the same")
        } else {
            this.view.updateButtonText("View live trains from $departureStation to $arrivalStation")
        }
    }

    private fun updateButtonState() {
        if (departureStation.isNullOrBlank() || arrivalStation.isNullOrBlank()) {
            view.disableViewTrainsButton()
        } else if (departureStation == arrivalStation) {
            view.disableViewTrainsButton()
        } else {
            view.enableViewTrainsButton()
        }
    }
}
