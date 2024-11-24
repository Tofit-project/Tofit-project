<template>
  <div>
    <div class="video-detail">
      <!-- 동영상 iframe -->
      <div class="video-container">
        <iframe
          :src="videoUrl"
          frameborder="0"
          allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
          class="video-frame"
        ></iframe>
      </div>

      <!-- 제목, 강사 이미지, 강사 채널명 -->
      <div class="video-info">
        <h5 class="video-title">
          {{ decode(store.video.title) }}
          <button @click="toggleFavorite" class="favorite-btn">
            <span class="heart-icon">{{
              favStore.favoriteInfo ? "❤️" : "🤍"
            }}</span>
          </button>
        </h5>
        <p>조회수 {{ store.video.viewCnt }}회</p>
        <div class="instructor-info">
          <img
            :src="store.video.profileImg"
            alt="instructor"
            class="instructor-img"
          />
          <p class="instructor-channel">
            {{ decode(store.video.channelName) }}
          </p>
        </div>
      </div>
    </div>
    <RouterView />
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import { useFavoriteStore } from "@/stores/favorite";
import { watch, computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const store = useVideoStore();
const favStore = useFavoriteStore();
const route = useRoute();

// 동영상 URL을 iframe에서 사용할 수 있는 형태로 변환
const videoUrl = computed(() => {
  return store.video.url ? store.video.url.replace("watch?v=", "embed/") : "";
});

// HTML 엔티티 변환 출력
const decode = function (encodedStr) {
  const doc = new DOMParser().parseFromString(encodedStr, "text/html");
  return doc.documentElement.textContent;
};

// 찜
const isFavorite = ref(favStore.favoriteInfo.value);
console.log(isFavorite.value);

const toggleFavorite = () => {
  if (!favStore.favoriteInfo) {
    // 찜 등록
    favStore.createFavorite(route.params.videoId);
  } else {
    // 찜 해제
    favStore.removeFavorite(route.params.videoId);
  }
};

onMounted(() => {
  store.getVideo(route.params.videoId);
  favStore.getFavoriteInfo(route.params.videoId);
});
</script>

<style scoped>
.video-detail {
  padding: 2rem;
  text-align: center;
}

.video-container {
  max-width: 800px;
  margin: 0 auto 2rem;
}

.video-frame {
  width: 100%;
  height: 450px; /* 높이는 필요에 따라 조정 가능 */
}

.video-info {
  text-align: left;
}

.video-title {
  /* font-size: 2rem; */
  font-weight: bold;
  margin-bottom: 1rem;
}

.favorite-btn {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  margin-left: 10px;
  font-size: 1.5rem;
  transition: color 0.3s ease;
}

.favorite-btn .heart-icon {
  transition: color 0.3s ease;
}

.favorite-btn:hover .heart-icon {
  transform: scale(1.3); /* hover 시 크기 증가 */
  font-size: 2rem; /* 크기를 더 크게 설정 */
}

.instructor-info {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-top: 1rem;
}

.instructor-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
}

.instructor-channel {
  font-size: 1rem;
  color: #555;
}
</style>
