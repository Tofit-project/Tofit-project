import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/tofit/video`

export const useVideoStore = defineStore('video', () => {

  // 추천 영상 목록
  const recomVideoList = ref([]);
  const getRecomVideoList = function () {
    axios({
      url: REST_API_URL + '/recom/' + 'user1',
      method: 'GET',
    })
      .then((response) => {
        recomVideoList.value = response.data;
        console.log(recomVideoList.value);
      })
  };

  // 전체 영상 목록 (검색어 기반)
  const videoList = ref([]);
  const searchVideoList = function (condition) {
    axios({
      url: REST_API_URL,
      method: 'GET',
      params: condition
    })
      .then((response) => {
        videoList.value = response.data;
      })
  };


  // 영상 상세보기
  const video = ref({});

  const getVideo = function (videoId) {
    axios.get(`${REST_API_URL}/${videoId}`)
      .then((response) => {
        video.value = response.data;
      })
  };

  return { recomVideoList, getRecomVideoList, videoList, searchVideoList, video, getVideo }
})
