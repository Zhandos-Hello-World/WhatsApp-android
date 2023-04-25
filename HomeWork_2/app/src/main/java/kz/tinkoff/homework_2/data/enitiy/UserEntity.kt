package kz.tinkoff.homework_2.data.enitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("full_name")
    val fullName: String,
    @ColumnInfo("email")
    val email: String,
    @ColumnInfo("is_mirror_dummy")
    val isMirrorDummy: Boolean = false,
)
