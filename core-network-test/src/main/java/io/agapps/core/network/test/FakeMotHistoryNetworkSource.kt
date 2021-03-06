package io.agapps.core.network.test

import io.agapps.common.result.Result
import io.agapps.core.network.MotHistoryNetworkSource
import io.agapps.core.network.model.MotHistoryDto
import io.agapps.core.network.test.model.MotHistoryDtoConverters

class FakeMotHistoryNetworkSource : MotHistoryNetworkSource {

    var shouldSucceed = true

    override suspend fun getMotHistory(registrationNumber: String): Result<MotHistoryDto> {
        if (!shouldSucceed) return Result.Error(Exception("FakeMotHistoryNetworkSource shouldSucceed == false"))
        val dto = MotHistoryDtoConverters.motHistoryDtoFromJson(FakeMotHistoryDto.fakeVehicleJson(registrationNumber))
        return Result.Success(data = dto!!)
    }
}

object FakeMotHistoryDto {

    fun fakeVehicleJson(registrationNumber: String) = """
        {
            "registration": "$registrationNumber",
            "make": "FORD",
            "model": "FOCUS",
            "firstUsedDate": "2010.11.13",
            "fuelType": "Petrol",
            "primaryColour": "Yellow",
            "vehicleId": "4Tq319nVKLz+25IRaUo79w==",
            "registrationDate": "2010.11.13",
            "manufactureDate": "2010.11.13",
            "engineSize": "1800",
            "motTests":[
                {
                    "completedDate": "2013.11.03 09:33:08",
                    "testResult": "PASSED",
                    "expiryDate": "2014.11.02",
                    "odometerValue": "47125",
                    "odometerUnit": "mi",
                    "odometerResultType": "READ",
                    "motTestNumber": "914655760009",
                    "rfrAndComments": []
                },
                {
                    "completedDate": "2013.11.01 11:28:34",
                    "testResult": "FAILED",
                    "odometerValue": "47118",
                    "odometerUnit": "mi",
                    "odometerResultType": "READ",
                    "motTestNumber": "841470560098",
                    "rfrAndComments":[
                        {
                            "text": "Front brake disc excessively pitted (3.5.1h)",
                            "type": "FAIL",
                            "dangerous": true
                        },
                        {
                            "text": "Nearside Rear wheel bearing has slight play (2.6.2)",
                            "type": "ADVISORY",
                            "dangerous": false
                        }
                    ]
                },
                {
                    "completedDate": "2018.05.20 11:28:34",
                    "testResult": "FAILED",
                    "odometerValue": "57318",
                    "odometerUnit": "mi",
                    "odometerResultType": "READ",
                    "motTestNumber": "741489560458",
                    "rfrAndComments":[
                        {
                            "text": "Nearside Parking brake efficiency below requirements (1.4.2 (a) (i))",
                            "type": "MAJOR",
                            "dangerous": false
                        },
                        {
                            "text": "Front brake disc excessively pitted (3.5.1h)",
                            "type": "DANGEROUS",
                            "dangerous": false
                        },
                        {
                            "text": "tyres wearing unevenly",
                            "type": "USER ENTERED",
                            "dangerous": true
                        }
                    ]
                }
            ]
        }
        """
}
