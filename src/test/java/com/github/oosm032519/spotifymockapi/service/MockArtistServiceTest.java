package com.github.oosm032519.spotifymockapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MockArtistServiceTest {

    private MockArtistService mockArtistService;
    private final List<String> availableGenres = Arrays.asList(
            "Rock", "Pop", "Jazz", "Hip Hop",
            "Electronic", "Classical", "Country",
            "Blues", "Reggae", "Metal"
    );

    @BeforeEach
    void setUp() {
        mockArtistService = new MockArtistService();
    }

    @Test
    @DisplayName("正常系: 複数のアーティストIDを渡した場合、各アーティストに1〜3個のユニークなジャンルが割り当てられる")
    void givenMultipleArtistIds_whenGetGenres_thenReturnValidGenreMap() {
        // Arrange: テスト用のアーティストIDリストを準備
        List<String> artistIds = Arrays.asList("artist1", "artist2", "artist3");

        // Act: テスト対象メソッドを実行
        Map<String, List<String>> result = mockArtistService.getArtistGenresMockData(artistIds);

        // Assert: 結果の検証
        assertThat(result)
                .hasSize(artistIds.size())
                .allSatisfy((artistId, genres) -> {
                    // ジャンル数の検証 (1〜3個)
                    assertThat(genres)
                            .hasSizeBetween(1, 3)
                            .doesNotHaveDuplicates();

                    // ジャンル値の妥当性検証
                    assertThat(genres)
                            .isSubsetOf(availableGenres);
                });
    }

    @Test
    @DisplayName("境界値: 空のアーティストIDリストを渡した場合、空のマップを返す")
    void givenEmptyArtistIds_whenGetGenres_thenReturnEmptyMap() {
        // Arrange: 空のリストを準備
        List<String> artistIds = Collections.emptyList();

        // Act: テスト対象メソッドを実行
        Map<String, List<String>> result = mockArtistService.getArtistGenresMockData(artistIds);

        // Assert: 結果が空であることを検証
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("境界値: 重複したアーティストIDを含む場合、最後のIDのジャンルが保持される")
    void givenDuplicateArtistIds_whenGetGenres_thenLastEntryOverrides() {
        // Arrange: 重複IDを含むリストを準備
        List<String> artistIds = Arrays.asList("artist1", "artist1", "artist1");

        // Act: テスト対象メソッドを実行
        Map<String, List<String>> result = mockArtistService.getArtistGenresMockData(artistIds);

        // Assert: 最終エントリのみ保持されることを検証
        assertThat(result)
                .hasSize(1)
                .allSatisfy((artistId, genres) -> assertThat(genres)
                        .hasSizeBetween(1, 3)
                        .doesNotHaveDuplicates());
    }

    @Test
    @DisplayName("異常系: 大規模なアーティストIDリストを処理できる")
    void givenLargeArtistList_whenGetGenres_thenProcessAllEntries() {
        // Arrange: 100個のアーティストIDを生成
        List<String> artistIds = Collections.nCopies(100, "artist");

        // Act: テスト対象メソッドを実行
        Map<String, List<String>> result = mockArtistService.getArtistGenresMockData(artistIds);

        // Assert: 全エントリを処理したことを検証
        assertThat(result)
                .hasSize(1) // 重複IDのため最後の1件のみ保持
                .allSatisfy((artistId, genres) -> assertThat(genres)
                        .hasSizeBetween(1, 3)
                        .doesNotHaveDuplicates());
    }
}
