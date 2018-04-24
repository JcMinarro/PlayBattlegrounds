/*
 * Copyright (C) 2017 Olmo Gallegos Hernández.
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
package es.voghdev.playbattlegrounds.common

import android.app.Application
import android.content.Context
import es.voghdev.playbattlegrounds.common.reslocator.AndroidResLocator
import es.voghdev.playbattlegrounds.common.reslocator.ResLocator
import es.voghdev.playbattlegrounds.features.players.api.request.GetPlayerByNameApiDataSource
import es.voghdev.playbattlegrounds.features.players.mock.GetPlayerByNameMockDataSource
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerByName
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {
    override val kodein = Kodein {
        bind<GetPlayerByName>() with singleton { GetPlayerByNameApiDataSource() }
        bind<ResLocator>() with singleton { AndroidResLocator(applicationContext) }
    }
}
