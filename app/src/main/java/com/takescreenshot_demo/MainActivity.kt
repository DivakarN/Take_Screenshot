package com.takescreenshot_demo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var fullPageScreenshot: Button? = null
    private var customPageScreenshot: Button? = null
    private var rootContent: LinearLayout? = null
    private var imageView: ImageView? = null
    private var hiddenText: TextView? = null

    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        implementClickEvents()
    }

    //endregion

    // region Find all views Ids
    private fun findViews() {
        fullPageScreenshot = findViewById<View>(R.id.full_page_screenshot) as Button
        customPageScreenshot = findViewById<View>(R.id.custom_page_screenshot) as Button
        rootContent = findViewById<View>(R.id.root_content) as LinearLayout
        imageView = findViewById<View>(R.id.image_view) as ImageView
        hiddenText = findViewById<View>(R.id.hidden_text) as TextView
    }

    //endregion

    //region  Implement Click events over Buttons
    private fun implementClickEvents() {
        fullPageScreenshot!!.setOnClickListener(this)
        customPageScreenshot!!.setOnClickListener(this)
    }

    //endregion


    //region onclick full page  and custom page
    override fun onClick(v: View) {
        when (v.id) {
            R.id.full_page_screenshot -> takeScreenshot(ScreenshotType.FULL)
            R.id.custom_page_screenshot -> takeScreenshot(ScreenshotType.CUSTOM)
        }
    }

    //endregion

    //region  take screenshot on of Screenshot Type ENUM
    private fun takeScreenshot(screenshotType: ScreenshotType) {
        var b: Bitmap? = null
        when (screenshotType) {
            ScreenshotType.FULL ->
                //If Screenshot type is FULL take full page screenshot i.e our root content.
                b = rootContent?.let { ScreenshotUtils.getScreenShot(it) }
            ScreenshotType.CUSTOM -> {
                //set the visibility to INVISIBLE of first button
                fullPageScreenshot!!.visibility = View.INVISIBLE
                //set the visibility to VISIBLE of hidden text
                hiddenText!!.visibility = View.VISIBLE
                //After taking screenshot reset the button and view again
                b = rootContent?.let { ScreenshotUtils.getScreenShot(it) }
                //set the visibility to VISIBLE of first button again
                fullPageScreenshot!!.visibility = View.VISIBLE
                //set the visibility to INVISIBLE of hidden text
                hiddenText!!.visibility = View.INVISIBLE
            }
        }
        //If bitmap is not null
        if (b != null) {
            //show bitmap over imageview
            showScreenShotImage(b)
            //get the path to save screenshot
            val saveFile = ScreenshotUtils.getMainDirectoryName(this)
            //save the screenshot to selected path
            val file = ScreenshotUtils.store(b, "screenshot$screenshotType.jpg", saveFile)
            shareScreenshot(file) //finally share screenshot
        } else  //If bitmap is null show toast message
            Toast.makeText(this, R.string.screenshot_take_failed, Toast.LENGTH_SHORT).show()
    }

    //endregion


    //region  Show screenshot Bitmap
    private fun showScreenShotImage(b: Bitmap) {
        imageView!!.setImageBitmap(b)
    }

    //endregion

    //region Share Screenshot
    private fun shareScreenshot(file: File) {
        val uri = Uri.fromFile(file) //Convert file path into Uri for sharing
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharing_text))
        //pass uri here
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)))
    } //endregion
}
