/*
 * Copyright (C) 2018 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.voghdev.playbattlegrounds.datasource.api.model

import es.voghdev.playbattlegrounds.features.players.api.model.PlayerByIdApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayerService {
    @GET("shards/{region}/players")
    fun getPlayerByName(
            @Header("Authorization") apiKey: String,
            @Header("accept") mediaType: String,
            @Path("region") region: String,
            @Query("filter[playerNames]") playerName: String): Call<PlayerByIdApiResponse>

    @GET("shards/{region}/players")
    fun getPlayerById(
            @Header("Authorization") apiKey: String,
            @Header("accept") mediaType: String,
            @Path("region") region: String,
            @Query("filter[playerIds]") playerName: String): Call<PlayerByIdApiResponse>
}