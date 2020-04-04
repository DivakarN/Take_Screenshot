package com.takescreenshot_demo

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileOutputStream

object ScreenshotUtils {

    //region   return Bitmap after taking screenshot.
// pass the view which we want to take screenshot.
    fun getScreenShot(view: View): Bitmap {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    } //endregion

    //region Create Directory where screenshot will save for sharing screenshot
    fun getMainDirectoryName(context: Context): File { // use getExternalFilesDir and inside that we will make our Image folder
//benefit of getExternalFilesDir is that whenever the app uninstalls the images will get deleted automatically.
        val save = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Image")
        //If File is not present create directory
        if (!save.exists()) {
            if (save.mkdir())
                Log.e("Create Directory", "Main Directory Created : $save")
        }
        return save
    }

    //endregion

    //region  Store taken screenshot into above created path
    fun store(bm: Bitmap, fileName: String?, saveFilePath: File): File {
        val dir = File(saveFilePath.absolutePath)
        if (!dir.exists()) dir.mkdirs()
        val file = File(saveFilePath.absolutePath, fileName)
        try {
            val fOut = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    } //endregion
}
