import axios from 'axios'
const myAxios = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000
})
myAxios.defaults.withCredentials = true
//拦截器
//请求拦截器
myAxios.interceptors.request.use(config => {
  // console.log(config)
  return config
}, error => {
  console.log(error)
  return Promise.reject(error)
})
//响应拦截器
myAxios.interceptors.response.use(response => {
  // console.log(response)
  if (response.data.code === 40101) {
    const redirectUrl = window.location.href
    window.location.href = `/user/login?redirect=${redirectUrl}`;
  }
  return response
}, error => {
  console.log(error)
  return Promise.reject(error)
})
export default myAxios