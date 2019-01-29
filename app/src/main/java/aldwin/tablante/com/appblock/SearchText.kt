package aldwin.tablante.com.appblock

import java.util.*

class SearchText(DeviceID:String,SearchInput:String,TimeStamp:Date) {
    var DeviceID = DeviceID
    var SearchInput = SearchInput
    var TimeStamp = TimeStamp

    constructor():this("","",Date())
}