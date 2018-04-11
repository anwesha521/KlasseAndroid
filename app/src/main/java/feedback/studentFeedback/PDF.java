package feedback.studentFeedback;

/**
 * Created by ASUS on 01-04-2018.
 */
public class PDF {

    private String url;
    private String name;
    private String week;
    private String roomid;

    public String getWeek() {

        return week;
    }

    public void setWeek(String week) {

        this.week = week;
    }



    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
    public void setRoomId(String roomid) {

        this.roomid = roomid;
    }
    public String getRoomId(){
        return roomid;
    }
}