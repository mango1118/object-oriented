import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/reader',
        component: () => import('../views/others/ReaderManager.vue'),
        redirect: '/reader/home',
        children: [
            // 主页
            {path: 'home', name: 'reader首页', component: () => import(`../views/others/Home`)},
            {path: 'mybook', name: 'reader我的借阅', component: () => import(`../views/others/Readers/ReaderOwnBorrow`)},
            {path: 'self', name: 'reader个人页面', component: () => import(`../views/others/Readers/ReaderSelfView`)},
            // 查询功能
            // {path: 'bookSearch', component: () => import (`../views/search/BookView`)},
            {path: 'bookSearch', name: 'reader全部图书', component: () => import (`../views/search/ReaderBook`)},
            {path: 'borrowSearch', name: 'reader借阅查询', component: () => import (`../views/search/BorrowView`)},
            {path: 'putSearch', name: 'reader存放查询', component: () => import (`../views/search/PutView`)},
            {path: 'stackSearch',  name: 'reader书库查询', component: () => import (`../views/search/StackView`)},
            {path: 'bookStackSearch', name: 'reader图书查询', component: () => import(`../views/search/BookSearchView`)},
        ]
    },
    {
        path: '/admin',
        component: () => import('../views/others/AdminManager.vue'),
        redirect: '/admin/home',
        children: [
            // 主页
            {path: 'home', name: 'admin首页', component: () => import(`../views/others/Home`)},
            // 系统管理
            {path: 'bookControl', name: 'admin图书管理', component: () => import (`../views/controller/BookControlView`)},
            {path: 'readerControl', name: 'admin读者管理', component: () => import (`../views/controller/ReaderControlView`)},
            {path: 'borrowControl', name: 'admin借阅管理', component: () => import (`../views/controller/BorrowControlView`)},
            {path: 'putControl', name: 'admin存放管理', component: () => import (`../views/controller/PutControlView`)},
            {path: 'stackControl', name: 'admin书库管理', component: () => import (`../views/controller/StackControlView`)},
            {path: 'backupDatabase', name: 'admin数据库备份', component: () => import(`../views/controller/DataBaseBackUpView`)},
            // 查询功能
            {path: 'adminSearch', name: 'admin管理员查询', component: () => import (`../views/search/AdminView`)},
            {path: 'bookSearch', name: 'admin全部图书', component: () => import (`../views/search/BookView`)},
            {path: 'readerSearch', name: 'admin读者查询', component: () => import (`../views/search/ReaderView`)},
            {path: 'borrowSearch', name: 'admin借阅查询', component: () => import (`../views/search/BorrowView`)},
            {path: 'putSearch', name: 'admin存放查询', component: () => import (`../views/search/PutView`)},
            {path: 'stackSearch', name: 'admin书库查询', component: () => import (`../views/search/StackView`)},
            {path: 'superAdminSearch', name: 'admin超级管理员查询', component: () => import (`../views/search/SuperAdminView`)},
            {path: 'bookStackSearch', name: 'admin图书查询', component: () => import(`../views/search/BookSearchView`)},

        ]
    },
    {
        path: '/superadmin',
        component: () => import('../views/others/SuperAdminManager.vue'),
        redirect: '/superadmin/home',
        children: [
            // 主页
            {path: 'home', name: 'superadmin首页', component: () => import(`../views/others/Home`)},
            // 系统管理
            {path: 'adminControl', name: 'superadmin管理员管理', component: () => import(`../views/controller/AdminControlView`)},
            {path: 'bookControl', name: 'superadmin图书管理', component: () => import (`../views/controller/BookControlView`)},
            {path: 'readerControl', name: 'superadmin读者管理', component: () => import (`../views/controller/ReaderControlView`)},
            {path: 'borrowControl', name: 'superadmin借阅管理', component: () => import (`../views/controller/BorrowControlView`)},
            {path: 'putControl', name: 'superadmin存放管理', component: () => import (`../views/controller/PutControlView`)},
            {path: 'stackControl', name: 'superadmin书库管理', component: () => import (`../views/controller/StackControlView`)},
            {path: 'superAdminControl', name: 'superadmin超级管理员管理', component: () => import(`../views/controller/SuperAdminControlView`)},
            // {path: 'backupDatabase', name: 'superadmin数据库备份', component: () => import(`../views/controller/DataBaseBackUpView`)},
            // 查询功能
            {path: 'adminSearch', name: 'superadmin管理员查询', component: () => import (`../views/search/AdminView`)},
            {path: 'bookSearch', name: 'superadmin全部图书', component: () => import (`../views/search/BookView`)},
            {path: 'readerSearch', name: 'superadmin读者查询', component: () => import (`../views/search/ReaderView`)},
            {path: 'borrowSearch', name: 'superadmin借阅查询', component: () => import (`../views/search/BorrowView`)},
            {path: 'putSearch', name: 'superadmin存放查询', component: () => import (`../views/search/PutView`)},
            {path: 'stackSearch', name: 'superadmin书库查询', component: () => import (`../views/search/StackView`)},
            {path: 'superAdminSearch', name: 'superadmin超级管理员查询', component: () => import (`../views/search/SuperAdminView`)},
            {path: 'bookStackSearch', name: 'superadmin图书查询', component: () => import(`../views/search/BookSearchView`)},

        ]
    },
    {
        path: '/login',
        name: 'login',
        component:() => import(`../views/others/Login`),
    },
    {
        path: '/',
        redirect: '/login'
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
