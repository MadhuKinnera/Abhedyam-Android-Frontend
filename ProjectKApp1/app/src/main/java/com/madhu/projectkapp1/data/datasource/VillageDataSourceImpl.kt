package com.madhu.projectkapp1.data.datasource

import android.util.Log
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.entity.GeneralResponse
import com.madhu.projectkapp1.data.entity.NameAndId
import com.madhu.projectkapp1.data.entity.Village
import com.madhu.projectkapp1.data.entity.VillageWiseResponse
import retrofit2.Response
import javax.inject.Inject

class VillageDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val login: Login
) : VillageDataSource {

    private val tag = "VillageDataSourceImpl"

    override suspend fun addVillage(
        token: String,
        villageDto: Village
    ): Response<GeneralResponse<Village>> {
        Log.d(tag, "Inside Add Village ")
        return apiService.addVillage(token, villageDto)
    }


    override suspend fun getVillageWiseData(token: String): Response<GeneralResponse<List<VillageWiseResponse>>> {
        Log.d(tag, "Inside get VillageWise Data ")
        return apiService.getVillageWiseData(token)
    }

    override suspend fun getVillageWiseDataByVillageId(
        token: String,
        villageId: Int
    ): Response<GeneralResponse<VillageWiseResponse>> {
        Log.d(tag, "Inside get VillageWise Data By Village Id $villageId")
        return apiService.getVillageWiseDataByVillageId(token, villageId)
    }

    override suspend fun searchVillagesByName(
        token: String,
        villageName: String
    ): Response<GeneralResponse<List<VillageWiseResponse>>> {
        Log.d(tag, "Inside Search Villages By Name")
        return apiService.searchVillagesByName(token, villageName)
    }

    override suspend fun deleteVillage(
        token: String,
        villageId: Int
    ): Response<GeneralResponse<Village>> {
        Log.d(tag, "Inside Delete Villages By Id")
        return apiService.deleteVillage(token, villageId)
    }

    override suspend fun getVillagesNames(token: String): Response<GeneralResponse<List<NameAndId>>> {
        Log.d(tag, "Inside Getting Villages Name")
        Log.d(tag,"Token is $token")
        return apiService.getVillagesNames(token)
    }
}