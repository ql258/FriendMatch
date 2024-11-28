<template>
  <div>
    <van-search
      v-model="searchText"
      placeholder="搜索队伍"
      clearable
      @search="onSearch"
    />
    <van-tabs v-model:active="active" @change="onTableChange">
      <van-tab title="公开" name="public"></van-tab>
      <van-tab title="加密" name="private"></van-tab>
    </van-tabs>
    <div style="margin-bottom: 16px"></div>
    <!-- <van-button class="add-button" type="primary" icon="plus" @click="JoinTeam"></van-button> -->
    <team-card-list :teamList="teamList" />
    <van-empty v-if="teamList.length < 1" description="数据为空" />
  </div>
</template>
  
  <script setup lang="ts">
import { showFailToast } from "vant";
import { onMounted, ref } from "vue";
// import { useRouter } from 'vue-router';
import TeamCardList from "../components/TeamCardList.vue";
import myAxios from "../plugins/myAxios";
// const router = useRouter();
const searchText = ref("");
// const JoinTeam = () => {
//     router.push('/team/add');
// }
const active = ref('public');
const onTableChange = (name: string) => {
  if (name === "public") {
    listTeam(searchText.value, 0);
  } else {
    listTeam(searchText.value, 2);
  }
};
const listTeam = async (val = "", status = 0) => {
  const res = await myAxios.get("/team/list/my/create", {
    params: {
      pageSize: 8,
      pageNum: 1,
      searchText: val,
      status,
    },
  });
  console.log(res);
  if (res.data.code === 0) {
    teamList.value = res.data.data;
  } else {
    showFailToast("加载队伍失败，请刷新");
  }
};

const teamList = ref([]);
onMounted(async () => {
  listTeam();
});
const onSearch = (val: string) => {
  listTeam(val);
};
</script>
  
  <style scoped>
</style>