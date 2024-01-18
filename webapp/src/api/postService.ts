import { PostDto, PostPreviewDto } from './models/post'
import { api, authApi } from './api'
import { TokenDto } from './models/user'

export const getPosts = async () => {
    return api()
        .get<PostPreviewDto[]>('/posts')
        .then((response) => response.data)
}

export const getPublishedPost = async (id: string) => {
    return api()
        .get<PostDto>(`/posts/published/${id}`)
        .then((response) => response.data)
}

export const getPost = async (token: TokenDto, id: string) => {
    return authApi(token)
        .get<PostDto>(`/posts/${id}`)
        .then((response) => response.data)
}

export const postPost = async (token: TokenDto, post: PostDto) => {
    return authApi(token)
        .post<PostDto>(`/posts`, post)
        .then((response) => response.data)
}

export const putPost = async (token: TokenDto, id: string, post: PostDto) => {
    return authApi(token)
        .put<PostDto>(`/posts/${id}`, post)
        .then((response) => response.data)
}
