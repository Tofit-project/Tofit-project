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
          <RouterLink :to="{ name: 'videoList' }">Exercise</RouterLink>
        </li>
        <li>
          <RouterLink :to="{ name: 'specialistList' }">Instructor</RouterLink>
        </li>
        <li>
          <RouterLink :to="{ name: 'feedAllList' }">Community</RouterLink>
        </li>
        <li><RouterLink :to="{ name: 'calendar' }">Daily</RouterLink></li>
      </ul>
      <div class="user-menu">
        <!-- 로그인 상태 -->
        <div
          v-if="userStore.loginUserProfileName"
          class="user-info d-flex align-items-center gap-2"
        >
          <div
            class="profile-dropdown-container d-flex align-items-center gap-2"
            @mouseenter="openDropdown"
            @mouseleave="closeDropdown"
          >
            <div
              v-if="userStore.loginUserProfileImage"
              class="profile-image-container"
            >
              <img
                :src="userStore.loginUserProfileImage"
                class="profile-image"
              />
              <span> {{ userStore.loginUserProfileName }}</span>
            </div>
            <div v-else class="profile-image-container">
              <img src="/images/default_profile.png" class="profile-image" />
              <span> {{ userStore.loginUserProfileName }}</span>
            </div>
            <span class="dropdown-toggle"></span>
            <ul class="dropdown-menu" v-show="isDropdownOpen">
              <li class="dropdown-header">
                {{ userStore.loginUserProfileName }}님 환영합니다.
              </li>
              <li>
                <RouterLink :to="{ name: 'userDetail' }" class="dropdown-item"
                  ><span>마이페이지</span></RouterLink
                >
              </li>
              <li>
                <RouterLink :to="{ name: 'feedList' }" class="dropdown-item"
                  ><span>피드 관리</span></RouterLink
                >
              </li>
              <li><span class="dropdown-item">My 운동</span></li>
              <li>
                <button class="dropdown-item" @click="logout">로그아웃</button>
              </li>
            </ul>
          </div>
        </div>

        <!-- 로그인 상태가 아닐 때 -->
        <div v-else>
          <RouterLink :to="{ name: 'login' }">
            <button class="login-btn me-2">로그인</button>
          </RouterLink>
          <RouterLink :to="{ name: 'signup' }">
            <button class="signup-btn">회원가입</button>
          </RouterLink>
        </div>
      </div>
    </nav>
  </header>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const isDropdownOpen = ref(false);

onMounted(() => {
  userStore.checkLoginStatus();
});

const openDropdown = () => {
  isDropdownOpen.value = true;
};

const closeDropdown = () => {
  isDropdownOpen.value = false;
};

const logout = function () {
  userStore.userLogout();
};
</script>

<style scoped>
/* 공통 스타일 */
.header-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f26465;
  color: white;
  padding: 0 2rem;
  height: 80px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.logo h1 {
  font-size: 1.8rem;
  font-weight: bold;
}

.logo-link {
  text-decoration: none;
  color: inherit;
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
}

.user-menu {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 1rem; /* 프로필 이미지와 드롭다운 사이의 간격 */
}

.profile-dropdown-container {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.profile-image-container {
  display: flex;
  align-items: center;
}

.profile-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid white;
  margin-right: 5px;
}

/* 드롭다운 스타일 */
.dropdown {
  position: relative;
  cursor: pointer;
}

.dropdown-toggle {
  display: flex;
  align-items: center;
}

.dropdown-menu {
  position: absolute;
  top: 60px;
  right: 0;
  background-color: white;
  color: black;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: none; /* 기본적으로 숨기기 */
  z-index: 1000; /* 드롭다운 메뉴가 다른 요소 위로 오도록 */
}

.dropdown-menu .dropdown-item {
  font-size: 0.9rem;
  padding: 0.5rem 1rem;
  color: #333;
  cursor: pointer;
  text-decoration-line: none;
}

.dropdown-menu .dropdown-item:hover {
  background-color: #f8f9fa;
  color: #ff848f;
}

.dropdown-header {
  font-weight: bold;
  padding: 1rem;
  font-size: 1rem;
  color: #0a0a0a;
  border-bottom: 1px solid #ddd;
}

/* 드롭다운 hover 시 표시 */
.dropdown-menu {
  display: block;
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
