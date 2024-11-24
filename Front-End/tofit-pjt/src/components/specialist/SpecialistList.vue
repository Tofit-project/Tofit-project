<template>
  <div class="specialist-page">
    <!-- 검색 영역 -->
    <div class="search-bar">
      <input
        type="text"
        v-model="searchInfo.keyWord"
        placeholder="강사명을 입력해주세요"
        @keyup.enter="searchSpecialistList"
      />
      <button @click="searchSpecialistList">검색</button>
    </div>

    <!-- 필터 버튼 영역 -->
    <div class="filter-buttons">
      <button
        v-for="(filter, index) in filters"
        :key="index"
        :class="{ active: activeFilter === filter }"
        @click="applyFilter(filter)"
      >
        {{ filter }}
      </button>
    </div>

    <!-- 전문가 리스트 -->
    <div class="specialist-list">
      <div
        class="specialist-card"
        v-for="(specialist, index) in filteredSpecialists"
        :key="index"
      >
        <RouterLink
          :to="`instructor/${specialist.specialistId}`"
          class="profile-section"
        >
          <img :src="specialist.profileImg" alt="Profile" class="profile-img" />
          <div class="specialist-info">
            <h4>{{ specialist.specialistId }}</h4>
            <p class="hashtags">
              <span>{{ specialist.hashtag }}</span>
            </p>
          </div>
        </RouterLink>

        <div class="video-list">
          <!-- 이전 버튼 -->
          <button class="prev-btn" @click="prevPage(specialist.specialistId)">
            &#60;
          </button>

          <!-- 영상 리스트 -->
          <div class="video-container">
            <RouterLink
              v-for="(video, idx) in paginatedVideos(specialist.specialistId)"
              :to="`/${video.videoId}`"
              :key="idx"
              class="video-item"
            >
              <img :src="video.thumbnail" alt="Video Thumbnail" />
              <p class="video-title">{{ truncatedTitle(video.title) }}</p>
            </RouterLink>
          </div>

          <!-- 다음 버튼 -->
          <button class="next-btn" @click="nextPage(specialist.specialistId)">
            &#62;
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useSpecialistStore } from "@/stores/specialist";

const store = useSpecialistStore();
const searchInfo = ref({ keyWord: "" });

const activeFilter = ref("");
const filters = [
  "다이어트",
  "근력강화",
  "체형교정",
  "스트레칭",
  "명상",
  "식단",
];

const videoPageIndex = ref({}); // 전문가별 현재 페이지 저장

// 전문가 필터링
const applyFilter = (filter) => {
  activeFilter.value = activeFilter.value === filter ? "" : filter; // 동일 필터 클릭 시 해제
};

// 필터링된 전문가 리스트 계산
const filteredSpecialists = computed(() => {
  let specialists = store.specialistList;

  // 필터링
  if (activeFilter.value) {
    specialists = specialists.filter((specialist) =>
      specialist.goal.includes(activeFilter.value)
    );
  }

  return specialists;
});

// 전문가의 비디오 목록 가져오기
const getVideosBySpecialist = (specialistId) => {
  return store.specialistVideoList.filter(
    (video) => video.channelName === specialistId
  );
};

// 검색 실행
const searchSpecialistList = () => {
  store.getSpecialistList(searchInfo.value); // DB에서 검색 실행
};

// 현재 페이지의 영상만 가져오기
const paginatedVideos = (specialistId) => {
  const videos = getVideosBySpecialist(specialistId);
  const page = videoPageIndex.value[specialistId] || 0;
  const videosPerPage = 5; // 한 페이지에 표시할 영상 수
  return videos.slice(page * videosPerPage, (page + 1) * videosPerPage);
};

// 이전 페이지로 이동
const prevPage = (specialistId) => {
  const page = videoPageIndex.value[specialistId] || 0;
  if (page > 0) {
    videoPageIndex.value[specialistId] = page - 1;
  }
};

// 다음 페이지로 이동
const nextPage = (specialistId) => {
  const videos = getVideosBySpecialist(specialistId);
  const page = videoPageIndex.value[specialistId] || 0;
  const videosPerPage = 4; // 한 페이지에 표시할 영상 수
  if ((page + 1) * videosPerPage < videos.length) {
    videoPageIndex.value[specialistId] = page + 1;
  }
};

// 영상 제목 잘림 처리
const truncatedTitle = (title) => {
  const maxLength = 10; // 최대 표시 글자 수
  return title.length > maxLength ? title.slice(0, maxLength) + "..." : title;
};

// 초기 데이터 로드
onMounted(() => {
  store.getSpecialistList(searchInfo.value);
  store.getSpecialistVideoList();
});
</script>

<style scoped>
/* 전체 페이지 */
.specialist-page {
  padding: 1rem;
  font-family: Arial, sans-serif;
}

/* 검색 영역 */
.search-bar {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.search-bar input {
  flex: 1;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-bar button {
  padding: 0.5rem 1rem;
  background-color: #ff848f;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-bar button:hover {
  background-color: #e63946;
}

/* 필터 버튼 영역 */
.filter-buttons {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
  margin-bottom: 1rem;
}

.filter-buttons button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 20px;
  background-color: #ffebf1;
  color: #555;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.filter-buttons button.active {
  background-color: #ff848f;
  color: white;
}

.filter-buttons button:hover {
  transform: scale(1.05);
  background-color: #ffb3c1;
}

/* 전문가 리스트 */
.specialist-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 전문가 카드 */
.specialist-card {
  display: flex;
  flex-direction: column;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 1rem;
  gap: 1rem;
  transition: background-color 0.3s ease; /* 카드 배경 전환 */
}

.profile-section {
  display: flex;
  align-items: center;
  text-decoration: none;
  gap: 1rem;
}

.profile-img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
}

.specialist-info {
  flex: 1;
}

.specialist-info h4 {
  margin: 0;
  font-size: 1.2rem;
  color: #000;
}

.specialist-info .hashtags {
  color: #555;
  margin: 0.5rem 0;
}

/* 영상 리스트 */
.video-list {
  display: flex;
  align-items: center;
  gap: 2rem;
}

.video-container {
  display: flex;
  gap: 1rem;
  overflow: hidden;
}

.video-item {
  text-align: center; /* 제목과 이미지를 정렬 */
  transition: transform 0.3s ease, font-size 0.3s ease;
}

.video-item img {
  width: 200px;
  height: 120px;
  border-radius: 8px;
  object-fit: cover;
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 이미지 크기 전환 효과 */
}

.video-item:hover img {
  transform: scale(1.05);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 살짝 그림자 추가 */
}

.video-item .video-title {
  margin-top: 0.5rem;
  font-size: 0.9rem;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 150px; /* 제목 길이 제한 */
  transition: font-size 0.3s ease; /* 제목 크기 전환 효과 */
  text-decoration: none;
}
.video-item:hover .video-title {
  font-size: 1rem; /* 호버 시 제목 크기 변경 */
}

/* 페이지네이션 버튼 */
.prev-btn,
.next-btn {
  top: 50%;
  transform: translateY(-50%);
  background: #ff848f;
  color: white;
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  padding: 0.5rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.prev-btn:hover,
.next-btn:hover {
  background-color: #e63946;
}
</style>
