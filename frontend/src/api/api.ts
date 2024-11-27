import { Configuration, MediaApi, PostsApi, TokenDto, UsersApi } from './codegen'

const configuration = (token?: TokenDto) => {
    return new Configuration({
        basePath: process.env.REACT_APP_SERVICE_URL,
        accessToken: token?.token,
    })
}

export const usersApi = () => {
    return new UsersApi(configuration())
}

export const postsApi = (token?: TokenDto) => {
    return new PostsApi(configuration(token))
}

export const mediaApi = (token?: TokenDto) => {
    return new MediaApi(configuration(token))
}
