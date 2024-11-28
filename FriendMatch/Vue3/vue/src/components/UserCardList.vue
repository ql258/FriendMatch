<template>
<div>
  <van-skeleton title avatar :row="3" :loading="props.loading ">
    <van-card
      v-for="user in props.userList"
      :key="user.id"
      :desc="user.profile"
      :title="`${user.username}`"
      :thumb="user.avatarUrl"
    >
      <template #tags>
        <van-tag
          plain
          type="danger"
          v-for="(tag, index) in user.tags"
          :key="index"
          style="margin-right: 8px; margin-top: 8px"
        >
          {{ tag }}
        </van-tag>
      </template>
      <template #footer>
        <van-button size="mini">联系我</van-button>
      </template>
    </van-card>
  </van-skeleton>
</div>
</template>

<script setup lang="ts">
import { UserType } from '../models/user';
interface UserCardListProps {
  loading: boolean;
    userList: UserType[];
}
const props = withDefaults(defineProps<UserCardListProps>(), {
  loading:false,  
    userList: () => [] as UserType[],
});
</script>

<style scoped>

</style>