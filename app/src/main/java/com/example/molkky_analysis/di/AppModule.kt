package com.example.molkky_analysis.di

import android.app.Application
import android.content.Context
import androidx.room.Room // RoomのimportはAppDatabase内で使われるのでここには不要な場合も
import com.example.molkky_analysis.data.local.AppDatabase
import com.example.molkky_analysis.data.local.ThrowDao
import com.example.molkky_analysis.data.local.UserDao
import com.example.molkky_analysis.data.repository.IThrowRepository
import com.example.molkky_analysis.data.repository.IUserRepository
import com.example.molkky_analysis.data.repository.ThrowRepository
import com.example.molkky_analysis.data.repository.UserRepository
import com.example.molkky_analysis.ui.data_display.DataViewModel
import com.example.molkky_analysis.ui.practice.PracticeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.molkky_analysis.ui.analysis.AnalysisViewModel

val appModule = module {
    // Database
    single {
        AppDatabase.getDatabase(androidContext())
    }

    // DAOs
    single<UserDao> { get<AppDatabase>().userDao() }
    single<ThrowDao> { get<AppDatabase>().throwDao() }

    // Repositories
    single<IUserRepository> { UserRepository(get()) }
    single<IThrowRepository> { ThrowRepository(get()) }

    // ViewModels
    // viewModel { (userId: Int) -> PracticeViewModel(get(), get(), userId) } // ★ 修正前: Int型を期待していた箇所
    viewModel { PracticeViewModel(get(), get(), androidContext()) } // ★ 修正後: androidContext() を渡す
    viewModel { DataViewModel(get(), get()) }
    viewModel { AnalysisViewModel(get(), get()) }
}