<template>
  <div id="teamPage">
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="addTeamData.name"
          name="name"
          label="队伍名"
          placeholder="请输入队伍名"
          :rules="[{ required: true, message: '请输入队伍名' }]"
        />
        <van-field
          v-model="addTeamData.description"
          rows="4"
          autosize
          label="队伍描述"
          type="textarea"
          placeholder="请输入队伍描述"
          style="height: 80px"
        />
        <van-uploader
          v-model="fileList"
          multiple
          :max-count="1"
          style="margin-left: 20px"
          :after-read="afterRead"
        />

        <van-field
          is-link
          readonly
          name="datetimePicker"
          label="过期时间"
          :placeholder="date ?? '点击选择过期时间'"
          @click="showPicker = true"
        />
        <van-popup v-model:show="showPicker" position="bottom">
          <van-date-picker
            v-model="date"
            @confirm="showPicker = false"
            type="datetime"
            title="请选择过期时间"
            :min-date="minDate"
          />
        </van-popup>
        <van-field name="radio" label="队伍状态">
          <template #input>
            <van-radio-group
              v-model="addTeamData.status"
              direction="horizontal"
            >
              <van-radio :name="0">公开</van-radio>
              <van-radio :name="1">私有</van-radio>
              <van-radio :name="2">加密</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field
          v-if="Number(addTeamData.status) === 2"
          v-model="addTeamData.password"
          :type="show ? 'text' : 'password'"
          name="password"
          label="密码"
          placeholder="请输入队伍密码"
          :rules="[{ required: true, message: '请填写密码' }]"
        >
          <template #right-icon>
            <van-icon name="eye-o" v-if="!show" @click="show = !show" />
            <van-icon name="closed-eye" v-else @click="show = !show" />
          </template>
        </van-field>
      </van-cell-group>
      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>
  
  <script setup>
import { showFailToast, showSuccessToast } from "vant";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
const router = useRouter();
const route = useRoute();
const fileList = ref([]);
// 展示日期选择器
const showPicker = ref(false);
const minDate = new Date();
const show = ref(false);

const addTeamData = ref({});
const date = ref();
const id = route.query.id;
//获取之前的队伍信息
onMounted(async () => {
  if (id <= 0) {
    showFailToast("加载队伍失败，请重试");
    return;
  }
  const res = await myAxios.get("/team/get", {
    params: {
      id,
    },
  });
  console.log(res.data);
  if (res.data?.code === 0) {
    addTeamData.value = res.data.data;
    // date.value =   new Date(addTeamData.value.expireTime[0], addTeamData.value.expireTime[1] - 1,addTeamData.value.expireTime[2])
    date.value = addTeamData.value.expireTime.split("T")[0].split("-");
    //  console.log(dateArr);
    fileList.value = [
      {
        url: res.data.data.imgUrl || "",
        file: null,
      },
    ];
  } else {
    showFailToast("加载队伍失败，请重试");
  }
});
const afterRead = async (file) => {
  console.log(file);
  // console.log(fileList.value[0].file);
  const res = await myAxios.post(
    "/team/upload",
    {
      file: file.file,
    },
    {
      headers: { "Content-Type": "multipart/form-data" },
    }
  );
  console.log(res);
  if (res.data.code === 0) {
    showSuccessToast("上传成功");
    addTeamData.value.imgUrl = res.data.data;
    // router.back();
  } else {
    showFailToast("上传失败");
  }
};

//提交
const onSubmit = async () => {
  const postData = {
    ...addTeamData.value,
    status: Number(addTeamData.value.status),
    expireTime: new Date(date.value[0], date.value[1], date.value[2]),
    // imgUrl: fileList.value[0].objectUrl.replace(/^blob:/, '')
    //  expireTime: new Date(addTeamData.value.expireTime[0], addTeamData.value.expireTime[1] - 1,addTeamData.value.expireTime[2]),
  };
  console.log(postData);
  //todo 前端数据校验
  const res = await myAxios.post("/team/update", postData);
  if (res.data?.code === 0 && res.data.data) {
    showSuccessToast("更新成功");
    router.push({
      path: "/team",
      replace: true,
    });
  } else {
    showFailToast("更新失败");
  }
};
</script>
  
  <style scoped>
</style>