package uploadpicture.`in`

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uploadpicture.`in`.ModelApi.AddImz.AddPicModel
import java.io.*

class MainActivityK : AppCompatActivity() {
    var uploadPic: ImageView? = null
    var add: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uploadPic = findViewById(R.id.upload_pic)
        add = findViewById(R.id.add_btn)
        add?.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12) {
            var uri = data!!.data
            Log.e("uri", uri.toString())
            if (uri.toString().contains("image")) {
                try {
                    val fileName = uri!!.path!!.split(":".toRegex()).toTypedArray()[1]
                    val `is` = contentResolver.openInputStream(uri)
                    val bitmap = Constant.getScaledBitmap(BitmapFactory.decodeStream(`is`))
                    `is`!!.close()
                    val file = File(cacheDir.toString() + File.separator + fileName + ".jpg")
                    uri = Uri.fromFile(file)
                    uploadPic!!.setImageURI(uri)
                    val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os)
                    os.close()
                    Log.e("data", Constant.getPath(this, data.data))
                    //                      RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    val requestBodyImage = RequestBody.create("*/*".toMediaTypeOrNull(), file)
                    val image: MultipartBody.Part = MultipartBody.Part.createFormData("images[]", file.name, requestBodyImage)
                    val server_key: MultipartBody.Part = MultipartBody.Part.createFormData("server_key", "1539874186")
                    val access_token: MultipartBody.Part = MultipartBody.Part.createFormData("access_token", "0b8260f2e55f9879071a7989adbf24cae754a448161244865000d29ddff833b490275c055972f56928")
                    val caption: MultipartBody.Part = MultipartBody.Part.createFormData("caption", "sd")
                    val networkInterface = MyApp.getRetrofit().create(NetworkInterface::class.java)
                    networkInterface.addImz(server_key, access_token, image, caption).enqueue(object : Callback<AddPicModel?> {
                        override fun onResponse(call: Call<AddPicModel?>, response: Response<AddPicModel?>) {
                            val addPicModel: AddPicModel? = response.body()
                            Toast.makeText(applicationContext, addPicModel?.getMessage(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<AddPicModel?>, t: Throwable) {}
                    })
                    Log.e("dddd", "image")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}