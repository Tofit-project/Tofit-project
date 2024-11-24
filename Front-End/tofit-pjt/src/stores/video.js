import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useUserStore } from "./user";

const REST_API_URL = `http://localhost:8080/tofit/video`;

export const useVideoStore = defineStore("video", () => {
  const userStore = useUserStore();

  // 추천 영상 목록
  const recomVideoList = ref([]);
  const getRecomVideoList = function () {
    if (userStore.token !== null) {
      axios({
        url: `${REST_API_URL}/recom`,
        method: "GET",
        headers: {
          Authorization: `Bearer ${userStore.token}`,
        },
      }).then((response) => {
        recomVideoList.value = response.data;
      });
    }
  };

  // 전체 영상 목록 (검색어 기반)
  const videoList = ref([]);
  const searchVideoList = function (condition) {
    axios({
      url: REST_API_URL,
      method: "GET",
      params: condition,
    }).then((response) => {
      videoList.value = response.data;
    });
  };

  // 영상 상세보기
  const video = ref({});

  const getVideo = function (videoId) {
    axios.get(`${REST_API_URL}/${videoId}`).then((response) => {
      video.value = response.data;
    });
  };

  return {
    recomVideoList,
    getRecomVideoList,
    videoList,
    searchVideoList,
    video,
    getVideo,
  };
});
