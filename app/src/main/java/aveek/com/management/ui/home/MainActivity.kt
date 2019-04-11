package aveek.com.management.ui.home


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import aveek.com.management.R
import aveek.com.management.databinding.ActivityMainBinding
import aveek.com.management.ui.db.AppDatabase
import aveek.com.management.ui.db.entity.Transaction
import dagger.android.AndroidInjection
import java.util.*

import javax.inject.Inject

class MainActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var binding : ActivityMainBinding

    private lateinit var database : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        initDatabase()

        initBinding()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }
        with(binding){
            this.viewmodel?.let {
                it.balanceText.set( "Aveek testing")
                it.data.observe(this@MainActivity, Observer {
                    it?.let {
                       if (it){
                           /*
                            @ColumnInfo(name = "payment_type") var paymentType : String?, // Cash or Card or Bank transaction
                            @ColumnInfo(name = "category") var category : String?, // Category of the income, i.e : Salary
                            @ColumnInfo(name = "purpose") var purpose : String?, // Note on the transaction
                            @ColumnInfo(name = "amount") var amount : Double?, // Amount to be credited
                            @ColumnInfo(name = "date") var date : String? // Date to add the transaction
                            */


                            database.transactionDao().insert(Transaction(UUID.randomUUID().toString(),"credit","shopping","DIY",25.50,"2019-04-11"))
                       }
                    }
                })
            }
        }
    }

    private fun initDatabase() {
        database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "expense.db"
        ).build()
//        database = AppDatabase(this)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(this) // To enable Live Data object to update the XML on update
    }

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
    }
}
