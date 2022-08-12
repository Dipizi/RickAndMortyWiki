package com.dipizi007.rickandmorty.data.net.entity

sealed class Result<T> {

    class Success<T>(val data:T): Result<T>()

    class Error<T>(val e: Exception): Result<T>()

    class Pending<T>(): Result<T>()
}
