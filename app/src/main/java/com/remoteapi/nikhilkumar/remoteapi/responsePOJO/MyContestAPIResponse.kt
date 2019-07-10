package com.remoteapi.nikhilkumar.remoteapi.responsePOJO

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/*data class MyContestAPIResponse(@SerializedName("meta") var meta: Meta? = null,
                            @SerializedName("data") var channelsHomeData: Data? = null) : BaseDataModel*/


data class MyContestAPIElement(@SerializedName("matchStatus") var matchStatus: String? = null,
                               @SerializedName("startTime") var startTimeMilis: Long? = null ,
                               @SerializedName("team1") var team1Data: Team? = null ,
                               @SerializedName("team2") var team2Data: Team? = null ,
                               @SerializedName("contestList") var contestList: List<Contests>? = null)

data class Team(@SerializedName("name") var name: String? = null,
                @SerializedName("alias") var alias: String? = null,
                @SerializedName("image") var imgUrl: String? = null,
                @SerializedName("overs") var overs: Float? = null,
                @SerializedName("score") var score: String? = null,
                @SerializedName("state") var state: String? = null,
                @SerializedName("winner") var isWinner: Boolean? = false)

data class Contests(@SerializedName("code") var code: String? = null,
                    @SerializedName("type") var type: String? = null,
                    @SerializedName("playing") var playingNumber: Int? = null,
                    @SerializedName("maxTeams") var maxTeams: Int? = null,
                    @SerializedName("maxTeamsPerUser") var maxTeamPerUser: Int? = null,
                    @SerializedName("creatorName") var creatorName: String? = null,
                    @SerializedName("coverImage") var coverImgUrl: String? = null,
                    @SerializedName("winnerPrizes") var winnerPrizeList: List<WinnerPrize>? = null,
                    @SerializedName("userTeams") var userTeamList: List<UserTeam>? = null)

data class WinnerPrize(@SerializedName("totalWinner") var totalWinner: Int? = null,
                       @SerializedName("totalPrize") var totalPrize : Int? = null,
                       @SerializedName("currency") var currency: String? = null)

data class UserTeam(@SerializedName("name") var name: String? = null,
                    @SerializedName("rank") var rank: Int? = null,
                    @SerializedName("points") var points: Float? = null)

data class PlayerData(@SerializedName("id") var id: Int? = null,
                      @SerializedName("name") var name: String? = null ,
                      @SerializedName("icon") var icon: String? = null ,
                      var score : Int ? = null
                      )


data class MatchData(@SerializedName("match") var matchNum: Int? = null,
                     @SerializedName("player1") var player1Data: PlayerDataForMatch? = null ,
                     @SerializedName("player2") var player2Data: PlayerDataForMatch? = null ) : Serializable

data class PlayerDataForMatch(@SerializedName("id") var id: Int? = null ,
                              @SerializedName("score") var score: Int? = null ,
                              var name : String ? = null
                              ) :Serializable

/*{
    "match": 1,
    "player1": {
    "id": 1,
    "score": 93
},
    "player2": {
    "id": 2,
    "score": 83
}
}*/
