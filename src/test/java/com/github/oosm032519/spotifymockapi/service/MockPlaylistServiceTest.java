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

class MockPlaylistServiceTest {

    @InjectMocks
    private MockPlaylistService mockPlaylistService;

    @Mock
    private MockTrackService mockTrackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenQueryAndOffsetAndLimit_whenGetPlaylistSearchMockData_thenReturnsMockData() {
        // Arrange
        String query = "Sample Query";
        int offset = 0;
        int limit = 10;

        // Act
        Map<String, Object> result = mockPlaylistService.getPlaylistSearchMockData(query, offset, limit);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).containsKeys("playlists", "total");
        assertThat(result.get("playlists")).isInstanceOf(List.class);
        assertThat((List<?>) result.get("playlists")).hasSize(limit);
    }

    @Test
    void givenPlaylistId_whenGetPlaylistDetailsMockData_thenReturnsMockData() {
        // Arrange
        String playlistId = "mockPlaylistId001";

        // Act
        Map<String, Object> result = mockPlaylistService.getPlaylistDetailsMockData(playlistId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).containsKeys("playlistName", "owner", "tracks");
        assertThat(result.get("playlistName")).isEqualTo("Mock Playlist 001");
        assertThat(result.get("owner")).isInstanceOf(Map.class);
        assertThat(result.get("tracks")).isInstanceOf(Map.class);
    }

    @Test
    void givenPlaylistId_whenGetPlaylistTracksMockData_thenReturnsMockData() {
        // Arrange
        String playlistId = "mockPlaylistId002";
        when(mockTrackService.generateRandomDurationMs(anyString())).thenReturn(200000); // Mock Track Service

        // Act
        List<Map<String, Object>> result = mockPlaylistService.getPlaylistTracksMockData(playlistId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSizeGreaterThan(0);
        for (Map<String, Object> track : result) {
            assertThat(track).containsKeys("id", "name", "album", "durationMs");
            assertThat(track.get("durationMs")).isEqualTo(200000);
        }
        verify(mockTrackService, atLeastOnce()).generateRandomDurationMs(anyString());
    }

    @Test
    void whenGetFollowedPlaylistsMockData_thenReturnsMockData() {
        // Act
        List<Map<String, Object>> result = mockPlaylistService.getFollowedPlaylistsMockData();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(MockDataGeneratorUtil.DEFAULT_FOLLOWED_PLAYLISTS_COUNT);
        for (Map<String, Object> playlist : result) {
            assertThat(playlist).containsKeys("id", "name", "tracks", "images", "owner");
        }
    }
}
