package aldwin.tablante.com.appblock.Account.AppBlock.Model

class OtherDevice(Serial:String,name:String) {
    var Serial= Serial
    var myId = ""
    var myName= ""
    var latitude= 0.0.toDouble()
    var longitude= 0.0.toDouble()
    constructor( ) : this("","")
}