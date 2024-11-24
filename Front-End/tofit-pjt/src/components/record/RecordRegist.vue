<template>
  <div>
    <h2 class="calendar-title">찜한 동영상 캘린더</h2>
    <FullCalendar :options="calendarOptions" />

    <!-- 찜한 동영상 목록 -->
    <div class="favorites-list">
      <h3>찜한 동영상</h3>
      <div v-if="favoriteList.length === 0">찜한 동영상이 없습니다.</div>
      <button
        v-else
        v-for="video in favoriteList"
        :key="video.videoId"
        class="favorite-button"
        @click="addVideoToToday(video)"
      >
        {{ video.title }}
      </button>
    </div>
  </div>
</template>

<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useFavoriteStore } from "@/stores/favorite";
import { useRecordStore } from "@/stores/record";

export default {
  components: {
    FullCalendar,
  },
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: "dayGridMonth",
        dateClick: this.handleDateClick,
        events: [],
      },
    };
  },
  computed: {
    favoriteList() {
      // `favoriteList`를 Pinia store에서 가져오기
      const favStore = useFavoriteStore();
      return favStore.favoriteList || [];
    },
    recordList() {
      const recordStore = useRecordStore();
      return recordStore.recordList || [];
    },
  },
  methods: {
    formatDate(timestamp) {
      const date = new Date(timestamp);
      const year = date.getUTCFullYear();
      const month = String(date.getUTCMonth() + 1).padStart(2, "0");
      const day = String(date.getUTCDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
    async loadFavorites() {
      const favStore = useFavoriteStore();
      await favStore.getFavoriteList();
    },
    async loadRecords() {
      const recordStore = useRecordStore();
      await recordStore.getRecordList();

      // 이벤트를 Calendar에 반영
      // 여기서부터는 record를 반영하도록 설정
      this.calendarOptions.events = this.recordList.map((rec) => ({
        title: rec.title,
        date: this.formatDate(rec.regDate),
      }));
    },
    handleDateClick(arg) {
      const clickedDate = arg.dateStr;
      const eventsOnDate = this.calendarOptions.events.filter(
        (event) => event.date === clickedDate
      );

      if (eventsOnDate.length > 0) {
        alert(
          `찜한 동영상 목록 (${clickedDate}):\n${eventsOnDate
            .map((e) => e.title)
            .join("\n")}`
        );
      } else {
        alert(`선택한 날짜(${clickedDate})에는 찜한 동영상이 없습니다.`);
      }
    },
    addVideoToToday(video) {
      const today = new Date();
      const formattedToday = `${today.getFullYear()}-${String(
        today.getMonth() + 1
      ).padStart(2, "0")}-${String(today.getDate()).padStart(2, "0")}`;
      const recordStore = useRecordStore();

      // db 저장
      recordStore.addRecord(video.videoId);

      this.calendarOptions.events.push({
        title: video.videoId,
        date: formattedToday,
      });

      this.calendarOptions = { ...this.calendarOptions }; // Reactivity 보장
    },
  },
  mounted() {
    this.loadFavorites();
    this.loadRecords();
  },
};
</script>

<style scoped>
/* 동일 스타일 유지 */
</style>
