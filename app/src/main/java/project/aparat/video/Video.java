package project.aparat.video;

/*
* id": "14681570",
        "title": "نمونه لمینت کامپوزیتی |  دکتر ندا هادی جراح و دندانپزشک زیبایی",
        "username": "Drnedahadi",
        "userid": "5723632",
        "visit_cnt": 49,
        "uid": "u2fjZ",
        "isHidden": false,
        "process": "done",
        "sender_name": "دکتر ندا هادی",
        "big_poster": "https://static.cdn.asset.aparat.com/avt/14681570-1302-b__296088272.jpg",
        "small_poster": "https://static.cdn.asset.aparat.com/avt/14681570-1302__8857.jpg",
        "profilePhoto": "https://www.aparat.com/public/public/user_data/profile_photo/1908/5723632-m.jpg",
        "duration": "43",
        "sdate": "7 اردیبهشت 1398",
        "create_date": "2019-04-27 16:17:16",
        "sdate_timediff": 336880,
        "frame": "https://www.aparat.com//video/video/embed/videohash/u2fjZ/vt/frame",
        "official": "no",
        "autoplay": true,
        "video_date_status": "notset",
        "360d": false,
        "deleteurl": ""
* */

public class Video {
    private int id;
    private String title;
    private String uid;
    private String big_poster;
    private String small_poster;
    private String create_date;

    public Video() {
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

    @Override
    public String toString() {
        return getTitle();
    }
}
