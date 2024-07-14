package com.flasshka.data.di

import javax.inject.Qualifier


/**
 * Network data source qualifier
 */
@Qualifier
annotation class NetworkDataSourceQualifier

/**
 * Database data source qualifier
 */
@Qualifier
annotation class DatabaseDataSourceQualifier

@Qualifier
annotation class NetworkRepositoryQualifier

@Qualifier
annotation class DatabaseRepositoryQualifier