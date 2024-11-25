<template>
  <div class="container">
    <div v-if="feedStore.feedAllList.length > 0" class="feed-list">
      <div
        v-for="(feed, index) in feedStore.feedAllList"
        :key="index"
        class="feed-card"
        @click="openModal(feed)"
      >
        <div class="feed-header">
          <!-- 프로필 이미지 클릭 시 해당 사용자 ID로 이동 -->
          <router-link :to="'/feed/' + feed.feed.userId">
            <img
              :src="feed.profileImg"
              alt="Profile Image"
              class="profile-img"
            />
          </router-link>
          <p class="profile-name">{{ feed.profileName }}</p>
        </div>
        <div class="feed-content">
          <p>{{ feed.feed.content }}</p>
        </div>
        <div
          v-if="feed.images && feed.images.length > 0"
          class="feed-image-container"
        >
          <img :src="feed.images[0].img" alt="피드 이미지" />
        </div>
        <div class="feed-footer">
          <p class="feed-date">{{ formatTimestamp(feed.feed.regDate) }}</p>
        </div>
      </div>
    </div>
    <p v-else>작성된 피드가 없습니다.</p>

    <!-- 모달 -->
    <div v-if="selectedFeed" class="modal-background">
      <div class="modal-content">
        <button class="close-modal" @click="closeModal">x</button>

        <img
          :src="selectedFeed.profileImg"
          alt="Profile Image"
          class="profile-img"
        />
        <p class="profile-name">{{ selectedFeed.profileName }}</p>

        <div v-if="selectedFeed.images.length > 0" class="modal-images">
          <button class="arrow left" @click="prevImage"><</button>
          <div class="modal-image">
            <img
              :src="selectedFeed.images[currentImageIndex].img"
              alt="피드 이미지!"
              style="border-radius: 5px"
            />
          </div>
          <button class="arrow right" @click="nextImage">></button>
        </div>

        <!-- 피드 내용 -->
        <p class="modal-feed-content">{{ selectedFeed.feed.content }}</p>

        <div class="modal-actions">
          <button class="modal-button edit-button">수정</button>
          <button
            class="modal-button delete-button"
            @click="deleteFeed(selectedFeed.feed.feedId)"
          >
            삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useFeedStore } from "@/stores/feed";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const feedStore = useFeedStore();
const selectedFeed = ref(null);
const currentImageIndex = ref(0);

onMounted(() => {
  feedStore.getFeedList();
  userStore.checkLoginStatus();
});

const openModal = (feed) => {
  selectedFeed.value = feed;
};

const closeModal = () => {
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

const deleteFeed = (feedId) => {
  feedStore.feedDelete(feedId);
  closeModal();
};

const formatTimestamp = (timestamp) => {
  const date = new Date(timestamp);
  return date.toLocaleString("ko-KR", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  });
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.container {
  padding: 20px;
  display: flex;
  width: 47%;
  flex-direction: column;
  align-items: center;
  background-color: rgb(163, 173, 173);
}

.feed-list {
  display: flex;
  flex-direction: column;
  gap: 25px;
  align-items: center;
}

.feed-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  padding: 10px;
  cursor: pointer;
}

.feed-header {
  display: flex;
  align-items: center;
  gap: 10px;
}

.profile-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.feed-image-container {
  width: 100%; /* 카드의 너비를 따라감 */
  overflow: hidden; /* 이미지를 자름 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.feed-image-container img {
  max-width: 100%; /* 컨테이너 너비에 맞게 이미지 크기 조정 */
  max-height: 100%; /* 컨테이너 높이에 맞게 이미지 크기 조정 */
  object-fit: cover; /* 비율 유지하며 채움 */
  border-radius: 5px; /* 이미지 모서리 둥글게 */
}

.feed-content {
  margin-top: 10px;
}

.feed-footer {
  margin-top: 10px;
  text-align: right;
}

.feed-date {
  color: #777;
  font-size: 12px;
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
  width: 600px;
  height: 700px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

/* 닫기 버튼 */
.close-modal {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #f26465;
  color: white;
  border: none;
  width: 40px;
  height: 40px;
  font-size: 20px;
  font-weight: bold;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
  display: flex;
  justify-content: center;
  align-items: center;
}

.close-modal:hover {
  background-color: #d45051;
}

/* 이미지 슬라이드 */
.modal-images {
  position: relative;
  text-align: center;
  margin-bottom: 20px;
  height: 300px; /* 이미지 영역 크기 축소 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-image img {
  max-width: 80%;
  max-height: 80%;
  object-fit: contain;
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
  font-size: 16px;
  line-height: 1.5;
  font-weight: bold;
  max-height: 200px;
  overflow-y: auto;
  margin-top: 10px;
}

/* 수정/삭제 버튼 */
.modal-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.modal-button {
  padding: 10px 20px;
  margin-left: 10px;
  font-size: 14px;
  cursor: pointer;
  border: none;
  border-radius: 5px;
}

.edit-button {
  background-color: #4caf50;
  color: white;
}

.delete-button {
  background-color: #f44336;
  color: white;
}
</style>
