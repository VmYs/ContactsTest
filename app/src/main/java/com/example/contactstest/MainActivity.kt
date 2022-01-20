package com.example.contactstest

import android.annotation.SuppressLint
import android.content.AbstractThreadedSyncAdapter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.contactstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
        binding.contactsView.adapter = adapter
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), 1)
        } else {

        }

    }

    @SuppressLint("Range")
    private fun readContacts() {
        //查询联系人数据
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
                ?.apply {
                    while (moveToNext()) {
                        //获取联系人姓名
                        val displayName = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val number = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        contactsList.add("$displayName\n$number")
                    }
                    adapter.notifyDataSetChanged()
                    close()
                }
    }
}