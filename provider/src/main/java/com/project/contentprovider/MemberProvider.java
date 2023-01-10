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

public class MemberProvider extends ContentProvider {

    static final String AUTHORITY = "provider.MembersList";
    static final String PATH = "members";

    static final String MEMBER_NAME = "MEMBERNAME";
    static final String MEMBER_ID = "IDMEMBER";
    static final String MEMBER_TABLE = "MEMBER_TABLE";

    public static final Uri MEMBER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);


    static final String MEMBER_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH;

    static final String MEMBER_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH;


    static final int URI_MEMBERS = 1;

    static final int URI_MEMBERS_ID = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, URI_MEMBERS);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", URI_MEMBERS_ID);
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
        switch (uriMatcher.match(uri)) {
            case URI_MEMBERS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = MEMBER_NAME + " ASC";
                }
                break;
            case URI_MEMBERS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = MEMBER_ID + " = " + id;
                } else {
                    selection = selection + " AND " + MEMBER_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = DB.getWritableDatabase();
        Cursor cursor = db.query(MEMBER_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                MEMBER_CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case URI_MEMBERS:
                return MEMBER_CONTENT_TYPE;
            case URI_MEMBERS_ID:
                return MEMBER_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) != URI_MEMBERS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = DB.getWritableDatabase();
        long rowID = db.insert(MEMBER_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(MEMBER_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_MEMBERS:
                break;
            case URI_MEMBERS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = MEMBER_ID + " = " + id;
                } else {
                    selection = selection + " AND " + MEMBER_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = DB.getWritableDatabase();
        int cnt = db.delete(MEMBER_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_MEMBERS:
                break;
            case URI_MEMBERS_ID:
                String id = uri.getLastPathSegment();
                Toast.makeText(getContext(), "URI_CONTACTS_ID, " + id, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(selection)) {
                    selection = MEMBER_ID + " = " + id;
                } else {
                    selection = selection + " AND " + MEMBER_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = DB.getWritableDatabase();
        int cnt = db.update(MEMBER_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
}
