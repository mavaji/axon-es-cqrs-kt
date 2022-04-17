package com.example.demo.api

import com.example.demo.readside.AccountDto
import com.example.demo.readside.GetAll
import com.example.demo.writeside.DepositAccountCommand
import com.example.demo.writeside.OpenAccountCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/accounts")
class AccountController(
    val commandGateway: CommandGateway,
    val queryGateway: QueryGateway
) {
    @PostMapping("/")
    fun openAccount(@RequestBody openAccountDto:OpenAccountDto) {
        commandGateway.send<OpenAccountCommand>(OpenAccountCommand(UUID.randomUUID().toString(), openAccountDto.accountName))
    }

    @PostMapping("/{id}/deposit/{amount}")
    fun openAccount(@PathVariable id:String, @PathVariable amount:Int) {
        commandGateway.send<DepositAccountCommand>(DepositAccountCommand(id, amount))
    }

    @GetMapping("/")
    fun getAll():List<AccountDto> {
        return queryGateway.query(GetAll(), ResponseTypes.multipleInstancesOf(AccountDto::class.java)).get()
    }
}