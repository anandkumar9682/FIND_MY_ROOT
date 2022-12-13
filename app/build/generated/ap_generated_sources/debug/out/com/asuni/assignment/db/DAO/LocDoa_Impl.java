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
import com.asuni.assignment.db.entity.LocModel;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class LocDoa_Impl implements LocDoa {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LocModel> __insertionAdapterOfLocModel;

  private final EntityDeletionOrUpdateAdapter<LocModel> __deletionAdapterOfLocModel;

  private final EntityDeletionOrUpdateAdapter<LocModel> __updateAdapterOfLocModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllLocs;

  public LocDoa_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocModel = new EntityInsertionAdapter<LocModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `loc_table` (`id`,`name`,`prioriry`,`address`,`distance`,`lat`,`log`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getPrioriry());
        if (value.getAddress() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAddress());
        }
        stmt.bindDouble(5, value.getDistance());
        if (value.getLat() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLat());
        }
        if (value.getLog() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLog());
        }
      }
    };
    this.__deletionAdapterOfLocModel = new EntityDeletionOrUpdateAdapter<LocModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `loc_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfLocModel = new EntityDeletionOrUpdateAdapter<LocModel>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `loc_table` SET `id` = ?,`name` = ?,`prioriry` = ?,`address` = ?,`distance` = ?,`lat` = ?,`log` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getPrioriry());
        if (value.getAddress() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAddress());
        }
        stmt.bindDouble(5, value.getDistance());
        if (value.getLat() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLat());
        }
        if (value.getLog() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLog());
        }
        stmt.bindLong(8, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllLocs = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM loc_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final LocModel model) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocModel.insert(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final LocModel model) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfLocModel.handle(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final LocModel model) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLocModel.handle(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllLocs() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllLocs.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllLocs.release(_stmt);
    }
  }

  @Override
  public LiveData<List<LocModel>> getAllLocsAsc() {
    final String _sql = "SELECT * FROM loc_table ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"loc_table"}, false, new Callable<List<LocModel>>() {
      @Override
      public List<LocModel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrioriry = CursorUtil.getColumnIndexOrThrow(_cursor, "prioriry");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLog = CursorUtil.getColumnIndexOrThrow(_cursor, "log");
          final List<LocModel> _result = new ArrayList<LocModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocModel _item;
            _item = new LocModel();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final int _tmpPrioriry;
            _tmpPrioriry = _cursor.getInt(_cursorIndexOfPrioriry);
            _item.setPrioriry(_tmpPrioriry);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            _item.setAddress(_tmpAddress);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            _item.setDistance(_tmpDistance);
            final String _tmpLat;
            _tmpLat = _cursor.getString(_cursorIndexOfLat);
            _item.setLat(_tmpLat);
            final String _tmpLog;
            _tmpLog = _cursor.getString(_cursorIndexOfLog);
            _item.setLog(_tmpLog);
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

  @Override
  public LiveData<List<LocModel>> getAllLocsDesc() {
    final String _sql = "SELECT * FROM loc_table ORDER BY name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"loc_table"}, false, new Callable<List<LocModel>>() {
      @Override
      public List<LocModel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrioriry = CursorUtil.getColumnIndexOrThrow(_cursor, "prioriry");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLog = CursorUtil.getColumnIndexOrThrow(_cursor, "log");
          final List<LocModel> _result = new ArrayList<LocModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocModel _item;
            _item = new LocModel();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final int _tmpPrioriry;
            _tmpPrioriry = _cursor.getInt(_cursorIndexOfPrioriry);
            _item.setPrioriry(_tmpPrioriry);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            _item.setAddress(_tmpAddress);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            _item.setDistance(_tmpDistance);
            final String _tmpLat;
            _tmpLat = _cursor.getString(_cursorIndexOfLat);
            _item.setLat(_tmpLat);
            final String _tmpLog;
            _tmpLog = _cursor.getString(_cursorIndexOfLog);
            _item.setLog(_tmpLog);
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

  @Override
  public LiveData<List<LocModel>> getAllLocsByPriority() {
    final String _sql = "SELECT * FROM loc_table ORDER BY prioriry ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"loc_table"}, false, new Callable<List<LocModel>>() {
      @Override
      public List<LocModel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrioriry = CursorUtil.getColumnIndexOrThrow(_cursor, "prioriry");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLog = CursorUtil.getColumnIndexOrThrow(_cursor, "log");
          final List<LocModel> _result = new ArrayList<LocModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocModel _item;
            _item = new LocModel();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final int _tmpPrioriry;
            _tmpPrioriry = _cursor.getInt(_cursorIndexOfPrioriry);
            _item.setPrioriry(_tmpPrioriry);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            _item.setAddress(_tmpAddress);
            final double _tmpDistance;
            _tmpDistance = _cursor.getDouble(_cursorIndexOfDistance);
            _item.setDistance(_tmpDistance);
            final String _tmpLat;
            _tmpLat = _cursor.getString(_cursorIndexOfLat);
            _item.setLat(_tmpLat);
            final String _tmpLog;
            _tmpLog = _cursor.getString(_cursorIndexOfLog);
            _item.setLog(_tmpLog);
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
