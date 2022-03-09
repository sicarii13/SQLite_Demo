package thinktankesolutions.com.sqlitedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class UserListing : AppCompatActivity() {

    private var helper = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_listing)

        var listData: ArrayList<UserInfo> = helper.listOfUsers()

        var listView: ListView = findViewById(R.id.lvNames)
        var listAdapter = ULAdapter(this, listData)
        listView.adapter = listAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            var intent = Intent(this, Add::class.java)
            intent.putExtra("ADD", false)
            intent.putExtra("ID",listData[position].id)
            startActivity(intent)
        }
    }
}