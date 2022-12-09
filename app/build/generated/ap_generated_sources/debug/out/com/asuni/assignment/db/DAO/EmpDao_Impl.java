package com.asuni.assignment.db.DAO;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.asuni.assignment.db.entity.EmpModal;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EmpDao_Impl implements EmpDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EmpModal> __insertionAdapterOfEmpModal;

  private final EntityDeletionOrUpdateAdapter<EmpModal> __deletionAdapterOfEmpModal;

  private final EntityDeletionOrUpdateAdapter<EmpModal> __updateAdapterOfEmpModal;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllCourses;

  public EmpDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmpModal = new EntityInsertionAdapter<EmpModal>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `emp_table` (`id`,`employee_name`,`employee_salary`,`employee_age`,`profile_image`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EmpModal value) {
        stmt.bindLong(1, value.getId());
        if (value.getEmployee_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEmployee_name());
        }
        stmt.bindLong(3, value.getEmployee_salary());
        stmt.bindLong(4, value.getEmployee_age());
        if (value.getProfile_image() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getProfile_image());
        }
      }
    };
    this.__deletionAdapterOfEmpModal = new EntityDeletionOrUpdateAdapter<EmpModal>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `emp_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EmpModal value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfEmpModal = new EntityDeletionOrUpdateAdapter<EmpModal>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `emp_table` SET `id` = ?,`employee_name` = ?,`employee_salary` = ?,`employee_age` = ?,`profile_image` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EmpModal value) {
        stmt.bindLong(1, value.getId());
        if (value.getEmployee_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEmployee_name());
        }
        stmt.bindLong(3, value.getEmployee_salary());
        stmt.bindLong(4, value.getEmployee_age());
        if (value.getProfile_image() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getProfile_image());
        }
        stmt.bindLong(6, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllCourses = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM emp_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final EmpModal model) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEmpModal.insert(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final EmpModal model) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEmpModal.handle(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final EmpModal model) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfEmpModal.handle(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllCourses() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllCourses.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllCourses.release(_stmt);
    }
  }

  @Override
  public LiveData<List<EmpModal>> getAllCourses() {
    final String _sql = "SELECT * FROM emp_table ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"emp_table"}, false, new Callable<List<EmpModal>>() {
      @Override
      public List<EmpModal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeName = CursorUtil.getColumnIndexOrThrow(_cursor, "employee_name");
          final int _cursorIndexOfEmployeeSalary = CursorUtil.getColumnIndexOrThrow(_cursor, "employee_salary");
          final int _cursorIndexOfEmployeeAge = CursorUtil.getColumnIndexOrThrow(_cursor, "employee_age");
          final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profile_image");
          final List<EmpModal> _result = new ArrayList<EmpModal>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final EmpModal _item;
            _item = new EmpModal();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpEmployee_name;
            _tmpEmployee_name = _cursor.getString(_cursorIndexOfEmployeeName);
            _item.setEmployee_name(_tmpEmployee_name);
            final int _tmpEmployee_salary;
            _tmpEmployee_salary = _cursor.getInt(_cursorIndexOfEmployeeSalary);
            _item.setEmployee_salary(_tmpEmployee_salary);
            final int _tmpEmployee_age;
            _tmpEmployee_age = _cursor.getInt(_cursorIndexOfEmployeeAge);
            _item.setEmployee_age(_tmpEmployee_age);
            final String _tmpProfile_image;
            _tmpProfile_image = _cursor.getString(_cursorIndexOfProfileImage);
            _item.setProfile_image(_tmpProfile_image);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
