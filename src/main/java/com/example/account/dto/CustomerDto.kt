package com.example.account.dto

import com.example.account.model.Account

data class CustomerDto(
        val id:String?,
        val name: String?,
        val surname: String?,
        val accounts: Set<CustomerAccountDto>
){

}