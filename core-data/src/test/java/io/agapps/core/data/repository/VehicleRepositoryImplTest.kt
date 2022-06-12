package io.agapps.core.data.repository

import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.toDomain
import io.agapps.core.database.test.dao.FakeVehicleDao
import io.agapps.core.database.test.dao.FakeVehicleEntity
import io.agapps.core.network.MotHistoryNetworkSource
import io.agapps.core.network.test.FakeMotHistoryNetworkSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VehicleRepositoryImplTest {

    private lateinit var fakeVehicleDao: VehicleDao
    private lateinit var fakeMotHistoryNetworkSource: MotHistoryNetworkSource
    private lateinit var subject: VehicleRepositoryImpl

    @Before
    fun setup() {
        fakeVehicleDao = FakeVehicleDao()
        fakeMotHistoryNetworkSource = FakeMotHistoryNetworkSource()

        subject = VehicleRepositoryImpl(
            motHistoryNetworkSource = fakeMotHistoryNetworkSource,
            vehicleDao = fakeVehicleDao
        )
    }

    @Test
    fun vehicle_repository_retrieves_from_database_when_present() = runTest {
        val registration = "ABC123"

        fakeVehicleDao.insertVehicle(FakeVehicleEntity.fakeVehicleEntityForRegistration(registration))

        val expected = fakeVehicleDao.getVehicleByRegistrationNumber(registration).first()?.toDomain()
        val actual = subject.getVehicle(registration).first()

        Assert.assertNotNull(actual)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun vehicle_repository_retrieves_null_from_database_when_not_present() = runTest {
        val registration = "ABC123"
        val actual = subject.getVehicle(registration).first()

        Assert.assertNull(actual)
    }

    @Test
    fun vehicle_repository_adds_to_database_on_update() = runTest {
        val registration = "ABC123"

        subject.updateVehicle(registration)

        val expected = fakeVehicleDao.getVehicleByRegistrationNumber(registration).first()?.toDomain()
        val actual = subject.getVehicle(registration).first()

        Assert.assertNotNull(actual)
        Assert.assertEquals(expected, actual)
    }
}
