package uploadpicture.in;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UploadVideoActivity extends AppCompatActivity {

    VideoView video;
    TextView add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        video = findViewById(R.id.video_view);
        add = findViewById(R.id.add_text);
    }
}
