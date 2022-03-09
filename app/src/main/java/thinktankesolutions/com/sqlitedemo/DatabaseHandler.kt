package thinktankesolutions.com.sqlitedemo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        val DATABASE_NAME = "users.db"
        val TABLE_NAME = "user_table"
        val ID = "ID"
        val NAME = "NAME"
        val AGE = "AGE"
        val PHONE = "PHONE"
        val EMAIL = "EMAIL"    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,AGE INTEGER,PHONE TEXT, EMAIL TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    //Insert data into user_table
    fun insertData(name: String, age: String, phone: String, email: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(AGE, age)
        contentValues.put(PHONE, phone)
        contentValues.put(EMAIL,email)
        db.insert(TABLE_NAME, null, contentValues)
    }

    //List all users in user_table
    fun listOfUsers(): ArrayList<UserInfo>  {
        val db = this.writableDatabase
        val res = db.rawQuery("select * from " + TABLE_NAME, null)
        val useList = ArrayList<UserInfo>()
        while (res.moveToNext()) {
            var userInfo = UserInfo()
            userInfo.id = Integer.valueOf(res.getString(0))
            userInfo.name = res.getString(1)
            userInfo.age = Integer.valueOf(res.getString(2))
            userInfo.phone = res.getString(3)
            userInfo.email = res.getString(4)
            useList.add(userInfo)
        }
        return useList
    }


    //Getting all user list
    @SuppressLint("Range")
    fun getAllUserData(): ArrayList<UserInfo> {
        val stuList: ArrayList<UserInfo> = arrayListOf<UserInfo>()
        val cursor: Cursor = getReadableDatabase().query(TABLE_NAME, arrayOf(ID, NAME, AGE, PHONE, EMAIL), null, null, null, null, null)
        cursor.use { cursor ->
            if (cursor.getCount() != 0) {
                cursor.moveToFirst()
                if (cursor.getCount() > 0) {
                    do {
                        val id : Int = cursor.getInt(cursor.getColumnIndex(ID))
                        val name: String = cursor.getString(cursor.getColumnIndex(NAME))
                        val age: Int = cursor.getInt(cursor.getColumnIndex(AGE))
                        val phone: String = cursor.getString(cursor.getColumnIndex(PHONE))
                        val email: String =  cursor.getString(cursor.getColumnIndex(EMAIL))
                        var userInfo = UserInfo()
                        userInfo.id = id
                        userInfo.name = name
                        userInfo.age = age
                        userInfo.phone = phone
                        userInfo.email = email
                        stuList.add(userInfo)
                    } while ((cursor.moveToNext()))
                }
            }
        }

        return stuList
    }

    @SuppressLint("Range")
    fun getParticularUserData(id: String): UserInfo {
        var userInfo  = UserInfo()
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + " = '" + id + "'"
        val cursor = db.rawQuery(selectQuery, null)
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        userInfo.id = cursor.getInt(cursor.getColumnIndex(ID))
                        userInfo.name = cursor.getString(cursor.getColumnIndex(NAME))
                        userInfo.age = cursor.getInt(cursor.getColumnIndex(AGE))
                        userInfo.phone = cursor.getString(cursor.getColumnIndex(PHONE))
                        userInfo.email = cursor.getString(cursor.getColumnIndex(EMAIL))
                    } while ((cursor.moveToNext()));
                }
            }
        } finally {
            cursor.close();
        }
        return userInfo
    }

    //Update use record
    fun updateData(id: String, name: String, age: String, phone: String, email: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)
        contentValues.put(NAME, name)
        contentValues.put(AGE, age)
        contentValues.put(PHONE, phone)
        contentValues.put(EMAIL,email)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    //Delete table entry
    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))

    }


}