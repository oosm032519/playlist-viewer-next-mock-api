package com.github.oosm032519.spotifymockapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Spotify API のモックサービスを提供するクラス。
 * プレイリスト、トラック、アーティストなどのモックデータを生成。
 */
@Service
public class MockSpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(MockSpotifyService.class);

    private final ObjectMapper objectMapper;

    /**
     * コンストラクタ。ObjectMapper を初期化し、JavaTimeModule を登録。
     */
    public MockSpotifyService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Java 8 Date/Time API を Jackson で扱うためのモジュール登録
    }

    /**
     * プレイリストIDの末尾3桁を数値として抽出するヘルパー関数。
     *
     * @param playlistId プレイリストID
     * @return 末尾3桁の数値 (NumberFormatException の場合は 0)
     */
    private int getLastThreeDigits(String playlistId) {
        try {
            if (playlistId.length() >= 3) {
                return Integer.parseInt(playlistId.substring(playlistId.length() - 3)); // 末尾3桁を抽出して数値に変換
            } else {
                return Integer.parseInt(playlistId); // 3桁未満の場合はそのまま数値に変換
            }
        } catch (NumberFormatException e) {
            logger.error("Error parsing playlist ID: {}", playlistId, e); // 数値変換エラーをログ出力
            return 0; // エラーの場合はデフォルト値 0 を返す
        }
    }

    /**
     * インデックスからプレイリストIDを生成するヘルパーメソッド。
     *
     * @param index インデックス
     * @return 生成されたプレイリストID (例: mockPlaylistId001)
     */
    private String generatePlaylistId(int index) {
        return String.format("mockPlaylistId%03d", index); // インデックスを3桁のゼロ埋め文字列としてフォーマット
    }

    /**
     * プレイリストIDからプレイリスト名を生成するヘルパーメソッド。
     *
     * @param playlistId プレイリストID
     * @return 生成されたプレイリスト名 (例: Mock Playlist 001)
     */
    private String generatePlaylistName(String playlistId) {
        return "Mock Playlist " + playlistId.substring(playlistId.length() - 3); // プレイリストIDの末尾3桁を名前に使用
    }

    /**
     * プレフィックスとインデックスからユーザーIDを生成するヘルパーメソッド。
     *
     * @param prefix プレフィックス
     * @param index  インデックス
     * @return 生成されたユーザーID (例: prefix_user_id_1)
     */
    private String generateUserId(String prefix, int index) {
        return prefix + "_user_id_" + index;
    }

    /**
     * プレフィックスとインデックスからユーザー名を生成するヘルパーメソッド。
     *
     * @param prefix プレフィックス
     * @param index  インデックス
     * @return 生成されたユーザー名 (例: prefix User 1)
     */
    private String generateUserName(String prefix, int index) {
        return prefix + " User " + index;
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
        int totalPlaylists = 999; // モックデータの総プレイリスト数

        // モックプレイリストデータを生成
        for (int i = 1; i <= totalPlaylists; i++) {
            String playlistId = generatePlaylistId(i); // プレイリストIDを生成
            String playlistName = generatePlaylistName(playlistId); // プレイリスト名を生成
            int trackCount = Math.min(getLastThreeDigits(playlistId), 50); // トラック数をID末尾3桁と50の小さい方で決定

            Map<String, Object> playlist = new HashMap<>(); // 各プレイリストのMap
            playlist.put("id", playlistId);
            playlist.put("name", playlistName);
            playlist.put("description", "Search Playlist " + i + " Description");
            playlist.put("tracks", Map.of("total", trackCount));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + i + "/64/64"))); // ランダムな画像URL
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/" + playlistId)));
            playlist.put("owner", Map.of("displayName", generateUserName("User", i)));
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

        int trackCount = Math.min(getLastThreeDigits(playlistId), 50); // トラック数をID末尾3桁と50の小さい方で決定
        String playlistName = generatePlaylistName(playlistId); // プレイリスト名を生成

        Map<String, Object> playlistDetails = new HashMap<>(); // プレイリスト詳細のMap
        playlistDetails.put("playlistName", playlistName);
        playlistDetails.put("owner", Map.of("id", generateUserId("detail_owner", getLastThreeDigits(playlistId)), "displayName", generateUserName("mockOwner", getLastThreeDigits(playlistId))));
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

        int numTracks = Math.min(getLastThreeDigits(playlistId), 50); // トラック数をID末尾3桁と50の小さい方で決定
        List<Map<String, Object>> playlistTracks = new ArrayList<>(); // プレイリストトラックリストを初期化

        // モックトラックデータを生成
        for (int i = 0; i < numTracks; i++) {
            Map<String, Object> track = new HashMap<>(); // 各トラックのMap

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

            playlistTracks.add(track); // 生成したトラックをリストに追加
        }

        logger.info("Returning mock data for playlist tracks: {}", playlistTracks);
        return playlistTracks;
    }

    /**
     * 複数のアーティストIDからジャンルリストのモックデータを取得。
     *
     * @param artistIds アーティストIDのリスト
     * @return アーティストIDとジャンルリストのマップ (Map 形式)
     */
    public Map<String, List<String>> getArtistGenresMockData(List<String> artistIds) {
        logger.info("getArtistGenresMockData called with artistIds: {}", artistIds);

        Map<String, List<String>> artistGenres = new HashMap<>(); // アーティストIDとジャンルリストのマップを初期化
        List<String> availableGenres = Arrays.asList("Rock", "Pop", "Jazz", "Hip Hop", "Electronic", "Classical", "Country", "Blues", "Reggae", "Metal"); // 利用可能なジャンルリスト
        Random random = new Random(); // 乱数生成器

        // 各アーティストIDに対してジャンルを生成
        for (String artistId : artistIds) {
            List<String> genres = new ArrayList<>(); // ジャンルリストを初期化
            int numGenres = random.nextInt(3) + 1; // 1アーティストあたり1〜3個のジャンルをランダムに決定
            // ジャンルをランダムに選択してリストに追加 (重複なし)
            for (int i = 0; i < numGenres; i++) {
                String genre = availableGenres.get(random.nextInt(availableGenres.size())); // ランダムにジャンルを選択
                if (!genres.contains(genre)) { // 重複チェック
                    genres.add(genre); // ジャンルリストに追加
                }
            }
            artistGenres.put(artistId, genres); // アーティストIDとジャンルリストをマップに追加
        }

        logger.info("Returning mock data for artist genres: {}", artistGenres);
        return artistGenres;
    }

    /**
     * おすすめトラックリストのモックデータを取得。
     *
     * @return おすすめトラックリストのモックデータ (List 形式)
     */
    public List<Map<String, Object>> getRecommendationsMockData() {
        logger.info("getRecommendationsMockData called");

        List<Map<String, Object>> recommendations = new ArrayList<>(); // おすすめトラックリストを初期化
        // モックおすすめトラックデータを生成
        for (int i = 0; i < 5; i++) {
            Map<String, Object> track = new HashMap<>(); // 各トラックのMap
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
            recommendations.add(track); // 生成したおすすめトラックをリストに追加
        }

        logger.info("Returning mock data for recommendations: {}", recommendations);
        return recommendations;
    }

    /**
     * 複数のトラックIDからAudioFeatures取得リクエストのモックデータを取得。
     *
     * @param trackIds トラックIDのリスト
     * @return トラックIDとAudioFeatures取得リクエストのリスト (List 形式)
     */
    public List<Map<String, Object>> getAudioFeaturesForTracksMockData(List<String> trackIds) {
        logger.info("getAudioFeaturesForTracksMockData called with trackIds: {}", trackIds);

        List<Map<String, Object>> audioFeaturesList = new ArrayList<>(); // AudioFeatures取得リクエストリストを初期化
        Random random = new Random(); // 乱数生成器

        // 各トラックIDに対してAudioFeatures取得リクエストを生成
        for (String trackId : trackIds) {
            Map<String, Object> audioFeatures = new HashMap<>(); // 各AudioFeatures取得リクエストのMap
            audioFeatures.put("acousticness", random.nextDouble());
            audioFeatures.put("danceability", random.nextDouble());
            audioFeatures.put("energy", random.nextDouble());
            audioFeatures.put("instrumentalness", random.nextDouble());
            audioFeatures.put("liveness", random.nextDouble());
            audioFeatures.put("loudness", -60.0 + random.nextDouble() * 60.0); // ラウドネスは-60.0〜0.0の範囲を想定
            audioFeatures.put("mode", random.nextInt(2)); // 0 または 1
            audioFeatures.put("speechiness", random.nextDouble());
            audioFeatures.put("tempo", 50.0 + random.nextDouble() * 150.0); // テンポは50〜200の範囲を想定
            audioFeatures.put("timeSignature", random.nextInt(5) + 1); // 拍子記号は1〜5の範囲を想定
            audioFeatures.put("valence", random.nextDouble());
            audioFeatures.put("key", random.nextInt(12)); // キーは0〜11の範囲を想定
            audioFeatures.put("durationMs", 100000 + random.nextInt(200000)); // 再生時間は100000〜300000msの範囲を想定
            audioFeatures.put("id", "audio_features_" + trackId);
            audioFeaturesList.add(audioFeatures); // 生成したAudioFeatures取得リクエストをリストに追加
        }

        logger.info("Returning mock data for audio features for tracks: {}", audioFeaturesList);
        return audioFeaturesList;
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
        for (int i = 1; i <= 8; i++) {
            String playlistId = generatePlaylistId(i); // プレイリストIDを生成
            String playlistName = generatePlaylistName(playlistId); // プレイリスト名を生成
            int trackCount = Math.min(getLastThreeDigits(playlistId), 50); // トラック数をID末尾3桁と50の小さい方で決定

            Map<String, Object> playlist = new HashMap<>(); // 各プレイリストのMap
            playlist.put("id", playlistId);
            playlist.put("name", playlistName);
            playlist.put("tracks", Map.of("total", trackCount));
            playlist.put("images", List.of(Map.of("url", "https://picsum.photos/seed/" + i + "/64/64")));
            playlist.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/playlist/" + playlistId)));

            // オーナー情報の生成
            Map<String, Object> owner = new HashMap<>();
            owner.put("displayName", generateUserName("Followed", i));
            owner.put("id", generateUserId("followed", i));
            owner.put("type", "USER");
            owner.put("externalUrls", Map.of("externalUrls", Map.of("spotify", "https://open.spotify.com/user/" + generateUserId("followed", i))));
            owner.put("href", "https://api.spotify.com/v1/users/" + generateUserId("followed", i));
            owner.put("uri", "spotify:user:" + generateUserId("followed", i));

            playlist.put("owner", owner); // プレイリストにオーナー情報を設定
            playlists.add(playlist); // 生成したプレイリストをリストに追加
        }

        logger.info("Returning mock data for followed playlists (using user_playlists.json): {}", playlists);
        return playlists;
    }
}
