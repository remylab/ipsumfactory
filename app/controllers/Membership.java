package controllers;

import models.Member;

import org.apache.commons.lang3.StringUtils;

import play.data.Form;
import play.mvc.Result;
import tools.StringUtil;

public class Membership extends ControllerBase {

    private final static Form<RegisterModel> registerForm = new Form<RegisterModel>(RegisterModel.class);
    public final static Form<LoginModel> loginForm = new Form<LoginModel>(LoginModel.class);

    public static class RegisterModel {

        public String email;
        public String password;
        public String passwordval;
    }

    public static class LoginModel {

        public String email;
        public String password;

    }

    public static Member getUser() {
        return (Member.find.where().eq("email", session("email")).findUnique());
    }

    /**
     * Register a new member
     * 
     * @return
     */
    public static Result register() {
        Form<RegisterModel> form = registerForm.bindFromRequest();

        if (StringUtil.isEmpty(form.get().email)) {
            form.reject("email", message("register.form.required"));
        } else if (Member.find.where().eq("email", form.get().email).findUnique() != null) {
            form.reject("email", message("register.form.email.notavailable"));
        }

        if (StringUtil.isEmpty(form.get().password)) {
            form.reject("password", message("register.form.required"));
        }
        if (StringUtil.isEmpty(form.get().passwordval)) {
            form.reject("passwordval", message("register.form.required"));
        }

        if (!StringUtil.isEmpty(form.get().password) && !StringUtil.isEmpty(form.get().passwordval) && !StringUtils.trimToEmpty(form.get().password).equals(form.get().passwordval)) {
            form.reject("passwordval", message("register.form.passwordval"));
        }

        if (form.hasErrors()) {
            return badRequest();
        } else {
            Member newMember = Member.create(form.get().email, form.get().password);
            flash("emailConfirmation", message("register.form.emailConfirmation", "http://" + request().host() + "/confirmation/" + newMember.email + "/" + newMember.confirmationToken));
            return redirect(routes.Membership.register());
        }
    }
}
