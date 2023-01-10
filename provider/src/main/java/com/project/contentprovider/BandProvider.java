package com.project.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.contentprovider.database.DB;


public class BandProvider extends ContentProvider {

    static final String AUTHORITY = "provider.BandsList";
    static final String PATH = "bands";

    static final String BAND_NAME = "BANDNAME";
    static final String BAND_ID = "IDBAND";
    static final String BAND_TABLE = "BAND_TABLE";

    // Общий Uri
    public static final Uri BAND_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

    // Типы данных
    // набор строк
    static final String BAND_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH;

    // одна строка
    static final String BAND_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH;

    //// UriMatcher
    // общий Uri
    static final int URI_BANDS = 1;

    // Uri с указанным ID
    static final int URI_BANDS_ID = 2;

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, URI_BANDS);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", URI_BANDS_ID);
    }

    com.project.contentprovider.database.DB DB;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DB = new DB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // проверяем Uri
        switch (uriMatcher.match(uri)) {
            case URI_BANDS: // общий Uri
                // если сортировка не указана, ставим свою - по имени
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BAND_NAME + " ASC";
                }
                break;
            case URI_BANDS_ID: // Uri с ID
                String id = uri.getLastPathSegment();
                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = BAND_ID + " = " + id;
                } else {
                    selection = selection + " AND " + BAND_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = DB.getWritableDatabase();
        Cursor cursor = db.query(BAND_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),
                BAND_CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_BANDS:
                return BAND_CONTENT_TYPE;
            case URI_BANDS_ID:
                return BAND_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) != URI_BANDS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = DB.getWritableDatabase();
        long rowID = db.insert(BAND_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(BAND_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_BANDS:
                break;
            case URI_BANDS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = BAND_ID + " = " + id;
                } else {
                    selection = selection + " AND " + BAND_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = DB.getWritableDatabase();
        int cnt = db.delete(BAND_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_BANDS:
                break;
            case URI_BANDS_ID:
                String id = uri.getLastPathSegment();
                Toast.makeText(getContext(), "URI_CONTACTS_ID, " + id, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(selection)) {
                    selection = BAND_ID + " = " + id;
                } else {
                    selection = selection + " AND " + BAND_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = DB.getWritableDatabase();
        int cnt = db.update(BAND_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
}
