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
package es.voghdev.playbattlegrounds.features.players.mock

import arrow.core.Either
import es.voghdev.playbattlegrounds.common.AbsError
import es.voghdev.playbattlegrounds.features.players.model.Player
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerById

class GetPlayerByIdMockDataSource : GetPlayerById {
    override fun getPlayerById(name: String): Either<AbsError, Player> =
            Either.right(
                    Player(
                            "account.afbb96044b3b4e888e3cef65fcdaf898",
                            "eqs_insanity",
                            "",
                            "bluehole-pubg",
                            "https://api.playbattlegrounds.com/shards/pc-eu/players/account.afbb96044b3b4e888e3cef65fcdaf898"
                    )
            )
}
