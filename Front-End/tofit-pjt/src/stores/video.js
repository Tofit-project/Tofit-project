import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/tofit/video`

export const useVideoStore = defineStore('video', () => {
  
  // 추천 영상 목록
  const recomVideoList = ref([]);
  const getRecomVideoList = function(){
    axios({
      url: REST_API_URL+'/recom/'+'user2',
      method: 'GET',
      params: {"keyWord" : "땅"}
    })
    .then((response)=>{
      recomVideoList.value = response.data;
      console.log(recomVideoList.value);
    })
  };

  // 전체 영상 목록
  const videoList = ref([]);
  const getVideoList = function(){
    axios({
      url: REST_API_URL,
      method: 'GET',
    })
  }

  return { recomVideoList, getRecomVideoList, videoList, }
})
