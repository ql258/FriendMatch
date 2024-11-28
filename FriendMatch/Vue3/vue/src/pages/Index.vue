<template>
  <div>
    <van-cell center title="心动模式">
      <template #right-icon>
        <van-switch v-model="isMatchMode" size="24" />
      </template>
    </van-cell>
    <user-card-list :user-list="userList" :loading="loading" />
    <van-empty v-if="!userList || userList.length < 1" description="数据为空" />
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import { useRoute } from "vue-router";

import { showFailToast, showSuccessToast } from "vant";
import UserCardList from "../components/UserCardList.vue";
import { UserType } from "../models/user";
import myAxios from "../plugins/myAxios";
const route = useRoute();
const { tags } = route.query;

const userList = ref([]);

const isMatchMode = ref<boolean>(false);
const loading = ref(true);

/**
 * 加载数据
 */
const loadData = async () => {
  let userListData;
  loading.value = true;
  //心动模式
  if (isMatchMode.value) {
    const num = 10;
    userListData = await myAxios
      .get("user/match", {
        params: {
          num,
        },
      })
      .then(function (response) {
        console.log("/user/match succeed", response);

        if (response.data.code === 0) {
          showSuccessToast("请求成功");
          return response?.data.data;
        }
        else {
          showFailToast(response.data.description || "请求失败");
          // isMatchMode.value = false;
          return [];
        }
        
      })
      .catch(function (error) {
        console.log("/user/match error", error);
        showFailToast("请求失败");
      });
  } else {
    //普通用户使用分页查询
    userListData = await myAxios
      .get("/user/recommend", {
        params: {
          pageSize: 8,
          pageNum: 1,
        },
      })
      .then(function (response) {
        console.log("/user/recommend succeed", response);
        showSuccessToast("请求成功");
        return response?.data?.data.records;
      })
      .catch(function (error) {
        console.log("/user/recommends error", error);
        showFailToast("请求失败");
      });
  }
  if (userListData) {
    userListData.forEach((user: UserType) => {
      // if (user.tags) {
      //   user.tags = JSON.parse(user.tags);
      // }
      if (user.tags) {
        // 检查 user.tags 是否是字符串，并且如果是，则解析它
        if (typeof user.tags === "string") {
          user.tags = JSON.parse(user.tags);
        } else if (Array.isArray(user.tags)) {
          // 如果已经是数组，则不需要解析
          // 这里可以根据需要处理用户标签的逻辑
        } else {
          console.warn("user.tags is neither a string nor an array", user.tags);
        }
      }
    });
    userList.value = userListData;
  }
  loading.value = false;
};

watchEffect(() => {
  loadData();
});
</script>

<style scoped></style>
