import {createApp} from 'vue'
import './style.css'
import App from './App.vue'

import api from './api'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./router/index.js"

createApp(App)
    .use(router)
    .use(ElementPlus)
    .provide("$api", api)
    .mount('#app')

const el = document.documentElement
getComputedStyle(el).getPropertyValue(`--el-color-primary`)
el.style.setProperty('--el-color-primary', '#6579bb')