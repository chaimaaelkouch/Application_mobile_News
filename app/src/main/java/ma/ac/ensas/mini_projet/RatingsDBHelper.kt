package ma.ac.ensas.mini_projet

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class RatingsDBHelper(context: Context) : SQLiteOpenHelper(context, RATINGS_DATABASE_NAME, null, RATINGS_DATABASE_VERSION) {

    companion object {
        const val RATINGS_DATABASE_NAME = "ratings.db"
        const val RATINGS_DATABASE_VERSION = 1

        const val TABLE_RATINGS = "ratings"
        const val COLUMN_ID = "_id"
        const val COLUMN_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_RATINGS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY," +
                    "$COLUMN_RATING REAL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Mettez à jour la base de données si nécessaire
    }

    @SuppressLint("Range")
    fun loadRatingForArticle(articleId: Int): Float {
        val db = readableDatabase
        var rating = 0.0f

        val cursor = db.query(
            TABLE_RATINGS,
            arrayOf(COLUMN_RATING),
            "$COLUMN_ID = ?",
            arrayOf(articleId.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING))
        }

        cursor.close()
        db.close()

        return rating
    }

    fun saveRatingForArticle(articleId: Int, rating: Float) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, articleId)
            put(COLUMN_RATING, rating)
        }

        // Insert or update the rating for the given articleId
        val result = db.insertWithOnConflict(TABLE_RATINGS, null, values, SQLiteDatabase.CONFLICT_REPLACE)

        if (result == -1L) {
            // Insertion ou mise à jour a échoué
            Log.e("RatingsDBHelper", "Failed to save rating for article $articleId")
        }

        db.close()
    }
}
