package com.madhu.projectkapp1.data.datasource

import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Village
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import retrofit2.Response

interface VillageDataSource {

    suspend fun addVillage(token: String, villageDto: Village): Response<GeneralResponse<Village>>

    suspend fun getVillageWiseData(token: String): Response<GeneralResponse<List<VillageWiseResponse>>>

    suspend fun getVillageWiseDataByVillageId(
        token: String,
        villageId: Int
    ): Response<GeneralResponse<VillageWiseResponse>>

    suspend fun searchVillagesByName(
        token: String,
        villageName: String
    ): Response<GeneralResponse<List<VillageWiseResponse>>>

    suspend fun deleteVillage(
        token: String,
        villageId: Int
    ): Response<GeneralResponse<Village>>

    suspend fun getVillagesNames(
        token: String,
    ): Response<GeneralResponse<List<NameAndId>>>

}