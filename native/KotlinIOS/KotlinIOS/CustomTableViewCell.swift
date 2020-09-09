//
//  CustomTableViewCell.swift
//  KotlinIOS
//
//  Created by Becky Carter on 06/09/2020.
//

import UIKit

class CustomTableViewCell: UITableViewCell {
    @IBOutlet weak var label: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
