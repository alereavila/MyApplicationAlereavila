package com.alereavila.myapplication

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.alereavila.myapplication.databinding.ActivityScrollingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomappbar.BottomAppBar

class ScrollingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScrollingBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_scrolling)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        /*
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            if(findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode= BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }*/
        binding.fab.setOnClickListener {
            if(binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                binding.bottomAppBar.fabAlignmentMode=BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }
        binding.bottomAppBar.setNavigationOnClickListener {
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_LONG)
                    //para que se muestre el mensaje arriba del boton flotante que esta sobre la bottom app bar
                    .setAnchorView(binding.fab)
                    .show()
        }
        binding.content.btnSkip.setOnClickListener {
            binding.content.cvAd.visibility= View.GONE
    }
        binding.content.btnComprar.setOnClickListener {
            Snackbar.make(it, R.string.buying, Snackbar.LENGTH_LONG)
                    .setAnchorView(binding.fab)
                    .setAction(R.string.card_to_go) { Toast.makeText(this, R.string.card_historial, Toast.LENGTH_SHORT).show() }
                    .show()
        }
        /*para usar el Glide se deben de agregar dependencias
        * de : https://github.com/bumptech/glide
        * esto en el build.gradle:
        * implementation 'com.github.bumptech.glide:glide:4.12.0'
        *  kapt 'com.github.bumptech.glide:compiler:4.12.0'
        *y un plugin id:  id 'kotlin-kapt'
        *tambien hay que agregar los permisos en el manifest:
        *<uses-permission android:name="android.permission.INTERNET"/>
        * */
        loadImage()
        /*
        Glide.with(this)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9TYLreanXKLVtehO7m4XiVl9B3MuXlrePbw&usqp=CAU")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.content.imgCover)*/
        binding.content.chEnablePass.setOnClickListener {
            binding.content.editPass.isEnabled = !binding.content.editPass.isEnabled
        }
        binding.content.itURL.onFocusChangeListener = View.OnFocusChangeListener {it , focused ->
            val url = binding.content.itURL.text.toString()
            var errorStr:String?=null
            if(!focused){
                if (url.isEmpty()){
                    loadImage()
                    errorStr = getString(R.string.card_required)
                }else if (URLUtil.isValidUrl(url)){

                    loadImage(url)
                }
                else{
                    errorStr=getString(R.string.card_invalid_url)
                }
            }
            binding.content.itURL.error= errorStr
        }
        binding.content.toogleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            binding.content.root.setBackgroundColor(
                    when(checkedId){
                        R.id.btnRed-> Color.RED
                        R.id.btnBlue->Color.BLUE
                        else->Color.GREEN
                    }
            )

        }

    }

    private fun loadImage (url:String="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9TYLreanXKLVtehO7m4XiVl9B3MuXlrePbw&usqp=CAU"){
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.content.imgCover)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}