package com.cwclaims.claimapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ClaimSqlLiteDbHelper extends SQLiteOpenHelper
{

	private static final int DATABASE_VERSION = 1;

	//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	public static String claimdb_PATH = "/data/data/com.cwclaims.old_ClaimMate_4/databases/";
	public static final String claimdb_NAME = "claimmatedb.sqlite";
	public static SQLiteDatabase DB;
	private final String tbCustomReport = "CustomReport", id = "id", reportName = "ReportName", report = "Report";
	Context context;

	public ClaimSqlLiteDbHelper(Context context)
	{
		super(context, claimdb_NAME, null, DATABASE_VERSION);
		this.context = context;
		claimdb_PATH = context.getFilesDir().getPath();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase DB)
	{
		DB.execSQL("create table CustomData (id integer primary key autoincrement, name text)");
		DB.execSQL("create table "+tbCustomReport+" ("+id+" integer primary key autoincrement, "+reportName+" text, "+report+" text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion)
	{
	}

	//copy DB from Assets folder
	public void CopyDataBaseFromAsset() throws IOException
	{
		InputStream in = context.getAssets().open(claimdb_NAME);

		String outputFileName = claimdb_PATH + claimdb_NAME;

		File databaseFile = new File(claimdb_PATH);
		if (!databaseFile.exists())
		{
			databaseFile.mkdir();
		}

		OutputStream out = new FileOutputStream(outputFileName);
		byte[] buffer = new byte[1024];
		int length;

		while ((length = in.read(buffer)) > 0)
		{
			out.write(buffer, 0, length);
		}
		out.flush();
		out.close();
		in.close();
	}

	//open DB
	public void openDataBase() throws SQLException
	{
		String path = claimdb_PATH + claimdb_NAME;
		DB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
	}

	public void addCustomData(String name) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		db.insert("CustomData", null, contentValues);
		db.close();
	}

	public ArrayList<String> getCustomData(boolean isEdit) {
		ArrayList<String> arrayList = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		arrayList.add("");
		arrayList.add("Temp");
		arrayList.add("GLR");
		Cursor cursor = db.rawQuery("select * from CustomData", null);
		while (cursor.moveToNext()) {
			arrayList.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		if (isEdit)
			arrayList.add("Add New Option");
		else
			arrayList.add("Insert custom data");
		db.close();
		return arrayList;
	}

	public void addCustomReport(String repName, String rep) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(reportName, repName);
		contentValues.put(report, rep);
		db.insert(tbCustomReport, null, contentValues);
		db.close();
	}

	public ArrayList<HashMap<String, String>> getCustomReport() {
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "+tbCustomReport, null);
		while (cursor.moveToNext()) {
			HashMap<String, String> hm = new HashMap<>();
			hm.put(id, cursor.getString(cursor.getColumnIndex(id)));
			hm.put(reportName, cursor.getString(cursor.getColumnIndex(reportName)));
			hm.put(report, cursor.getString(cursor.getColumnIndex(report)));
			arrayList.add(hm);
		}
		db.close();
		return arrayList;
	}
}