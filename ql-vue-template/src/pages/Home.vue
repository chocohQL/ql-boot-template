<script setup>
import { inject } from "vue";
import {ElMessage} from "element-plus";
import router from "../router/index.js";

const $api = inject('$api');
const logout = () => {
  $api.user.logout().then(resp => {
    if (resp.data.code === 200) {
      localStorage.clear()
      sessionStorage.clear()
      ElMessage.success("退出登录成功")
      router.push('/login')
    }
  })
}
$api.user.me()
</script>

<template>
  <div class="top"/>
  <el-row>
    <el-col :span="8"/>
    <el-col :span="8">
      <h1 class="title">ql-boot-template</h1>
      <div class="container">
         <el-button class="logout" type="primary" @click="logout">logout</el-button>
       </div>
    </el-col>
    <el-col :span="8"/>
  </el-row>
</template>

<style scoped>
.title {
  text-align: center;
}

.container {
  display: flex;
  justify-content: center;
}

.top {
  min-height: 200px;
}
</style>