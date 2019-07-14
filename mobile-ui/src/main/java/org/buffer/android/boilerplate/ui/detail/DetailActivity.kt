package org.buffer.android.boilerplate.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.progress
import kotlinx.android.synthetic.main.activity_detail.view_empty
import kotlinx.android.synthetic.main.activity_detail.view_error
import org.buffer.android.boilerplate.presentation.ViewModelFactory
import org.buffer.android.boilerplate.presentation.data.ResourceState
import org.buffer.android.boilerplate.presentation.detail.BufferooDetailViewModel
import org.buffer.android.boilerplate.presentation.model.BufferooView
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.mapper.BufferooMapper
import org.buffer.android.boilerplate.ui.widget.empty.EmptyListener
import org.buffer.android.boilerplate.ui.widget.error.ErrorListener
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
    }

    @Inject lateinit var mapper: BufferooMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var bufferooDetailViewModel: BufferooDetailViewModel
    private var id: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        AndroidInjection.inject(this)

        bufferooDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BufferooDetailViewModel::class.java)
        id = intent?.getLongExtra(EXTRA_ID, 0L) ?: 0L
        bufferooDetailViewModel.fetchBufferoo(id)

        setupViewListeners()
    }

    override fun onStart() {
        super.onStart()
        bufferooDetailViewModel.getBufferoo().observe(this, Observer {
            if (it != null) this.handleDataState(it.status, it.data, it.message)
        })
    }

    private fun handleDataState(resourceState: ResourceState, data: BufferooView?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        content_linear_layout.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: BufferooView?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null) {
            updateView(data)
            content_linear_layout.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateView(data: BufferooView) {
        Glide.with(this)
                .load(data.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(image_avatar)
        text_name.text = data.name
        text_title.text = data.title
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        content_linear_layout.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            id?.let { bufferooDetailViewModel.fetchBufferoo(it) }
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            id?.let { bufferooDetailViewModel.fetchBufferoo(it) }
        }
    }
}