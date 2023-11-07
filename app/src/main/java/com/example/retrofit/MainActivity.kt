package com.example.retrofit

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.model.RickModel
import com.example.retrofit.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ApiClient.getInstance()
        val response = client.getRickCharacter()
        val rickList = ArrayList<String>()

        ApiClient.getInstance().getRickCharacter().enqueue(object : retrofit2.Callback<RickModel> {
            override fun onResponse(call: Call<RickModel>, response: Response<RickModel>) {
                val thisResult = response.body()
                val datas = thisResult?.results?: emptyList()
                if (datas.isNotEmpty()){
                    for (i in datas){
                        rickList.add(i.name)
                    }
                }

                val listAdapter = ArrayAdapter(
                    this@MainActivity,
                    R.layout.simple_list_item_1,
                    rickList
                )

                binding.lvNama.adapter = listAdapter
            }
            override fun onFailure(call: Call<RickModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error Connection",
                    Toast.LENGTH_LONG).show()
            }
        })
    }

}