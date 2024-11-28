<template>
<div id="teamPage">
  <van-button type="primary" @click="JoinTeam">加入队伍</van-button>
</div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();

const JoinTeam = () => {
    router.push('/team/add');
};
</script>

<style scoped>

</style>