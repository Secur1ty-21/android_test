@file:Suppress("DEPRECATION")

package com.sirius.test_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sirius.test_app.adapter.CommentsAdapter
import com.sirius.test_app.adapter.TagsAdapter
import com.sirius.test_app.databinding.ActivityMainBinding
import com.sirius.test_app.network.DownloadImageTask


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Изменение status bar на прозрачный
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        val dataModel = DataModel()
        // Создание прокручиваемого списка тэгов
        binding.recyclerViewTags.adapter = TagsAdapter(dataModel)
        // Создание прокручиваемого списка отзывов/комментариев
        binding.recyclerViewComments.adapter = CommentsAdapter(dataModel)
        binding.recyclerViewComments.Recycler().setViewCacheSize(5)
        // Подгрузка изображения по ссылке
        try {
            DownloadImageTask(binding.gameLogo, false).execute(dataModel.logo)
        } catch (e: Exception) {
            Log.e("loadImage", "$e")
        }
        // Подгрузка текста из DataModel и изображений из ресурсов
        setUiWithData(dataModel)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUiWithData(dataModel: DataModel) {
        runOnUiThread {
            binding.ratingFloatText.text = dataModel.rating.toString()
            binding.gameNameText.text = dataModel.name
            binding.gameIsText.text = dataModel.description
            binding.numDownloadsText.text = dataModel.gradeCnt
            binding.buttonInstall.text = dataModel.action.name
            binding.buttonInstall.contentDescription = dataModel.action.action
            binding.reviewHead.text = resources.getString(R.string.review_header_text)
            binding.numDownloadsTextNearMark.text =
                resources.getString(R.string.num_of_downloads_near_mark_text, dataModel.gradeCnt)
            binding.backgroundGameImage.setImageDrawable(
                resources.getDrawable(
                    R.drawable.img_background,
                    null
                )
            )
        }
    }
}