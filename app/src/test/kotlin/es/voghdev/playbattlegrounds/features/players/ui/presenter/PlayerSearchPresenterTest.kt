package es.voghdev.playbattlegrounds.features.players.ui.presenter

import arrow.core.Either
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import es.voghdev.playbattlegrounds.common.reslocator.ResLocator
import es.voghdev.playbattlegrounds.features.matches.Match
import es.voghdev.playbattlegrounds.features.matches.MatchRepository
import es.voghdev.playbattlegrounds.features.onboarding.usecase.GetPlayerAccount
import es.voghdev.playbattlegrounds.features.players.PlayerRepository
import es.voghdev.playbattlegrounds.features.players.model.Player
import es.voghdev.playbattlegrounds.features.players.usecase.IsContentAvailableForPlayer
import es.voghdev.playbattlegrounds.features.season.usecase.GetCurrentSeason
import es.voghdev.playbattlegrounds.features.season.usecase.GetPlayerSeasonInfo
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PlayerSearchPresenterTest {

    @Mock
    lateinit var mockResLocator: ResLocator

    @Mock
    lateinit var mockGetPlayerAccount: GetPlayerAccount

    @Mock
    lateinit var mockGetCurrentSeason: GetCurrentSeason

    @Mock
    lateinit var mockGetPlayerSeasonInfo: GetPlayerSeasonInfo

    @Mock
    lateinit var mockNavigator: PlayerSearchPresenter.Navigator

    @Mock
    lateinit var mockPlayerRepository: PlayerRepository

    @Mock
    lateinit var mockMatchRepository: MatchRepository

    @Mock
    lateinit var mockIsContentAvailableForPlayer: IsContentAvailableForPlayer

    @Mock
    lateinit var mockView: PlayerSearchPresenter.MVPView

    lateinit var presenter: PlayerSearchPresenter

    val someMatches = (1..5).map {
        Match(id = "uuid00$it", gameMode = "solo-fpp", numberOfKillsForCurrentPlayer = it)
    } as MutableList

    val oneMoreMatch = Match(id = "uuid006", gameMode = "duo-fpp", numberOfKillsForCurrentPlayer = 15)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = createPresenterWithMocks(mockPlayerRepository, mockMatchRepository)
    }

    @Test
    fun `should request player by name on start`() {
        val data = object : PlayerSearchPresenter.InitialData {
            override fun getPlayerName(): String = "DiabloVT"
        }

        runBlocking {
            presenter.initialize()

            presenter.onInitialData(data)
        }

        verify(mockPlayerRepository).getPlayerByName("DiabloVT")
    }

    @Test
    fun `should load matches 1 to 5 when search button is clicked`() {
        val data = object : PlayerSearchPresenter.InitialData {
            override fun getPlayerName(): String = "DiabloVT"
        }

        whenever(mockPlayerRepository.getPlayerByName(anyString())).thenReturn(
                Either.right(Player(
                        name = "DiabloVT",
                        matches = someMatches

                ))
        )

        runBlocking {
            presenter.initialize()

            presenter.onInitialData(data)
        }

        verify(mockMatchRepository).getMatchById("uuid001")
        verify(mockMatchRepository).getMatchById("uuid002")
        verify(mockMatchRepository).getMatchById("uuid003")
        verify(mockMatchRepository).getMatchById("uuid004")
        verify(mockMatchRepository).getMatchById("uuid005")
    }

    @Test
    fun `should show a "load more" icon if there are more matches`() {
        val data = object : PlayerSearchPresenter.InitialData {
            override fun getPlayerName(): String = "DiabloVT"
        }

        whenever(mockPlayerRepository.getPlayerByName(anyString())).thenReturn(
                Either.right(Player(
                        name = "DiabloVT",
                        matches = someMatches.apply { add(oneMoreMatch) }
                ))
        )

        runBlocking {
            presenter.initialize()

            presenter.onInitialData(data)
        }

        verify(mockView).addLoadMoreItem()
    }

    @Test
    fun `should not show a "load more" icon if there are no more matches`() {
        val data = object : PlayerSearchPresenter.InitialData {
            override fun getPlayerName(): String = "DiabloVT"
        }

        whenever(mockPlayerRepository.getPlayerByName(anyString())).thenReturn(
                Either.right(Player(
                        name = "DiabloVT",
                        matches = someMatches
                ))
        )

        runBlocking {
            presenter.initialize()

            presenter.onInitialData(data)
        }

        verify(mockView, never()).addLoadMoreItem()
    }

    @Test
    fun `should show "load more" only once if there are ten matches`() {
        val data = object : PlayerSearchPresenter.InitialData {
            override fun getPlayerName(): String = "DiabloVT"
        }

        whenever(mockPlayerRepository.getPlayerByName(anyString())).thenReturn(
                Either.right(Player(
                        name = "DiabloVT",
                        matches = (1..10).map {
                            Match(id = "id00$it", gameMode = "solo", numberOfKillsForCurrentPlayer = it)
                        }
                ))
        )

        runBlocking {
            presenter.initialize()

            presenter.onInitialData(data)

            presenter.onLoadMoreMatchesClicked()
        }

        verify(mockView, times(1)).addLoadMoreItem()
    }

    @Test
    fun `should show "content available" button if there is content available for current player`() {
        val data = givenThatInitialDataIsEmpty()

        givenThereIsContentAvailableForAllPlayers()
        givenThatQueryingForAnyPlayerReturns(Player(
                name = "DiabloVT",
                matches = someMatches
        ))

        runBlocking {
            presenter.initialize()

            presenter.onInitialData(data)

            presenter.onSendButtonClicked("DiabloVT")
        }

        verify(mockView).showContentAvailableButton()
    }

    private fun givenThatQueryingForAnyPlayerReturns(player: Player) {
        whenever(mockPlayerRepository.getPlayerByName(anyString())).thenReturn(
                Either.right(player)
        )
    }

    fun anyPlayer(): Player = any()

    private fun givenThereIsContentAvailableForAllPlayers() {
        whenever(mockIsContentAvailableForPlayer.isContentAvailableForPlayer(anyPlayer()))
                .thenReturn(Either.right(true))
    }


    private fun givenThatInitialDataIsEmpty(): PlayerSearchPresenter.InitialData {
        return object : PlayerSearchPresenter.InitialData {
            override fun getPlayerName(): String = ""
        }
    }

    private fun createPresenterWithMocks(playerRepository: PlayerRepository, matchRepository: MatchRepository): PlayerSearchPresenter {
        val presenter = PlayerSearchPresenter(mockResLocator,
                playerRepository,
                matchRepository,
                mockGetPlayerAccount,
                mockGetCurrentSeason,
                mockGetPlayerSeasonInfo,
                mockIsContentAvailableForPlayer)
        presenter.view = mockView
        presenter.navigator = mockNavigator
        return presenter
    }
}
