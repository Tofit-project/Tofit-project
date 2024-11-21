<template>
    <div class="main-page">
      <section class="recommended-section">
        <h4>추천 운동 목록</h4>
        <div class="card-container">
          <div class="card" v-for="(video, index) in store.recomVideoList" :key="index">
            <img :src="video.thumbnail" alt="thumbnail" class="thumbnail" />
            <div class="card-content">
              <img :src="video.instructorImage" alt="instructor" class="instructor-img" />
              <div class="info">
                <h5 class="title">{{ video.title }}</h5>
                <p class="instructor">{{ video.instructor }}</p>
              </div>
            </div>
          </div>
        </div>
      </section>
  
      <section class="all-section">
        <h4>내 목표에 맞는 운동을 찾아보세요!</h4>
        <div class="search-filter">
          <input
            type="text"
            v-model="searchQuery"
            placeholder="운동 제목을 검색하세요"
            class="search-bar"
          />
          <button class="search-btn">검색</button>
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
            <img :src="video.thumbnail" alt="thumbnail" class="thumbnail" />
            <div class="card-content">
              <img :src="video.instructorImage" alt="instructor" class="instructor-img" />
              <div class="info">
                <h5 class="title">{{ video.title }}</h5>
                <p class="instructor">{{ video.instructor }}</p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue';
  import { useVideoStore } from '@/stores/video';
  
  const store = useVideoStore();
  const searchQuery = ref('');
  const activeFilter = ref('');
  const filters = ['다이어트', '근력강화', '체형교정', '스트레칭', '명상', '식단'];
  
  const allVideos = computed(() => store.allVideos);
  
  const filteredVideos = computed(() => {
    let videos = allVideos.value;
    if (activeFilter.value) {
      videos = videos.filter((video) => video.category === activeFilter.value);
    }
    if (searchQuery.value) {
      videos = videos.filter((video) =>
        video.title.toLowerCase().includes(searchQuery.value.toLowerCase())
      );
    }
    return videos;
  });
  
  const applyFilter = (filter) => {
    activeFilter.value = activeFilter.value === filter ? '' : filter;
  };
  
  onMounted(() => {
    store.getRecomVideoList();
    store.getAllVideoList();
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
  