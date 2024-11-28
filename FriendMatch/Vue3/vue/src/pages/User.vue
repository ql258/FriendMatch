<template>
  <div>
  <template v-if="user">
    <van-cell title="昵称" is-link to="/user/edit" :value="user.username" @click="toEdit('username','昵称',user.username)"/>
    <van-cell title="账户" :value="user.userAccount" />
    <van-cell title="头像" to="/user/upload" >
      <img style="height: 48px;" :src="user.avatarUrl">
    </van-cell>
    <!-- <van-cell title="性别" is-link to="/user/edit" :value="user.gender" @click="toEdit('gender','性别',user.gender)" />
    <van-cell title="电话" is-link to="/user/edit" :value="user.phone" @click="toEdit('phone','电话',user.phone)"/>
    <van-cell title="邮箱" is-link to="/user/edit" :value="user.email" @click="toEdit('email','邮箱',user.email)"/>
    <van-cell title="注册时间" :value="user.createTime"  /> -->
    <van-cell title="修改标签" is-link to="/user/tag" />
    <van-cell title="修改信息" is-link to="/user/update" />
    <van-cell title="我创建的队伍" is-link to="/team/create" />
    <van-cell title="我加入的队伍" is-link to="/team/join" />
    <van-notice-bar v-if="user.username === ''&&user.tag === ''"
  left-icon="volume-o"
  text="请完善昵称与标签信息，否则无法被其他用户匹配。"
  style="margin-top: 10px;"
/>

  </template>
</div>
</template>

<script setup lang="ts">

import { showFailToast, showSuccessToast } from "vant";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { getCurrentUser } from "../services/user";
// const user = {
//   id : 9527,
//   username : 'shayu',
//   userAccount : '931',
//   avatarUrl : 'https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/shayu931/shayu.png',
//   gender : '男',
//   phone : '430821',
//   email : 'shayu-yusha@qq.com',
//   planetCode : '931',
//   createTime : new Date(),
// }
const user =ref();
const router = useRouter();

onMounted(async () =>{
  const res = await getCurrentUser();
  if (res){
    user.value = res;
    showSuccessToast('获取用户信息成功');
  } else {
    showFailToast('获取用户信息失败')
  }
  // console.log(user.value)
})

const toEdit = (editKey: string,editName: string,currentValue: string) =>{
  router.push({
    path:'/user/edit',
    query: {
      editKey,
      editName,
      currentValue,
    }
  })
}

</script>

<style scoped>

</style>