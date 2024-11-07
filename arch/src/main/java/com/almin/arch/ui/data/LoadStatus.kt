package com.almin.arch.ui.data



/**
 * Created by Almin on 2023/10/8.
 */
sealed class LoadStatus {
    object Default : LoadStatus()
    object Loading : LoadStatus()
    object Finish : LoadStatus()
    object Empty : LoadStatus()
    object NoMore : LoadStatus()
    object LoadFailed : LoadStatus()
    object LoadMore : LoadStatus()
    object LoadMoreFailed : LoadStatus()
    object LoadMoreFinish : LoadStatus()
    object LoadMoreEnd : LoadStatus()
    object Refresh : LoadStatus()
    object RefreshFailed : LoadStatus()
    object RefreshFinish : LoadStatus()
    companion object {
        fun loading(refresh: Boolean = false, loadMore: Boolean = false): LoadStatus {
            return when {
                refresh -> Refresh
                loadMore -> LoadMore
                else -> Loading
            }
        }

        fun finish(refresh: Boolean = false, loadMore: Boolean = false): LoadStatus {
            return when {
                refresh -> RefreshFinish
                loadMore -> LoadMoreFinish
                else -> Finish
            }
        }

        fun noData(refresh: Boolean = false, loadMore: Boolean = false): LoadStatus {
            return when {
                refresh -> Empty
                loadMore -> LoadMoreEnd
                else -> Empty
            }
        }

        fun failed(refresh: Boolean = false, loadMore: Boolean = false): LoadStatus {
            return when {
                refresh -> RefreshFailed
                loadMore -> LoadMoreFailed
                else -> LoadFailed
            }
        }
    }

}


data class UiData<T>(
    var value: T? = null,
    var loadStatus: LoadStatus = LoadStatus.Default,
    var message: String? = null,
    var throwable: Throwable? = null
)  {

    companion object {
        fun <T> loading() : UiData<T> = UiData<T>().apply { loadStatus = LoadStatus.Loading }
        fun <T> refresh() : UiData<T> = UiData<T>().apply { loadStatus = LoadStatus.Refresh }
        fun <T> loadMore() : UiData<T> = UiData<T>().apply { loadStatus = LoadStatus.LoadMore }

        fun <T> state(loadStatus: LoadStatus) : UiData<T> = UiData<T>(loadStatus = loadStatus)

        fun <T> failed(loadStatus: LoadStatus = LoadStatus.LoadFailed, message: String?, throwable: Throwable? = null) : UiData<T> = UiData<T>(loadStatus = loadStatus, message = message)

        fun <T> error(loadStatus: LoadStatus) : UiData<T> = UiData<T>(loadStatus = loadStatus)
    }
}
