package project.aparat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.aparat.R;
import project.aparat.video.Video;
import project.aparat.video.VideoEvent;
import project.aparat.video.VideoListAdapter;

public class VideoListActivity extends AppCompatActivity {


  //widgets
  @BindView(R.id.rclvVideos)
  XRecyclerView rclvVideos;
  @BindView(R.id.llayLoading)
  LinearLayout llayLoading;
  @BindView(R.id.item_no_internet_connection)
  LinearLayout item_no_internet_connection;
  @BindView(R.id.btnRetryToRequest)
  Button btnRetryToRequest;
  //filds
  private boolean wating = false;
  private boolean loadMore = false;
  private ArrayList<Video> videos;
  private final String GET_CHANEL_VIDEOS = "https://www.aparat.com/etc/api/videoByUser/username/Drnedahadi/perpage/10";
  private final String GET_LINK_VIDEO = "http://www.aparat.com//etc/api/videoshow/videohash/";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_list);
    ButterKnife.bind(this);

    initFilds();
    getDataFromNetAndInitialTeViews();
  }

  //***************************************************** INITIALS *********************************************************************
  private void initFilds() {
    /*
     * initial the activity filds
     * */
    videos = new ArrayList<>();
  }

  private void initXRecyclerView(List<Video> videos) {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    rclvVideos.setLayoutManager(linearLayoutManager);
    rclvVideos.setLoadingMoreEnabled(true);
    rclvVideos.setPullRefreshEnabled(false);
    rclvVideos.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override
      public void onRefresh() {
        return;
      }

      @Override
      public void onLoadMore() {
        loadMore = true;
        getDataFromNetAndInitialTeViews();
      }
    });
    VideoListAdapter videoListAdapter = new VideoListAdapter(VideoListActivity.this, videos);
    rclvVideos.setAdapter(videoListAdapter);
    rclvVideos.refresh();
  }

  //***************************************************** GET DATA FROM NET AND EXECUTE **************************************************
  private void getDataFromNetAndInitialTeViews() {
    /*
     * getData from net and inital recycler view
     * */
    if (wating) {
      return;
    }
    wating = true;
    llayLoading.setVisibility(View.VISIBLE);
    item_no_internet_connection.setVisibility(View.INVISIBLE);
    Ion.with(VideoListActivity.this)
      .load(GET_CHANEL_VIDEOS)
      .asString()
      .setCallback(new FutureCallback<String>() {
        @Override
        public void onCompleted(Exception e, String result) {
          if (!loadMore)
            videos.clear();
          loadMore = false;
          llayLoading.setVisibility(View.GONE);
          wating = false;
          if (e != null) {
            Log.e("ERROR", e.toString());
            item_no_internet_connection.setVisibility(View.VISIBLE);
            btnRetryToRequest.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                getDataFromNetAndInitialTeViews();
              }
            });
            return;
          }
          try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray videobyuserJA = jsonObject.getJSONArray("videobyuser");
            Log.i("RESULT", "result: " + jsonObject.toString(4));
            for (int i = 0; i < videobyuserJA.length(); i++) {
              JSONObject object = videobyuserJA.getJSONObject(i);
              Video video = new Video();
              video.setId(object.getInt("id"));
              video.setTitle(object.getString("title"));
              video.setUid(object.getString("uid"));
              video.setBig_poster(object.getString("big_poster"));
              video.setSmall_poster(object.getString("small_poster"));
              video.setCreate_date(object.getString("create_date"));
              videos.add(video);
            }//end for
          } catch (JSONException e1) {
            e1.printStackTrace();
          }
          initXRecyclerView(videos);
        }
      });
  }

  // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(VideoEvent event) {
    Toast.makeText(VideoListActivity.this, event.getTitle(), Toast.LENGTH_SHORT).show();
    llayLoading.setVisibility(View.VISIBLE);
    Ion.with(this)
      .load(GET_LINK_VIDEO + event.getUid())
      .setTimeout(8000)
      //.setBodyParameters(params)
      .asString()
      .setCallback(new FutureCallback<String>() {
        @Override
        public void onCompleted(Exception e, String strResult) {
          llayLoading.setVisibility(View.GONE);
          if (e != null) {
            e.printStackTrace();
            return;
          }
          try {
            JSONObject object = new JSONObject(strResult);
            Log.i("RESULT2", "result: " + object.toString(4));
            JSONObject videoshow = object.getJSONObject("videoshow");

            Intent intent = new Intent(VideoListActivity.this, VideoPlayerActivity.class);
            intent.putExtra("file_link", videoshow.getString("file_link"));
            VideoListActivity.this.startActivity(intent);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      });
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
  }
}
