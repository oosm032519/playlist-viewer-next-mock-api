package com.github.oosm032519.spotifymockapi.controller;

import com.github.oosm032519.spotifymockapi.service.MockSpotifyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MockApiControllerTest {

    @InjectMocks
    private MockApiController mockApiController;

    @Mock
    private MockSpotifyService mockSpotifyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * searchPlaylists メソッドのテスト。
     * MockSpotifyService の getPlaylistSearchMockData メソッドが正しく呼び出され、
     * 適切な ResponseEntity が返されることを検証する。
     */
    @Test
    void searchPlaylists_validRequest_returnsOkResponse() {
        // Arrange: テストデータの準備
        String query = "test";
        int offset = 0;
        int limit = 20;
        Map<String, Object> mockData = new HashMap<>();
        mockData.put("playlists", Collections.emptyList());
        mockData.put("total", 0);

        // Arrange: MockSpotifyService の getPlaylistSearchMockData メソッドの振る舞いを設定
        when(mockSpotifyService.getPlaylistSearchMockData(query, offset, limit)).thenReturn(mockData);

        // Act: テスト対象メソッドの実行
        ResponseEntity<Map<String, Object>> response = mockApiController.searchPlaylists(query, offset, limit);

        // Assert: レスポンスの検証
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(mockSpotifyService, times(1)).getPlaylistSearchMockData(query, offset, limit);
    }

    /**
     * getPlaylistDetails メソッドのテスト。
     * MockSpotifyService の getPlaylistDetailsMockData メソッドが正しく呼び出され、
     * 適切な ResponseEntity が返されることを検証する。
     */
    @Test
    void getPlaylistDetails_validPlaylistId_returnsOkResponse() {
        // Arrange: テストデータの準備
        String playlistId = "123";
        Map<String, Object> mockData = new HashMap<>();
        mockData.put("playlistName", "Mock Playlist");
        mockData.put("owner", Map.of("id", "ownerId", "displayName", "Owner Name"));
        mockData.put("tracks", Map.of("total", 10));

        // Arrange: MockSpotifyService の getPlaylistDetailsMockData メソッドの振る舞いを設定
        when(mockSpotifyService.getPlaylistDetailsMockData(playlistId)).thenReturn(mockData);

        // Act: テスト対象メソッドの実行
        ResponseEntity<Map<String, Object>> response = mockApiController.getPlaylistDetails(playlistId);

        // Assert: レスポンスの検証
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(mockSpotifyService, times(1)).getPlaylistDetailsMockData(playlistId);
    }

    /**
     * getPlaylistTracks メソッドのテスト。
     * MockSpotifyService の getPlaylistTracksMockData メソッドが正しく呼び出され、
     * 適切な ResponseEntity が返されることを検証する。
     */
    @Test
    void getPlaylistTracks_validPlaylistId_returnsOkResponse() {
        // Arrange: テストデータの準備
        String playlistId = "123";
        List<Map<String, Object>> mockData = new ArrayList<>();
        mockData.add(Map.of("id", "track1", "name", "Track 1"));

        // Arrange: MockSpotifyService の getPlaylistTracksMockData メソッドの振る舞いを設定
        when(mockSpotifyService.getPlaylistTracksMockData(playlistId)).thenReturn(mockData);

        // Act: テスト対象メソッドの実行
        ResponseEntity<List<Map<String, Object>>> response = mockApiController.getPlaylistTracks(playlistId);

        // Assert: レスポンスの検証
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(mockSpotifyService, times(1)).getPlaylistTracksMockData(playlistId);
    }

    /**
     * getArtistGenres メソッドのテスト。
     * MockSpotifyService の getArtistGenresMockData メソッドが正しく呼び出され、
     * 適切な ResponseEntity が返されることを検証する。
     */
    @Test
    void getArtistGenres_validArtistIds_returnsOkResponse() {
        // Arrange: テストデータの準備
        List<String> artistIds = Arrays.asList("artist1", "artist2");
        Map<String, List<String>> mockData = new HashMap<>();
        mockData.put("artist1", Arrays.asList("Genre1", "Genre2"));
        mockData.put("artist2", List.of("Genre3"));

        // Arrange: MockSpotifyService の getArtistGenresMockData メソッドの振る舞いを設定
        when(mockSpotifyService.getArtistGenresMockData(artistIds)).thenReturn(mockData);

        // Act: テスト対象メソッドの実行
        ResponseEntity<Map<String, List<String>>> response = mockApiController.getArtistGenres(artistIds);

        // Assert: レスポンスの検証
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(mockSpotifyService, times(1)).getArtistGenresMockData(artistIds);
    }

    /**
     * getRecommendations メソッドのテスト。
     * MockSpotifyService の getRecommendationsMockData メソッドが正しく呼び出され、
     * 適切な ResponseEntity が返されることを検証する。
     */
    @Test
    void getRecommendations_returnsOkResponse() {
        // Arrange: テストデータの準備
        List<Map<String, Object>> mockData = new ArrayList<>();
        mockData.add(Map.of("id", "track1", "name", "Track 1"));

        // Arrange: MockSpotifyService の getRecommendationsMockData メソッドの振る舞いを設定
        when(mockSpotifyService.getRecommendationsMockData()).thenReturn(mockData);

        // Act: テスト対象メソッドの実行
        ResponseEntity<List<Map<String, Object>>> response = mockApiController.getRecommendations();

        // Assert: レスポンスの検証
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(mockSpotifyService, times(1)).getRecommendationsMockData();
    }

    /**
     * getAudioFeaturesForTracks メソッドのテスト。
     * MockSpotifyService の getAudioFeaturesForTracksMockData メソッドが正しく呼び出され、
     * 適切な ResponseEntity が返されることを検証する。
     */
    @Test
    void getAudioFeaturesForTracks_validTrackIds_returnsOkResponse() {
        // Arrange: テストデータの準備
        List<String> trackIds = Arrays.asList("track1", "track2");
        List<Map<String, Object>> mockData = new ArrayList<>();
        mockData.add(Map.of("id", "track1", "acousticness", 0.5));
        mockData.add(Map.of("id", "track2", "acousticness", 0.8));

        // Arrange: MockSpotifyService の getAudioFeaturesForTracksMockData メソッドの振る舞いを設定
        when(mockSpotifyService.getAudioFeaturesForTracksMockData(trackIds)).thenReturn(mockData);

        // Act: テスト対象メソッドの実行
        ResponseEntity<List<Map<String, Object>>> response = mockApiController.getAudioFeaturesForTracks(trackIds);

        // Assert: レスポンスの検証
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(mockSpotifyService, times(1)).getAudioFeaturesForTracksMockData(trackIds);
    }

    /**
     * getUserPlaylists メソッドのテスト。
     * MockSpotifyService の getFollowedPlaylistsMockData メソッドが正しく呼び出され、
     * 適切な ResponseEntity が返されることを検証する。
     */
    @Test
    void getUserPlaylists_returnsOkResponse() {
        // Arrange: テストデータの準備
        List<Map<String, Object>> mockData = new ArrayList<>();
        mockData.add(Map.of("id", "playlist1", "name", "Playlist 1"));

        // Arrange: MockSpotifyService の getFollowedPlaylistsMockData メソッドの振る舞いを設定
        when(mockSpotifyService.getFollowedPlaylistsMockData()).thenReturn(mockData);

        // Act: テスト対象メソッドの実行
        ResponseEntity<List<Map<String, Object>>> response = mockApiController.getUserPlaylists();

        // Assert: レスポンスの検証
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(mockSpotifyService, times(1)).getFollowedPlaylistsMockData();
    }
}
