package com.asuni.assignment.db.util;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.asuni.assignment.db.DAO.EmpDao;
import com.asuni.assignment.db.DAO.EmpDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EmpDatabase_Impl extends EmpDatabase {
  private volatile EmpDao _empDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `emp_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `employee_name` TEXT, `employee_salary` INTEGER NOT NULL, `employee_age` INTEGER NOT NULL, `profile_image` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '32cee17a04e3c68fa82986f9a228bf4a')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `emp_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsEmpTable = new HashMap<String, TableInfo.Column>(5);
        _columnsEmpTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpTable.put("employee_name", new TableInfo.Column("employee_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpTable.put("employee_salary", new TableInfo.Column("employee_salary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpTable.put("employee_age", new TableInfo.Column("employee_age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpTable.put("profile_image", new TableInfo.Column("profile_image", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmpTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEmpTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEmpTable = new TableInfo("emp_table", _columnsEmpTable, _foreignKeysEmpTable, _indicesEmpTable);
        final TableInfo _existingEmpTable = TableInfo.read(_db, "emp_table");
        if (! _infoEmpTable.equals(_existingEmpTable)) {
          return new RoomOpenHelper.ValidationResult(false, "emp_table(com.asuni.assignment.db.entity.EmpModal).\n"
                  + " Expected:\n" + _infoEmpTable + "\n"
                  + " Found:\n" + _existingEmpTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "32cee17a04e3c68fa82986f9a228bf4a", "8666011819e49d126d714643eea270f0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "emp_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `emp_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public EmpDao Dao() {
    if (_empDao != null) {
      return _empDao;
    } else {
      synchronized(this) {
        if(_empDao == null) {
          _empDao = new EmpDao_Impl(this);
        }
        return _empDao;
      }
    }
  }
}
