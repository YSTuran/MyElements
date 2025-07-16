package yusufs.turan.myelements.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


class Element {
    @Entity
    data class Element(
        @PrimaryKey
        @ColumnInfo(name = "Atom Numarası")
        var atomNumarasi: Int,

        @ColumnInfo(name = "Element Adı")
        var name: String,

        @ColumnInfo(name = "Ağırlık")
        var agirlik: Double,

        @ColumnInfo(name = "Madde Hali")
        var hal: String,

        @ColumnInfo(name = "Grup Numarası")
        var grup: String,

        @ColumnInfo(name = "Element Cinsi")
        var cins: String
    )
}