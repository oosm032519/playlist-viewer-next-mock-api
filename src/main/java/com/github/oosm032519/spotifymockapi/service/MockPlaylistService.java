package com.github.oosm032519.spotifymockapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * プレイリスト関連のモックデータ生成サービス。
 */
@Service
public class MockPlaylistService {

    private static final Logger logger = LoggerFactory.getLogger(MockPlaylistService.class);

    private static final int DEFAULT_TOTAL_PLAYLISTS = MockDataGeneratorUtil.DEFAULT_TOTAL_PLAYLISTS;
    private static final int MAX_TRACKS_PER_PLAYLIST = MockDataGeneratorUtil.MAX_TRACKS_PER_PLAYLIST;
    private static final int DEFAULT_FOLLOWED_PLAYLISTS_COUNT = MockDataGeneratorUtil.DEFAULT_FOLLOWED_PLAYLISTS_COUNT;

    private static final String PLAYLIST_ID_PREFIX = MockDataGeneratorUtil.PLAYLIST_ID_PREFIX;
    private static final String PLAYLIST_NAME_PREFIX = MockDataGeneratorUtil.PLAYLIST_NAME_PREFIX;
    private static final String USER_NAME_PREFIX = MockDataGeneratorUtil.USER_NAME_PREFIX;
    private static final String DETAIL_OWNER_PREFIX = MockDataGeneratorUtil.DETAIL_OWNER_PREFIX;
    private static final String MOCK_OWNER_PREFIX = MockDataGeneratorUtil.MOCK_OWNER_PREFIX;
    private static final String FOLLOWED_USER_PREFIX = MockDataGeneratorUtil.FOLLOWED_USER_PREFIX;

    private final MockTrackService mockTrackService;

    public MockPlaylistService(MockTrackService mockTrackService) {
        this.mockTrackService = mockTrackService;
    }

