package com.kdbrian.tow.data.remote.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.domain.repo.VehicleRepo
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class VehicleRepoImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : VehicleRepo() {

    override suspend fun loadModels(): Result<List<Vehicle>> {
        return Result.success(emptyList())
    }

    override suspend fun saveVehicle(vehicle: Vehicle): Result<String> {
        Timber.d("Saving vehicle: $vehicle")
        return try {
            val documentReference = firestore
//                .document()//main
                .collection("$collection/${firebaseAuth.currentUser?.uid ?: ""}/mine")//user's
                .document()
            documentReference.set(
                vehicle.copy(
                    lastUpdated = System.currentTimeMillis(),
                    vid = documentReference.id
                )
            ).addOnSuccessListener {
                Timber.d("Vehicle saved successfully. #${documentReference.id}")
            }.addOnFailureListener {
                Timber.e("Failed to save vehicle. ${it.message}")
            }
            Result.success("Vehicle saved successfully. #${documentReference.id}")
        } catch (e: Exception) {
            Timber.e("Failed to save vehicle. ${e.message}")
            Result.failure(e)
        }


    }

    override suspend fun loadSavedVehicles(): Result<List<Vehicle>> {
        return try {
            Timber.d("Loading saved vehicles...")
            val querySnapshot = firestore
                .collection("$collection/${firebaseAuth.currentUser?.uid ?: ""}/mine")//user's
                .get()
                .await()

            Timber.d("Loaded/querySnapshot : ${querySnapshot.size()}.")

            val vehicles = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Vehicle::class.java)
            }

            Timber.d("Loaded/vehicles ${vehicles.size} vehicles.")
            Result.success(vehicles)
        } catch (e: Exception) {
            Timber.e("Failed to load saved vehicles. ${e.message}")
            Result.failure(e)
        }

    }
}