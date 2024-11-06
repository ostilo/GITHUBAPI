package com.github.githubsearch.data

import com.github.githubsearch.data.mapper.toDomain
import com.github.githubsearch.data.mapper.toDomains
import com.github.githubsearch.data.mapper.toUserDomain
import com.github.githubsearch.data.mapper.toUserDomains
import com.github.githubsearch.data.mapper.toUserRepoDomain
import com.github.githubsearch.data.mapper.toUserRepoDomains
import org.junit.Test
import org.junit.Assert.assertEquals

internal class MappersKtTest {


    @Test
    fun `Given mockRepositoryList object when mapped to domain returns valid list of repositories `() {
        val given = mockRepositoryListDto.items
        val expected = mockRepoList

        val result = given

        assertEquals(result, expected)
    }

    @Test
    fun `Given reposDto object when mapped to domain returns valid repo entity `() {
        val given = mockRepositoryDto
        val expected = mockRepository

        val result = given.toDomains()

        assertEquals(result, expected)
    }

    @Test
    fun `Given mockUsersList object when mapped to domain returns valid list of users `() {
        val given = mockUsersListDto.toUserDomain().items
        val expected = mockUsersList

        val result = given
            //.toUserDomain()

        assertEquals(result, expected)
    }

    @Test
    fun `Given usersDto object when mapped to domain returns valid users entity `() {
        val given = mockUsersDto
        val expected = mockUsers

        val result = given.toUserDomains()

        assertEquals(result, expected)
    }


    @Test
    fun `Given mockUsersRepoList object when mapped to domain returns valid list of users repositories `() {
        val given = mockUsersRepoListDto
        val expected = mockUsersRepoList

        val result = given

        assertEquals(result, expected)
    }

    @Test
    fun `Given usersRepoDto object when mapped to domain returns valid userRepo entity `() {
        val given = mockUsersRepoDto
        val expected = mockUsersRepo

        val result = given.toUserRepoDomains()

        assertEquals(result, expected)
    }

}