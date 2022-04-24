package repository;

import dao.UserDao;
import models.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private static final String DATABASE_REACH_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String DATABASE_UNEXPECTED_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting part.";

    private final UserDao users;

    public UserRepository(Jdbi jdbi) {
        jdbi.registerRowMapper(new UserMapper());
        users = jdbi.onDemand(UserDao.class);
    }

    public User findByUsername(String username) {
        return users.findByUsername(username);
    }

    public User create(User user) {
        synchronized (users) {
            users.create(user.getUsername(), user.getPassword());
        }
        return user;
    }

    public String performHealthCheck() {
        return null;
    }


    private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
        if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
    }
    static class UserMapper implements RowMapper<User> {
        @Override
        public User map(ResultSet rs, StatementContext ctx) throws SQLException {
            return  new User (rs.getString("username"),rs.getString("password"));

        }
    }
}
