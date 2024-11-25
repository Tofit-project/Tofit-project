<template>
  <div class="feed-create-container">
    <div class="feed-create-box">
      <div class="content-wrapper">
        <textarea
          v-model="feed.content"
          placeholder="내용을 입력하세요."
          class="content-input"
          rows="12"
        ></textarea>
      </div>

      <div class="image-upload">
        <input type="file" @change="previewImage" multiple />
        <div class="image-preview">
          <img
            v-for="(image, index) in previewImages"
            :key="index"
            :src="image"
            alt="Preview Image"
          />
        </div>
      </div>

      <button class="submit-button" @click="regist">게시하기</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useFeedStore } from "@/stores/feed";
import { useUserStore } from "@/stores/user";

const feedStore = useFeedStore();
const feed = ref({
  content: "",
  images: [],
});

const userStore = useUserStore();
onMounted(()=>{
  userStore.checkLoginStatus();
})

const previewImages = ref([]);

const previewImage = (event) => {
  const files = event.target.files;

  // 기존꺼 초기화
  previewImages.value = [];
  feed.value.images = [];

  for (let i = 0; i < files.length; i++) {
    const reader = new FileReader();
    reader.onload = (e) => {
      previewImages.value.push(e.target.result);
      feed.value.images.push(files[i]);
    };
    reader.readAsDataURL(files[i]); // 파일을 DataURL로 읽어서 미리보기
  }
};

const regist = function () {
  if (feed.value.content) {
    feedStore.writeFeed(feed.value);
  } else {
    alert("내용을 작성해주세요.");
  }
};
</script>

<style scoped>
.feed-create-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.feed-create-box {
  width: 500px;
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.content-wrapper {
  background-color: #f5f5f5;
  padding: 15px;
  border-radius: 10px;
  margin-bottom: 20px;
}

.content-input {
  width: 100%;
  padding: 12px;
  border-radius: 10px;
  border: 1px solid #dfdddd;
  font-size: 16px;
  resize: none;
  box-sizing: border-box;
  background-color: transparent;
  transition: border-color 0.3s;
}

.content-input:focus {
  outline: none;
  border-color: none;
}

/* 이미지 첨부 및 미리보기 영역 */
.image-upload {
  margin-bottom: 20px;
  text-align: left;
}

.image-upload input {
  width: 100%;
  padding: 10px;
  border-radius: 10px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  margin-bottom: 10px;
}

.image-preview {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-preview img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 10px;
}

.submit-button {
  background-color: #f26465;
  color: white;
  padding: 10px 20px;
  border-radius: 20px;
  border: none;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #e84a5f;
}
</style>
