package com.github.oosm032519.spotifymockapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * アーティスト関連のモックデータ生成サービス。
 */
@Service
public class MockArtistService {

    private static final Logger logger = LoggerFactory.getLogger(MockArtistService.class);

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
}
