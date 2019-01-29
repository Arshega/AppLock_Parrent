package aldwin.tablante.com.appblock

import ca.antonious.materialdaypicker.MaterialDayPicker

class SettingSchedules(Days: ArrayList<String>, From: String, To:String) {
    var Days = Days
    var From = From
    var To = To

    constructor(): this(ArrayList(), "", "")
}