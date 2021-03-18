package uploadpicture.in;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import uploadpicture.in.ModelApi.AddImz.AddPicModel;

public interface NetworkInterface {

    @Multipart
    @POST(Constant.UPLOAD_PIC)
    Call<AddPicModel> addImz(@Part MultipartBody.Part server_key,
                             @Part MultipartBody.Part access_token,
                             @Part MultipartBody.Part image,
                             @Part MultipartBody.Part caption);

}
