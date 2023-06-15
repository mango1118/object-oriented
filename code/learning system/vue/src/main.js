import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import axios from "./utils/request"

Vue.config.productionTip = false
Vue.use(ElementUI, {size: "small"})


Vue.prototype.axios = axios;

localStorage.setItem("user", null)

// 路由守卫
// const WhiteListRouter = ['/login']
// router.beforeEach((to, from, next) => {
//     let user = store.
// })


new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
