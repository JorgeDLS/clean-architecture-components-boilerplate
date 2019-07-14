package org.buffer.android.boilerplate.ui.injection.module

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.buffer.android.boilerplate.ui.UiThread
import org.buffer.android.boilerplate.ui.browse.BrowseActivity
import org.buffer.android.boilerplate.ui.detail.DetailActivity

/**
 * Module that provides all dependencies from the mobile-ui package/layer and injects all activities.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity
}