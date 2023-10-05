package com.ivy.data.testing

import com.ivy.data.model.Account
import com.ivy.data.model.AccountId
import com.ivy.data.model.primitive.AssetCode
import com.ivy.data.model.primitive.ColorInt
import com.ivy.data.model.primitive.NotBlankTrimmedString
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.time.Instant
import java.util.UUID

class FakeAccountRepositoryTest : FreeSpec({
    fun newRepository() = FakeAccountRepository()

    "find by id" - {
        "existing id" {
            // given
            val repository = newRepository()
            val id = AccountId(UUID.randomUUID())
            val account = Account(
                id = id,
                name = NotBlankTrimmedString("Bank"),
                asset = AssetCode("BGN"),
                color = ColorInt(1),
                icon = null,
                includeInBalance = true,
                orderNum = 1.0,
                lastUpdated = Instant.EPOCH,
                removed = false
            )

            // when
            repository.save(account)
            val res = repository.findById(id)

            // then
            res shouldBe account
        }

        "not existing id" {
            // given
            val repository = newRepository()
            val id = AccountId(UUID.randomUUID())

            // when
            val res = repository.findById(id)

            // then
            res shouldBe null
        }
    }

    "find all not deleted" - {
        "accounts" {
            // given
            val repository = newRepository()
            val id = AccountId(UUID.randomUUID())
            val accounts = listOf(
                Account(
                    id = id,
                    name = NotBlankTrimmedString("Bank"),
                    asset = AssetCode("BGN"),
                    color = ColorInt(1),
                    icon = null,
                    includeInBalance = true,
                    orderNum = 1.0,
                    lastUpdated = Instant.EPOCH,
                    removed = false
                )
            )

            // when
            repository.saveMany(accounts)
            val res = repository.findAll(false)

            // then
            res shouldBe accounts
        }

        "empty list" {
            // given
            val repository = newRepository()
            val accounts = emptyList<Account>()

            // when
            repository.saveMany(accounts)
            val res = repository.findAll(false)

            // then
            res shouldBe emptyList()
        }
    }
})