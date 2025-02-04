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

    // Helper function to extract the last three digits of a playlist ID
    private int getLastThreeDigits(String playlistId) {
        try {
            if (playlistId.length() >= 3) {
                return Integer.parseInt(playlistId.substring(playlistId.length() - 3));
            } else {
                return Integer.parseInt(playlistId);
            }
        } catch (NumberFormatException e) {
            logger.error("Error parsing playlist ID: {}", playlistId, e);
            return 0; // Default value in case of error
        }
    }

    // Helper method to generate playlist ID
    private String generatePlaylistId(int index) {
        return String.format("mockPlaylistId%03d", index);
    }

    // Helper method to generate playlist name
    private String generatePlaylistName(String playlistId) {
        return "Mock Playlist " + playlistId.substring(playlistId.length() - 3);
    }

    // Helper method to generate user ID
    private String generateUserId(String prefix, int index) {
        return prefix + "_user_id_" + index;
    }

    // Helper method to generate user name
    private String generateUserName(String prefix, int index) {
        return prefix + " User " + index;
    }

    // 1. searchPlaylists
    public Map<String, Object> getPlaylistSearchMockData(String query, int offset, int limit) {
        logger.info("getPlaylistSearchMockData called with query: {}, offset: {}, limit: {}", query, offset, limit);

        List<Map<String, Object>> playlists = new ArrayList<>();
        int totalPlaylists = 999;

        for (int i = 1; i <= totalPlaylists; i++) {
            String playlistId = generatePlaylistId(i);
            String playlistName = generatePlaylistName(playlistId);
            int trackCount = Math.min(getLastThreeDigits(playlistId), 50);

            Map<String, Object> playlist = new HashMap<>();
            playlist.put("id", playlistId);
            playlist.put("name", playlistName);
            playlist.put("description", "Search Playlist " + i + " Description");
            playlist.put("tracks", Map.of("total", trackCount));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + i + "/64/64")));
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/" + playlistId)));
            playlist.put("owner", Map.of("displayName", generateUserName("User", i)));
            playlists.add(playlist);
        }

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

        int trackCount = Math.min(getLastThreeDigits(playlistId), 50);
        String playlistName = generatePlaylistName(playlistId);

        Map<String, Object> playlistDetails = new HashMap<>();
        playlistDetails.put("playlistName", playlistName);
        playlistDetails.put("owner", Map.of("id", generateUserId("detail_owner", getLastThreeDigits(playlistId)), "displayName", generateUserName("mockOwner", getLastThreeDigits(playlistId))));
        playlistDetails.put("tracks", Map.of("total", trackCount));

        logger.info("Returning mock data for playlist details: {}", playlistDetails);
        return playlistDetails;
    }

    // 3. getPlaylistTracks
    public List<Map<String, Object>> getPlaylistTracksMockData(String playlistId) {
        logger.info("getPlaylistTracksMockData called with playlistId: {}", playlistId);

        int numTracks = Math.min(getLastThreeDigits(playlistId), 50);
        List<Map<String, Object>> playlistTracks = new ArrayList<>();

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
        List<String> availableGenres = Arrays.asList("Rock", "Pop", "Jazz", "Hip Hop", "Electronic", "Classical", "Country", "Blues", "Reggae", "Metal");
        Random random = new Random();

        for (String artistId : artistIds) {
            List<String> genres = new ArrayList<>();
            int numGenres = random.nextInt(3) + 1; // 1 to 3 genres per artist
            for (int i = 0; i < numGenres; i++) {
                String genre = availableGenres.get(random.nextInt(availableGenres.size()));
                if (!genres.contains(genre)) {
                    genres.add(genre);
                }
            }
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
        Random random = new Random();

        for (String trackId : trackIds) {
            Map<String, Object> audioFeatures = new HashMap<>();
            audioFeatures.put("acousticness", random.nextDouble());
            audioFeatures.put("danceability", random.nextDouble());
            audioFeatures.put("energy", random.nextDouble());
            audioFeatures.put("instrumentalness", random.nextDouble());
            audioFeatures.put("liveness", random.nextDouble());
            audioFeatures.put("loudness", -60.0 + random.nextDouble() * 60.0); // Assuming loudness ranges from -60.0 to 0.0
            audioFeatures.put("mode", random.nextInt(2)); // 0 or 1
            audioFeatures.put("speechiness", random.nextDouble());
            audioFeatures.put("tempo", 50.0 + random.nextDouble() * 150.0); // Assuming tempo ranges from 50 to 200
            audioFeatures.put("timeSignature", random.nextInt(5) + 1); // Assuming time signature ranges from 1 to 5
            audioFeatures.put("valence", random.nextDouble());
            audioFeatures.put("key", random.nextInt(12)); // Assuming key ranges from 0 to 11
            audioFeatures.put("durationMs", 100000 + random.nextInt(200000)); // Assuming duration ranges from 100000 to 300000
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
        for (int i = 1; i <= 8; i++) {
            String playlistId = generatePlaylistId(i);
            String playlistName = generatePlaylistName(playlistId);
            int trackCount = Math.min(getLastThreeDigits(playlistId), 50);

            Map<String, Object> playlist = new HashMap<>();
            playlist.put("id", playlistId);
            playlist.put("name", playlistName);
            playlist.put("tracks", Map.of("total", trackCount));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + i + "/64/64")));
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/" + playlistId)));

            // Owner information
            Map<String, Object> owner = new HashMap<>();
            owner.put("displayName", generateUserName("Followed", i));
            owner.put("id", generateUserId("followed", i));
            owner.put("type", "USER");
            owner.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/user/" + generateUserId("followed", i))));
            owner.put("href", "https://api.spotify.com/v1/users/" + generateUserId("followed", i));
            owner.put("uri", "spotify:user:" + generateUserId("followed", i));

            playlist.put("owner", owner);
            playlists.add(playlist);
        }

        logger.info("Returning mock data for followed playlists (using user_playlists.json): {}", playlists);
        return playlists;
    }
}
