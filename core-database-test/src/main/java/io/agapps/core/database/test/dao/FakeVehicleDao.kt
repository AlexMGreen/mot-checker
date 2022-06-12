package io.agapps.core.database.test.dao

import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.MotTestEntity
import io.agapps.core.database.model.RfrAndCommentEntity
import io.agapps.core.database.model.VehicleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeVehicleDao : VehicleDao {

    private val fakeVehicleEntities = MutableStateFlow(emptySet<VehicleEntity>())

    override fun getAllVehicles(): Flow<List<VehicleEntity>> = fakeVehicleEntities.map { it.toList() }

    override fun getVehicleByRegistrationNumber(registrationNumber: String) = fakeVehicleEntities.map { entities ->
        entities.firstOrNull { entity ->
            entity.registrationNumber == registrationNumber
        }
    }

    override fun getAllVehiclesByRegistrationNumbers(registrationNumbers: List<String>) = fakeVehicleEntities.map { vehicleEntities ->
        vehicleEntities.filter { registrationNumbers.contains(it.registrationNumber) }.toList()
    }

    override suspend fun insertVehicle(vehicle: VehicleEntity) {
        fakeVehicleEntities.update { it.plus(vehicle) }
    }

    override suspend fun delete(vehicle: VehicleEntity) {
        fakeVehicleEntities.update { it.minus(vehicle) }
    }
}

object FakeVehicleEntity {

    private val fakeMotTestEntity = MotTestEntity(
        completedDate = "2021.10.05 08:34:38",
        expiryDate = "2022.10.13",
        motTestNumber = "324288228889",
        odometerResultType = "READ",
        odometerUnit = "mi",
        odometerValue = "108606",
        rfrAndComments = listOf(
            RfrAndCommentEntity(
                dangerous = false,
                text = "Offside Front Drive shaft joint constant velocity boot severely deteriorated (6.1.7 (g) (i))",
                type = "MINOR"
            ),
            RfrAndCommentEntity(
                dangerous = false,
                text = "Nearside Front Tyre worn close to legal limit/worn on edge  badly worn edges (5.2.3 (e))",
                type = "ADVISORY"
            )
        ),
        testResult = "PASSED"
    )

    fun fakeVehicleEntityForRegistration(registrationNumber: String) = VehicleEntity(
        registrationNumber = registrationNumber,
        make = "Ford",
        model = "Focus",
        primaryColour = "Blue",
        fuelType = "Petrol",
        engineSizeCc = "1199",
        manufactureDate = "2004.09.29",
        motTests = listOf(fakeMotTestEntity)
    )
}
