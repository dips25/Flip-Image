package com.example.flipimage.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flipimage.Models.TableInfo;

public class MySqlite extends SQLiteOpenHelper {

    private DatabaseErrorHandler errorHandler;

    private static final String TAG = MySqlite.class.getName();

    private static final String DATABASE_NAME = "FlipDatabase";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "FlipTable";
    private static final String ID = "id";
    private static final String TG_ID = "TGid";
    private static final String COM_ID = "ComID";
    private static final String GAME_ID = "Gameid";
    private static final String PIC1 = "pic1";
    private static final String PIC2 = "pic2";
    private static final String PIC3 = "pic3";
    private static final String PIC4 = "pic4";
    private static final String PIC5 = "pic5";
    private static final String PIC6 = "pic6";
    private static final String STATUS = "status";
    private static final String LOG = "log";

    SQLiteDatabase sqLiteDatabase;

    DatabaseErrorHandler databaseErrorHandler;

    private static MySqlite mySqlite;

    public static MySqlite getInstance(Context context){

        if (mySqlite == null) {

            return new MySqlite(context);
        }

        return mySqlite;
    }

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+TG_ID+" INTEGER,"
            +COM_ID+" INTEGER,"+GAME_ID+" INTEGER,"+PIC1+" TEXT,"+PIC2+" TEXT,"+PIC3+" TEXT,"+PIC4+" TEXT,"+PIC5+" TEXT,"+PIC6+" TEXT,"
            +STATUS+" TEXT,"+LOG+" TEXT"+")";
    public MySqlite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.databaseErrorHandler = errorHandler;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "onCreate: " + "Table Created");



    }

    public void insertData(TableInfo tableInfo) {

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID , tableInfo.getId());
        contentValues.put(TG_ID , tableInfo.getTGid());
        contentValues.put(COM_ID , tableInfo.getComID());
        contentValues.put(GAME_ID , tableInfo.getGameid());
        contentValues.put(PIC1 , tableInfo.getPic1());
        contentValues.put(PIC2 , tableInfo.getPic2());
        contentValues.put(PIC3 , tableInfo.getPic3());
        contentValues.put(PIC4 , tableInfo.getPic4());
        contentValues.put(PIC5 , tableInfo.getPic5());
        contentValues.put(PIC6 , tableInfo.getPic6());
        contentValues.put(STATUS , tableInfo.getStatus());
        contentValues.put(LOG , tableInfo.getLog());

        sqLiteDatabase.insert(TABLE_NAME , null , contentValues);
        sqLiteDatabase.close();





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}
