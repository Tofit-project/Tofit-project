import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import router from "@/router";

const REST_API_URL = `http://localhost:8080/users`;

export const useUserStore = defineStore("user", () => {
  // 로그인
  const loginUserId = ref(null);
  const loginUserProfileName = ref(null);
  const userLogin = function (id, password) {
    axios
      .post(`${REST_API_URL}/login`, {
        userId: id,
        password: password,
      })
      .then((response) => {
        console.log(response.data["message"]);

        // 서버 응답에서 토큰 저장
        const token = response.data["access-token"];
        sessionStorage.setItem("access-token", token);

        // userId 추출
        const payload = JSON.parse(atob(token.split(".")[1]));
        loginUserId.value = payload["userId"];
        loginUserProfileName.value = payload["profileName"];

        router.push({ name: "home" });
      })
      .catch((err) => {
        console.log(err);
        alert("아이디/비밀번호가 틀렸습니다.");
      });
  };

  // 로그아웃
  const userLogout = function () {
    sessionStorage.removeItem("access-token");
    loginUserId.value = null;
    loginUserProfileName.value = null;

    router.push({ name: "home" });
  };

  // 페이지 로드 시 로그인 상태 확인
  const checkLoginStatus = function () {
    const token = sessionStorage.getItem("access-token");
    if (token) {
      const payload = JSON.parse(atob(token.split(".")[1]));
      loginUserId.value = payload["userId"];
      loginUserProfileName.value = payload["profileName"];
    }
  };

  return {
    loginUserId,
    loginUserProfileName,
    userLogin,
    userLogout,
    checkLoginStatus,
  };
});
