package com.example.demo.writeside

import com.example.demo.events.AccountOpened
import com.example.demo.events.AmountDeposited
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Account {
    @AggregateIdentifier
    lateinit var accountId: String
    lateinit var accountName: String

    var amount = 0

    constructor()

    @CommandHandler
    constructor(command: OpenAccountCommand) {
        AggregateLifecycle.apply(AccountOpened(command.accountId, command.accountName))
    }

    @CommandHandler
    fun handle(command: DepositAccountCommand) {
        AggregateLifecycle.apply(AmountDeposited(command.accountId, command.amount))
    }

    @EventSourcingHandler
    fun handle(event: AccountOpened) {
        this.accountId = event.accountId
        this.accountName = event.accountName
    }

    @EventSourcingHandler
    fun handle(event: AmountDeposited) {
        this.amount += event.amount
    }
}