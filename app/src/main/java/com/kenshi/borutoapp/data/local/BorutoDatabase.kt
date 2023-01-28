package com.kenshi.borutoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kenshi.borutoapp.data.local.dao.HeroDao
import com.kenshi.borutoapp.data.local.dao.HeroRemoteKeyDao
import com.kenshi.borutoapp.domain.model.Hero
import com.kenshi.borutoapp.domain.model.HeroRemoteKeys

@Database(
    entities = [Hero::class, HeroRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {

    //remoteMediator test class 가 이 함수에 접근
    // test class 를 위한 메모리 내 데이터베이스를 생성, 실제 데이터베이스를 테스트하는것은 어려움
    companion object {
        fun create(context: Context, useInMemory: Boolean): BorutoDatabase {
            val databaseBuilder = if(useInMemory) {
                Room.inMemoryDatabaseBuilder(context, BorutoDatabase::class.java)
            } else {
                Room.databaseBuilder(context, BorutoDatabase::class.java, "test_database.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeyDao
}