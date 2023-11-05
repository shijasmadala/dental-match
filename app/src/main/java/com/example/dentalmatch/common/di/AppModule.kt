package com.example.dentalmatch.common.di

import android.app.Application
import androidx.room.Room
import com.example.dentalmatch.add_patient.data.source.PatientDao
import com.example.dentalmatch.common.database.DentalDataBase
import com.example.dentalmatch.upload_image.data.source.ColorCodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDentalDataBase(application: Application) = Room.databaseBuilder(
        application, DentalDataBase::class.java,"dental_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providePatientDao(dentalDataBase: DentalDataBase) : PatientDao{
        return dentalDataBase.patientDao
    }

    @Provides
    @Singleton
    fun provideColorDao(dentalDataBase: DentalDataBase) : ColorCodeDao{
        return dentalDataBase.colorCodeDao
    }
}