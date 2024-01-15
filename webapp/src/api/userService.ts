import api from './api'
import { PostUsersTokenRequest, TokenDto } from './models/user'

export const postUsersToken = async (request: PostUsersTokenRequest) => {
    return api
        .post<TokenDto>('/users/token', request)
        .then((response) => response.data)
}
