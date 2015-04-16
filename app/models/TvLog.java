package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class TvLog extends Model {

    @Id
    public Long id;
    public String title;
    public String channel;
    public String description;
    public String time;

    public TvLog( String title, String channel,String description, String time) {
        this.title = title;
        this.channel = channel;
        this.description = description;
        this.time = time;
    }

    public static Model.Finder<Long,TvLog> find = new Model.Finder(Long.class, TvLog.class);


    public static TvLog create( String title, String channel, String description, String time) {
        TvLog tvlog = new TvLog( title, channel, description, time);
        tvlog.save();
        return tvlog;
    }


}