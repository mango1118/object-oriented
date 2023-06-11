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
            {path: 'self', name:'student个人页面', component:()=> import('../views/others/own/StudentSelfView')},
            //系统功能
            // {path: 'studentPaperSearch', name:'student试卷查询', component:() => import('../views/controller/student/StudentPaperSearchView')},
            {path: 'studentScoreSearch', name:'student成绩查询', component:() => import('../views/controller/student/StudentScoreView')},
            {path: 'studentSelectExam', name: 'student挑选考试', component:() => import('../views/controller/student/StudentSelectExamView')},
            {path: 'studentExam', name: 'student考试页面', component:() => import('../views/controller/student/StudentExamView')},

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
            {path: 'self', name:'student个人页面', component:()=> import('../views/others/own/TeacherSelfView')},
            //系统功能
            {path: 'teacherPaperSearch', name:'teacher试卷查询', component:() => import('../views/controller/teacher/TeacherPaperSearchView')},
            {path: 'teacherQuestionControl', name:'teacher题目管理', component:() => import('../views/controller/teacher/QuestionControlView')},
            {path: 'teacherQuestionProperty', name:'teacher题目属性', component:() => import('../views/controller/teacher/QuestionPropertyView')},
            {path: 'teacherAutoCompose', name:'teacher自动组卷', component:() => import('../views/controller/teacher/AutoComposeView')},
            {path: 'teacherManualCompose', name:'teacher手动组卷', component:() => import('../views/controller/teacher/ManualComposeView')},
            {path: 'teacherCorrectAnswer', name:'teacher改卷功能', component:() => import('../views/controller/teacher/CorrectQuestionView')},
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
