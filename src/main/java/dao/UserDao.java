package dao;

import models.User;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {
    @SqlUpdate("insert into users(username, password) values(:username, :password)")
    void create (@Bind("username") final String username,@Bind("password") final String password);
    @SqlQuery("select * from users where username = :username")
    User findByUsername (@Bind("username") final String username);
}
