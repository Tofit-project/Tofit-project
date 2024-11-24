<template>
  <div>
    <h1>피드 관리 페이지</h1>
    <RouterLink :to="{ name: 'feedCreate' }">
      <button>작성</button>
    </RouterLink>

    <div>
      <h2>내 피드 리스트</h2>
      <ul>
        <li v-for="feed in feedStore.myFeedList" :key="feed.feed.feedId">
          <p><strong>내용:</strong> {{ feed.feed.content }}</p>
          <p><strong>이미지 수:</strong> {{ feed.images.length }}</p>
          <!-- 이미지가 있을 경우에만 표시 -->
          <div v-if="feed.images.length > 0">
            <img :src="feed.images[0].url" alt="피드 이미지" />
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useFeedStore } from "@/stores/feed";

const userStore = useUserStore();
const feedStore = useFeedStore();

onMounted(() => {
  userStore.checkLoginStatus();
  feedStore.getMyFeedList();
});
</script>

<style scoped></style>
