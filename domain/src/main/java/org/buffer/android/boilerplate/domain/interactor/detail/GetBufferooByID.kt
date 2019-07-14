package org.buffer.android.boilerplate.domain.interactor.detail

import io.reactivex.Flowable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.buffer.android.boilerplate.domain.executor.ThreadExecutor
import org.buffer.android.boilerplate.domain.interactor.FlowableUseCase
import org.buffer.android.boilerplate.domain.model.Bufferoo
import org.buffer.android.boilerplate.domain.repository.BufferooRepository
import javax.inject.Inject

/**
 * Use case used for retrieving a [Bufferoo] instance by ID from the [BufferooRepository]
 */
open class GetBufferooByID @Inject constructor(val bufferooRepository: BufferooRepository,
                                            threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread):
        FlowableUseCase<Bufferoo, Long?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Long?): Flowable<Bufferoo> {
        return bufferooRepository.getBufferooByID(params!!)
    }

}