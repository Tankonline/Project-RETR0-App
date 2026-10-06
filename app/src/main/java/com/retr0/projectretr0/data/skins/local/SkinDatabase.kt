package com.retr0.projectretr0.data.skins.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [LocalSkinEntity::class, SkinImageEntity::class, SkinBackupEntity::class, AppliedSkinEntity::class, SkinOperationEntity::class],
    version = 3,
    exportSchema = true,
)
abstract class SkinDatabase : RoomDatabase() {
    abstract fun skinDao(): SkinDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE local_skins ADD COLUMN targetRelativePath TEXT")
            }
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE local_skins ADD COLUMN communityId TEXT")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_local_skins_communityId ON local_skins(communityId)")
            }
        }

        @Volatile private var instance: SkinDatabase? = null
        fun get(context: Context): SkinDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, SkinDatabase::class.java, "skins.db")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build().also { instance = it }
        }
    }
}
