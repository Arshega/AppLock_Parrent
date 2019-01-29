package aldwin.tablante.com.appblock.Account.Model

/**
 * Created by Bobby on 03/05/2018.
 */
class User(accID: String, username: String, password: String, email: String, code: String, fname: String, lname: String) {

    var username = username
    var password = password
    var email = email
    var accID = accID
    var codd = code
    var Firstname = fname
    var Lastname = lname
    var DeviceModel = "Model: " + android.os.Build.MODEL + " " + android.os.Build.BRAND

    constructor() : this("", "", "", "", "", "", "")


}