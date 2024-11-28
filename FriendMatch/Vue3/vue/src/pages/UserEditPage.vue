<template>
  <div>
    <van-form @submit="onSubmit">
      <van-field
        v-model="editUser.currentValue"
        :name="editUser.editKey"
        :label="editUser.editName"
        :placeholder="`请输入${editUser.editName}`"
      />
      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { showFailToast, showSuccessToast } from "vant";
import "vant/es/toast/style";
import { ref } from "vue";
import { useRoute } from "vue-router";
import myAxios from "../plugins/myAxios";
import { getCurrentUser } from "../services/user";
const route = useRoute();
const editUser: any = ref({
  editKey: route.query.editKey,
  currentValue: route.query.currentValue,
  editName: route.query.editName,
});
const onSubmit = async () => {
  //获取用户信息
  const currentUser = await getCurrentUser();

  if (!currentUser) {
    showFailToast("用户未登录");
    return;
  }

  const res = await myAxios.post("/user/update", {
    'id': currentUser.id,
    [editUser.value.editKey]: editUser.value.currentValue,
  });
  console.log(res);
  if (res.data.code === 0 && res.data.data > 0) {
    showSuccessToast("修改成功");
  } else {
    showFailToast("修改失败");
  }
};
</script>

<style scoped>
</style>