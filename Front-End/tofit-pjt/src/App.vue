<template>
  <div>
    <Header/>
    <RouterView />
    <Footer/>
  </div>
</template>

<script setup>
import Header from './components/common/Header.vue';
import Main from './components/common/Main.vue';
import Footer from './components/common/Footer.vue';

</script>

<style scoped>

</style>