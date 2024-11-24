import { defineStore } from "pinia";
import { ref } from "vue";
import axios, { AxiosHeaders } from "axios";
import router from "@/router";

const REST_API_URL = `http://localhost:8080/tofit/users`;

export const useUserStore = defineStore("user", () => {
  // 회원가입
  const userSignup = function (user) {
    const formData = new FormData();
    formData.append("userId", user.id);
    formData.append("password", user.password);
    formData.append("email", user.mail);
    formData.append("gender", user.gender);
    formData.append("birth", user.birth);
    formData.append("profileName", user.profileName);
    // formData.append('profileImg', user.profileImage);

    // 사진 등록했을때만 profileImg 추가
    if (user.profileImage) {
      formData.append("profileImg", user.profileImage);
    }

    axios
      .post(`${REST_API_URL}/signup`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        if (response.status === 201) {
          alert("회원가입이 성공적으로 완료되었습니다.");
          router.push({ name: "login" });
        } else {
          alert("회원가입에 실패했습니다. 다시 시도해주세요.");
        }
      })
      .catch((err) => {
        console.log(err);
        alert("다시 시도해주세요.");
      });
  };

  const loginUserId = ref(null);
  const loginUserProfileName = ref(null);
  const loginUserProfileImage = ref(null);

  // 로그인
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

        // 일단 프로필이미지를 세션에 저장...(배포할시 수정해야함!!)
        const profileImg = response.data["profileImg"];
        if (profileImg !== null) {
          sessionStorage.setItem("profile-img", profileImg);
        }
        alert("로그인 성공");
        router.push({ name: "videoList" });
      })
      .catch((err) => {
        console.log(err);
        alert("아이디/비밀번호가 틀렸습니다.");
      });
  };

  // 로그아웃
  const userLogout = function () {
    sessionStorage.removeItem("access-token");
    sessionStorage.removeItem("profile-img");
    loginUserId.value = null;
    loginUserProfileName.value = null;
    loginUserProfileImage.value = null;

    router.push({ name: "videoList" });
  };

  // 페이지 로드 시 로그인 상태 확인
  const token = sessionStorage.getItem("access-token");
  const checkLoginStatus = function () {
    if (token) {
      const payload = JSON.parse(atob(token.split(".")[1]));
      loginUserId.value = payload["userId"];
      loginUserProfileName.value = payload["profileName"];
    }
    // 프로필 이미지 URL 가져오기
    const profileImg = sessionStorage.getItem("profile-img");
    if (profileImg) {
      loginUserProfileImage.value = profileImg;
    }
  };

  return {
    userSignup,
    loginUserId,
    loginUserProfileName,
    userLogin,
    userLogout,
    checkLoginStatus,
    loginUserProfileImage,
    token,
  };
});
