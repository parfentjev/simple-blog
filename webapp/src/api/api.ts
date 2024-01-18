import axios from 'axios'
import { TokenDto } from './models/user'

export const api = () =>
    axios.create({
        baseURL: process.env.REACT_APP_SERVICE_URL,
    })

export const authApi = (token: TokenDto) =>
    axios.create({
        baseURL: process.env.REACT_APP_SERVICE_URL,
        headers: { Authorization: 'Brearer ' + token.token },
    })
