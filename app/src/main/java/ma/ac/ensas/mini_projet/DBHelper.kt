package ma.ac.ensas.mini_projet

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "NewsDatabase"
        private const val TABLE_NAME = "news"

        // Colonnes de la table
        private const val KEY_ID = "id"
        private const val KEY_TITRE = "titre"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_IMAGE = "image"
        private const val KEY_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Création de la table
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_TITRE TEXT,"
                + "$KEY_DESCRIPTION TEXT,"
                + "$KEY_IMAGE TEXT,"
                + "$KEY_CATEGORY TEXT"
                + ")")
        db.execSQL(CREATE_TABLE)
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Gestion des mises à jour de la base de données, si nécessaire
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Exemple d'insertion d'une nouvelle news
    fun addNews(news: News,context: Context) {
        val db = this.writableDatabase

        // Vérifier si l'article existe déjà dans la base de données
        if (!isNewsAlreadyInDatabase(news.id)) {
            val values = ContentValues()

            values.put(KEY_ID, news.id)
            values.put(KEY_TITRE, news.titre)
            values.put(KEY_DESCRIPTION, news.description)
            values.put(KEY_IMAGE, news.image)
            values.put(KEY_CATEGORY, news.category)

            // Insertion de la nouvelle news dans la table
            db.insert(TABLE_NAME, null, values)
            Toast.makeText(context, "Article ajoutées avec succès", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Article déja existe", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

    private fun isNewsAlreadyInDatabase(newsId: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $KEY_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(newsId.toString()))

        val alreadyExists = cursor.count > 0

        cursor.close()
        return alreadyExists
    }
    @SuppressLint("Range")
    fun getAllNews(): List<News> {
        val newsList = mutableListOf<News>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db?.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            return ArrayList()
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    val titre = cursor.getString(cursor.getColumnIndex(KEY_TITRE))
                    val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                    val image = cursor.getInt(cursor.getColumnIndex(KEY_IMAGE))
                    val category = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY))
                    val news = News(id, titre,description, image, category)
                    newsList.add(news)
                } while (cursor.moveToNext())
            }

            cursor.close()
        }

        return newsList
    }


    // Fonction pour supprimer une news en fonction de son ID
    fun deleteAllNews(): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, null, null)
        return result > 0
    }
}


