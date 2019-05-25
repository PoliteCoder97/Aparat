package project.aparat.video;

public class VideoEvent {

    private int id;
    private String title;
    private String uid;
    private String big_poster;
    private String small_poster;
    private String create_date;

    public VideoEvent(Video video){
        this.id  = video.getId();
        this.title  = video.getTitle();
        this.uid  = video.getUid();
        this.big_poster  = video.getBig_poster();
        this.small_poster  = video.getSmall_poster();
        this.create_date  = video.getCreate_date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBig_poster() {
        return big_poster;
    }

    public void setBig_poster(String big_poster) {
        this.big_poster = big_poster;
    }

    public String getSmall_poster() {
        return small_poster;
    }

    public void setSmall_poster(String small_poster) {
        this.small_poster = small_poster;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
