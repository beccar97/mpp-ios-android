package com.jetbrains.handson.mpp.mobile

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
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
            station.displayName
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
        val departureStation = this.departureStation
        val arrivalStation = this.arrivalStation

        if (departureStation == null || arrivalStation == null) {
            throw RuntimeException("Missing one of departure and arrival station")
        }

        getLiveTrainTimes(departureStation, arrivalStation)
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
        } else if (departureStation.crs == arrivalStation.crs) {
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
        } else if (departureStation.crs == arrivalStation.crs) {
            view.disableViewTrainsButton()
        } else {
            view.enableViewTrainsButton()
        }
    }

    private fun httpClient(): HttpClient {
        val jsonLenient = Json(
            JsonConfiguration.Stable.copy(
                ignoreUnknownKeys = true
            )
        )

        return HttpClient {
            install(JsonFeature) {
                serializer =
                    KotlinxSerializer(jsonLenient)

            }
        }
    }

    private fun getLiveTrainTimes(departureStation: Station, arrivalStation: Station) {
        val client = httpClient()

        val dateFormat = DateFormat("YYYY-MM-dd'T'HH:mm:ss.SSSXXX")
        val searchDateTime = DateTime.now().plus(DateTimeSpan(minutes = 2)).format(dateFormat)

        val url = "https://mobile-api-dev.lner.co.uk/v1/fares?" +
                "originStation=${departureStation.crs}" +
                "&destinationStation=${arrivalStation.crs}" +
                "&noChanges=false" +
                "&numberOfAdults=1" +
                "&journeyType=single" +
                "&outboundDateTime=${searchDateTime}" +
                "&outboundIsArriveBy=false"

        launch {
            val trains = client.get<FareSearchResponse>(url)
            view.setLiveTrainData(trains.outboundJourneys)
        }
    }
}
