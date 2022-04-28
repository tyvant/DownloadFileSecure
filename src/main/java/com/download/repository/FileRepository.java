package com.download.repository;

import com.download.dao.FileDao;
import com.download.models.FileInfo;
import com.download.utils.EncryptManager;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FileRepository {
    private final FileDao fileDao;

    public FileRepository(Jdbi jdbi) {
        jdbi.registerRowMapper(new FileMapper());
        fileDao = jdbi.onDemand(FileDao.class);
    }

    public void saveIntoDB(FileInfo fileInfo) {

        try {
            byte[] encryptData = EncryptManager.encryptData(fileInfo.getData());
            fileDao.save(fileInfo.getName(),fileInfo.getType(), encryptData);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    public List<FileInfo> getAllFiles (){
          return  fileDao.getAllFile();
    }

    public  FileInfo getFileById(Integer id) {
        return  fileDao.getFileById(id);
    }

    static class FileMapper implements RowMapper<FileInfo> {
        @Override
        public FileInfo map(ResultSet rs, StatementContext ctx) throws SQLException {

            try {
                byte[] data = rs.getBytes("data");
                byte[] decryptData = EncryptManager.decryptData(data);
                return new FileInfo(rs.getInt("id"),rs.getString("filename"), rs.getString("type"), decryptData);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
