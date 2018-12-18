//引入axios
import axios from 'axios'
import env from './build/env'
import Qs from 'qs'
import {router} from './router/index';
import Cookies from 'js-cookie'

axios.defaults.withCredentials = true

axios.interceptors.request.use(request => {
    // before
    if (env === 'development') {
        request.headers.env = env
    }
    return request;
}, err => {
    return Promise.reject(err);
});

axios.defaults.paramsSerializer = params => {
    return Qs.stringify(params, {arrayFormat: 'repeat'})
};

axios.interceptors.response.use(response => {
    //after
    return response;
}, error => {
    if (error.response) {
        switch (error.response.status) {
            case 403:
                localStorage.clear();
                Cookies.set('Auth-Token', '');
                router.push({
                    name: 'login'
                });
        }
    }
    return Promise.reject(error.response.data);
});

if (env === 'development') {
    axios.defaults.baseURL = 'http://localhost:7070'
}

export const axiosUse = function (vue) {
    vue.prototype.$http = axios
}
