package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.RandomStringUtils;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import tools.DateUtil;
import tools.StringUtil;

@Entity
public class Member extends Model {
    private static final long serialVersionUID = 9034117438432239004L;
    private static String passwordSeed = "danslajungleterriblejungle";
    public static Finder<String, Member> find = new Finder<String, Member>(String.class, Member.class);

    @Id
    public long id;
    @Email
    @Required
    @MinLength(4)
    @Column(unique = true)
    public String email;
    public String password;
    public boolean active;
    public String confirmationToken;
    public Long creationTime;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
        this.active = false;
        this.confirmationToken = RandomStringUtils.randomAlphanumeric(40);

        this.creationTime = DateUtil.getTimeNow();
    }

    public static Member create(String email, String password) {
        Member member = new Member(email, getStoredPassword(password));
        member.save();
        return member;
    }

    public static Member confirmToken(String email, String token) {
        Member member = find.where().eq("email", email).eq("confirmationToken", token).findUnique();

        if (member != null) {
            member.active = true;
            member.confirmationToken = null;
            member.save();
        }
        return member;
    }

    public static Member findByEmail(String email) {
        return find.where().eq("email", email).eq("active", true).findUnique();
    }

    public static Member authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", getStoredPassword(password)).eq("active", true).findUnique();
    }

    private static String getStoredPassword(String s) {
        return StringUtil.encrypt("SHA1", s, passwordSeed);
    }
}