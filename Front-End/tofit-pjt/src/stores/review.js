import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useUserStore } from "./user";

const REST_API_URL = `http://localhost:8080/tofit/video`;

export const useReviewStore = defineStore("review", () => {
  const userStore = useUserStore();

  // 영상에 대한 리뷰 목록
  const videoReviewList = ref([]);
  const getVideoReviewList = function (videoId) {
    axios({
      url: `${REST_API_URL}/${videoId}/review`,
      method: "GET",
    }).then((response) => {
      videoReviewList.value = response.data;
    });
  };

  // 영상에 대한 리뷰 등록
  const addReview = function (review) {
    axios({
      url: `${REST_API_URL}/${review.videoId}/review`,
      method: "POST",
      headers: {
        Authorization: `Bearer ${userStore.token}`,
      },
      data: review,
    }).then(() => {
      // 목록 다시 불러오기
      getVideoReviewList(review.videoId);
    });
  };

  // 영상에 대한 리뷰 수정
  const updateReview = function (review) {
    axios({
      url: `${REST_API_URL}/${review.videoId}/review/${review.reviewId}`,
      method: "PUT",
      data: review,
    }).then(() => {
      getVideoReviewList(review.videoId);
    });
  };

  // 영상에 대한 리뷰 삭제
  const removeReview = function (review) {
    axios({
      url: `${REST_API_URL}/${review.videoId}/review/${review.reviewId}`,
      method: "DELETE",
    }).then(() => {
      getVideoReviewList(review.videoId);
    });
  };

  return {
    videoReviewList,
    getVideoReviewList,
    addReview,
    removeReview,
    updateReview,
  };
});
