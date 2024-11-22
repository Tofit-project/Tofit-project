import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/tofit/video`

export const useReviewStore = defineStore('review', () => {

  // 영상에 대한 리뷰 목록
  const videoReviewList = ref([]);
  const getVideoReviewList = function(videoId){
    axios({
      url: `${REST_API_URL}/${videoId}/review`,
      method: 'GET',
    })
    .then((response)=>{
      videoReviewList.value = response.data;
    })
  }

  // 영상에 대한 리뷰 등록
  const addReview = function(review){
    axios({
      url: `${REST_API_URL}/${review.videoId}/review`,
      method: 'POST',
      data: review
    })
    .then(()=>{
      // 목록 다시 불러오기
      getVideoReviewList(review.videoId);
    })
  }

  return { videoReviewList, getVideoReviewList, addReview }
})
