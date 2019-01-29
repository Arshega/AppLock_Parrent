package aldwin.tablante.com.appblock.Histories

import aldwin.tablante.com.appblock.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.history.*

class HistoryView:AppCompatActivity() {

    var id = ""

    val bundle = Bundle()
    val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        val iin = intent
        var b: Bundle = iin.extras
        bundle.putString("serial", b.getString("serial"))

        showSearchHistory()
        youtubeHistory.setOnClickListener { showYoutubeHistory()}
        searchHistory.setOnClickListener { showSearchHistory()}

    }


    fun showYoutubeHistory(){
        val transaction = manager.beginTransaction()
        val fragment = YoutubeHistory_Fragment()
        fragment.arguments = bundle
        transaction.replace(R.id.fragholder, fragment, "tagname")
        transaction.addToBackStack("tagname")
        transaction.commit()


    }
    fun showSearchHistory(){
        val transaction = manager.beginTransaction()
        val fragment = SearchHistory_Fragment()
        fragment.arguments = bundle
        transaction.replace(R.id.fragholder, fragment, "tagname")
        transaction.addToBackStack("tagname")
        transaction.commit()


    }
}