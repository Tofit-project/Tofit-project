<template>
  <div class="main-page">
    <section
      class="recommended-section"
      v-if="userStore.loginUserProfileName !== null"
    >
      <h4>{{ userStore.loginUserProfileName }}님을 위한 추천 운동</h4>
      <div class="card-container">
        <div
          class="card"
          v-for="(video, index) in store.recomVideoList"
          :key="index"
        >
          <RouterLink :to="`/${video.videoId}`" class="card-link">
            <img :src="video.thumbnail" alt="thumbnail" class="thumbnail" />
            <div class="card-content">
              <img
                :src="video.instructorImage"
                alt="profile"
                class="instructor-img"
              />
              <div class="info">
                <h5 class="title">{{ decode(video.title) }}</h5>
                <p class="instructor">{{ decode(video.channelName) }}</p>
              </div>
            </div>
          </RouterLink>
        </div>
      </div>
    </section>

    <section class="all-section">
      <h4>내 목표에 맞는 운동을 찾아보세요!</h4>
      <div class="search-filter">
        <input
          type="text"
          v-model="searchInfo.keyWord"
          placeholder="영상제목 또는 강사명을 입력해주세요"
          @keyup.enter="searchVideoList"
          class="search-bar"
        />
        <button class="search-btn" @click="searchVideoList">검색</button>
      </div>
      <div class="filter-buttons">
        <button
          v-for="(filter, index) in filters"
          :key="index"
          class="filter-btn"
          :class="{ active: activeFilter === filter }"
          @click="applyFilter(filter)"
        >
          {{ filter }}
        </button>
      </div>
      <div class="card-container">
        <div class="card" v-for="(video, index) in filteredVideos" :key="index">
          <RouterLink :to="`/${video.videoId}`" class="card-link">
            <img :src="video.thumbnail" alt="thumbnail" class="thumbnail" />
            <div class="card-content">
              <img
                :src="video.instructorImage"
                alt="instructor"
                class="instructor-img"
              />
              <div class="info">
                <h5 class="title">{{ decode(video.title) }}</h5>
                <p class="instructor">{{ decode(video.channelName) }}</p>
              </div>
            </div>
          </RouterLink>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useVideoStore } from "@/stores/video";
import { useUserStore } from "@/stores/user";

const store = useVideoStore();
const userStore = useUserStore();

const searchInfo = ref({
  keyWord: "",
});

const activeFilter = ref("");
const filters = [
  "다이어트",
  "근력강화",
  "체형교정",
  "스트레칭",
  "명상",
  "식단",
];

const searchVideoList = function () {
  store.searchVideoList(searchInfo.value);
};

const filteredVideos = computed(() => {
  let videos = store.videoList;

  if (activeFilter.value) {
    videos = videos.filter((video) => video.goal === activeFilter.value);
  }

  return videos;
});

// 필터 버튼 클릭 핸들러
const applyFilter = (filter) => {
  activeFilter.value = activeFilter.value === filter ? "" : filter; // 동일 필터 클릭 시 해제
};

// HTML 엔티티 변환 출력
const decode = function (encodedStr) {
  const doc = new DOMParser().parseFromString(encodedStr, "text/html");
  return doc.documentElement.textContent;
};

onMounted(() => {
  store.getRecomVideoList();
  searchVideoList();
  userStore.checkLoginStatus();
});
</script>

<style scoped>
.main-page {
  padding: 1rem;
}

.recommended-section,
.all-section {
  margin-bottom: 2rem;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: calc(33% - 1rem);
  overflow: hidden;
}

/* 카드 호버 효과 */
.card:hover {
  transform: scale(1.05); /* 확대 효과 */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* 더 강한 그림자 */
}

/* RouterLink 기본 스타일 제거 */
.card-link {
  text-decoration: none; /* 하이퍼링크 밑줄 제거 */
  color: inherit; /* 텍스트 색상 유지 */
}

.thumbnail {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.card-content {
  display: flex;
  padding: 1rem;
  align-items: center;
}

.instructor-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 1rem;
}

.info {
  display: flex;
  flex-direction: column;
}

.title {
  font-size: 1rem;
  font-weight: bold;
}

.instructor {
  font-size: 0.9rem;
  color: #555;
}

.search-filter {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.search-bar {
  flex: 1;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-btn {
  background-color: #ff848f;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.search-btn:hover {
  background-color: #e63946; /* 더 짙은 색상 */
}

.filter-buttons {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
  margin-bottom: 1rem;
}

.filter-btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 20px;
  background-color: #ffebf1;
  color: #555;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.filter-btn.active {
  background-color: #ff848f;
  color: white;
}

.filter-btn:hover {
  background-color: #ffb3c1;
}
</style>
