package io.agapps.vehicledetails

import TestDispatcherRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.core.data.repository.VehicleRepositoryImpl
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.toDomain
import io.agapps.core.database.test.dao.FakeVehicleDao
import io.agapps.core.database.test.dao.FakeVehicleEntity
import io.agapps.core.network.MotHistoryNetworkSource
import io.agapps.core.network.test.FakeMotHistoryNetworkSource
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepository
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepositoryImpl
import io.agapps.feature.favouritevehicles.test.database.FakeFavouriteVehicleDao
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import io.agapps.feature.recentvehicles.data.RecentVehicleRepositoryImpl
import io.agapps.feature.recentvehicles.test.database.FakeRecentVehiclesDao
import io.agapps.vehicledetails.navigation.VehicleDetailsDestination
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val TestRegistration = "ABC123"

class VehicleDetailsViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var fakeVehicleDao: VehicleDao
    private lateinit var fakeDataSource: MotHistoryNetworkSource
    private lateinit var vehicleRepository: VehicleRepository
    private lateinit var recentVehicleRepository: RecentVehicleRepository
    private lateinit var favouriteVehicleRepository: FavouriteVehicleRepository
    private lateinit var subject: VehicleDetailsViewModel

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
        favouriteVehicleRepository = FavouriteVehicleRepositoryImpl(
            vehicleDao = fakeVehicleDao,
            favouriteVehicleDao = FakeFavouriteVehicleDao()
        )

        subject = VehicleDetailsViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    VehicleDetailsDestination.registrationArg to TestRegistration
                )
            ),
            vehicleRepository = vehicleRepository,
            favouriteVehicleRepository = favouriteVehicleRepository,
            recentVehicleRepository = recentVehicleRepository
        )
    }

    @Test
    fun viewState_initially_loading() = runTest {
        val expected = VehicleDetailsViewState(
            vehicleState = VehicleDetailsVehicleViewState.Loading(TestRegistration),
            favouriteState = VehicleDetailsFavouriteViewState.Loading
        )
        val actual = subject.viewState.first()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun favouriteViewState_is_true_on_db_success() = runTest {
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(TestRegistration)
        val vehicle = entity.toDomain()

        subject.viewState.test {
            awaitItem()
            fakeVehicleDao.insertVehicle(entity)
            favouriteVehicleRepository.addFavouriteVehicle(vehicle)
            awaitItem()

            val expected = VehicleDetailsFavouriteViewState.Favourite(true)
            val actual = awaitItem().favouriteState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun favouriteViewState_is_false_on_db_null() = runTest {
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(TestRegistration)

        subject.viewState.test {
            awaitItem()
            fakeVehicleDao.insertVehicle(entity)
            awaitItem()

            val expected = VehicleDetailsFavouriteViewState.Favourite(false)
            val actual = awaitItem().favouriteState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun vehicleViewState_is_success_on_db_success_and_network_failure() = runTest {
        (fakeDataSource as FakeMotHistoryNetworkSource).shouldSucceed = false
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(TestRegistration)
        val vehicle = entity.toDomain()

        subject.viewState.test {
            awaitItem()
            fakeVehicleDao.insertVehicle(entity)
            awaitItem()

            val expected = VehicleDetailsVehicleViewState.Success(TestRegistration, vehicle)
            val actual = awaitItem().vehicleState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun vehicleViewState_is_success_on_db_success_and_network_success() = runTest {
        (fakeDataSource as FakeMotHistoryNetworkSource).shouldSucceed = true
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(TestRegistration)
        val vehicle = entity.toDomain()

        subject.viewState.test {
            awaitItem()
            fakeVehicleDao.insertVehicle(entity)
            awaitItem()

            val expected = VehicleDetailsVehicleViewState.Success(TestRegistration, vehicle)
            val actual = awaitItem().vehicleState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun vehicleViewState_is_error_on_db_null_and_network_failure() = runTest {
        (fakeDataSource as FakeMotHistoryNetworkSource).shouldSucceed = false
        subject.viewState.test {
            awaitItem()
            awaitItem()

            val expected = VehicleDetailsVehicleViewState.Error(TestRegistration)
            val actual = awaitItem().vehicleState

            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun viewState_registration_correct_on_loading() = runTest {
        val expected = TestRegistration
        val viewState = subject.viewState.first()
        val actual = viewState.vehicleState.registration

        Assert.assertTrue(viewState.vehicleState is VehicleDetailsVehicleViewState.Loading)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun viewState_registration_correct_on_vehicle_success() = runTest {
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(TestRegistration)

        subject.viewState.test {
            awaitItem()
            fakeVehicleDao.insertVehicle(entity)
            awaitItem()

            val expected = TestRegistration
            val viewState = awaitItem()
            val actual = viewState.vehicleState.registration

            Assert.assertTrue(viewState.vehicleState is VehicleDetailsVehicleViewState.Success)
            Assert.assertEquals(expected, actual)

            cancel()
        }
    }

    @Test
    fun viewState_registration_correct_on_vehicle_error() = runTest {
        (fakeDataSource as FakeMotHistoryNetworkSource).shouldSucceed = false
        subject.viewState.test {
            awaitItem()
            awaitItem()

            val expected = TestRegistration
            val viewState = awaitItem()
            val actual = viewState.vehicleState.registration

            Assert.assertTrue(viewState.vehicleState is VehicleDetailsVehicleViewState.Error)
            Assert.assertEquals(expected, actual)

            cancel()
        }
    }
}
