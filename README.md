
## ** ScreenShot **

## **Intoduction:**
    
   This Project is Create for if any issue show using for any project take the screenshot then send for local file.
--------------------------------------------------------------------------------------------------------------------------------------
Handler Part:
       Create Kotlin Class inside MainActivity Class. The app using for OnClickListener .  
      inside the OnCreate () 
               findViews()
               implementClickEvents()
   
 ## **ImplementClickEvent ** - 
 FullPage Screenshot and CustomPage Screenshot use onclick method.

 ## **Full page and Custom Page:**  -
 TakeScreenShot method implemented.

 ## **TakeScreenShot method-**
   1.If Screenshot type is FULL take full page screenshot i.e our root content.set the visibility to INVISIBLE of first button

   2.set the visibility to VISIBLE of hidden text.After taking screenshot reset the button and view again
set the visibility to VISIBLE of first button again set the visibility to INVISIBLE of hidden text

   3.If bitmap is not null show bitmap over imageview get the path to save screenshot save the screenshot to selected path

   4.finally share screenshot .If bitmap is null show toast message

  ## **ShareScreenshot**: -
   Convert file path into Uri for sharing pass uri .

  ## **ScreenshotUtils**

   1. return Bitmap after taking screenshot. pass the view which we want to take screenshot.

        2.Create Directory where screenshot will save for sharing screenshot use getExternalFilesDir and inside that we will make our Image folder

          3.benefit of getExternalFilesDir is that whenever the app uninstalls the images will get deleted automatically.
      context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Image");

          4.If File is not present create directoryStore taken screenshot into above created path.