package com.example.dentalmatch.common.di

import com.example.dentalmatch.add_patient.data.repository.AddPatientRepositoryImpl
import com.example.dentalmatch.add_patient.domain.repository.AddPatientRepository
import com.example.dentalmatch.home.data.repository.HomeRepositoryImpl
import com.example.dentalmatch.home.domain.repository.HomeRepository
import com.example.dentalmatch.upload_image.data.repository.UploadImageRepositoryImpl
import com.example.dentalmatch.upload_image.domain.UploadImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAddPatientRepository(addPatientRepositoryImpl: AddPatientRepositoryImpl) : AddPatientRepository

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl) : HomeRepository

    @Binds
    abstract fun bindUploadImageRepository(uploadImageRepositoryImpl: UploadImageRepositoryImpl) : UploadImageRepository
}