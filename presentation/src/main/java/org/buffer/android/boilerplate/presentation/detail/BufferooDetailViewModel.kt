package org.buffer.android.boilerplate.presentation.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.subscribers.DisposableSubscriber
import org.buffer.android.boilerplate.domain.interactor.detail.GetBufferooByID
import org.buffer.android.boilerplate.domain.model.Bufferoo
import org.buffer.android.boilerplate.presentation.data.Resource
import org.buffer.android.boilerplate.presentation.data.ResourceState
import org.buffer.android.boilerplate.presentation.mapper.BufferooMapper
import org.buffer.android.boilerplate.presentation.model.BufferooView
import javax.inject.Inject

open class BufferooDetailViewModel @Inject internal constructor(
        private val getBufferooByID: GetBufferooByID,
        private val bufferooMapper: BufferooMapper) : ViewModel() {

    private val bufferooLiveData: MutableLiveData<Resource<BufferooView>> =
            MutableLiveData()

    override fun onCleared() {
        getBufferooByID.dispose()
        super.onCleared()
    }

    fun getBufferoo(): LiveData<Resource<BufferooView>> {
        return bufferooLiveData
    }

    fun fetchBufferoo(id: Long) {
        bufferooLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getBufferooByID.execute(BufferooSubscriber(), id)
    }

    inner class BufferooSubscriber: DisposableSubscriber<Bufferoo>() {

        override fun onComplete() { }

        override fun onNext(t: Bufferoo) {
            bufferooLiveData.postValue(Resource(ResourceState.SUCCESS,
                     bufferooMapper.mapToView(t), null))
        }

        override fun onError(exception: Throwable) {
            bufferooLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

    }

}