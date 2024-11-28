<template>
  <div>
    <form action="/">
    <van-search
      v-model="searchText"
      show-action
      placeholder="请输入搜索标签"
      @search="onSearch"
    @cancel="onCancel"
    />
  </form>
  <van-divider content-position="left">已选标签</van-divider>
  <div v-if="length === 0">请选择标签</div>
  <van-row gutter="16" style="padding: 0 16px">
    <van-col v-for="tag in activeIds" :key="tag">
      <van-tag  closeable size="medium" type="primary" @close="onClose(tag)">
      {{ tag }}
    </van-tag>
  </van-col>
</van-row>

    <!-- <van-divider content-position="left">已选标签</van-divider> -->
    <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="tagList"
    />
    <div style="padding: 16px">
        <van-button type="primary" block @click="doChange">修改</van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
import {showSuccessToast,showFailToast} from "vant";
const router = useRouter();
const searchText = ref("");
//已选中的标签
const activeIds = ref([]);
const activeIndex = ref(0);
//标签列表
let tagList = ref([]);
onMounted( () => {
  // const res = await myAxios.post("/tag/list",{
  //    params: {
  //      name: '' // 传递空字符串作为name参数
  //    }
  // });
  // // console.log(res);
  // tagList.value = res.data.data;
  showTag();
  onSearch();
  console.log(tagList.value);
  console.log(activeIds.value);
})
const length =ref(0)
const onSearch = async () => {
   const nameValue = searchText.value.trim(); // 去除空格
    const res = await myAxios.post("/tag/list", null, {
        params: {
            name: nameValue // 将name参数作为查询参数传递
        }
    });
    console.log(res);
    tagList.value = res.data.data;
    length.value = res.data.data.length;
     // 更新标签列表
    //  console.log
};




const onClose = (tag: any) => {
  activeIds.value = activeIds.value.filter((item) => {
    return item !== tag;
  });
};
// const doSearch = () => {
//   router.push({
//     path: "/user/list",
//     query: {
//       tags: activeIds.value
//     }
//   })
// }
// const onConfirm = () => {
//     console.log(11);
// }
const showTag = async()=>{
   const res =await myAxios.get("/tag/show")
activeIds.value = res.data.data;
if(res.data.data==null) {
  activeIds.value = [];
}
// console.log(res.data.data);
}
const onCancel = () => {
    searchText.value = '';
  onSearch();
  }
  const doChange = async() => {
    const activeIdsLength = Array.isArray(activeIds.value) ? activeIds.value.length : 0
    if(activeIdsLength > 10) {
      showSuccessToast('最多只能选择10个标签');
      onSearch();
      return;
    }
  
    const res = await myAxios.put("/tag/update", {
      tags: activeIds.value
    })
    if(res.data.code === 0) {
      showSuccessToast('修改成功');
      onSearch();
    }
    else {
      showFailToast('修改失败');
      onSearch();
    }
    
  }
</script>

<style scoped>
</style>