import UIKit
import SharedCode

class ViewController: UIViewController {

    @IBOutlet private var label: UILabel!

    private let presenter: ApplicationContractPresenter = ApplicationPresenter()
    private var data: [String] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.onViewTaken(view: self)

        for i in 0...10 {
             data.append("Item \(i)")
        }
    }
}

extension ViewController: ApplicationContractView {
    func setLabel(text: String) {
        label.text = text
    }
}

extension ViewController: UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellReuseIdentifier") as! CustomTableViewCell

        let text = data[indexPath.row]

        cell.label.text = text

        return cell
    }
}
