package com.example.demo.events

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AccountOpened(
    @TargetAggregateIdentifier val accountId:String,
    val accountName:String
)

data class AmountDeposited(
    @TargetAggregateIdentifier val accountId:String,
    val amount:Int
)