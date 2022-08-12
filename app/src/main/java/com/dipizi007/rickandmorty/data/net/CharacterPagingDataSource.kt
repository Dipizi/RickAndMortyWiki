package com.dipizi007.rickandmorty.data.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dipizi007.rickandmorty.data.net.entity.Character
import com.dipizi007.rickandmorty.data.net.entity.Results
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingDataSource(private val data: suspend (page: Int) -> Character): PagingSource<Int, Results>() {

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = data.invoke(nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.info.pages) nextPageNumber + 1 else null
            )
        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }
    }
}
