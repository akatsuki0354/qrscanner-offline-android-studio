package Databases;

import static android.provider.Contacts.SettingsColumns.KEY;
import static org.xmlpull.v1.XmlPullParser.TEXT;

import static java.sql.Types.INTEGER;
import static java.text.Collator.PRIMARY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import Model.ListItem;

public class DbHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME = "mytable";
    public static final String DATABASE_NAME = "qrdb.db";

    public static final String COL_1 = ("id");
    public static final String COL_2 = "code";
    public static final String COL_3 = "type";



    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL ("create table " + TABLE_NAME + "(id  INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                " code TEXT, type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE EXISTS"+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String code, String type)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,code);
        contentValues.put(COL_3,type);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }


    public ArrayList<ListItem>getAllInformation()
    {
        ArrayList<ListItem> arrayList = new ArrayList<>();


        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME,null);

        if (cursor!=null)
        {
            while (cursor.moveToNext())
            {
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                String type = cursor.getString(2);

                ListItem listItem = new ListItem(id,code,type);

                arrayList.add(listItem);
            }
        }



        return arrayList;
    }


    public void deleteRow(int value)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME +" WHERE " +COL_1+ " =\" "+ value +"\"; ");
        sqLiteDatabase.close();
    }


}
