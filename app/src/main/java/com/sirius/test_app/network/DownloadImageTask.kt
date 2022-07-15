@file:Suppress("DEPRECATION")

package com.sirius.test_app.network

import android.annotation.SuppressLint
import android.graphics.*
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * AsyncTask для загрузки изображения по url в фоне
 */
open class DownloadImageTask(bmImage: ImageView, private val isAvatar: Boolean) :
    AsyncTask<String?, Void?, Bitmap?>() {
    @SuppressLint("StaticFieldLeak")
    var bmImage: ImageView

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg p0: String?): Bitmap? {
        val urlDisplay = p0[0]
        var mIcon: Bitmap? = null
        try {
            Log.e("src", "$urlDisplay")
            val url = URL(urlDisplay)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            mIcon = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned")
        } catch (e: Exception) {
            Log.e("loadImage", "$e")
            e.printStackTrace()
        }
        if (isAvatar)
            return getRoundedCornerBitmap(mIcon, 1000)
        return mIcon
    }

    @Deprecated("Deprecated in Java", ReplaceWith("bmImage.setImageBitmap(result)"))
    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }

    init {
        this.bmImage = bmImage
    }

    /**
     * Скругление аватарок
     */
    private fun getRoundedCornerBitmap(bitmap: Bitmap?, pixels: Int): Bitmap? {
        if (bitmap == null) return null
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }
}