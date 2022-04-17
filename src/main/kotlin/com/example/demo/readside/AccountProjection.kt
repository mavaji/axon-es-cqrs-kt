package com.example.demo.readside

import com.example.demo.events.AccountOpened
import com.example.demo.events.AmountDeposited
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Service

@Service
class AccountProjection(
    val repository: AccountEntityRepository,
) {

    @QueryHandler
    fun getAll(query: GetAll): List<AccountDto> {
        return repository.findAll().map { AccountDto(it.id, it.name, it.amount) }
    }

    @EventHandler
    fun handle(event: AccountOpened) {
        repository.save(AccountEntity(event.accountId, event.accountName))
    }

    @EventHandler
    fun handle(event: AmountDeposited) {
        val accountEntity = repository.findById(event.accountId).get()
        accountEntity.amount += event.amount
        repository.save(accountEntity)
    }
}