package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class ConcertLog extends Model {

    @Id
    public Long id;
    public String name;
    public String time;
    public String date;
    public String loc;

    public ConcertLog( String name, String time,String date, String loc) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.loc = loc;
    }

    public static Model.Finder<Long,ConcertLog> find = new Model.Finder(Long.class, ConcertLog.class);


    public static ConcertLog create( String name, String time, String date, String loc) {
        ConcertLog concertlog = new ConcertLog( name, time, date, time);
        concertlog.save();
        return concertlog;
    }


}