<template>
  <div>
    <div class="container">
      <van-uploader
        class="custom-uploader"
        v-model="fileList"
        multiple
        :max-count="1"
        :preview-size="140"
      >
        <template #default>
          <van-icon name="plus" class="van-icon" />
        </template>
      </van-uploader>
    </div>
    <div
      style="
        margin-top: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
      "
    >
      <van-button type="primary" @click="handleUpload" style="width: 140px"
        >上传</van-button
      >
    </div>
  </div>
</template>

<script setup lang="ts">
import { showFailToast, showSuccessToast } from "vant";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
import { getCurrentUser } from "../services/user";
const router = useRouter();
const fileList = ref([]);
const handleUpload = async () => {
  console.log(fileList.value[0].file);
  const res = await myAxios.post(
    "/user/upload",
    {
      file: fileList.value[0].file,
    },
    {
      headers: { "Content-Type": "multipart/form-data" },
    }
  );
  console.log(res);
  if (res.data.code === 0) {
    showSuccessToast("上传成功");
    router.back();
  } else {
    showFailToast("上传失败");
  }
};
onMounted(async () => {
  const res = await getCurrentUser();
  if (res) {
    // showSuccessToast('获取用户信息成功');
    fileList.value = [
      {
        url: res.avatarUrl,
        file: null,
      },
    ];
    // console.log(res);
  } else {
    showFailToast("获取头像信息失败,请刷新");
  }
});
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.custom-uploader {
  margin-top: 20px;
  width: 140px; /* 设置宽度 */
  height: 140px; /* 设置高度 */
  background-color: #f9f9f9; /* 可选：修改背景颜色 */
}

.van-icon {
  color: grey;
  font-size: 140px;
}
</style>