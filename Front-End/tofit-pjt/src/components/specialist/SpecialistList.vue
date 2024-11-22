<template>
    <div>
        <h4>전문가 목록</h4>
        <p v-for="spe in store.specialistList">
            {{ spe.specialistId }}
        </p>
    </div>
</template>

<script setup>
import { useSpecialistStore } from '@/stores/specialist';
import { onMounted, ref } from 'vue';

const store = useSpecialistStore();

const searchInfo = ref({
    keyWord: '',
});

const searchSpecialistList = function(){
    store.getSpecialistList(searchInfo.value);
}

onMounted(()=>{
    searchSpecialistList();
})
</script>

<style scoped>

</style>