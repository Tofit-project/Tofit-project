import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const REST_API_URL = `http://localhost:8080/tofit/specialist`;

export const useSpecialistStore = defineStore("specialist", () => {
  // 전문가 조회
  const specialistList = ref([]);
  const getSpecialistList = function (condition) {
    axios({
      url: REST_API_URL,
      method: "GET",
      params: condition,
    }).then((response) => {
      specialistList.value = response.data;
    });
  };

  // 전문가가 가진 비디오리스트 조회
  const specialistVideoList = ref([]);
  const getSpecialistVideoList = function () {
    axios({
      url: `${REST_API_URL}/video`,
      method: "GET",
    }).then((response) => {
      specialistVideoList.value = response.data;
    });
  };

  // 전문가 상세 조회
  const specialistContentList = ref([]);
  const getSpecialistContentList = function (specialistId) {
    axios({
      url: `${REST_API_URL}/${specialistId}`,
      method: "GET",
    }).then((response) => {
      specialistContentList.value = response.data;
    });
  };

  return {
    specialistList,
    getSpecialistList,
    specialistVideoList,
    getSpecialistVideoList,
    specialistContentList,
    getSpecialistContentList,
  };
});
