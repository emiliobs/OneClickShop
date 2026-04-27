package com.emilio.oneclickshop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emilio.oneclickshop.data.model.CartItem
import com.emilio.oneclickshop.data.model.Order
import com.emilio.oneclickshop.data.model.OrderItem

/**
 * AppDatabase is the MAIN DOOR to our local database.
 * It connects all the DAOs and tells Room which tables to create.
 *
 * @Database lists every table (Entity) and the database version.
 * If you add a new table later, increase the version number.
 *
 * This is also a Singleton — only ONE database exists in the app.
 * Just like a house only needs ONE front door.
 */
@Database(
    entities = [
        CartItem::class,    // creates the "cart_items" table
        Order::class,       // creates the "orders" table
        OrderItem::class    // creates the "order_items" table
    ],
    version = 1,
    exportSchema = false    // no need to export schema for this coursework
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Room generates the actual implementation of these DAOs automatically.
     * We just declare them here as abstract functions.
     */
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao

    /**
     * companion object = like a static class in Java.
     * INSTANCE makes sure we only ever create ONE database.
     *
     * @Volatile = all threads see the same value immediately.
     * This prevents two threads creating two databases at the same time.
     */
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Gets the existing database or creates it for the first time.
         * synchronized = only one thread can run this block at a time.
         * Safe for multi-threading — no crashes.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "oneclickshop_database"  // the name of the .db file on the device
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}