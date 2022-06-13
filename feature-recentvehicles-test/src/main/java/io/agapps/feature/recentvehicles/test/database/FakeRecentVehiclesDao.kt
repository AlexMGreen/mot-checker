package io.agapps.feature.recentvehicles.test.database

import io.agapps.feature.recentvehicles.database.RecentVehicleDao
import io.agapps.feature.recentvehicles.database.RecentVehicleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeRecentVehiclesDao : RecentVehicleDao {

    private val fakeRecentEntities = MutableStateFlow(emptySet<RecentVehicleEntity>())

    override fun getRecentVehicles(limit: Int) = fakeRecentEntities.map { entities ->
        entities.toList().take(limit)
    }

    override fun getAllRecentVehicles() = fakeRecentEntities.map { entities ->
        entities.toList()
    }

    override suspend fun insertRecentVehicle(recentVehicle: RecentVehicleEntity) {
        fakeRecentEntities.update {
            it.plus(recentVehicle)
        }
    }
}
