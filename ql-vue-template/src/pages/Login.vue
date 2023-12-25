<script setup>
import { inject, reactive } from "vue";
import {ElMessage} from 'element-plus'
import router from "../router/index.js";

const $api = inject('$api');
const form = reactive({
  password: "",
  username: "",
})
const onSubmit = () => {
  if (form.password === '' || form.username === '') {
    ElMessage.error("用户名或密码不能为空")
    return
  }
  sessionStorage.removeItem('token')
  $api.user.login({
    username: form.username,
    password: form.password
  }).then(resp => {
    if (resp.data.code === 200) {
      sessionStorage.setItem('token', resp.data.data)
      localStorage.setItem('username', form.username)
      localStorage.setItem('password', form.password)
      ElMessage.success("登录成功")
      router.push('/')
    } else {
      ElMessage.error(resp.data.msg)
    }
  })
}
</script>

<template>
  <div class="top"/>
  <el-row>
    <el-col :span="8"/>
    <el-col :span="8">
      <el-card class="box-card">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/login' }">Login</el-breadcrumb-item>
          <el-breadcrumb-item :to="{}">register</el-breadcrumb-item>
          <el-breadcrumb-item :to="{}">forgetPassword</el-breadcrumb-item>
        </el-breadcrumb>
        <h1 class="title">Login</h1>
        <div class="form">
          <el-input class="input" v-model="form.username" placeholder="username"/>
          <el-input class="input" v-model="form.password" type="password" placeholder="password" show-password/>
          <el-button @click="onSubmit" class="submit" type="primary" round>Login</el-button>
        </div>
      </el-card>
    </el-col>
    <el-col :span="8"/>
  </el-row>
</template>

<style scoped>
.top {
  min-height: 200px;
}

.box-card {
  border-radius: 15px;
  border-width: 0;
}

.title {
  text-align: center;
}

.input {
  margin-top: 5%;
}

.submit {
  width: 100%;
  margin-top: 5%;
  border-width: 0;
}
</style>