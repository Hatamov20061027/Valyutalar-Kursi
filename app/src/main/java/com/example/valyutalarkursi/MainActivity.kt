package com.example.valyutalarkursi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.textclassifier.TextLanguage
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.valyutalarkursi.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.dialog_item.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var networkHeler: NetworkHeler
    lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkHeler = NetworkHeler(this)
        requestQueue = Volley.newRequestQueue(this)
        loadArray("https://cbu.uz/uzc/arkhiv-kursov-valyut/json/")

    }


    fun loadArray(url: String) {
        val jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, url, null, object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    var str = response.toString()
                    val type = object :TypeToken<ArrayList<Money>>(){}.type
                    val list = Gson().fromJson<ArrayList<Money>>(str,type)
                    val adapter = MoneyAdapter(list,object : MoneyAdapter.RVClickGroups{
                        override fun onClick(money: Money) {
                            val bottomSheetDialog = BottomSheetDialog(this@MainActivity,R.style.NewDialog)

                            bottomSheetDialog.setContentView(LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog_item, null, false))
                            bottomSheetDialog.setCancelable(true)
                            bottomSheetDialog.create()

                            bottomSheetDialog.Eng_Name.text=money.CcyNm_EN
                            bottomSheetDialog.Ru_Name.text=money.CcyNm_RU
                            bottomSheetDialog.Uz_Name.text=money.CcyNm_UZ
                            bottomSheetDialog.qiyati.text=money.Rate
                            bottomSheetDialog.ohirgi_yangilanish.text=money.Date

                            bottomSheetDialog.show()
                        }

                    })
                    binding.rv.adapter = adapter
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                }
            })
        requestQueue.add(jsonArrayRequest)

    }
}