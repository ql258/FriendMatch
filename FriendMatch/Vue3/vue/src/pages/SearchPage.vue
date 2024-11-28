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
  <div v-if="activeIds.length === 0">请选择标签</div>
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
        <van-button type="primary" block @click="doSearch">搜索</van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
const router = useRouter();
const searchText = ref('');
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
  onSearch();
})
//  const onSearch = (val) => {
//    tagList.value = tagList.value.map((parentTag) => {
//      const tempChildren = [...parentTag.children];
//      const tempParentTag = { ...parentTag };
//      tempParentTag.children = tempChildren.filter((item) =>
//         typeof item.father === 'string' && item.father.includes(searchText.value)
//     );
//      return tempParentTag;
//    });
//  };

const onSearch = async () => {
   const nameValue = searchText.value.trim(); // 去除空格
    const res = await myAxios.post("/tag/list", null, {
        params: {
            name: nameValue // 将name参数作为查询参数传递
        }
    });
    console.log(res);
    tagList.value = res.data.data;
     // 更新标签列表
    //  console.log
};




const onClose = (tag: any) => {
  activeIds.value = activeIds.value.filter((item) => {
    return item !== tag;
  });

};
const doSearch = () => {
  router.push({
    path: "/user/list",
    query: {
      tags: activeIds.value
    }
  })
}
const onCancel = () => {
    searchText.value = '';
  onSearch();
  }
</script>

<style scoped>
</style>