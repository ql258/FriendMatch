<template>
<div v-if="user">
    <van-cell title="昵称" is-link to="/user/edit" :value="user.username"  @click="toEdit('username', '昵称', user.username)"/>
    <van-cell title="账号" :value="user.userAccount"/>
    <van-cell title="头像"  to="/user/upload">
      <img style="height: 48px" :src="user.avatarUrl"/>
    </van-cell>
    <van-cell title="留言" is-link :value="user.profile" @click="toEdit('profile', '留言', user.profile)" />
    <van-cell title="性别" v-if="user.gender !== null && user.gender !== undefined" is-link :value="user.gender===1?'男':'女'" @click="toSelect('gender', '性别', user.gender)"/>
     <van-cell title="性别" v-else is-link :value="user.gender" @click="toSelect('gender', '性别', user.gender)"/>
    <van-cell title="电话" is-link to="/user/edit" :value="user.phone" @click="toEdit('phone', '电话', user.phone)"/>
    <van-cell title="邮箱" is-link to="/user/edit" :value="user.email" @click="toEdit('email', '邮箱', user.email)"/>
    <van-cell title="注册时间" :value="formatDate(user.createTime)"/>

</div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser } from "../services/user";
const user = ref();

onMounted(async () => {
  user.value = await getCurrentUser();
  console.log(user.value)
})

const router = useRouter();

const toEdit = (editKey: string, editName: string, currentValue: string) => {
  router.push({
    path: '/user/edit',
    query: {
      editKey,
      editName,
      currentValue,
    }
  })
}
const toSelect = (editKey: string, editName: string, currentValue: number) => {
  router.push({
    path: '/gender/select',
    query: {
      editKey,
      editName,
      currentValue,
    }
  })
}
// 格式化日期的函数
const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleString(); // 返回本地化的日期字符串
};
</script>

<style scoped>

</style>