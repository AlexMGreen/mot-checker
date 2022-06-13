package io.agapps.feature.search

import TestDispatcherRule
import app.cash.turbine.test
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.core.data.repository.VehicleRepositoryImpl
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.toDomain
import io.agapps.core.database.test.dao.FakeVehicleDao
import io.agapps.core.database.test.dao.FakeVehicleEntity
import io.agapps.core.network.MotHistoryNetworkSource
import io.agapps.core.network.test.FakeMotHistoryNetworkSource
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import io.agapps.feature.recentvehicles.data.RecentVehicleRepositoryImpl
import io.agapps.feature.recentvehicles.test.database.FakeRecentVehiclesDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val TestRegistration = "ABC123"

class SearchViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var fakeVehicleDao: VehicleDao
    private lateinit var fakeDataSource: MotHistoryNetworkSource
    private lateinit var vehicleRepository: VehicleRepository
    private lateinit var recentVehicleRepository: RecentVehicleRepository
    private lateinit var subject: SearchViewModel

    @Before
    fun setup() {
        fakeVehicleDao = FakeVehicleDao()
        fakeDataSource = FakeMotHistoryNetworkSource()

        vehicleRepository = VehicleRepositoryImpl(
            motHistoryNetworkSource = fakeDataSource,
            vehicleDao = fakeVehicleDao
        )
        recentVehicleRepository = RecentVehicleRepositoryImpl(
            vehicleDao = fakeVehicleDao,
            recentVehicleDao = FakeRecentVehiclesDao()
        )

        subject = SearchViewModel(
            vehicleRepository = vehicleRepository,
            recentVehicleRepository = recentVehicleRepository
        )
    }

    @Test
    fun viewState_initially_loading() = runTest {
        val expected = SearchViewState(
            vehicleState = SearchVehicleViewState.Loading,
            recentVehiclesState = SearchRecentsViewState.Loading
        )
        val actual = subject.viewState.first()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun viewState_empty_on_blank_registration() = runTest {
        subject.viewState.test {
            awaitItem()
            subject.onRegistrationNumberEntered("")

            val expected = SearchVehicleViewState.Empty
            val actual = awaitItem().vehicleState
            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun vehicleState_is_true_on_network_success() = runTest {
        (fakeDataSource as FakeMotHistoryNetworkSource).shouldSucceed = true

        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(TestRegistration)
        val vehicle = entity.toDomain()

        subject.viewState.test {
            awaitItem()
            subject.onRegistrationNumberEntered(TestRegistration)
            awaitItem()
            awaitItem()
            awaitItem()
            val expected = SearchVehicleViewState.Success(vehicle)
            val actual = awaitItem().vehicleState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun vehicleState_is_error_on_network_error() = runTest {
        (fakeDataSource as FakeMotHistoryNetworkSource).shouldSucceed = false

        subject.viewState.test {
            awaitItem()
            subject.onRegistrationNumberEntered(TestRegistration)
            awaitItem()
            awaitItem()
            awaitItem()
            val expected = SearchVehicleViewState.Error
            val actual = awaitItem().vehicleState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun recentVehicleState_is_populated_on_non_empty_recents_db() = runTest {
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(TestRegistration)
        val vehicle = entity.toDomain()

        subject.viewState.test {
            awaitItem()
            subject.onRegistrationNumberEntered(TestRegistration)
            recentVehicleRepository.addRecentVehicle(vehicle)
            awaitItem()
            awaitItem()
            awaitItem()
            val expected = SearchRecentsViewState.Success(listOf(vehicle))
            val actual = awaitItem().recentVehiclesState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }
}
