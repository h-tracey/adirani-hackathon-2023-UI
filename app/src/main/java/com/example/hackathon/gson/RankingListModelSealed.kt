package com.example.hackathon.gson

sealed class RankingListModelSealed {
    data class RankingListGson(val conversion_rate_per_user: BaseModel.StatusBean, val response: RankingListResponseBean) : RankingListModelSealed()
    data class RankingListResponseBean(val response: ArrayList<RankingBean>)
    data class RankingBean(val item:String)
    data class conversionRatePerUser(val double: Double)

}