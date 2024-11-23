<template>
  <header>
    <nav class="header-nav">
      <div class="logo">
        <h1>
          <RouterLink :to="{ name: 'videoList' }" class="logo-link"
            >ToFit</RouterLink
          >
        </h1>
      </div>
      <ul class="nav-links">
        <li>
          <RouterLink :to="{ name: 'videoList' }">Excercise</RouterLink>
        </li>
        <li>
          <RouterLink :to="{ name: 'specialistList' }">Instructor</RouterLink>
        </li>
        <li><a href="#about">About</a></li>
        <li><a href="#contact">Contact</a></li>
      </ul>
      <div class="user-menu">
        <!-- 로그인 상태 -->
        <div v-if="userStore.loginUserProfileName">
          <span>{{ userStore.loginUserProfileName }}님 환영합니다.</span>
          <button @click="logout">로그아웃</button>
        </div>
        <!-- 로그인 상태가 아닐 때 -->
        <div v-else>
          <RouterLink :to="{ name: 'login' }">
            <button class="login-btn">로그인</button>
          </RouterLink>
          <button class="signup-btn">회원가입</button>
        </div>
      </div>
    </nav>
  </header>
</template>

<script setup>
import { onMounted } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

// onMounted(() => {
//   userStore.checkLoginStatus();
// });

const logout = function () {
  userStore.userLogout();
};
</script>

<style scoped>
.header-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ff848f;
  color: white;
  padding: 0 2rem;
  height: 80px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.logo h1 {
  font-size: 1.8rem;
  font-weight: bold;
}

/* RouterLink 기본 스타일 제거 */
.logo-link {
  text-decoration: none;
  /* 하이퍼링크 밑줄 제거 */
  color: inherit;
  /* 텍스트 색상 유지 */
}

.nav-links {
  display: flex;
  gap: 1.5rem;
  list-style: none;
  margin: 0;
  padding: 0;
}

.nav-links li a {
  text-decoration: none;
  color: white;
  font-size: 1rem;
  font-weight: 500;
  transition: color 0.3s ease, font-weight 0.3s ease;
}

.nav-links li a:hover {
  color: #ffebf1;
  font-weight: 700;
  /* 호버 시 글씨 두께 증가 */
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.login-btn,
.signup-btn {
  background-color: #ffd6dc;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  color: #5d1a24;
  font-weight: bold;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.login-btn:hover,
.signup-btn:hover {
  background-color: #ffb3c1;
  transform: scale(1.05);
}
</style>
