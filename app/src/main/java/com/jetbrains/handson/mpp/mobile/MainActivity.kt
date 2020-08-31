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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ApplicationPresenter()
        presenter.onViewTaken(this)

        viewTrainsButton = findViewById(R.id.view_trains_button)
        viewTrainsButton.setOnClickListener { presenter.viewTrainsButtonSelected() }
    }

    override fun setInstructionText(text: String) {
        findViewById<TextView>(R.id.spinner_instructions).text = text
    }

    override fun setStations(stations: List<String>) {

        val departureSpinner = findViewById<Spinner>(R.id.departure_station_spinner)
        val arrivalSpinner = findViewById<Spinner>(R.id.arrival_station_spinner)

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        departureSpinner.adapter = adapter
        departureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                presenter.selectDepartureStation(stations[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                presenter.selectDepartureStation(null)
            }
        }

        arrivalSpinner.adapter = adapter
        arrivalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                presenter.selectArrivalStation(stations[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                presenter.selectArrivalStation(null)
            }
        }
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
}
