package com.example.demo.readside

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class AccountEntity {
    @Id
    lateinit var id:String
    lateinit var name:String
    var amount:Int = 0

    constructor()

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }
}