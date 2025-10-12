/*
* Angel Gabriel Chavez Otzoy - 24248
* */
package com.example.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app.database.dao.CharacterDao
import com.example.app.database.dao.LocationDao
import com.example.app.database.entities.CharacterEntity
import com.example.app.database.entities.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rick_morty_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}