package project.aparat.video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.glide.slider.library.svg.GlideApp;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.aparat.R;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

  private final Context context;
  private final List<Video> videos;

  public VideoListAdapter(Context context, List<Video> videos) {
    this.videos = videos;
    this.context = context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    try {
      return new ViewHolder(LayoutInflater.from(this.context)
        .inflate(R.layout.adapter_videos_list, null, false));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    final Video video = videos.get(position);
    viewHolder.txtTitle.setText("" + video.getTitle());
    GlideApp.with(context)
      .load(video.getBig_poster())
      .placeholder(R.mipmap.ic_launcher)
      .into(viewHolder.img);

    viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        EventBus.getDefault().post(new VideoEvent(video));
      }
    });
  }

  @Override
  public int getItemCount() {
    return videos.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.btnPlay)
    Button btnPlay;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.img)
    ImageView img;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
