package controllers;

import Concerts.Concert;
import Concerts.SearchConcert;
import TvSchedule.TvProgram;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import models.*;
import TvSchedule.Main;
import org.json.JSONException;
import play.data.Form;
import play.mvc.*;
import play.mvc.Result;
import static play.data.Form.*;
import views.html.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import models.TvLog;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
         return ok(index.render(
                 User.find.byId(request().username()),
                 TvLog.find.all(),
                 ConcertLog.find.all()
         ));
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

    public static Result login() {
        return play.mvc.Controller.ok(
                login.render(form(Login.class))
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.index()
            );
        }
    }

    public static Result search() throws UnsupportedEncodingException, JSONException {
        String term = Form.form(Search.class).bindFromRequest().get().searchTerm;

        SqlUpdate clearSearchResTV = Ebean.createSqlUpdate("DELETE FROM tv_log");
        clearSearchResTV.execute();

        SqlUpdate clearSearchResConc = Ebean.createSqlUpdate("DELETE FROM concert_log");
        clearSearchResConc.execute();

        SearchConcert searchconcert = new SearchConcert();
        Main main = new Main();


        ArrayList<Concert> listConcerts = searchconcert.getFilteredData(term, "", "", "");
        for (int i = 0; i < listConcerts.size(); i++) {
            ConcertLog.create(
                    listConcerts.get(i).getName(),
                    listConcerts.get(i).getTime(),
                    listConcerts.get(i).getDate(),
                    listConcerts.get(i).getLoc()
            );
        }


        ArrayList<TvProgram> list = main.searchForTvProgram(term);
        for (int i = 0; i < list.size(); i++) {
            TvLog.create(
                    list.get(i).getTitle(),
                    list.get(i).getChannel(),
                    list.get(i).getDescription(),
                    list.get(i).getStartTime()
             );
        }


        return redirect(routes.Application.index());
    }



/*
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Projects.add(),
                        controllers.routes.javascript.Projects.delete(),
                        controllers.routes.javascript.Projects.rename(),
                        controllers.routes.javascript.Projects.addGroup()
                )
        );
    } */


    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }
}