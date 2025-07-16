package yusufs.turan.myelements.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Element(
    @PrimaryKey
    @ColumnInfo(name = "Atom Numarası")
    val atomNumarasi: Int,

    @ColumnInfo(name = "Element Adı")
    val name: String,

    @ColumnInfo(name = "Ağırlık")
    val agirlik: Double,

    @ColumnInfo(name = "Madde Hali")
    val hal: String,

    @ColumnInfo(name = "Grup Numarası")
    val grup: String
)
