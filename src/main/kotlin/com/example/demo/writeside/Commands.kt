package com.example.demo.writeside

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class OpenAccountCommand(
    @TargetAggregateIdentifier val accountId:String,
    val accountName:String
)

data class DepositAccountCommand(
    @TargetAggregateIdentifier val accountId:String,
    val amount:Int
)