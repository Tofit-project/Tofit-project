import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import router from "@/router";

const REST_API_URL = `http://localhost:8080/tofit/feeds`;

export const useFeedStore = defineStore("feed", () => {
  // 피드 생성
  const writeFeed = function (feed) {
    // 보낼 데이터 세팅
    const token = sessionStorage.getItem("access-token");
    const formData = new FormData();
    formData.append("content", feed.content);
    feed.images.forEach((image) => {
      formData.append("files", image);
    });

    axios
      .post(`${REST_API_URL}/write`, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        if (response.status === 201) {
          alert("게시 완료");
          router.push({ name: "feedList" });
        } else {
          alert("게시 실패, 다시 시도해주세요.");
        }
      })
      .catch((err) => {
        alert("다시 시도해주세요.");
      });
  };

  // 특정인의 피드리스트 전체 조회
  const myFeedList = ref([]);
  const getMyFeedList = function () {
    const token = sessionStorage.getItem("access-token");

    axios
      .get(`${REST_API_URL}/user`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        console.log(res);
        myFeedList.value = res.data;
      })
      .catch(() => {
        alert("피드 리스트를 가져오는데 실패했습니다.");
      });
  };

  return {
    writeFeed,
    getMyFeedList,
    myFeedList,
  };
});
