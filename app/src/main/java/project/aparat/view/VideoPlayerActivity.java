package project.aparat.view;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.aparat.R;

public class VideoPlayerActivity extends AppCompatActivity {

  //widgets
  @BindView(R.id.play_video)
  VideoView play_video;

  //filds
  String fileLink;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_player);
    ButterKnife.bind(this);

    getExtrasFromPreviousActivity();
    initVideoAndPlay();
  }

  private void getExtrasFromPreviousActivity() {
    /*
     * one of the previous activity is VideoListActivity
     * */
    //getString extras
    fileLink = getIntent().getStringExtra("file_link");
  }

  private void initVideoAndPlay() {
    if (fileLink == null || fileLink.isEmpty() || fileLink.equals("")) {
      showVideoErrorAndGoBack("خطا در دریافت اطلاعات");
    } else {
      play_video.setVideoURI(Uri.parse(fileLink));
      play_video.setMediaController(new MediaController(this));
      play_video.requestFocus();
      play_video.start();

      play_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
          switch (extra) {
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
              showVideoErrorAndGoBack("متاسفانه این فایل توسط پلیر پشتیبانی نمیشود");
              break;
            case MediaPlayer.MEDIA_ERROR_IO:
              showVideoErrorAndGoBack("خطایی در پخش به وجود امده است");
              break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
              showVideoErrorAndGoBack("خطایی در پخش به وجود امده است");
              break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
              showVideoErrorAndGoBack("خطایی در پخش به وجود امده است");
              break;
          }
          return false;
        }
      });
    }
  }//initVideoAndPlay

  private void showVideoErrorAndGoBack(String errorMessage) {
    Toast.makeText(VideoPlayerActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    VideoPlayerActivity.this.onBackPressed();
    VideoPlayerActivity.this.finish();
  }
}
