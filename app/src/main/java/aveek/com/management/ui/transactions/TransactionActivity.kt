package aveek.com.management.ui.transactions

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import aveek.com.management.R
import aveek.com.management.databinding.ActivityTransactionBinding
import aveek.com.management.ui.common.NetworkActivity
import aveek.com.management.db.AppDatabase
import aveek.com.management.db.entity.Transaction
import aveek.com.management.util.EnumTransactionType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransactionActivity : NetworkActivity(), LifecycleOwner {

    private lateinit var binding : ActivityTransactionBinding

//    private lateinit var database : AppDatabase

    @Inject
    lateinit var viewModel : TransactionVM

    @Inject
    lateinit var database: AppDatabase

    private lateinit var adapter : TransactionAdapter

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var compositeDisposable : CompositeDisposable

    private lateinit var recyclerView : RecyclerView

    // Recycler View
    private var pageNumber = 1
    private var contentSize = 0
    private var totalCount: Long = 0
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()

        initBinding()

//        initDatabase()

        initiateRecyclerView()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        loadTransactions()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }

//    private fun initDatabase() {
//        database = AppDatabase.getAppDataBase(this)!!
//    }

    /**
     * initialize recycler view
     * @param none
     * @return none
     */
    private fun initiateRecyclerView() {
        layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayoutManager.VERTICAL }
        adapter = TransactionAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.rcv_transaction).apply {
            this.layoutManager = this@TransactionActivity.layoutManager
            this.adapter = this@TransactionActivity.adapter
        }.also {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) { // Detects if it is scrolling downwards
                        val lastVisibleItemPosition = (it.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == adapter.itemCount - 1) {
//                            if (isNetworkAvailable) {
                                if (contentSize < totalCount && !isLoading) { // isLoading = false
                                    loadMoreRecyclerData(pageNumber = ++pageNumber)
                                }
//                            }
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    /**
     * load more data on scroll on recyclerview
     * @param pageNumber, pageSize, numberOfDays
     * call fetch api data on scroll with the parameters passed
     * @return nothing
     */
    fun loadMoreRecyclerData(pageNumber: Int) {

        val mockData = Transaction().apply {
            type = EnumTransactionType.LOADING.type
        }

        adapter.addDataAtPos(mockData)

        isLoading = true

        fetchDataFromLocalDBOnLoadMore(pageNumber)

    }

    private fun fetchDataFromLocalDBOnLoadMore(pageNumber: Int) {
        // TODO : Fetch data from database with offset
    }

    private fun loadTransactions(){
        val disposable = database.transactionDao().getAllTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun onSuccess(transactionList : List<Transaction>) {
//        for (transaction in transactionList){
//            Toast.makeText(this, transaction.uid, Toast.LENGTH_LONG).show()
//        }
        adapter.setData(transactionList)
    }
//    private fun onError(throwable : Throwable){
//
//    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        compositeDisposable.dispose()
    }

}
