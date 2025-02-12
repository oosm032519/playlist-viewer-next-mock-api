package com.github.oosm032519.spotifymockapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MockSpotifyServiceTest {

    @InjectMocks
    private MockSpotifyService mockSpotifyService;

    @Mock
    private MockPlaylistService mockPlaylistService;

    @Mock
    private MockArtistService mockArtistService;

    @Mock
    private MockTrackService mockTrackService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenQueryAndOffsetAndLimit_whenGetPlaylistSearchMockData_thenDelegateToPlaylistService() {
        // Arrange: モックの振る舞いを設定
        Map<String, Object> mockData = Map.of("playlists", List.of(), "total", 100);
        when(mockPlaylistService.getPlaylistSearchMockData("query", 0, 10)).thenReturn(mockData);

        // Act: テスト対象メソッドを実行
        Map<String, Object> result = mockSpotifyService.getPlaylistSearchMockData("query", 0, 10);

        // Assert: 結果の検証とモックの呼び出し回数を確認
        assertThat(result).isEqualTo(mockData);
        verify(mockPlaylistService, times(1)).getPlaylistSearchMockData("query", 0, 10);
    }

    @Test
    void givenPlaylistId_whenGetPlaylistDetailsMockData_thenDelegateToPlaylistService() {
        // Arrange: モックの振る舞いを設定
        Map<String, Object> mockData = Map.of("playlistName", "Mock Playlist 001");
        when(mockPlaylistService.getPlaylistDetailsMockData("mockPlaylistId001")).thenReturn(mockData);

        // Act: テスト対象メソッドを実行
        Map<String, Object> result = mockSpotifyService.getPlaylistDetailsMockData("mockPlaylistId001");

        // Assert: 結果の検証とモックの呼び出し回数を確認
        assertThat(result).isEqualTo(mockData);
        verify(mockPlaylistService, times(1)).getPlaylistDetailsMockData("mockPlaylistId001");
    }

    @Test
    void givenPlaylistId_whenGetPlaylistTracksMockData_thenDelegateToPlaylistService() {
        // Arrange: モックの振る舞いを設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "track1"));
        when(mockPlaylistService.getPlaylistTracksMockData("mockPlaylistId001")).thenReturn(mockData);

        // Act: テスト対象メソッドを実行
        List<Map<String, Object>> result = mockSpotifyService.getPlaylistTracksMockData("mockPlaylistId001");

        // Assert: 結果の検証とモックの呼び出し回数を確認
        assertThat(result).isEqualTo(mockData);
        verify(mockPlaylistService, times(1)).getPlaylistTracksMockData("mockPlaylistId001");
    }

    @Test
    void givenArtistIds_whenGetArtistGenresMockData_thenDelegateToArtistService() {
        // Arrange: モックの振る舞いを設定
        Map<String, List<String>> mockData = Map.of("artistId1", List.of("Rock", "Pop"));
        when(mockArtistService.getArtistGenresMockData(List.of("artistId1"))).thenReturn(mockData);

        // Act: テスト対象メソッドを実行
        Map<String, List<String>> result = mockSpotifyService.getArtistGenresMockData(List.of("artistId1"));

        // Assert: 結果の検証とモックの呼び出し回数を確認
        assertThat(result).isEqualTo(mockData);
        verify(mockArtistService, times(1)).getArtistGenresMockData(List.of("artistId1"));
    }

    @Test
    void whenGetRecommendationsMockData_thenDelegateToTrackService() {
        // Arrange: モックの振る舞いを設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "track1", "name", "Recommendation Track 1"));
        when(mockTrackService.getRecommendationsMockData()).thenReturn(mockData);

        // Act: テスト対象メソッドを実行
        List<Map<String, Object>> result = mockSpotifyService.getRecommendationsMockData();

        // Assert: 結果の検証とモックの呼び出し回数を確認
        assertThat(result).isEqualTo(mockData);
        verify(mockTrackService, times(1)).getRecommendationsMockData();
    }

    @Test
    void givenTrackIds_whenGetAudioFeaturesForTracksMockData_thenDelegateToTrackService() {
        // Arrange: モックの振る舞いを設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "audio_features_track1"));
        when(mockTrackService.getAudioFeaturesForTracksMockData(List.of("track1"))).thenReturn(mockData);

        // Act: テスト対象メソッドを実行
        List<Map<String, Object>> result = mockSpotifyService.getAudioFeaturesForTracksMockData(List.of("track1"));

        // Assert: 結果の検証とモックの呼び出し回数を確認
        assertThat(result).isEqualTo(mockData);
        verify(mockTrackService, times(1)).getAudioFeaturesForTracksMockData(List.of("track1"));
    }

    @Test
    void whenGetFollowedPlaylistsMockData_thenDelegateToPlaylistService() {
        // Arrange: モックの振る舞いを設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "mockPlaylistId001"));
        when(mockPlaylistService.getFollowedPlaylistsMockData()).thenReturn(mockData);

        // Act: テスト対象メソッドを実行
        List<Map<String, Object>> result = mockSpotifyService.getFollowedPlaylistsMockData();

        // Assert: 結果の検証とモックの呼び出し回数を確認
        assertThat(result).isEqualTo(mockData);
        verify(mockPlaylistService, times(1)).getFollowedPlaylistsMockData();
    }
}
