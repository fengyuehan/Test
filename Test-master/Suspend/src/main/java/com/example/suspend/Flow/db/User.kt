package com.example.suspend.Flow.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * 1、@PrimaryKey定义主键，并可以通过autoGenerate指定是否为自增长
 * 2、默认情况下，Room会将类名作为表名，我们可以通过@Entity的tableName属性去定义表的名字。
 *    @Entity(tableName = "users")
 * 3、默认情况下，Room会将属性名作为列名，我们可以通过@ColumnInfo去自定义列名。
 * 4、默认情况下，Room会为实体类中的每个字段都生成对应的表的列。如果我们有某个字段不想在建列，那么可以使用@Ignore注解，表示忽略该字段
 *    如果实体类继承了父类的字段，想要忽略父类的字段可以使用@Entity的ignoredColumns字段去声明
 *     open class User{
 *           var picture:Bitmap? = null
 *     }
 *     @Entity(ignoredColumns = arrayOf("picture"))
 *     data class RemoteUser(
 *          @PrimaryKey val id: Int,
 *          val hasVpn: Boolean
 *     ) :User()
 *
 *
 */
@Entity
data class User (
        @PrimaryKey(autoGenerate = true)
        val id:Int,

        //Room会将属性名作为列名，我们可以通过@ColumnInfo去自定义列名。可以不写
        @ColumnInfo(name = "name")
        val name:String,

        @ColumnInfo(name = "email")
        val email:String,

        @ColumnInfo(name = "avatar")
        val avatar:String

 )

