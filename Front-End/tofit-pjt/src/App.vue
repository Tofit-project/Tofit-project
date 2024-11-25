<template>
  <div class="d-flex flex-column min-vh-100">
    <Header class="mb-5" />

    <div class="container d-flex flex-column flex-grow-1">
      <RouterView />
    </div>

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
