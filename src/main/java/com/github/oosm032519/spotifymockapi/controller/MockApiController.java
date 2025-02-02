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

    @Autowired
    private MockSpotifyService mockSpotifyService;

    @GetMapping("/search/playlists")
    public ResponseEntity<Map<String, Object>> searchPlaylists(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {
        logger.info("GET /mock/search/playlists called with query: {}, offset: {}, limit: {}", query, offset, limit);
        Map<String, Object> response = mockSpotifyService.getPlaylistSearchMockData(query, offset, limit);
        logger.info("GET /mock/search/playlists response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/playlists/{playlistId}")
    public ResponseEntity<Map<String, Object>> getPlaylistDetails(@PathVariable String playlistId) {
        logger.info("GET /mock/playlists/{} called", playlistId);
        Map<String, Object> response = mockSpotifyService.getPlaylistDetailsMockData(playlistId);
        logger.info("GET /mock/playlists/{} response: {}", playlistId, response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/playlists/{playlistId}/tracks")
    public ResponseEntity<List<Map<String, Object>>> getPlaylistTracks(@PathVariable String playlistId) {
        logger.info("GET /mock/playlists/{}/tracks called", playlistId);
        List<Map<String, Object>> response = mockSpotifyService.getPlaylistTracksMockData(playlistId);
        logger.info("GET /mock/playlists/{}/tracks response: {}", playlistId, response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/artists/genres")
    public ResponseEntity<Map<String, List<String>>> getArtistGenres(@RequestParam List<String> artistIds) {
        logger.info("GET /mock/artists/genres called with artistIds: {}", artistIds);
        Map<String, List<String>> response = mockSpotifyService.getArtistGenresMockData(artistIds);
        logger.info("GET /mock/artists/genres response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Map<String, Object>>> getRecommendations() {
        logger.info("GET /mock/recommendations called");
        List<Map<String, Object>> response = mockSpotifyService.getRecommendationsMockData();
        logger.info("GET /mock/recommendations response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tracks/audio-features")
    public ResponseEntity<List<Map<String, Object>>> getAudioFeaturesForTracks(@RequestParam List<String> trackIds) {
        logger.info("GET /mock/tracks/audio-features called with trackIds: {}", trackIds);
        List<Map<String, Object>> response = mockSpotifyService.getAudioFeaturesForTracksMockData(trackIds);
        logger.info("GET /mock/tracks/audio-features response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/following/playlists")
    public ResponseEntity<List<Map<String, Object>>> getFollowedPlaylists() {
        logger.info("GET /mock/following/playlists called (Returning user playlists)");
        List<Map<String, Object>> response = mockSpotifyService.getFollowedPlaylistsMockData();
        logger.info("GET /mock/following/playlists response: {}", response);
        return ResponseEntity.ok(response);
    }
}
