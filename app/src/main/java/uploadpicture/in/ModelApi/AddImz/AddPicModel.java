package uploadpicture.in.ModelApi.AddImz;

import com.google.gson.annotations.SerializedName;

public class AddPicModel{

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"AddPicModel{" + 
			"code = '" + code + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}