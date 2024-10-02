import { Configuration, PostsApi, UserTokenDto, UsersApi } from './codegen'

const configuration = (token?: UserTokenDto) => {
    return new Configuration({
        basePath: process.env.REACT_APP_SERVICE_URL,
        accessToken: token?.token,
    })
}

export const usersApi = () => {
    return new UsersApi(configuration())
}

export const postsApi = (token?: UserTokenDto) => {
    return new PostsApi(configuration(token))
}
