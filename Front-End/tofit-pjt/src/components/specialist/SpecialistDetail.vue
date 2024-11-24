<template>
  <div>
    <h4>Specialist Detail</h4>
    <p>{{ store.specialistContentList[0].channelName }}</p>
  </div>
</template>

<script setup>
import { useSpecialistStore } from "@/stores/specialist";
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const store = useSpecialistStore();
const route = useRoute();

onMounted(() => {
  store.getSpecialistContentList(route.params.specialistId);
});
</script>

<style scoped></style>
