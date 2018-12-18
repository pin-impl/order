import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Labnetwork Ocean - Login'
    },
    component: () => import('@/views/login.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    access: ['ROLE_SITE_ADMIN', 'ROLE_CSR', 'ROLE_SUPPLIER_ADMIN'],
    component: Main,
    children: [
        {
            path: 'home',
            title: {i18n: 'home'},
            name: 'home_index',
            component: () => import('@/views/home/home.vue')
        }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/order',
        icon: 'key',
        name: 'order',
        title: 'Order Manage',
        component: Main,
        children: [
            {
                path: 'list',
                title: 'Order Manage',
                name: 'edit',
                component: () => import('@/views/order/manage/order.vue')
            }
        ]
    },
    {
        path: '/order/item',
        icon: '',
        name: 'Order Item Manage',
        title: 'Item Manage',
        component: Main,
        children: [
            {
                path: 'list',
                title: 'Item Manage',
                name: 'item-edit',
                component: () => import('@/views/order/manage/order-item.vue')
            }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    locking,
    ...appRouter
];
