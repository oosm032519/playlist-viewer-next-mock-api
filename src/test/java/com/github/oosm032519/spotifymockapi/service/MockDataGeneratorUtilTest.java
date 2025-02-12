package com.github.oosm032519.spotifymockapi.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MockDataGeneratorUtilTest {

    @Test
    void testGetLastThreeDigits_validId() {
        // プレイリストIDの末尾3桁を取得できるかテスト
        int result = MockDataGeneratorUtil.getLastThreeDigits("mockPlaylistId123");
        assertThat(result).isEqualTo(123);
    }

    @Test
    void testGetLastThreeDigits_shortId() {
        // 3桁未満のIDで正しく動作するかテスト
        int result = MockDataGeneratorUtil.getLastThreeDigits("12");
        assertThat(result).isEqualTo(12);
    }

    @Test
    void testGetLastThreeDigits_invalidId() {
        // 無効なID（数字ではない場合）を処理できるかテスト
        int result = MockDataGeneratorUtil.getLastThreeDigits("mockPlaylistIdXYZ");
        assertThat(result).isEqualTo(0); // デフォルト値0が返ることを確認
    }

    @Test
    void testGeneratePlaylistId() {
        // プレイリストIDが正しい形式で生成されるかテスト
        String playlistId = MockDataGeneratorUtil.generatePlaylistId(5);
        assertThat(playlistId).isEqualTo("mockPlaylistId005");
    }

    @Test
    void testGeneratePlaylistName() {
        // プレイリスト名が正しい形式で生成されるかテスト
        String playlistName = MockDataGeneratorUtil.generatePlaylistName("mockPlaylistId987");
        assertThat(playlistName).isEqualTo("Mock Playlist 987");
    }

    @Test
    void testGenerateUserId() {
        // ユーザーIDが正しい形式で生成されるかテスト
        String userId = MockDataGeneratorUtil.generateUserId("userPrefix", 10);
        assertThat(userId).isEqualTo("userPrefix_user_id_10");
    }

    @Test
    void testGenerateUserName() {
        // ユーザー名が正しい形式で生成されるかテスト
        String userName = MockDataGeneratorUtil.generateUserName("UserPrefix", 3);
        assertThat(userName).isEqualTo("UserPrefix User 3");
    }
}
