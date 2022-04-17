package com.example.demo.readside

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountEntityRepository:CrudRepository<AccountEntity, String>