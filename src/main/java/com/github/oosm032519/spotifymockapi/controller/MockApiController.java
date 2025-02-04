package com.github.oosm032519.spotifymockapi.controller;

import com.github.oosm032519.spotifymockapi.service.MockSpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MockSpotifyService mockSpotifyService;

    /**
     * プレイリストを検索する。
     *
     * @param query  検索クエリ
     * @param offset 検索結果のオフセット
     * @param limit  検索結果の最大件数
     * @return プレイリストの検索結果
     */
    @GetMapping("/search/playlists")
    public ResponseEntity<Map<String, Object>> searchPlaylists(
            @RequestParam String query,
            @RequestParam(defaultValue = "" + DEFAULT_OFFSET) int offset,
            @RequestParam(defaultValue = "" + DEFAULT_LIMIT) int limit) {
        logger.info("GET /mock/search/playlists called with query: {}", query);
        Map<String, Object> response = mockSpotifyService.getPlaylistSearchMockData(query, offset, limit);
        return createOkResponse(response);
    }

    /**
     * プレイリストの詳細情報を取得する。
     *
     * @param playlistId プレイリストID
     * @return プレイリストの詳細情報
     */
    @GetMapping("/playlists/{playlistId}")
    public ResponseEntity<Map<String, Object>> getPlaylistDetails(@PathVariable String playlistId) {
        logger.info("GET /mock/playlists/{} called", playlistId);
        Map<String, Object> response = mockSpotifyService.getPlaylistDetailsMockData(playlistId);
        return createOkResponse(response);
    }

    /**
     * プレイリストのトラックを取得する。
     *
     * @param playlistId プレイリストID
     * @return プレイリストのトラック
     */
    @GetMapping("/playlists/{playlistId}/tracks")
    public ResponseEntity<List<Map<String, Object>>> getPlaylistTracks(@PathVariable String playlistId) {
        logger.info("GET /mock/playlists/{}/tracks called", playlistId);
        List<Map<String, Object>> response = mockSpotifyService.getPlaylistTracksMockData(playlistId);
        return createOkResponse(response);
    }

    /**
     * アーティストのジャンルを取得する。
     *
     * @param artistIds アーティストIDのリスト
     * @return アーティストIDとジャンルのマップ
     */
    @GetMapping("/artists/genres")
    public ResponseEntity<Map<String, List<String>>> getArtistGenres(@RequestParam List<String> artistIds) {
        logger.info("GET /mock/artists/genres called with artistIds: {}", artistIds);
        Map<String, List<String>> response = mockSpotifyService.getArtistGenresMockData(artistIds);
        return createOkResponse(response);
    }

    /**
     * おすすめのトラックを取得する。
     *
     * @return おすすめのトラック
     */
    @GetMapping("/recommendations")
    public ResponseEntity<List<Map<String, Object>>> getRecommendations() {
        logger.info("GET /mock/recommendations called");
        List<Map<String, Object>> response = mockSpotifyService.getRecommendationsMockData();
        return createOkResponse(response);
    }

    /**
     * トラックのオーディオ特徴量を取得する。
     *
     * @param trackIds トラックIDのリスト
     * @return トラックのオーディオ特徴量
     */
    @GetMapping("/tracks/audio-features")
    public ResponseEntity<List<Map<String, Object>>> getAudioFeaturesForTracks(@RequestParam List<String> trackIds) {
        logger.info("GET /mock/tracks/audio-features called with trackIds: {}", trackIds);
        List<Map<String, Object>> response = mockSpotifyService.getAudioFeaturesForTracksMockData(trackIds);
        return createOkResponse(response);
    }

    /**
     * ユーザーのプレイリストを取得する。
     *
     * @return ユーザーのプレイリスト
     */
    @GetMapping("/following/playlists")
    public ResponseEntity<List<Map<String, Object>>> getUserPlaylists() {
        logger.info("GET /mock/following/playlists called");
        List<Map<String, Object>> response = mockSpotifyService.getFollowedPlaylistsMockData();
        return createOkResponse(response);
    }

    /**
     * 指定されたボディを含む、ステータスコード 200 (OK) の ResponseEntity を作成する。
     *
     * @param body レスポンスボディ
     * @param <T>  レスポンスボディの型
     * @return ステータスコード 200 の ResponseEntity
     */
    private <T> ResponseEntity<T> createOkResponse(T body) {
        return ResponseEntity.ok(body);
    }
}
