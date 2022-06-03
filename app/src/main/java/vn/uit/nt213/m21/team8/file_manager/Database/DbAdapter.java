//package vn.uit.nt213.m21.team8.file_manager.Database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import java.io.File;
//
//public class DbAdapter {
//    public static final String KEY_ID = "_id";
//    public static final String KEY_PATH = "originalPath";
//    public static final File KEY_FILE = "file";
//
//
//    private DatabaseHelper dbHelper;
//    private SQLiteDatabase sqLiteDatabase;
//    private static final String DATABASE_NAME = "dbFileManager";
//    private static final String DATABASE_TABLE = "files";
//    private static final int DATABASE_VERSION = 2;
//    private final Context context;
//
//    public DbAdapter(Context ctx) {
//        this.context = ctx;
//    }
//
//    public DbAdapter open() {
//        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
//        sqLiteDatabase = dbHelper.getWritableDatabase();
//        return this;
//    }
//
//    public void close() {
//        dbHelper.close();
//    }
//
//    public long addPath(String path) {
//        ContentValues inititalValues = new ContentValues();
//        inititalValues.put(KEY_PATH, path);
//        return sqLiteDatabase.insert(DATABASE_TABLE, null, inititalValues);
//    }
//    public boolean deletePath(long rowId) {
//        return sqLiteDatabase.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
//    }
//    public boolean deleteAllUsers() {
//        return sqLiteDatabase.delete(DATABASE_TABLE, null, null) > 0;
//    }
//
//    public Cursor getAllUsers() {
//        return sqLiteDatabase.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_PATH}, null, null, null, null, null);
//    }
//}
//
//}
