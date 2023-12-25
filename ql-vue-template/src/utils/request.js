import axios from 'axios'
import router from '../router'

const request = axios.create({
    baseURL: '/api',
    timeoutL: 10000
})
request.defaults.withCredentials = true
request.defaults.headers.post['Content-Type'] = 'application/json'

request.interceptors.request.use(config => {
        const token = sessionStorage.getItem('token')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    async (error) => {
        return await Promise.reject(error)
    }
)

request.interceptors.response.use(
    async (response) => {
        if (response.status === 200) {
            if (response.data.code === 401) {
                const username = localStorage.getItem('username')
                const password = localStorage.getItem('password')
                if (username !== null && password !== null) {
                    sessionStorage.removeItem('token')
                    await request({
                        method: 'POST',
                        url: '/login',
                        data: JSON.stringify({
                            username: username,
                            password: password
                        })
                    }).then(async (res) => {
                            if (res.data.code === 200) {
                                sessionStorage.setItem('token', res.data.data)
                            } else {
                                await router.push('/login')
                            }
                        }).catch(err => {
                            console.log(err.message)
                        })
                    return await request.request(response.config)
                } else {
                    console.log(response.data.code, response.data.msg)
                    sessionStorage.clear()
                    await router.push('/login')
                }
            }
        }
        return response
    },
    async (error) => {
        return await Promise.reject(error)
    }
)

export default request