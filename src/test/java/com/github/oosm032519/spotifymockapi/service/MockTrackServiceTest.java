package com.github.oosm032519.spotifymockapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MockTrackServiceTest {

    private MockTrackService mockTrackService;

    @BeforeEach
    void setUp() {
        mockTrackService = new MockTrackService();
    }

    @Test
    void testGetRecommendationsMockData() {
        // 実行
        List<Map<String, Object>> recommendations = mockTrackService.getRecommendationsMockData();

        // 検証
        assertNotNull(recommendations);
        assertEquals(5, recommendations.size()); // 推奨トラックが5件生成されることを確認
        for (int i = 0; i < recommendations.size(); i++) {
            Map<String, Object> track = recommendations.get(i);
            assertEquals("recommendation_track_id_" + (i + 1), track.get("id"));
            assertEquals("Recommendation Track " + (i + 1), track.get("name"));
            assertNotNull(track.get("durationMs"));
            assertNotNull(track.get("album"));
            assertNotNull(track.get("artists"));
        }
    }

    @Test
    void testGetAudioFeaturesForTracksMockData() {
        // テストデータ
        List<String> trackIds = List.of("track1", "track2", "track3");

        // 実行
        List<Map<String, Object>> audioFeatures = mockTrackService.getAudioFeaturesForTracksMockData(trackIds);

        // 検証
        assertNotNull(audioFeatures);
        assertEquals(trackIds.size(), audioFeatures.size()); // トラックID数とAudioFeaturesが一致することを確認
        Set<String> expectedIds = Set.of("audio_features_track1", "audio_features_track2", "audio_features_track3");

        for (Map<String, Object> features : audioFeatures) {
            assertNotNull(features.get("acousticness"));
            assertNotNull(features.get("danceability"));
            assertNotNull(features.get("energy"));
            assertNotNull(features.get("instrumentalness"));
            assertNotNull(features.get("liveness"));
            assertNotNull(features.get("loudness"));
            assertNotNull(features.get("mode"));
            assertNotNull(features.get("speechiness"));
            assertNotNull(features.get("tempo"));
            assertNotNull(features.get("timeSignature"));
            assertNotNull(features.get("valence"));
            assertNotNull(features.get("key"));
            assertNotNull(features.get("durationMs"));
            assertNotNull(features.get("id"));

            // IDが期待値のセットに存在することを確認
            String id = (String) features.get("id");
            assert (expectedIds.contains(id));
        }
    }

    @Test
    void testGenerateRandomDurationMs() {
        // テストデータ
        String trackId = "track123";

        // 実行
        int durationMs1 = mockTrackService.generateRandomDurationMs(trackId);
        int durationMs2 = mockTrackService.generateRandomDurationMs(trackId);

        // 検証
        assertEquals(durationMs1, durationMs2); // 同じトラックIDでは同じdurationMsを返すことを確認
        assert (durationMs1 >= 100000 && durationMs1 <= 300000); // durationMsが範囲内であることを確認
    }
}
