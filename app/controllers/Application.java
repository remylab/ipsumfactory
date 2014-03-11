package controllers;

import models.Member;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {

        Member member = Membership.getUser();
        return ok(index.render(member));
    }

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", controllers.routes.javascript.Membership.register()));

    }
}
