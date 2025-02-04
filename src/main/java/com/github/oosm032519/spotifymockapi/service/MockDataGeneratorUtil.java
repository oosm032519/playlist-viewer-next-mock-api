package com.github.oosm032519.spotifymockapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * モックデータ生成に関するユーティリティクラス。
 * 各サービスで共通利用する定数やヘルパーメソッドを提供する。
 */
public class MockDataGeneratorUtil {

    private static final Logger logger = LoggerFactory.getLogger(MockDataGeneratorUtil.class);

    public static final int DEFAULT_TOTAL_PLAYLISTS = 999;
    public static final int MAX_TRACKS_PER_PLAYLIST = 50;
    public static final int DEFAULT_FOLLOWED_PLAYLISTS_COUNT = 8;

    public static final String PLAYLIST_ID_PREFIX = "mockPlaylistId";
    public static final String PLAYLIST_NAME_PREFIX = "Mock Playlist ";
    public static final String USER_NAME_PREFIX = "User";
    public static final String DETAIL_OWNER_PREFIX = "detail_owner";
    public static final String MOCK_OWNER_PREFIX = "mockOwner";
    public static final String FOLLOWED_USER_PREFIX = "followed";
    public static final String RECOMMENDATION_TRACK_ID_PREFIX = "recommendation_track_id_";
    public static final String RECOMMENDATION_TRACK_NAME_PREFIX = "Recommendation Track ";
    public static final String RECOMMENDATION_ALBUM_NAME_PREFIX = "Recommendation Album ";
    public static final String RECOMMENDATION_ALBUM_ID_PREFIX = "recommendation_album_id_";
    public static final String RECOMMENDATION_ARTIST_NAME_PREFIX = "Recommendation Artist ";
    public static final String RECOMMENDATION_ARTIST_ID_PREFIX = "recommendation_artist_id_";
    public static final String AUDIO_FEATURES_ID_PREFIX = "audio_features_";


    /**
     * プレイリストIDの末尾3桁を数値として抽出するヘルパー関数。
     *
     * @param playlistId プレイリストID
     * @return 末尾3桁の数値 (NumberFormatException の場合は 0)
     */
    public static int getLastThreeDigits(String playlistId) {
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
    public static String generatePlaylistId(int index) {
        return String.format(PLAYLIST_ID_PREFIX + "%03d", index); // インデックスを3桁のゼロ埋め文字列としてフォーマット
    }

    /**
     * プレイリストIDからプレイリスト名を生成するヘルパーメソッド。
     *
     * @param playlistId プレイリストID
     * @return 生成されたプレイリスト名 (例: Mock Playlist 001)
     */
    public static String generatePlaylistName(String playlistId) {
        return PLAYLIST_NAME_PREFIX + playlistId.substring(playlistId.length() - 3); // プレイリストIDの末尾3桁を名前に使用
    }

    /**
     * プレフィックスとインデックスからユーザーIDを生成するヘルパーメソッド。
     *
     * @param prefix プレフィックス
     * @param index  インデックス
     * @return 生成されたユーザーID (例: prefix_user_id_1)
     */
    public static String generateUserId(String prefix, int index) {
        return prefix + "_user_id_" + index;
    }

    /**
     * プレフィックスとインデックスからユーザー名を生成するヘルパーメソッド。
     *
     * @param prefix プレフィックス
     * @param index  インデックス
     * @return 生成されたユーザー名 (例: prefix User 1)
     */
    public static String generateUserName(String prefix, int index) {
        return prefix + " User " + index;
    }
}
