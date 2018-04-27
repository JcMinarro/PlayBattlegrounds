package es.voghdev.playbattlegrounds.common.di

import es.voghdev.playbattlegrounds.features.players.mock.GetPlayerByIdMockDataSource
import es.voghdev.playbattlegrounds.features.players.mock.GetPlayerByNameMockDataSource
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerById
import es.voghdev.playbattlegrounds.features.players.usecase.GetPlayerByName
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class MockModule {
    companion object {
        val module = Kodein.Module {
            bind<GetPlayerByName>() with singleton { GetPlayerByNameMockDataSource() }
            bind<GetPlayerById>() with singleton { GetPlayerByIdMockDataSource() }
        }
    }
}
