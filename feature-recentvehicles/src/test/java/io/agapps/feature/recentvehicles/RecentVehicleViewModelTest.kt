package io.agapps.feature.recentvehicles

import TestDispatcherRule
import app.cash.turbine.test
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.toDomain
import io.agapps.core.database.test.dao.FakeVehicleDao
import io.agapps.core.database.test.dao.FakeVehicleEntity
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import io.agapps.feature.recentvehicles.data.RecentVehicleRepositoryImpl
import io.agapps.feature.recentvehicles.test.database.FakeRecentVehiclesDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecentVehicleViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var fakeVehicleDao: VehicleDao
    private lateinit var recentVehicleRepository: RecentVehicleRepository
    private lateinit var subject: RecentVehicleViewModel

    @Before
    fun setup() {
        fakeVehicleDao = FakeVehicleDao()
        recentVehicleRepository = RecentVehicleRepositoryImpl(
            vehicleDao = fakeVehicleDao,
            recentVehicleDao = FakeRecentVehiclesDao()
        )
        subject = RecentVehicleViewModel(recentVehicleRepository)
    }

    @Test
    fun viewState_initially_empty() = runTest {
        val expected = RecentVehicleViewState.Empty
        val actual = subject.viewState.first()

        Assert.assertEquals(expected, actual)
    }


    @Test
    fun viewState_recentvehicle_with_populated_list_when_recents_db_not_empty() = runTest {
        val registration = "ABC123"
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(registration)
        val vehicle = entity.toDomain()

        subject.viewState.test {
            val first = awaitItem()
            Assert.assertEquals(RecentVehicleViewState.Empty, first)

            fakeVehicleDao.insertVehicle(entity)
            recentVehicleRepository.addRecentVehicle(vehicle)

            val second = awaitItem()
            Assert.assertEquals(RecentVehicleViewState.RecentVehicle(listOf(vehicle)), second)

            cancel()
        }
    }
}
