package uploadpicture.in;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uploadpicture.in.ModelApi.AddImz.AddPicModel;


public class MainActivity extends AppCompatActivity {

    ImageView uploadPic;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadPic = findViewById(R.id.upload_pic);
        add = findViewById(R.id.add_btn);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 12) {
            Uri uri = data.getData();

            Log.e("uri", String.valueOf(uri));

            if (uri.toString().contains("image")) {

                try {
                    String fileName = uri.getPath().split(":")[1];

                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = Constant.getScaledBitmap(BitmapFactory.decodeStream(is));
                    is.close();

                    File file = new File(getCacheDir() + File.separator + fileName + ".jpg");
                    uri = Uri.fromFile(file);
                    uploadPic.setImageURI(uri);
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                    os.close();

                    Log.e("data", Constant.getPath(this, data.getData()));
//                      RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    RequestBody requestBodyImage = RequestBody.create(MediaType.parse("*/*"), file);

                    MultipartBody.Part image = MultipartBody.Part.createFormData("images[]", file.getName(), requestBodyImage);
                    MultipartBody.Part server_key = MultipartBody.Part.createFormData("server_key", "1539874186");
                    MultipartBody.Part access_token = MultipartBody.Part.createFormData("access_token", "0b8260f2e55f9879071a7989adbf24cae754a448161244865000d29ddff833b490275c055972f56928");
                    MultipartBody.Part caption = MultipartBody.Part.createFormData("caption", "sd");

                    NetworkInterface networkInterface = MyApp.getRetrofit().create(NetworkInterface.class);
                    networkInterface.addImz(server_key, access_token, image, caption).enqueue(new Callback<AddPicModel>() {
                        @Override
                        public void onResponse(Call<AddPicModel> call, Response<AddPicModel> response) {
                            AddPicModel addPicModel = response.body();
                            Toast.makeText(getApplicationContext(), addPicModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AddPicModel> call, Throwable t) {

                        }
                    });
                    Log.e("dddd", "image");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}