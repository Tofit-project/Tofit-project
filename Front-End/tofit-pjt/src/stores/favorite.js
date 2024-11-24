import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useUserStore } from "./user";

const REST_API_URL = `http://localhost:8080/tofit/video`;

export const useFavoriteStore = defineStore("favorite", () => {
  const userStore = useUserStore();

  // 찜 상태 조회
  const favoriteInfo = ref(false);
  const getFavoriteInfo = function (videoId) {
    axios({
      url: `${REST_API_URL}/${videoId}/favorite`,
      method: "GET",
      headers: {
        Authorization: `Bearer ${userStore.token}`,
      },
    }).then((response) => {
      favoriteInfo.value = response.data;
    });
  };

  // 찜 등록
  const createFavorite = function (videoId) {
    axios({
      url: `${REST_API_URL}/${videoId}/favorite`,
      method: "POST",
      headers: {
        Authorization: `Bearer ${userStore.token}`,
      },
    }).then((response) => {
      favoriteInfo.value = true;
    });
  };

  // 찜 해제
  const removeFavorite = function (videoId) {
    axios({
      url: `${REST_API_URL}/${videoId}/favorite`,
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${userStore.token}`,
      },
    }).then((response) => {
      favoriteInfo.value = false;
    });
  };

  return { getFavoriteInfo, favoriteInfo, createFavorite, removeFavorite };
});
