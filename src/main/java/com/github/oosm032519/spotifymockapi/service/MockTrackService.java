package com.github.oosm032519.spotifymockapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * トラック関連のモックデータ生成サービス。
 */
@Service
public class MockTrackService {

    private static final Logger logger = LoggerFactory.getLogger(MockTrackService.class);

    private static final String RECOMMENDATION_TRACK_ID_PREFIX = MockDataGeneratorUtil.RECOMMENDATION_TRACK_ID_PREFIX;
    private static final String RECOMMENDATION_TRACK_NAME_PREFIX = MockDataGeneratorUtil.RECOMMENDATION_TRACK_NAME_PREFIX;
    private static final String RECOMMENDATION_ALBUM_NAME_PREFIX = MockDataGeneratorUtil.RECOMMENDATION_ALBUM_NAME_PREFIX;
    private static final String RECOMMENDATION_ALBUM_ID_PREFIX = MockDataGeneratorUtil.RECOMMENDATION_ALBUM_ID_PREFIX;
    private static final String RECOMMENDATION_ARTIST_NAME_PREFIX = MockDataGeneratorUtil.RECOMMENDATION_ARTIST_NAME_PREFIX;
    private static final String RECOMMENDATION_ARTIST_ID_PREFIX = MockDataGeneratorUtil.RECOMMENDATION_ARTIST_ID_PREFIX;
    private static final String AUDIO_FEATURES_ID_PREFIX = MockDataGeneratorUtil.AUDIO_FEATURES_ID_PREFIX;


    /**
     * おすすめトラックリストのモックデータを取得。
     *
     * @return おすすめトラックリストのモックデータ (List 形式)
     */
    public List<Map<String, Object>> getRecommendationsMockData() {
        logger.info("getRecommendationsMockData called");

        List<Map<String, Object>> recommendations = new ArrayList<>(); // おすすめトラックリストを初期化
        // モックおすすめトラックデータを生成
        for (int i = 0; i < 5; i++) {
            Map<String, Object> track = new HashMap<>(); // 各トラックのMap
            track.put("id", RECOMMENDATION_TRACK_ID_PREFIX + (i + 1));
            track.put("name", RECOMMENDATION_TRACK_NAME_PREFIX + (i + 1));
            track.put("durationMs", 200000 + (i * 10000));
            track.put("album", Map.of(
                    "name", RECOMMENDATION_ALBUM_NAME_PREFIX + (i + 1),
                    "images", List.of(Map.of("url", "https://picsum.photos/seed/" + (i + 1) + "/64/64")),
                    "externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/album/" + RECOMMENDATION_ALBUM_ID_PREFIX + (i + 1)))
            ));
            track.put("artists", List.of(Map.of(
                    "name", RECOMMENDATION_ARTIST_NAME_PREFIX + (i + 1),
                    "externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/artist/" + RECOMMENDATION_ARTIST_ID_PREFIX + (i + 1)))
            )));
            track.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/track/" + RECOMMENDATION_TRACK_ID_PREFIX + (i + 1))));
            track.put("previewUrl", "https://via.placeholder.com/150");
            recommendations.add(track); // 生成したおすすめトラックをリストに追加
        }

        logger.info("Returning mock data for recommendations: {}", recommendations);
        return recommendations;
    }

    /**
     * 複数のトラックIDからAudioFeatures取得リクエストのモックデータを取得。
     *
     * @param trackIds トラックIDのリスト
     * @return トラックIDとAudioFeatures取得リクエストのリスト (List 形式)
     */
    public List<Map<String, Object>> getAudioFeaturesForTracksMockData(List<String> trackIds) {
        logger.info("getAudioFeaturesForTracksMockData called with trackIds: {}", trackIds);

        List<Map<String, Object>> audioFeaturesList = new ArrayList<>(); // AudioFeatures取得リクエストリストを初期化
        Random random = new Random(); // 乱数生成器

        // 各トラックIDに対してAudioFeatures取得リクエストを生成
        for (String trackId : trackIds) {
            Map<String, Object> audioFeatures = new HashMap<>(); // 各AudioFeatures取得リクエストのMap
            audioFeatures.put("acousticness", random.nextDouble());
            audioFeatures.put("danceability", random.nextDouble());
            audioFeatures.put("energy", random.nextDouble());
            audioFeatures.put("instrumentalness", random.nextDouble());
            audioFeatures.put("liveness", random.nextDouble());
            audioFeatures.put("loudness", -60.0 + random.nextDouble() * 60.0); // ラウドネスは-60.0〜0.0の範囲を想定
            audioFeatures.put("mode", random.nextInt(2)); // 0 または 1
            audioFeatures.put("speechiness", random.nextDouble());
            audioFeatures.put("tempo", 50.0 + random.nextDouble() * 150.0); // テンポは50〜200の範囲を想定
            audioFeatures.put("timeSignature", random.nextInt(5) + 1); // 拍子記号は1〜5の範囲を想定
            audioFeatures.put("valence", random.nextDouble());
            audioFeatures.put("key", random.nextInt(12)); // キーは0〜11の範囲を想定
            audioFeatures.put("durationMs", 100000 + random.nextInt(200000)); // 再生時間は100000〜300000msの範囲を想定
            audioFeatures.put("id", AUDIO_FEATURES_ID_PREFIX + trackId);
            audioFeaturesList.add(audioFeatures); // 生成したAudioFeatures取得リクエストをリストに追加
        }

        logger.info("Returning mock data for audio features for tracks: {}", audioFeaturesList);
        return audioFeaturesList;
    }
}
