<template>
  <div>
    <van-picker
      v-model="selectedValues"
      :columns="columns"
      @cancel="onCancel"
      @confirm="onConfirm"
    />
  </div>
</template>

<script setup lang="ts">
import { showFailToast, showSuccessToast } from "vant";
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
import { getCurrentUser } from "../services/user";
const route = useRoute();
const router = useRouter();
const key: any = ref({
  editKey: route.query.editKey,
  currentValue: route.query.currentValue,
  editName: route.query.editName,
});

const columns = [
  { text: "男", value: 1 },
  { text: "女", value: 0 },
];
const selectedValues = ref([1]);
// console.log(typeof key.value.currentValue)
//   user.value = await getCurrentUser();
if (key.value.currentValue != null) {
  // selectedValues.value = [1];
  selectedValues.value = [Number(key.value.currentValue)];
  // console.log("不为空")
}
if (key.value.currentValue == null) {
  // console.log("为空")
}
// console.log(key.value.currentValue)

// console.log(selectedValues.value)
const onCancel = () => {
  // console.log("取消");
  router.back();
};
const onConfirm = async () => {
  //获取用户信息
  const currentUser = await getCurrentUser();
  // console.log(selectedValues.value[0])
  if (!currentUser) {
    showFailToast("用户未登录");
    return;
  }

  const res = await myAxios.post("/user/update", {
    id: currentUser.id,
    gender: selectedValues.value[0],
  });
  // console.log(res);
  if (res.data.code === 0 && res.data.data > 0) {
    showSuccessToast("修改成功");
    router.back();
  } else {
    showFailToast("修改失败");
  }
};
</script>

<style scoped>
</style>