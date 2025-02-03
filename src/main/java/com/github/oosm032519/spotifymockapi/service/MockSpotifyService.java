package com.github.oosm032519.spotifymockapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class MockSpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(MockSpotifyService.class);

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Value("${spotify.mock-api.data.path}")
    private String mockDataPath;

    public MockSpotifyService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    // 1. searchPlaylists
    public Map<String, Object> getPlaylistSearchMockData(String query, int offset, int limit) {
        logger.info("getPlaylistSearchMockData called with query: {}, offset: {}, limit: {}", query, offset, limit);
        try {
            logger.info("Loading resource from: {}", mockDataPath + "/search_playlists.json");
            Resource resource = resourceLoader.getResource(mockDataPath + "/search_playlists.json");
            InputStream inputStream = resource.getInputStream();
            Map<String, Object> response = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {
            });
            logger.info("Returning mock data for playlist search: {}", response);
            return response;
        } catch (IOException e) {
            logger.error("Error reading mock data for playlist search", e);
            throw new RuntimeException("Error reading mock data for playlist search", e);
        }
    }

    // 2. getPlaylistDetails
    public Map<String, Object> getPlaylistDetailsMockData(String playlistId) {
        logger.info("getPlaylistDetailsMockData called with playlistId: {}", playlistId);
        try {
            logger.info("Loading resource from: {}", mockDataPath + "/playlist_details.json");
            Resource resource = resourceLoader.getResource(mockDataPath + "/playlist_details.json");
            InputStream inputStream = resource.getInputStream();
            Map<String, Object> response = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {
            });
            logger.info("Returning mock data for playlist details: {}", response);
            return response;
        } catch (IOException e) {
            logger.error("Error reading mock data for playlist details", e);
            throw new RuntimeException("Error reading mock data for playlist details", e);
        }
    }

    // 3. getPlaylistTracks
    public List<Map<String, Object>> getPlaylistTracksMockData(String playlistId) { // 返り値の型を変更
        logger.info("getPlaylistTracksMockData called with playlistId: {}", playlistId);
        try {
            logger.info("Loading resource from: {}", mockDataPath + "/playlist_tracks.json");
            Resource resource = resourceLoader.getResource(mockDataPath + "/playlist_tracks.json");
            InputStream inputStream = resource.getInputStream();

            // JSONをList<Map<String, Object>>として読み込む
            List<Map<String, Object>> playlistTracks = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() { // TypeReference を変更
            });

            logger.info("Returning mock data for playlist tracks: {}", playlistTracks);
            return playlistTracks;
        } catch (IOException e) {
            logger.error("Error reading mock data for playlist tracks", e);
            throw new RuntimeException("Error reading mock data for playlist tracks", e);
        }
    }

    // 4. getArtistGenres
    public Map<String, List<String>> getArtistGenresMockData(List<String> artistIds) {
        logger.info("getArtistGenresMockData called with artistIds: {}", artistIds);
        try {
            logger.info("Loading resource from: {}", mockDataPath + "/artist_genres.json");
            Resource resource = resourceLoader.getResource(mockDataPath + "/artist_genres.json");
            InputStream inputStream = resource.getInputStream();
            Map<String, List<String>> response = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<String>>>() {
            });
            logger.info("Returning mock data for artist genres: {}", response);
            return response;
        } catch (IOException e) {
            logger.error("Error reading mock data for artist genres", e);
            throw new RuntimeException("Error reading mock data for artist genres", e);
        }
    }

    // 5. getRecommendations
    public List<Map<String, Object>> getRecommendationsMockData() {
        logger.info("getRecommendationsMockData called");
        try {
            logger.info("Loading resource from: {}", mockDataPath + "/recommendations.json");
            Resource resource = resourceLoader.getResource(mockDataPath + "/recommendations.json");
            InputStream inputStream = resource.getInputStream();
            List<Map<String, Object>> tracks = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {
            });
            logger.info("Returning mock data for recommendations: {}", tracks);
            return tracks;
        } catch (IOException e) {
            logger.error("Error reading mock data for recommendations", e);
            throw new RuntimeException("Error reading mock data for recommendations", e);
        }
    }

    // 6. getAudioFeaturesForTracks
    public List<Map<String, Object>> getAudioFeaturesForTracksMockData(List<String> trackIds) {
        logger.info("getAudioFeaturesForTracksMockData called with trackIds: {}", trackIds);
        try {
            logger.info("Loading resource from: {}", mockDataPath + "/audio_features_tracks.json");
            Resource resource = resourceLoader.getResource(mockDataPath + "/audio_features_tracks.json");
            InputStream inputStream = resource.getInputStream();
            List<Map<String, Object>> audioFeatures = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {
            });
            logger.info("Returning mock data for audio features for tracks: {}", audioFeatures);
            return audioFeatures;
        } catch (IOException e) {
            logger.error("Error reading mock data for audio features for tracks", e);
            throw new RuntimeException("Error reading mock data for audio features for tracks", e);
        }
    }

    // 7. getFollowedPlaylists
    public List<Map<String, Object>> getFollowedPlaylistsMockData() {
        logger.info("getFollowedPlaylistsMockData called (Returning user playlists)");
        try {
            // user_playlists.json からデータを取得するように変更
            logger.info("Loading resource from: {}", mockDataPath + "/user_playlists.json");
            Resource resource = resourceLoader.getResource(mockDataPath + "/user_playlists.json");
            InputStream inputStream = resource.getInputStream();
            List<Map<String, Object>> playlists = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {
            });
            logger.info("Returning mock data for followed playlists (using user_playlists.json): {}", playlists);
            return playlists;
        } catch (IOException e) {
            logger.error("Error reading mock data for followed playlists", e);
            throw new RuntimeException("Error reading mock data for followed playlists", e);
        }
    }
}
