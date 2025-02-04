package com.github.oosm032519.spotifymockapi.controller;

import com.github.oosm032519.spotifymockapi.service.MockSpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockApiController {

    private static final Logger logger = LoggerFactory.getLogger(MockApiController.class);
    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 20;

    private final MockSpotifyService mockSpotifyService;

    public MockApiController(MockSpotifyService mockSpotifyService) {
        this.mockSpotifyService = mockSpotifyService;
    }

    /**
     * プレイリストを検索。
     *
     * @param query  検索キーワード
     * @param offset 検索開始位置 (デフォルト: 0)
     * @param limit  取得件数上限 (デフォルト: 20)
     * @return プレイリストの検索結果
     */
    @GetMapping("/search/playlists")
    public ResponseEntity<Map<String, Object>> searchPlaylists(
            @RequestParam("query") String query,
            @RequestParam(name = "offset", defaultValue = "" + DEFAULT_OFFSET) int offset,
            @RequestParam(name = "limit", defaultValue = "" + DEFAULT_LIMIT) int limit
    ) {
        logger.debug("プレイリスト検索リクエスト: query={}, offset={}, limit={}", query, offset, limit);
        Map<String, Object> response = mockSpotifyService.getPlaylistSearchMockData(query, offset, limit);
        return createOkResponse(response);
    }

    /**
     * プレイリストの詳細情報を取得。
     *
     * @param playlistId プレイリストID
     * @return プレイリストの詳細情報
     */
    @GetMapping("/playlists/{playlistId}")
    public ResponseEntity<Map<String, Object>> getPlaylistDetails(@PathVariable("playlistId") String playlistId) {
        logger.debug("プレイリスト詳細情報取得リクエスト: playlistId={}", playlistId);
        Map<String, Object> response = mockSpotifyService.getPlaylistDetailsMockData(playlistId);
        return createOkResponse(response);
    }

    /**
     * プレイリストのトラックリストを取得。
     *
     * @param playlistId プレイリストID
     * @return プレイリストのトラックリスト
     */
    @GetMapping("/playlists/{playlistId}/tracks")
    public ResponseEntity<List<Map<String, Object>>> getPlaylistTracks(@PathVariable("playlistId") String playlistId) {
        logger.debug("プレイリストトラックリスト取得リクエスト: playlistId={}", playlistId);
        List<Map<String, Object>> response = mockSpotifyService.getPlaylistTracksMockData(playlistId);
        return createOkResponse(response);
    }

    /**
     * 複数のアーティストのジャンル情報を取得。
     *
     * @param artistIds アーティストIDリスト
     * @return アーティストIDとジャンルリストのマップ
     */
    @GetMapping("/artists/genres")
    public ResponseEntity<Map<String, List<String>>> getArtistGenres(@RequestParam("artistIds") List<String> artistIds) {
        logger.debug("アーティストジャンル情報取得リクエスト: artistIds={}", artistIds);
        Map<String, List<String>> response = mockSpotifyService.getArtistGenresMockData(artistIds);
        return createOkResponse(response);
    }

    /**
     * おすすめトラックリストを取得。
     *
     * @return おすすめトラックリスト
     */
    @GetMapping("/recommendations")
    public ResponseEntity<List<Map<String, Object>>> getRecommendations() {
        logger.debug("おすすめトラックリスト取得リクエスト");
        List<Map<String, Object>> response = mockSpotifyService.getRecommendationsMockData();
        return createOkResponse(response);
    }

    /**
     * 複数のトラックのAudioFeatures取得リクエストを取得。
     *
     * @param trackIds トラックIDリスト
     * @return トラックIDとAudioFeatures取得リクエストのリスト
     */
    @GetMapping("/tracks/audio-features")
    public ResponseEntity<List<Map<String, Object>>> getAudioFeaturesForTracks(@RequestParam("trackIds") List<String> trackIds) {
        logger.debug("AudioFeatures取得リクエスト: trackIds={}", trackIds);
        List<Map<String, Object>> response = mockSpotifyService.getAudioFeaturesForTracksMockData(trackIds);
        return createOkResponse(response);
    }

    /**
     * ユーザーのフォロー済みプレイリストを取得。
     *
     * @return ユーザーのフォロー済みプレイリスト
     */
    @GetMapping("/following/playlists")
    public ResponseEntity<List<Map<String, Object>>> getUserPlaylists() {
        logger.debug("ユーザープレイリスト取得リクエスト");
        List<Map<String, Object>> response = mockSpotifyService.getFollowedPlaylistsMockData();
        return createOkResponse(response);
    }

    /**
     * HTTPステータスコード200 (OK) のレスポンスEntityを作成。
     *
     * @param body レスポンスボディ
     * @param <T>  レスポンスボディの型
     * @return HTTPステータスコード200のレスポンスEntity
     */
    private <T> ResponseEntity<T> createOkResponse(T body) {
        return ResponseEntity.ok(body);
    }
}
