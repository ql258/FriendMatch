<template>
  <div>
    <van-nav-bar
      :title="title"
      right-text="按钮"
      left-arrow
      @click-left="onClickedLeft"
      @click-right="onClickedRight"
    >
      <template #right>
        <van-icon name="search" size="18" />
      </template>
    </van-nav-bar>

    <div id="content">
      <router-view />
    </div>

    <van-tabbar route v-model="active">
      <van-tabbar-item to="/" icon="home-o" name="index">主页</van-tabbar-item>
      <van-tabbar-item to="/team" icon="search" name="team"
        >队伍</van-tabbar-item
      >
      <van-tabbar-item to="/user" icon="friends-o" name="user"
        >个人</van-tabbar-item
      >
    </van-tabbar>
  </div>
</template>

<script setup lang="ts">
import 'vant/es/toast/style';
import { ref } from "vue";
import { useRouter } from "vue-router";
import routes from '../config/router';

const router = useRouter();

const DEFAULT_TITLE="伙伴匹配"
const title = ref(DEFAULT_TITLE)
router.beforeEach((to) => {
  const toPath=to.path
  const route = routes.find((route)=>{
    return toPath == route.path
  })
  title.value = route?.title ?? DEFAULT_TITLE
})
const onClickedLeft =() => {
  router.back();
  // clickHandler.leftClick();
};
const onClickedRight = () => {
  router.push("/search");
  // clickHandler.rightClick();
};
const active = ref("index");
// const onChange = (index: any) => {
//   showToast(`标签${index}`);
// };
</script>

<style scoped>
#content {
  padding-bottom: 50px;
}
</style>