    /**
     * プレイリスト検索のモックデータを取得。
     *
     * @param query  検索クエリ
     * @param offset オフセット (ページネーション用)
     * @param limit  取得件数 (ページネーション用)
     * @return プレイリスト検索結果のモックデータ (Map 形式)
     */
    public Map<String, Object> getPlaylistSearchMockData(String query, int offset, int limit) {
        logger.info("getPlaylistSearchMockData called with query: {}, offset: {}, limit: {}", query, offset, limit);

        List<Map<String, Object>> playlists = new ArrayList<>(); // プレイリストのリストを初期化
        int totalPlaylists = DEFAULT_TOTAL_PLAYLISTS; // モックデータの総プレイリスト数

        // モックプレイリストデータを生成
        for (int i = 1; i <= totalPlaylists; i++) {
            String playlistId = MockDataGeneratorUtil.generatePlaylistId(i); // プレイリストIDを生成
            String playlistName = MockDataGeneratorUtil.generatePlaylistName(playlistId); // プレイリスト名を生成
            int trackCount = Math.min(MockDataGeneratorUtil.getLastThreeDigits(playlistId), MAX_TRACKS_PER_PLAYLIST); // トラック数をID末尾3桁と50の小さい方で決定

            Map<String, Object> playlist = new HashMap<>(); // 各プレイリストのMap
            playlist.put("id", playlistId);
            playlist.put("name", playlistName);
            playlist.put("description", "Search Playlist " + i + " Description");
            playlist.put("tracks", Map.of("total", trackCount));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + i + "/64/64"))); // ランダムな画像URL
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/" + playlistId)));
            playlist.put("owner", Map.of("displayName", MockDataGeneratorUtil.generateUserName(USER_NAME_PREFIX, i)));
            playlists.add(playlist); // 生成したプレイリストをリストに追加
        }

        // ページネーション処理
        int start = Math.min(offset, playlists.size()); // 開始位置を計算
        int end = Math.min(offset + limit, playlists.size()); // 終了位置を計算
        List<Map<String, Object>> paginatedPlaylists = playlists.subList(start, end); // リストをサブリストとしてページネーション

        Map<String, Object> response = new HashMap<>(); // レスポンス全体のMap
        response.put("playlists", paginatedPlaylists); // ページネーションされたプレイリストリストを格納
        response.put("total", totalPlaylists); // 総プレイリスト数を格納

        logger.info("Returning mock data for playlist search: {}", response);
        return response;
    }

    /**
     * 特定のプレイリスト詳細のモックデータを取得。
     *
     * @param playlistId プレイリストID
     * @return プレイリスト詳細のモックデータ (Map 形式)
     */
    public Map<String, Object> getPlaylistDetailsMockData(String playlistId) {
        logger.info("getPlaylistDetailsMockData called with playlistId: {}", playlistId);

        int trackCount = Math.min(MockDataGeneratorUtil.getLastThreeDigits(playlistId), MAX_TRACKS_PER_PLAYLIST); // トラック数をID末尾3桁と50の小さい方で決定
        String playlistName = MockDataGeneratorUtil.generatePlaylistName(playlistId); // プレイリスト名を生成

        Map<String, Object> playlistDetails = new HashMap<>(); // プレイリスト詳細のMap
        playlistDetails.put("playlistName", playlistName);
        playlistDetails.put("owner", Map.of("id", MockDataGeneratorUtil.generateUserId(DETAIL_OWNER_PREFIX, MockDataGeneratorUtil.getLastThreeDigits(playlistId)), "displayName", MockDataGeneratorUtil.generateUserName(MOCK_OWNER_PREFIX, MockDataGeneratorUtil.getLastThreeDigits(playlistId))));
        playlistDetails.put("tracks", Map.of("total", trackCount));

        logger.info("Returning mock data for playlist details: {}", playlistDetails);
        return playlistDetails;
    }

    /**
     * 特定のプレイリストのトラックリストのモックデータを取得。
     *
     * @param playlistId プレイリストID
     * @return プレイリストトラックリストのモックデータ (List 形式)
     */
    public List<Map<String, Object>> getPlaylistTracksMockData(String playlistId) {
        logger.info("getPlaylistTracksMockData called with playlistId: {}", playlistId);

        int numTracks = Math.min(MockDataGeneratorUtil.getLastThreeDigits(playlistId), MAX_TRACKS_PER_PLAYLIST); // トラック数をID末尾3桁と50の小さい方で決定
        List<Map<String, Object>> playlistTracks = new ArrayList<>(); // プレイリストトラックリストを初期化

        // モックトラックデータを生成
        for (int i = 0; i < numTracks; i++) {
            Map<String, Object> track = new HashMap<>(); // 各トラックのMap

            // トラックIDの生成
            String trackId = "track_id_" + (i + 1);

            // アルバムデータの生成
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
            track.put("album", album); // トラックにアルバム情報を設定

            // トラックアーティストデータの生成
            List<Map<String, Object>> trackArtists = new ArrayList<>();
            Map<String, Object> trackArtist = new HashMap<>();
            trackArtist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/artist/artist_id_" + (i + 1))));
            trackArtist.put("href", "https://api.spotify.com/v1/artists/artist_id_" + (i + 1));
            trackArtist.put("id", "artist_id_" + (i + 1));
            trackArtist.put("name", "Artist " + (i + 1));
            trackArtist.put("type", "ARTIST");
            trackArtist.put("uri", "spotify:artist:artist_id_" + (i + 1));
            trackArtists.add(trackArtist);
            track.put("artists", trackArtists); // トラックにアーティスト情報を設定

            track.put("availableMarkets", new ArrayList<>());
            track.put("discNumber", 1);
            track.put("durationMs", mockTrackService.generateRandomDurationMs(trackId)); // MockTrackServiceからdurationMsを取得 (トラックIDに基づいて生成)
            track.put("explicit", false);
            track.put("externalIds", Map.of("isrc", "USUM7180000" + (i + 1)));
            track.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/track/" + trackId)));
            track.put("href", "https://api.spotify.com/v1/tracks/" + trackId);
            track.put("id", trackId);
            track.put("isPlayable", true);
            track.put("linkedFrom", null);
            track.put("restrictions", null);
            track.put("name", "Track " + (i + 1));
            track.put("popularity", 80 - (i * 5));
            track.put("previewUrl", "https://via.placeholder.com/150");
            track.put("trackNumber", i + 1);
            track.put("type", "TRACK");
            track.put("uri", "spotify:track:" + trackId);

            playlistTracks.add(track); // 生成したトラックをリストに追加
        }

        logger.info("Returning mock data for playlist tracks: {}", playlistTracks);
        return playlistTracks;
    }

    /**
     * フォロー中のプレイリストのモックデータを取得。
     *
     * @return フォロー中のプレイリストのモックデータ (List 形式)
     */
    public List<Map<String, Object>> getFollowedPlaylistsMockData() {
        logger.info("getFollowedPlaylistsMockData called (Returning user playlists)");

        List<Map<String, Object>> playlists = new ArrayList<>(); // プレイリストリストを初期化
        // モックフォロー中プレイリストデータを生成
        for (int i = 1; i <= DEFAULT_FOLLOWED_PLAYLISTS_COUNT; i++) {
            String playlistId = MockDataGeneratorUtil.generatePlaylistId(i); // プレイリストIDを生成
            String playlistName = MockDataGeneratorUtil.generatePlaylistName(playlistId); // プレイリスト名を生成
            int trackCount = Math.min(MockDataGeneratorUtil.getLastThreeDigits(playlistId), MAX_TRACKS_PER_PLAYLIST); // トラック数をID末尾3桁と50の小さい方で決定

            Map<String, Object> playlist = new HashMap<>(); // 各プレイリストのMap
            playlist.put("id", playlistId);
            playlist.put("name", playlistName);
            playlist.put("tracks", Map.of("total", trackCount));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + i + "/64/64")));
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/" + playlistId)));

            // オーナー情報の生成
            Map<String, Object> owner = new HashMap<>();
            owner.put("displayName", MockDataGeneratorUtil.generateUserName(FOLLOWED_USER_PREFIX, i));
            owner.put("id", MockDataGeneratorUtil.generateUserId(FOLLOWED_USER_PREFIX, i));
            owner.put("type", "USER");
            owner.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/user/" + MockDataGeneratorUtil.generateUserId(FOLLOWED_USER_PREFIX, i))));
            owner.put("href", "https://api.spotify.com/v1/users/" + MockDataGeneratorUtil.generateUserId(FOLLOWED_USER_PREFIX, i));
            owner.put("uri", "spotify:user:" + MockDataGeneratorUtil.generateUserId(FOLLOWED_USER_PREFIX, i));

            playlist.put("owner", owner); // プレイリストにオーナー情報を設定
            playlists.add(playlist); // 生成したプレイリストをリストに追加
        }

        logger.info("Returning mock data for followed playlists (using user_playlists.json): {}", playlists);
        return playlists;
    }
}
