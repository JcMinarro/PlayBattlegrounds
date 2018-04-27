package es.voghdev.playbattlegrounds.common.di

import es.voghdev.playbattlegrounds.features.players.api.request.GetPlayerByIdApiDataSource
import es.voghdev.playbattlegrounds.features.players.api.request.GetPlayerByNameApiDataSource
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerById
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerByName
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class ApiModule {
    companion object {
        val module = Kodein.Module {
            bind<GetPlayerByName>() with singleton { GetPlayerByNameApiDataSource() }
            bind<GetPlayerById>() with singleton { GetPlayerByIdApiDataSource() }
        }
    }
}
