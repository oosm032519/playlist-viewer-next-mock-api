package com.github.oosm032519.spotifymockapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MockSpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(MockSpotifyService.class);

    private final ObjectMapper objectMapper;

    public MockSpotifyService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    // 1. searchPlaylists
    public Map<String, Object> getPlaylistSearchMockData(String query, int offset, int limit) {
        logger.info("getPlaylistSearchMockData called with query: {}, offset: {}, limit: {}", query, offset, limit);

        List<Map<String, Object>> playlists = new ArrayList<>();
        int totalPlaylists = 10; // Total number of playlists to generate

        for (int i = 0; i < totalPlaylists; i++) {
            Map<String, Object> playlist = new HashMap<>();
            playlist.put("name", "Search Playlist " + (i + 1));
            playlist.put("description", "Search Playlist " + (i + 1) + " Description");
            playlist.put("tracks", Map.of("total", (i + 1) * 5));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + (i + 1) + "/64/64")));
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/search_playlist_id_" + (i + 1))));
            playlist.put("owner", Map.of("displayName", "User " + (i + 1)));
            playlists.add(playlist);
        }

        // Apply offset and limit
        int start = Math.min(offset, playlists.size());
        int end = Math.min(offset + limit, playlists.size());
        List<Map<String, Object>> paginatedPlaylists = playlists.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("playlists", paginatedPlaylists);
        response.put("total", totalPlaylists);

        logger.info("Returning mock data for playlist search: {}", response);
        return response;
    }

    // 2. getPlaylistDetails
    public Map<String, Object> getPlaylistDetailsMockData(String playlistId) {
        logger.info("getPlaylistDetailsMockData called with playlistId: {}", playlistId);

        // Extract the last three digits of the playlist ID
        String lastThreeDigits = playlistId.length() > 3 ? playlistId.substring(playlistId.length() - 3) : playlistId;

        Map<String, Object> playlistDetails = new HashMap<>();
        playlistDetails.put("playlistName", "mockPlaylist" + lastThreeDigits);
        playlistDetails.put("owner", Map.of("id", "detail_owner_id_" + playlistId, "displayName", "mockOwner" + lastThreeDigits));

        logger.info("Returning mock data for playlist details: {}", playlistDetails);
        return playlistDetails;
    }

    // 3. getPlaylistTracks
    public List<Map<String, Object>> getPlaylistTracksMockData(String playlistId) {
        logger.info("getPlaylistTracksMockData called with playlistId: {}", playlistId);

        List<Map<String, Object>> playlistTracks = new ArrayList<>();
        int numTracks = 5; // Number of tracks to generate for each playlist

        for (int i = 0; i < numTracks; i++) {
            Map<String, Object> track = new HashMap<>();

            // Album data
            Map<String, Object> album = new HashMap<>();
            album.put("albumType", "ALBUM");
            List<Map<String, Object>> albumArtists = new ArrayList<>();
            Map<String, Object> albumArtist = new HashMap<>();
            albumArtist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/artist/artist_id_" + (i + 1))));
            albumArtist.put("href", "https://api.spotify.com/v1/artists/artist_id_" + (i + 1));
            albumArtist.put("id", "artist_id_" + (i + 1));
            albumArtist.put("name", "Artist " + (i + 1));
            albumArtist.put("type", "ARTIST");
            albumArtist.put("uri", "spotify:artist:artist_id_" + (i + 1));
            albumArtists.add(albumArtist);
            album.put("artists", albumArtists);
            album.put("availableMarkets", new ArrayList<>());
            album.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/album/album_id_" + (i + 1))));
            album.put("href", "https://api.spotify.com/v1/albums/album_id_" + (i + 1));
            album.put("id", "album_id_" + (i + 1));
            album.put("images", List.of(Map.of("height", 640, "url", "https://picsum.photos/seed/" + (i + 1) + "/64/64", "width", 640)));
            album.put("name", "Album " + (i + 1));
            album.put("releaseDate", "2023-01-0" + (i + 1));
            album.put("releaseDatePrecision", "DAY");
            album.put("type", "ALBUM");
            album.put("uri", "spotify:album:album_id_" + (i + 1));
            track.put("album", album);

            // Track artists
            List<Map<String, Object>> trackArtists = new ArrayList<>();
            Map<String, Object> trackArtist = new HashMap<>();
            trackArtist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/artist/artist_id_" + (i + 1))));
            trackArtist.put("href", "https://api.spotify.com/v1/artists/artist_id_" + (i + 1));
            trackArtist.put("id", "artist_id_" + (i + 1));
            trackArtist.put("name", "Artist " + (i + 1));
            trackArtist.put("type", "ARTIST");
            trackArtist.put("uri", "spotify:artist:artist_id_" + (i + 1));
            trackArtists.add(trackArtist);
            track.put("artists", trackArtists);

            track.put("availableMarkets", new ArrayList<>());
            track.put("discNumber", 1);
            track.put("durationMs", 180000 + (i * 10000));
            track.put("explicit", false);
            track.put("externalIds", Map.of("isrc", "USUM7180000" + (i + 1)));
            track.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/track/track_id_" + (i + 1))));
            track.put("href", "https://api.spotify.com/v1/tracks/track_id_" + (i + 1));
            track.put("id", "track_id_" + (i + 1));
            track.put("isPlayable", true);
            track.put("linkedFrom", null);
            track.put("restrictions", null);
            track.put("name", "Track " + (i + 1));
            track.put("popularity", 80 - (i * 5));
            track.put("previewUrl", "https://via.placeholder.com/150");
            track.put("trackNumber", i + 1);
            track.put("type", "TRACK");
            track.put("uri", "spotify:track:track_id_" + (i + 1));

            playlistTracks.add(track);
        }

        logger.info("Returning mock data for playlist tracks: {}", playlistTracks);
        return playlistTracks;
    }

    // 4. getArtistGenres
    public Map<String, List<String>> getArtistGenresMockData(List<String> artistIds) {
        logger.info("getArtistGenresMockData called with artistIds: {}", artistIds);

        Map<String, List<String>> artistGenres = new HashMap<>();
        for (String artistId : artistIds) {
            List<String> genres = new ArrayList<>();
            genres.add("genre" + artistId + "_1");
            genres.add("genre" + artistId + "_2");
            artistGenres.put(artistId, genres);
        }

        logger.info("Returning mock data for artist genres: {}", artistGenres);
        return artistGenres;
    }

    // 5. getRecommendations
    public List<Map<String, Object>> getRecommendationsMockData() {
        logger.info("getRecommendationsMockData called");

        List<Map<String, Object>> recommendations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> track = new HashMap<>();
            track.put("id", "recommendation_track_id_" + (i + 1));
            track.put("name", "Recommendation Track " + (i + 1));
            track.put("durationMs", 200000 + (i * 10000));
            track.put("album", Map.of(
                    "name", "Recommendation Album " + (i + 1),
                    "images", List.of(Map.of("url", "https://picsum.photos/seed/" + (i + 1) + "/64/64")),
                    "externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/album/recommendation_album_id_" + (i + 1)))
            ));
            track.put("artists", List.of(Map.of(
                    "name", "Recommendation Artist " + (i + 1),
                    "externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/artist/recommendation_artist_id_" + (i + 1)))
            )));
            track.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/track/recommendation_track_id_" + (i + 1))));
            track.put("previewUrl", "https://via.placeholder.com/150");
            recommendations.add(track);
        }

        logger.info("Returning mock data for recommendations: {}", recommendations);
        return recommendations;
    }

    // 6. getAudioFeaturesForTracks
    public List<Map<String, Object>> getAudioFeaturesForTracksMockData(List<String> trackIds) {
        logger.info("getAudioFeaturesForTracksMockData called with trackIds: {}", trackIds);

        List<Map<String, Object>> audioFeaturesList = new ArrayList<>();
        for (String trackId : trackIds) {
            Map<String, Object> audioFeatures = new HashMap<>();
            audioFeatures.put("acousticness", 0.1);
            audioFeatures.put("danceability", 0.2);
            audioFeatures.put("energy", 0.3);
            audioFeatures.put("instrumentalness", 0.4);
            audioFeatures.put("liveness", 0.5);
            audioFeatures.put("loudness", -60.0);
            audioFeatures.put("mode", 1);
            audioFeatures.put("speechiness", 0.6);
            audioFeatures.put("tempo", 100.0);
            audioFeatures.put("timeSignature", 4);
            audioFeatures.put("valence", 0.7);
            audioFeatures.put("key", 0);
            audioFeatures.put("durationMs", 60000);
            audioFeatures.put("id", "audio_features_" + trackId);
            audioFeaturesList.add(audioFeatures);
        }

        logger.info("Returning mock data for audio features for tracks: {}", audioFeaturesList);
        return audioFeaturesList;
    }

    // 7. getFollowedPlaylists
    public List<Map<String, Object>> getFollowedPlaylistsMockData() {
        logger.info("getFollowedPlaylistsMockData called (Returning user playlists)");

        List<Map<String, Object>> playlists = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> playlist = new HashMap<>();
            playlist.put("id", "followed_playlist_id_" + (i + 1));
            playlist.put("name", "Followed Playlist " + (i + 1));
            playlist.put("tracks", Map.of("total", 5 + (i * 5)));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + (i + 1) + "/64/64")));
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/followed_playlist_id_" + (i + 1))));
            playlist.put("owner", Map.of("display_name", "Followed User " + (i + 1)));
            playlists.add(playlist);
        }

        logger.info("Returning mock data for followed playlists (using user_playlists.json): {}", playlists);
        return playlists;
    }
}
