package io.agapps.feature.home

import TestDispatcherRule
import app.cash.turbine.test
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.toDomain
import io.agapps.core.database.test.dao.FakeVehicleDao
import io.agapps.core.database.test.dao.FakeVehicleEntity
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepository
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepositoryImpl
import io.agapps.feature.favouritevehicles.test.database.FakeFavouriteVehicleDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var fakeVehicleDao: VehicleDao
    private lateinit var favouriteVehicleRepository: FavouriteVehicleRepository
    private lateinit var subject: HomeViewModel

    @Before
    fun setup() {
        fakeVehicleDao = FakeVehicleDao()
        favouriteVehicleRepository = FavouriteVehicleRepositoryImpl(
            vehicleDao = fakeVehicleDao,
            favouriteVehicleDao = FakeFavouriteVehicleDao()
        )
        subject = HomeViewModel(favouriteVehicleRepository)
    }

    @Test
    fun viewState_initially_empty() = runTest {
        val expected = HomeViewState.Home(emptyList())
        val actual = subject.viewState.first()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun viewState_favouritevehicle_empty_list_when_favourites_db_empty() = runTest {
        subject.viewState.test {
            val first = awaitItem()
            Assert.assertEquals(HomeViewState.Home(emptyList()), first)

            expectNoEvents()

            cancel()
        }
    }

    @Test
    fun viewState_favourite_vehicle_populated_list_when_favourites_db_not_empty() = runTest {
        val registration = "ABC123"
        val entity = FakeVehicleEntity.fakeVehicleEntityForRegistration(registration)
        val vehicle = entity.toDomain()

        subject.viewState.test {
            val first = awaitItem()
            Assert.assertEquals(HomeViewState.Home(emptyList()), first)

            fakeVehicleDao.insertVehicle(entity)
            favouriteVehicleRepository.addFavouriteVehicle(vehicle)

            val second = awaitItem()
            Assert.assertEquals(HomeViewState.Home(listOf(vehicle)), second)

            cancel()
        }
    }
}
