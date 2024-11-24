import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useUserStore } from "./user";

const REST_API_URL = `http://localhost:8080/tofit/video`;

export const useRecordStore = defineStore("record", () => {
  const userStore = useUserStore();

  // 아래 수정해야 됨!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  // 찜 목록 조회
  const recordList = ref([]);
  const getRecordList = async function () {
    try {
      const response = await axios({
        url: `${REST_API_URL}/favoriteList`,
        method: "GET",
        headers: {
          Authorization: `Bearer ${userStore.token}`,
        },
      });

      favoriteList.value = response.data;
      return response.data;
    } catch (error) {
      return [];
    }
  };

  // 찜 등록
  const addRecord = function (videoId) {
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

  return {};
});
