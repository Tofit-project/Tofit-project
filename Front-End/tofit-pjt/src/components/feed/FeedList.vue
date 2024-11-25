<template>
  <div class="feed-page">
    <header class="page-header">
      <h1 class="page-title">나의 피드 홈</h1>
      <p class="page-subtitle">피드를 작성하고 관리해보세요.</p>
    </header>
    <RouterLink :to="{ name: 'feedCreate' }">
      <button class="create-button">작성</button>
    </RouterLink>

    <div class="feed-list">
      <div
        v-for="feed in feedStore.myFeedList"
        :key="feed.feed.feedId"
        class="feed-card"
        @click="openFeedDetail(feed)"
      >
        <div class="feed-image" v-if="feed.images.length > 0">
          <img :src="feed.images[0].img" alt="피드 이미지" />
        </div>
        <div class="feed-image-placeholder" v-else></div>
        <div class="feed-content">
          <p class="feed-text">
            <span v-if="!feed.images || feed.images.length === 0">
              {{ feed.feed.content }}
            </span>
            <span v-else>
              {{
                feed.feed.content && feed.feed.content.length > 5
                  ? feed.feed.content.substring(0, 25) + "..."
                  : feed.feed.content
              }}
            </span>
          </p>
        </div>
      </div>
    </div>

    <!-- 모달 뷰!! -->
    <div v-if="selectedFeed" class="modal-background">
      <div class="modal-content">
        <!-- 닫기 버튼 (상단) -->
        <button class="close-modal" @click="closeFeedDetail">닫기</button>

        <!-- 이미지 슬라이드 -->
        <div class="modal-images">
          <button class="arrow left" @click="prevImage"><</button>
          <div class="modal-image">
            <img
              :src="selectedFeed.images[currentImageIndex].img"
              alt="피드 이미지"
            />
          </div>
          <button class="arrow right" @click="nextImage">></button>
        </div>

        <!-- 피드 내용 (모달 하단에 위치) -->
        <p class="modal-feed-content">{{ selectedFeed.feed.content }}</p>

        <!-- 수정 및 삭제 버튼 -->
        <div class="modal-actions">
          <button class="modal-button edit-button">수정</button>
          <button class="modal-button delete-button">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useFeedStore } from "@/stores/feed";

const userStore = useUserStore();
const feedStore = useFeedStore();
const selectedFeed = ref(null);
const currentImageIndex = ref(0);

onMounted(() => {
  feedStore.getMyFeedList();
  userStore.checkLoginStatus();
});

const openFeedDetail = (feed) => {
  selectedFeed.value = feed;
};

const closeFeedDetail = () => {
  selectedFeed.value = null;
  currentImageIndex.value = 0; // 모달을 닫을 때 이미지 인덱스 초기화
};

const prevImage = () => {
  currentImageIndex.value =
    (currentImageIndex.value - 1 + selectedFeed.value.images.length) %
    selectedFeed.value.images.length;
};

const nextImage = () => {
  currentImageIndex.value =
    (currentImageIndex.value + 1) % selectedFeed.value.images.length;
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.feed-page {
  font-family: Arial, sans-serif;
  padding: 20px;
  background-color: #fff;
}

/* 헤더 스타일 */
.page-header {
  text-align: center;
  margin-bottom: 30px;
  padding: 10px 0;
  border-bottom: 2px solid #f26465;
}
.page-title {
  color: #f26465; /* 메인 색상 */
  font-size: 28px;
  font-weight: bold;
  margin: 0;
}
.page-subtitle {
  font-size: 16px;
  color: #555;
  margin-top: 5px;
}

/* 작성 버튼 스타일 (오른쪽 정렬) */
.create-button {
  display: block;
  margin: 0 0 20px auto;
  padding: 10px 20px;
  font-size: 16px;
  background-color: #f26465; /* 메인 색상 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.create-button:hover {
  background-color: #d45051;
}

/* 피드 리스트 레이아웃 */
.feed-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.feed-card {
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.feed-image img {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 5px;
}

.feed-content {
  padding: 10px;
}

.feed-text {
  font-size: 14px;
  color: #333;
}

/* 모달 배경 */
.modal-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* 모달 콘텐츠 */
.modal-content {
  position: relative;
  background: white;
  padding: 20px;
  max-width: 800px;
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 닫기 버튼 */
.close-modal {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #f26465; /* 테마 색상 */
  color: white;
  border: none;
  font-size: 20px;
  font-weight: bold;
  padding: 8px;
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s;
}

.close-modal:hover {
  background-color: #d45051;
}

/* 이미지 슬라이드 */
.modal-images {
  position: relative;
  text-align: center;
  margin-top: 20px;
}

.modal-image img {
  width: 100%;
  height: auto;
  border-radius: 5px;
}

.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  border: none;
  padding: 10px;
  font-size: 24px;
  color: white;
  cursor: pointer;
  z-index: 1;
}

.arrow.left {
  left: 10px;
}

.arrow.right {
  right: 10px;
}

/* 피드 내용 */
.modal-feed-content {
  margin-top: 20px;
  font-size: 16px;
  line-height: 1.5;
  font-weight: bold;
}

/* 수정/삭제 버튼 스타일 */
.modal-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.modal-button {
  background-color: #f26465;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-left: 10px;
}

.modal-button:hover {
  background-color: #d45051;
}

.edit-button {
  background-color: #4a90e2;
}

.delete-button {
  background-color: #e74c3c;
}
</style>
