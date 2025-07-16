package yusufs.turan.myelements.roomdb

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import yusufs.turan.myelements.model.Element

@Dao
interface ElementDAO {

    @Query("SELECT * FROM Element")
    fun getAll(): Flowable<List<Element>>

    @Query("SELECT * FROM Element WHERE `Atom Numarası` = :atomNumarasi")
    fun findByAtomNumarasi(atomNumarasi: Int): Flowable<Element>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: Element): Completable

    @Delete
    fun delete(element: Element): Completable

    @Update
    fun update(element: Element): Completable
}
