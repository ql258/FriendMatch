<template>
  <div>
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model.trim="userAccount"
          name="userAccount"
          label="账号"
          placeholder="请输入账号"
          :rules="[{ required: true, message: '请输入账号' }]"
        />
        <van-field
          v-model.trim="userPassword"
          name="userPassword"
          label="密码"
          placeholder="请输入密码"
          :type="show ? 'text' : 'password'"
          :rules="[{ required: true, message: '请输入密码' }]"
        >
          <template #right-icon>
            <van-icon name="eye-o" v-if="!show" @click="show = !show" />
            <van-icon name="closed-eye" v-else @click="show = !show" />
          </template>
        </van-field>
      </van-cell-group>
      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit"
          >登录</van-button
        >
      </div>
    </van-form>
    <div style="margin: 16px;display: flex;align-items: center;justify-content: space-between">
     <div>没有账号?请<span style="color: blue;cursor: pointer" @click="router.push('/user/register')">注册</span></div>
     <div style="color: pink;cursor: pointer" @click="router.push('/user/forget')">忘记密码</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { showFailToast, showSuccessToast } from "vant";

import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
const router = useRouter();
const route = useRoute();
const userAccount = ref("");
const userPassword = ref("");
const show = ref(false);
const onSubmit = async () => {
  const res = await myAxios.post("/user/login", {
    userAccount: userAccount.value,
    userPassword: userPassword.value,
  });
  console.log(res, "用户登录");
  if (res.data.code === 0) {
    showSuccessToast("登录成功");
    router.replace("/");
    const redirectUrl = route.query?.redirect as string ?? '/'
    window.location.href = redirectUrl
  } else {
    showFailToast(res.data.description);
  }
};
</script>

<style scoped>
</style>