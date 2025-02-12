package com.github.oosm032519.spotifymockapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MockSpotifyServiceTest {

    private MockSpotifyService mockSpotifyService;

    @Mock
    private MockPlaylistService mockPlaylistService;

    @Mock
    private MockArtistService mockArtistService;

    @Mock
    private MockTrackService mockTrackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // @Mockでアノテーションされたモックを初期化
        mockSpotifyService = new MockSpotifyService(mockPlaylistService, mockArtistService, mockTrackService);
    }

    @Test
    void testGetPlaylistSearchMockData() {
        // モック設定
        Map<String, Object> mockData = Map.of("playlists", List.of(), "total", 100);
        when(mockPlaylistService.getPlaylistSearchMockData("query", 0, 10)).thenReturn(mockData);

        // 実行
        Map<String, Object> result = mockSpotifyService.getPlaylistSearchMockData("query", 0, 10);

        // 検証
        assertNotNull(result);
        assertEquals(mockData, result);
        verify(mockPlaylistService, times(1)).getPlaylistSearchMockData("query", 0, 10);
    }

    @Test
    void testGetPlaylistDetailsMockData() {
        // モック設定
        Map<String, Object> mockData = Map.of("playlistName", "Mock Playlist 001");
        when(mockPlaylistService.getPlaylistDetailsMockData("mockPlaylistId001")).thenReturn(mockData);

        // 実行
        Map<String, Object> result = mockSpotifyService.getPlaylistDetailsMockData("mockPlaylistId001");

        // 検証
        assertNotNull(result);
        assertEquals(mockData, result);
        verify(mockPlaylistService, times(1)).getPlaylistDetailsMockData("mockPlaylistId001");
    }

    @Test
    void testGetPlaylistTracksMockData() {
        // モック設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "track1"));
        when(mockPlaylistService.getPlaylistTracksMockData("mockPlaylistId001")).thenReturn(mockData);

        // 実行
        List<Map<String, Object>> result = mockSpotifyService.getPlaylistTracksMockData("mockPlaylistId001");

        // 検証
        assertNotNull(result);
        assertEquals(mockData, result);
        verify(mockPlaylistService, times(1)).getPlaylistTracksMockData("mockPlaylistId001");
    }

    @Test
    void testGetArtistGenresMockData() {
        // モック設定
        Map<String, List<String>> mockData = Map.of("artistId1", List.of("Rock", "Pop"));
        when(mockArtistService.getArtistGenresMockData(List.of("artistId1"))).thenReturn(mockData);

        // 実行
        Map<String, List<String>> result = mockSpotifyService.getArtistGenresMockData(List.of("artistId1"));

        // 検証
        assertNotNull(result);
        assertEquals(mockData, result);
        verify(mockArtistService, times(1)).getArtistGenresMockData(List.of("artistId1"));
    }

    @Test
    void testGetRecommendationsMockData() {
        // モック設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "track1", "name", "Recommendation Track 1"));
        when(mockTrackService.getRecommendationsMockData()).thenReturn(mockData);

        // 実行
        List<Map<String, Object>> result = mockSpotifyService.getRecommendationsMockData();

        // 検証
        assertNotNull(result);
        assertEquals(mockData, result);
        verify(mockTrackService, times(1)).getRecommendationsMockData();
    }

    @Test
    void testGetAudioFeaturesForTracksMockData() {
        // モック設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "audio_features_track1"));
        when(mockTrackService.getAudioFeaturesForTracksMockData(List.of("track1"))).thenReturn(mockData);

        // 実行
        List<Map<String, Object>> result = mockSpotifyService.getAudioFeaturesForTracksMockData(List.of("track1"));

        // 検証
        assertNotNull(result);
        assertEquals(mockData, result);
        verify(mockTrackService, times(1)).getAudioFeaturesForTracksMockData(List.of("track1"));
    }

    @Test
    void testGetFollowedPlaylistsMockData() {
        // モック設定
        List<Map<String, Object>> mockData = List.of(Map.of("id", "mockPlaylistId001"));
        when(mockPlaylistService.getFollowedPlaylistsMockData()).thenReturn(mockData);

        // 実行
        List<Map<String, Object>> result = mockSpotifyService.getFollowedPlaylistsMockData();

        // 検証
        assertNotNull(result);
        assertEquals(mockData, result);
        verify(mockPlaylistService, times(1)).getFollowedPlaylistsMockData();
    }
}
