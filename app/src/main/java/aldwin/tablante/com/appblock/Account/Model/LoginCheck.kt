package aldwin.tablante.com.appblock.Account.Model

/**
 * Created by Bobby on 04/05/2018.
 */
class LoginCheck {


    fun isFilled(username: String, password: String): Boolean {
        var bool = true

        if (username == "" && password == "") {

            bool = false
        }

        return bool
    }
}