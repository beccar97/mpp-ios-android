package com.jetbrains.handson.mpp.mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ApplicationContract.View {
    lateinit var presenter: ApplicationContract.Presenter
    private lateinit var viewTrainsButton: Button
    private var stations: List<Station> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ApplicationPresenter()

        setUpDropdowns()

        viewTrainsButton = findViewById(R.id.view_trains_button)
        viewTrainsButton.setOnClickListener { presenter.viewTrainsButtonSelected() }

        presenter.onViewTaken(this)
    }

    override fun setInstructionText(text: String) {
        findViewById<TextView>(R.id.spinner_instructions).text = text
    }

    override fun setStations(stations: List<Station>) {
        this.stations = stations

        val departureSpinner = findViewById<Spinner>(R.id.departure_station_spinner)
        val arrivalSpinner = findViewById<Spinner>(R.id.arrival_station_spinner)

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                listOf("") + stations.map { station -> "${station.stationCode} - ${station.stationName}" })

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        departureSpinner.adapter = adapter
        arrivalSpinner.adapter = adapter
    }

    override fun updateButtonText(text: String) {
        viewTrainsButton.text = text
    }

    override fun enableViewTrainsButton() {
        viewTrainsButton.isEnabled = true
    }

    override fun disableViewTrainsButton() {
        viewTrainsButton.isEnabled = false
    }

    override fun openTrainTimesLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setUpDropdowns() {
        val departureSpinner = findViewById<Spinner>(R.id.departure_station_spinner)
        val arrivalSpinner = findViewById<Spinner>(R.id.arrival_station_spinner)

        departureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                val selected = if (position > 0) {
                    stations[position - 1]
                } else null

                presenter.selectDepartureStation(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                presenter.selectDepartureStation(null)
            }
        }

        arrivalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                val selected = if (position > 0) {
                    stations[position - 1]
                } else null

                presenter.selectArrivalStation(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                presenter.selectArrivalStation(null)
            }
        }
    }
}
