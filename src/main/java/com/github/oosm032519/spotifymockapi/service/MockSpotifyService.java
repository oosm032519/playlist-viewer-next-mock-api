package com.github.oosm032519.spotifymockapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Spotify API のモックサービスを提供するFacadeクラス。
 * 各機能ごとのサービスに処理を委譲する。
 */
@Service
public class MockSpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(MockSpotifyService.class);
    private final ObjectMapper objectMapper;
    private final MockPlaylistService mockPlaylistService;
    private final MockArtistService mockArtistService;
    private final MockTrackService mockTrackService;

    /**
     * コンストラクタ。ObjectMapper を初期化し、JavaTimeModule を登録。
     *
     * @param mockPlaylistService プレイリストモックデータ生成サービス
     * @param mockArtistService   アーティストモックデータ生成サービス
     * @param mockTrackService    トラックモックデータ生成サービス
     */
    public MockSpotifyService(MockPlaylistService mockPlaylistService, MockArtistService mockArtistService, MockTrackService mockTrackService) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Java 8 Date/Time API を Jackson で扱うためのモジュール登録
        this.mockPlaylistService = mockPlaylistService;
        this.mockArtistService = mockArtistService;
        this.mockTrackService = mockTrackService;
    }

    /**
     * プレイリスト検索のモックデータを取得。
     *
     * @param query  検索クエリ
     * @param offset オフセット (ページネーション用)
     * @param limit  取得件数 (ページネーション用)
     * @return プレイリスト検索結果のモックデータ (Map 形式)
     */
    public Map<String, Object> getPlaylistSearchMockData(String query, int offset, int limit) {
        logger.info("Delegating getPlaylistSearchMockData to MockPlaylistService");
        return mockPlaylistService.getPlaylistSearchMockData(query, offset, limit);
    }

    /**
     * 特定のプレイリスト詳細のモックデータを取得。
     *
     * @param playlistId プレイリストID
     * @return プレイリスト詳細のモックデータ (Map 形式)
     */
    public Map<String, Object> getPlaylistDetailsMockData(String playlistId) {
        logger.info("Delegating getPlaylistDetailsMockData to MockPlaylistService");
        return mockPlaylistService.getPlaylistDetailsMockData(playlistId);
    }

    /**
     * 特定のプレイリストのトラックリストのモックデータを取得。
     *
     * @param playlistId プレイリストID
     * @return プレイリストトラックリストのモックデータ (List 形式)
     */
    public List<Map<String, Object>> getPlaylistTracksMockData(String playlistId) {
        logger.info("Delegating getPlaylistTracksMockData to MockPlaylistService");
        return mockPlaylistService.getPlaylistTracksMockData(playlistId);
    }

    /**
     * 複数のアーティストIDからジャンルリストのモックデータを取得。
     *
     * @param artistIds アーティストIDのリスト
     * @return アーティストIDとジャンルリストのマップ (Map 形式)
     */
    public Map<String, List<String>> getArtistGenresMockData(List<String> artistIds) {
        logger.info("Delegating getArtistGenresMockData to MockArtistService");
        return mockArtistService.getArtistGenresMockData(artistIds);
    }

    /**
     * おすすめトラックリストのモックデータを取得。
     *
     * @return おすすめトラックリストのモックデータ (List 形式)
     */
    public List<Map<String, Object>> getRecommendationsMockData() {
        logger.info("Delegating getRecommendationsMockData to MockTrackService");
        return mockTrackService.getRecommendationsMockData();
    }

    /**
     * 複数のトラックIDからAudioFeatures取得リクエストのモックデータを取得。
     *
     * @param trackIds トラックIDのリスト
     * @return トラックIDとAudioFeatures取得リクエストのリスト (List 形式)
     */
    public List<Map<String, Object>> getAudioFeaturesForTracksMockData(List<String> trackIds) {
        logger.info("Delegating getAudioFeaturesForTracksMockData to MockTrackService");
        return mockTrackService.getAudioFeaturesForTracksMockData(trackIds);
    }

    /**
     * フォロー中のプレイリストのモックデータを取得。
     *
     * @return フォロー中のプレイリストのモックデータ (List 形式)
     */
    public List<Map<String, Object>> getFollowedPlaylistsMockData() {
        logger.info("Delegating getFollowedPlaylistsMockData to MockPlaylistService");
        return mockPlaylistService.getFollowedPlaylistsMockData();
    }
}
