//public class MainActivity extends AppCompatActivity {
//
//    Button btnUpload, btnPickImage;
//    String mediaPath;
//    ImageView imgView1,imgView2;
//    String[] mediaColumns = { MediaStore.Video.Media._ID };
//    ProgressDialog progressDialog;
//
//    public static int choice;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Uploading...");
//
//        btnUpload = (Button) findViewById(R.id.upload);
//        btnPickImage = (Button) findViewById(R.id.pick_img);
//        imgView1 = (ImageView) findViewById(R.id.preview1);
//        imgView2 = (ImageView) findViewById(R.id.preview2);
//
//        choice=0;
//
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadFile();
//            }
//        });
//
//        imgView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choice=1;
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, 0);
//            }
//        });
//
//        imgView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choice=2;
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, 0);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            // When an Image is picked
//            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
//
//                // Get the Image from data
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                assert cursor != null;
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                mediaPath = cursor.getString(columnIndex);
//                // Set the Image in ImageView for Previewing the Media
//                if(choice==1) {
//                    imgView1.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
//                    cursor.close();
//                }
//
//          /*  else if(choice==2) {
//                imgView2.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
//                cursor.close();
//            }  */
//
//            } else {
//                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//
//
//    // Uploading Image/Video
//    private void uploadFile() {
//        progressDialog.show();
//
//        // Map is used to multipart the file using okhttp3.RequestBody
//        File file1 = new File(mediaPath);
//        // File file2 = new File(mediaPath);
//
//        // Parsing any Media type file
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
//        // RequestBody requestBody2 = RequestBody.create(MediaType.parse("*/*"), file2);
//
//        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file1.getName(), requestBody1);
//        // MultipartBody.Part fileToUpload2 = MultipartBody.Part.createFormData("file", file2.getName(), requestBody2);
//
//        RequestBody filename1 = RequestBody.create(MediaType.parse("text/plain"), file1.getName());
//        // RequestBody filename2 = RequestBody.create(MediaType.parse("text/plain"), file2.getName());
//
//        ApiService getResponse = ApiClient.getClient().create(ApiService.class);
//        // Call<ServerResponse> call = getResponse.uploadFile(fileToUpload1);
//        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload1,24);
//
//        Toast.makeText(MainActivity.this,String.valueOf(file1),Toast.LENGTH_LONG).show();
//        call.enqueue(new Callback<ServerResponse>() {
//
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse serverResponse = response.body();
//                if (serverResponse != null) {
//                    if (serverResponse.getSuccess()) {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    assert serverResponse != null;
//                    Log.v("Response", serverResponse.toString());
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//}