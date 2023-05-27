import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: "login",
        component:() => import("../views/others/Login")
    },
    {
        path:"/",
        redirect:"/login"
    },
    {
        path:"/student",
        component: () => import('../views/others/StudentManager'),
        redirect: '/student/home',
        children: [
            //主页
            {path: 'home', name:'student首页', component: () => import('../views/others/Home')},
            //个人功能

            //系统功能

        ]
    },
    {
        path:"/teacher",
        component: () => import('../views/others/TeacherManager'),
        redirect: '/teacher/home',
        children: [
            //主页
            {path: 'home', name:'teacher首页', component: () => import('../views/others/Home')},
            //个人功能

            //系统功能

        ]
    }



]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
