<template>
  <div class="d-flex flex-column min-vh-100">
    <Header />
    <div class="flex-grow-1">
      <RouterView />
    </div>
    <Footer />
  </div>
</template>

<script setup>
import Header from "./components/common/Header.vue";
import Footer from "./components/common/Footer.vue";
</script>

<style scoped></style>
