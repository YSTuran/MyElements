package yusufs.turan.receiptbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import yusufs.turan.myelements.model.Element
import yusufs.turan.myelements.roomdb.ElementDAO

@Database(entities = [Element::class], version = 1)
abstract class ElementDB : RoomDatabase() {
    abstract fun elementDao(): ElementDAO
}