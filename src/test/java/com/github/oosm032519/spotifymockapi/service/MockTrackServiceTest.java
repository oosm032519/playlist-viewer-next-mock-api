package com.github.oosm032519.spotifymockapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MockTrackServiceTest {

    @InjectMocks
    private MockTrackService mockTrackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetRecommendationsMockData_thenReturnsFiveRecommendations() {
        // Act: テスト対象メソッドを実行
        List<Map<String, Object>> recommendations = mockTrackService.getRecommendationsMockData();

        // Assert: 結果の検証
        assertThat(recommendations)
                .isNotNull()
                .hasSize(5); // 推奨トラックが5件生成されることを確認

        for (int i = 0; i < recommendations.size(); i++) {
            Map<String, Object> track = recommendations.get(i);
            assertThat(track.get("id")).isEqualTo("recommendation_track_id_" + (i + 1));
            assertThat(track.get("name")).isEqualTo("Recommendation Track " + (i + 1));
            assertThat(track).containsKey("durationMs");
            assertThat(track).containsKey("album");
            assertThat(track).containsKey("artists");
        }
    }

    @Test
    void givenTrackIds_whenGetAudioFeaturesForTracksMockData_thenReturnsAudioFeatures() {
        // Arrange: テストデータ
        List<String> trackIds = List.of("track1", "track2", "track3");

        // Act: テスト対象メソッドを実行
        List<Map<String, Object>> audioFeatures = mockTrackService.getAudioFeaturesForTracksMockData(trackIds);

        // Assert: 結果の検証
        assertThat(audioFeatures)
                .isNotNull()
                .hasSize(trackIds.size()); // トラックID数とAudioFeaturesが一致することを確認

        Set<String> expectedIds = Set.of("audio_features_track1", "audio_features_track2", "audio_features_track3");

        for (Map<String, Object> features : audioFeatures) {
            assertThat(features).containsKey("acousticness");
            assertThat(features).containsKey("danceability");
            assertThat(features).containsKey("energy");
            assertThat(features).containsKey("instrumentalness");
            assertThat(features).containsKey("liveness");
            assertThat(features).containsKey("loudness");
            assertThat(features).containsKey("mode");
            assertThat(features).containsKey("speechiness");
            assertThat(features).containsKey("tempo");
            assertThat(features).containsKey("timeSignature");
            assertThat(features).containsKey("valence");
            assertThat(features).containsKey("key");
            assertThat(features).containsKey("durationMs");
            assertThat(features).containsKey("id");

            // IDが期待値のセットに存在することを確認
            String id = (String) features.get("id");
            assertThat(expectedIds).contains(id);
        }
    }

    @Test
    void givenTrackId_whenGenerateRandomDurationMs_thenReturnsCachedDurationMs() {
        // Arrange: テストデータ
        String trackId = "track123";

        // Act: テスト対象メソッドを複数回実行
        int durationMs1 = mockTrackService.generateRandomDurationMs(trackId);
        int durationMs2 = mockTrackService.generateRandomDurationMs(trackId);

        // Assert: 結果の検証
        assertThat(durationMs1).isEqualTo(durationMs2); // 同じトラックIDでは同じdurationMsを返すことを確認
        assertThat(durationMs1).isBetween(100000, 300000); // durationMsが範囲内であることを確認
    }
}
