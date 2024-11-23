<template>
  <div class="d-flex flex-column min-vh-100">
    <!-- Header에 margin-bottom을 크게 추가 -->
    <Header class="mb-5" />

    <div class="container d-flex flex-column flex-grow-1">
      <RouterView />
    </div>

    <!-- Footer에 margin-top을 크게 추가 -->
    <Footer class="mt-5" />
  </div>
</template>

<script setup>
import Header from "./components/common/Header.vue";
import Footer from "./components/common/Footer.vue";

import { onMounted } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

onMounted(() => {
  userStore.checkLoginStatus();
});
</script>

<style scoped></style>
