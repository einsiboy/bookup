package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by drepheitur on 8.4.2015.
 */
@Entity
public class Search extends Model {
    @Id
    @GeneratedValue
    public String id;

    public String searchTerm;
}
