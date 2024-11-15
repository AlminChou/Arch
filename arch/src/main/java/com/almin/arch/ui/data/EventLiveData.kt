package com.almin.arch.ui.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by Almin on 2024/10/31.
 * Desc: 事件性 livedata， 支持取消黏性
 */
class EventLiveData<T>(private val observeLastVersionValue: Boolean = false) :
    MutableLiveData<T>() {

    @Volatile
    private var version: Int = 0

    override fun setValue(value: T?) {
        version++
        super.setValue(value)
    }

//     不需要重写 version++， 内部最终会调用setValue
//    override fun postValue(value: T?) {
//        super.postValue(value)
//    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverWrapper(observer, observeLastVersionValue, this))
    }

    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(ObserverWrapper(observer, observeLastVersionValue, this))
    }

    override fun removeObserver(observer: Observer<in T>) {
        if (observer is ObserverWrapper) {
            observer.onRemove()
        }
        super.removeObserver(observer)
        if(!hasObservers()) {
            version = 0
        }
    }

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        version = 0
    }

    internal class ObserverWrapper<T>(
        private val observer: Observer<in T>,
        private val observeLastVersionValue: Boolean,
        private var liveData: EventLiveData<T>?
    ) : Observer<T> {

        private var lastVersion = liveData?.version ?: 0

        override fun onChanged(value: T) {
            val currentVersion = liveData?.version ?: 0
            if (lastVersion < currentVersion || observeLastVersionValue) {
                lastVersion = currentVersion
                observer.onChanged(value)
            }
        }

        fun onRemove() {
            liveData = null
        }
    }
}
