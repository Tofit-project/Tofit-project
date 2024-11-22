<template>
    <div>
        <h4>강의 리뷰</h4>
            <!-- 댓글 리스트 출력 -->
    <div v-for="(review, index) in store.videoReviewList" :key="review.id" class="review-card">
      <div class="review-header">
        <img :src="review.profileImg" alt="Author Image" class="author-img" />
        <div class="review-author">
          <strong>{{ review.userId }}</strong>
        </div>
      </div>
      <p class="review-content">{{ review.content }}</p>
    </div>

    <!-- 댓글 작성 폼 -->
    <div class="comment-form">
      <textarea v-model="newComment.content" placeholder="댓글을 작성하세요..." rows="3"></textarea>
      <button @click="submitComment">등록</button>

    </div>
    </div>
</template>

<script setup>
import { useReviewStore } from '@/stores/review';
import { ref, onMounted, watch, nextTick } from 'vue';
import { useRoute } from 'vue-router';

const store = useReviewStore();
const route = useRoute();

const newComment = ref({
    userId: 'user6',
    videoId: route.params.videoId,
    content: '',
}); // 새 댓글 내용


onMounted(()=>{
    store.getVideoReviewList(route.params.videoId)
});

// 댓글 작성
const submitComment = () => {
  if (newComment.value.content.trim() !== '') {
    store.addReview(newComment.value);
    newComment.value.content = '';
  }
};

</script>

<style scoped>
.review-card {
  background: #f9f9f9;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.review-header {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.author-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.review-author {
  font-weight: bold;
}

.review-content {
  font-size: 1rem;
  line-height: 1.5;
}

.loading-spinner {
  text-align: center;
  margin: 1rem 0;
}

.load-more-trigger {
  height: 1px;
}

.comment-form {
  margin-top: 2rem;
  display: flex;
  flex-direction: column;
}

.comment-form textarea {
  padding: 1rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-bottom: 1rem;
}

.comment-form button {
  align-self: flex-end;
  padding: 0.5rem 1rem;
  background-color: #ff848f;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comment-form button:hover {
  background-color: #e63946; /* 더 짙은 색상 */
}
</style>