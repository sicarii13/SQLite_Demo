package thinktankesolutions.com.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Add : AppCompatActivity() {

    private var helper = DatabaseHandler(this)
    var isAdd: Boolean = false;
    var id: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val etName: EditText = findViewById(R.id.etName)
        val etAge: EditText = findViewById(R.id.etAge)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val btnDelete: Button = findViewById(R.id.btnDelete)

        isAdd = intent.getBooleanExtra("ADD", true);
        id = intent.getIntExtra("ID", 0)

        if (!isAdd) {
            var user = helper.getParticularUserData("" + id)
            etName.setText(user.name)
            etAge.setText("" + user.age)
            etPhone.setText("" + user.phone)
            etEmail.setText(user.email)
            btnDelete.visibility = View.VISIBLE
        }

        addData()
    }

    private fun validate(): Boolean {

        val etName: EditText = findViewById(R.id.etName)
        val etAge: EditText = findViewById(R.id.etAge)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val etEmail: EditText = findViewById(R.id.etEmail)

        when {
            etName.text.isEmpty() -> {
                Toast.makeText(this@Add, "Enter Name", Toast.LENGTH_SHORT).show()
                return false
            }
            etAge.text.isEmpty() -> {
                Toast.makeText(this@Add, "Enter Age", Toast.LENGTH_SHORT).show()
                return false
            }
            etPhone.text.isEmpty() -> {
                Toast.makeText(this@Add, "Enter Phone", Toast.LENGTH_SHORT).show()
                return false
            }
            etEmail.text.isEmpty() -> {
                Toast.makeText(this@Add, "Enter Email", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }

    private fun addData() {

        val etName: EditText = findViewById(R.id.etName)
        val etAge: EditText = findViewById(R.id.etAge)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnDelete: Button = findViewById(R.id.btnDelete)

        btnAdd.setOnClickListener {
            if (validate()) {
                if(isAdd) {
                    helper.insertData(etName.text.toString(),
                        etAge.text.toString(),
                        etPhone.text.toString(), etEmail.text.toString())
                }else{
                    helper.updateData(""+id,etName.text.toString(),
                        etAge.text.toString(),
                        etPhone.text.toString(), etEmail.text.toString())
                }
                clearAllFields()
                finish()
            }
        }
        btnDelete.setOnClickListener {
            helper.deleteData(""+id)
        }
    }

    private fun clearAllFields() {

        val etName: EditText = findViewById(R.id.etName)
        val etAge: EditText = findViewById(R.id.etAge)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val etEmail: EditText = findViewById(R.id.etEmail)

        etName.text.clear()
        etAge.text.clear()
        etPhone.text.clear()
        etEmail.text.clear()
    }
}
