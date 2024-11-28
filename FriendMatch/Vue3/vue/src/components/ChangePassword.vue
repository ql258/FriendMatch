<template>
  <div>
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model.trim="registerData.userAccount"
          name="userAccount"
          label="账号"
          placeholder="请输入账号"
          :rules="[{ required: true, message: '请输入账号' }]"
        />
        <van-field
          v-model.trim="registerData.userPassword"
          name="userPassword"
          :label="props.change === 'register' ? '密码' : '新密码'"
          placeholder="请输入密码"
          :type="show ? 'text' : 'password'"
          :rules="[{ required: true, message: '请输入密码' }]"
        >
          <template #right-icon>
            <van-icon name="eye-o" v-if="!show" @click="show = !show" />
            <van-icon name="closed-eye" v-else @click="show = !show" />
          </template>
        </van-field>
        <van-field
          v-model.trim="registerData.checkPassword"
          ref="checkPasswordRef"
          name="checkPassword"
          label="确认密码"
          placeholder="请再次输入密码"
          :type="checkShow ? 'text' : 'password'"
          :rules="[
            { required: true, message: '请再次输入密码' },
            { validator: validatorCheck },
          ]"
        >
          <template #right-icon>
            <van-icon
              name="eye-o"
              v-if="!checkShow"
              @click="checkShow = !checkShow"
            />
            <van-icon
              name="closed-eye"
              v-else
              @click="checkShow = !checkShow"
            />
          </template>
        </van-field>
      </van-cell-group>
      <div style="margin: 16px">
        <van-button
          round
          block
          type="primary"
          native-type="submit"
          v-if="props.change === 'register'"
          >注册账号</van-button
        >
        <van-button
          round
          block
          type="primary"
          native-type="submit"
          v-if="props.change === 'forget'"
          >修改密码</van-button
        >
      </div>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { showFailToast, showSuccessToast, showToast } from "vant";
import myAxios from "../plugins/myAxios";
import { useRouter } from "vue-router";
const props = defineProps<{
  change: string;
}>();
// console.log(props.change)

const checkPasswordRef = ref(null);
const registerData = ref({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
});

const show = ref(false);
const checkShow = ref(false);
const router = useRouter();

const validatorCheck = () => {
  new Promise<void>((resolve, reject) => {
    if (registerData.value.checkPassword !== registerData.value.userPassword) {
      // reject(new Error("两次输入密码不一致!")); // 如果两次输入密码不一致，返回错误
      showToast("两次输入密码不一致!");
    } else {
      resolve(); // 验证成功
    }
  }).catch((error) => {
    // console.log(error);
    return Promise.reject(error);
  });
};
const onSubmit = async () => {
  if (props.change === "register") {
    const res = await myAxios.post("/user/register", registerData.value);
    //  console.log(res);
    if (res.data.code === 0) {
      showSuccessToast("注册成功");
      router.push("/user/login");
      router;
    } else {
      showFailToast(res.data.description);
      registerData.value = {
        userAccount: "",
        userPassword: "",
        checkPassword: "",
      };
    }
  } else if (props.change === "forget") {
    const res = await myAxios.post("/user/changePassword", registerData.value);
    // console.log(res);
    if (res.data.code === 0) {
      showSuccessToast("修改成功");
      router.push("/user/login");
      router;
    } else {
      showFailToast(res.data.description);
      registerData.value = {
        userAccount: "",
        userPassword: "",
        checkPassword: "",
      };
    }
  }
};
</script>

<style scoped>
</style>