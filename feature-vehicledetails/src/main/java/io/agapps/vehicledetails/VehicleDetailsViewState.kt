package io.agapps.vehicledetails

import io.agapps.core.model.Vehicle

sealed class VehicleDetailsViewState {
    abstract val registration: String

    data class VehicleDetailsResult(
        override val registration: String,
        val vehicle: Vehicle,
    ) : VehicleDetailsViewState()

    data class VehicleDetailsLoading(
        override val registration: String,
    ) : VehicleDetailsViewState()

    data class VehicleDetailsError(
        override val registration: String,
        val error: String
    ) : VehicleDetailsViewState()
}
