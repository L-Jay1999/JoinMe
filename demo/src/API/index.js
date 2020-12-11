import axios from "axios";


const instance = axios.create({   
    timeout: 10000, // 设置超时时间10s
    baseURL: '52.250.51.146:8080'  
})


instance.defaults.headers.post['Content-Type'] = 'application/json'

/* 统一封装get请求 */
export const get = (url, params, config = {}) => {
    return new Promise((resolve, reject) => {
        instance({
            method: 'get',
            url,
            params,
            ...config
        }).then(response => {
            resolve(response)
        }).catch(error => {
            reject(error)
        })
    })
}

/* 统一封装post请求  */
export const post = (url, data, config = {}) => {
    return new Promise((resolve, reject) => {
        instance({
            method: 'post',
            url,
            data,
            ...config
        }).then(response => {
            resolve(response);
            alert(response);
        }).catch(error => {
            reject(error);
            alert(error);
        })
    })
}