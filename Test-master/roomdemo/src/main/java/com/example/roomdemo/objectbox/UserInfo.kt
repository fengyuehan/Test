package com.example.roomdemo.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class UserInfo {

    @Id(assignable = true)
    var id :Long = 1
    var nickName:String ? = null
    var sex:Int = 0

    constructor(id: Long, nickName: String?, sex: Int) {
        this.id = id
        this.nickName = nickName
        this.sex = sex
    }
}