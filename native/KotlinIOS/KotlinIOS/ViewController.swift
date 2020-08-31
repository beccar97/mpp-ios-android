import UIKit
import SharedCode

class ViewController: UIViewController {

    @IBOutlet private var instructionText: UILabel!
    @IBOutlet var departureStationPicker: UIPickerView!
    @IBOutlet var arrivalStationPicker: UIPickerView!
    @IBOutlet var button: UIButton!

    @IBAction func viewTrainsButtonTapped(_ sender: Any) {
        presenter.viewTrainsButtonSelected()
    }


    private let presenter: ApplicationContractPresenter = ApplicationPresenter()
    private var stations: [String] = ["sad"]

    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.onViewTaken(view: self)

        departureStationPicker.delegate = self
        departureStationPicker.dataSource = self

        arrivalStationPicker.delegate = self
        arrivalStationPicker.dataSource = self
    }
}

extension ViewController: ApplicationContractView {
    func disableViewTrainsButton() {
        button.isEnabled = false
    }

    func enableViewTrainsButton() {
        button.isEnabled = true
    }

    func openTrainTimesLink(url: String) {
        let appDelegate = UIApplication.shared

        appDelegate.open(URL.init(string: url)!)
    }

    func setInstructionText(text: String) {
        instructionText.text = text
    }

    func setStations(stations: [String]) {
        self.stations = stations
    }

    func updateButtonText(text: String) {
        button.setTitle(text, for: .normal)
    }
}

extension ViewController : UIPickerViewDelegate, UIPickerViewDataSource {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }

    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return stations.count + 1
    }

    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if row > 0{
            return stations[row - 1]
        } else {
            return ""
        }
    }

    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        let stationName = row > 0 ? stations[row - 1] : ""

        if pickerView == departureStationPicker {
            presenter.selectDepartureStation(text: stationName)
        } else {
            presenter.selectArrivalStation(text: stationName)
        }
    }

}
