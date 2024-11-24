<template>
  <div>
    <h4 class="review-title">강의 리뷰</h4>
    <!-- 댓글 작성 폼 -->
    <div class="comment-form">
      <div class="textarea-wrapper">
        <textarea
          v-model="newComment.content"
          placeholder="댓글을 작성하세요..."
          rows="3"
          @keyup.enter="submitComment"
        ></textarea>
        <button @click="submitComment" class="submit-btn">등록</button>
      </div>
    </div>
    <!-- 댓글 리스트 출력 -->
    <div
      v-for="(review, index) in store.videoReviewList"
      :key="review.reviewId"
      class="review-card"
    >
      <div class="review-header">
        <div v-if="review.profileImg !== null">
          <img
            :src="review.profileImg"
            alt="Author Image"
            class="profile-img"
          />
        </div>
        <div v-else>
          <img src="/images/default_profile.png" class="profile-img" />
        </div>
        <div class="review-author">
          <strong>{{ review.profileName }}</strong>
          <span class="review-date"> {{ formatDate(review.regDate) }}</span>
        </div>
      </div>

      <!-- 수정 모드 -->
      <div v-if="editingId === review.reviewId">
        <textarea
          v-model="editingContent"
          rows="3"
          class="edit-textarea"
        ></textarea>
        <div class="edit-actions" @keyup.enter="confirmEdit(review.reviewId)">
          <button class="edit-submit" @click="confirmEdit(review.reviewId)">
            등록
          </button>
          <button class="edit-cancel" @click="cancelEdit">취소</button>
        </div>
      </div>

      <!-- 기본 상태 -->
      <div v-else>
        <p class="review-content">{{ review.content }}</p>
        <div
          v-if="review.userId === userStore.loginUserId"
          class="comment-actions"
        >
          <button @click="startEditing(review.reviewId, review.content)">
            수정
          </button>
          <button class="delete" @click="deleteComment(review)">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useReviewStore } from "@/stores/review";
import { useUserStore } from "@/stores/user";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

const store = useReviewStore();
const route = useRoute();
const userStore = useUserStore();

const newComment = ref({
  videoId: route.params.videoId,
  content: "",
}); // 새 댓글 내용

// 댓글 수정 상태 관리
const editingId = ref(null); // 현재 수정 중인 댓글 ID
const editingContent = ref(""); // 수정 중인 댓글 내용

onMounted(() => {
  store.getVideoReviewList(route.params.videoId);
});

// 댓글 작성
const submitComment = () => {
  if (newComment.value.content.trim() !== "") {
    store.addReview(newComment.value);
    newComment.value.content = "";
  }
};

// 댓글 삭제
const deleteComment = function (review) {
  store.removeReview(review);
};

// 댓글 수정 모드 시작
const startEditing = (reviewId, content) => {
  editingId.value = reviewId;
  editingContent.value = content;
};

// 댓글 수정 등록
const confirmEdit = (reviewId) => {
  store.updateReview({
    reviewId,
    videoId: route.params.videoId,
    content: editingContent.value,
  });

  editingId.value = null; // 수정 모드 종료
  editingContent.value = "";
};

// 댓글 수정 취소
const cancelEdit = () => {
  editingId.value = null; // 수정 모드 종료
  editingContent.value = "";
};

// 날짜 형식 변환 함수
const formatDate = (date) => {
  const formattedDate = new Date(date);
  const year = formattedDate.getFullYear();
  const month = String(formattedDate.getMonth() + 1).padStart(2, "0");
  const day = String(formattedDate.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};
</script>

<style scoped>
/* 공통 */
.review-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
  text-align: center;
}

textarea {
  width: 100%;
  font-size: 1rem;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  outline: none;
  resize: none;
  transition: border-color 0.3s ease;
}

textarea:focus {
  border-color: #ff848f;
  box-shadow: 0 0 4px rgba(255, 132, 143, 0.4);
}

/* 댓글 작성 폼 */
.comment-form {
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.textarea-wrapper {
  position: relative;
  width: 100%;
}

.submit-btn {
  position: absolute;
  bottom: 10px;
  right: 10px;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  color: white;
  background-color: #ff848f;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.submit-btn:hover {
  background-color: #e63946;
}

/* 댓글 카드 */
.review-card {
  background: #ffffff;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: relative;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.review-header {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.review-author {
  font-weight: bold;
  font-size: 1.1rem;
  color: #555;
}

.review-author .review-date {
  margin-left: 10px;
  font-size: 0.9rem;
  color: #aaa;
}

/* 수정 중일 때 */
.edit-textarea {
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-bottom: 1rem;
}

.edit-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

/* 수정 버튼 색상 */
.edit-submit,
.edit-cancel {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 8px;
  color: white;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.edit-submit {
  background-color: #ff848f;
}

.edit-submit:hover {
  background-color: #e63946;
}

.edit-cancel {
  background-color: #f9ccd4;
}

.edit-cancel:hover {
  background-color: #f5a9b8;
}

/* 댓글 액션 버튼 */
.comment-actions {
  display: flex;
  gap: 0.5rem;
  justify-content: flex-end;
}

.comment-actions button {
  padding: 0.4rem 1rem;
  background-color: #ff848f;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.comment-actions button:hover {
  background-color: #e63946;
}

.comment-actions .delete {
  background-color: #f9ccd4;
}

.comment-actions .delete:hover {
  background-color: #f5a9b8;
}
</style>
