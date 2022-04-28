package com.download.dao;

import com.download.models.FileInfo;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface FileDao {

    @SqlUpdate("insert into file_data(filename, type, data) values(:name, :type, :data)")
    void save(final @Bind("name") String filename, @Bind("type") final String type, @Bind("data") final byte [] data );

    @SqlQuery("select * from file_data")
     List<FileInfo> getAllFile();
    @SqlQuery("select * from file_data where id = :id")
     FileInfo getFileById(@Bind("id")Integer id);

   /* @SqlUpdate("insert into users(username, password) values(:username, :password)")
    void create (@Bind("username") final String username, @Bind("password") final String password);
    @SqlQuery("select * from users where username = :username")
    User findByUsername (@Bind("username") final String username); */
}
