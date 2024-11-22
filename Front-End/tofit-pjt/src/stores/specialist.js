import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/tofit/specialist`

export const useSpecialistStore = defineStore('specialist', () => {

  // 전문가 조회
  const specialistList = ref([]);
  const getSpecialistList = function(condition){
    axios({
      url : REST_API_URL,
      method: 'GET',
      params : condition
    })
    .then((response)=>{
      specialistList.value = response.data;
      console.log(specialistList.value)
    })
  };



  return { specialistList, getSpecialistList }
})
