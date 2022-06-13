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
        completedDate = "2013.11.03 09:33:08",
        expiryDate = "2014.11.02",
        motTestNumber = "914655760009",
        odometerResultType = "READ",
        odometerUnit = "mi",
        odometerValue = "47125",
        rfrAndComments = emptyList(),
        testResult = "PASSED"
    )

    private val fakeMotTestEntity2 = MotTestEntity(
        completedDate = "2013.11.01 11:28:34",
        expiryDate = null,
        motTestNumber = "841470560098",
        odometerResultType = "READ",
        odometerUnit = "mi",
        odometerValue = "47118",
        rfrAndComments = listOf(
            RfrAndCommentEntity(
                dangerous = true,
                text = "Front brake disc excessively pitted (3.5.1h)",
                type = "FAIL"
            ),
            RfrAndCommentEntity(
                dangerous = false,
                text = "Nearside Rear wheel bearing has slight play (2.6.2)",
                type = "ADVISORY"
            )
        ),
        testResult = "FAILED"
    )

    private val fakeMotTestEntity3 = MotTestEntity(
        completedDate = "2018.05.20 11:28:34",
        expiryDate = null,
        motTestNumber = "741489560458",
        odometerResultType = "READ",
        odometerUnit = "mi",
        odometerValue = "57318",
        rfrAndComments = listOf(
            RfrAndCommentEntity(
                dangerous = false,
                text = "Nearside Parking brake efficiency below requirements (1.4.2 (a) (i))",
                type = "MAJOR"
            ),
            RfrAndCommentEntity(
                dangerous = false,
                text = "Front brake disc excessively pitted (3.5.1h)",
                type = "DANGEROUS"
            ),
            RfrAndCommentEntity(
                dangerous = true,
                text = "tyres wearing unevenly",
                type = "USER ENTERED"
            )
        ),
        testResult = "FAILED"
    )

    fun fakeVehicleEntityForRegistration(registrationNumber: String) = VehicleEntity(
        registrationNumber = registrationNumber,
        make = "FORD",
        model = "FOCUS",
        primaryColour = "Yellow",
        fuelType = "Petrol",
        engineSizeCc = "1800",
        manufactureDate = "2010.11.13",
        motTests = listOf(fakeMotTestEntity, fakeMotTestEntity2, fakeMotTestEntity3)
    )
}
